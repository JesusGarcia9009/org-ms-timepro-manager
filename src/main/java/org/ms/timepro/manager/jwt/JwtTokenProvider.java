package org.ms.timepro.manager.jwt;

import java.io.IOException;
import java.security.SignatureException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.ms.timepro.manager.exception.UserNotAuthException;
import org.ms.timepro.manager.log.Loguer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JwtTokenProvider - Spring Boot main class
 *
 * @author Jesus Garcia
 * @since 0.0.1
 * @version jdk-21
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final ObjectMapper mapper = new ObjectMapper();

    @Value("${api.config.key}")
    private String apiKey;

    private static final String AUTHORITIES = "authorities";
    private static final String ROLES = "roles";
    private static final String ID_USUARIO = "idUsuario";
    private static final String FULL_NAME = "fullName";
    private static final String EMAIL = "email";
    private static final String USERNAME = "username";
    private static final String RUT = "rut";
    private static final String USERINPUT = "userinput";
    private static final String MFA_KEY = "mfaKey";
    private static final String IS_MFA = "isMfa";
    private static final String ISBLOCKED = "isBLocked";

    @Loguer
    public String generateToken(JwtUserPrincipal userPrincipal, String apiKey) throws IOException {
        Collection<? extends GrantedAuthority> permisos = userPrincipal.getAuthorities();

        Map<String, Object> claims = Map.of(
            ROLES, userPrincipal.getListaPerfiles(),
            ID_USUARIO, userPrincipal.getIdUsuario(),
            FULL_NAME, userPrincipal.getFullName(),
            EMAIL, userPrincipal.getDscEmail(),
            USERNAME, userPrincipal.getUsername(),
            RUT, userPrincipal.getRut(),
            USERINPUT, userPrincipal.getUserInput(),
            MFA_KEY, userPrincipal.getMfaKey(),
            IS_MFA, userPrincipal.getIsMfa(),
            ISBLOCKED, userPrincipal.getIsBlocked()
        );
        
        claims.put(AUTHORITIES, permisos.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));

        byte[] keyBytes = Decoders.BASE64.decode(apiKey);

        return Jwts.builder()
                .claims(claims)
                .subject(userPrincipal.getIdUsuario().toString())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(Keys.hmacShaKeyFor(keyBytes), Jwts.SIG.HS256)
                .compact();
    }

    @Loguer
    public JwtUserPrincipal getUserPrincipalFromToken(String token) throws IOException {
        Claims claims = getClaims(token);
        JwtUserPrincipal userPrincipal = new JwtUserPrincipal();

        userPrincipal.setAuthorities(this.getAuthorityList(token));
        userPrincipal.setListaPerfiles(this.getRoles(token));

        // Set properties based on claims
        userPrincipal.setFullName((String) findKeyClaimsInData(FULL_NAME, claims));
        userPrincipal.setDscEmail((String) findKeyClaimsInData(EMAIL, claims));
        userPrincipal.setUsername((String) findKeyClaimsInData(USERNAME, claims));
        userPrincipal.setRut((String) findKeyClaimsInData(RUT, claims));
        userPrincipal.setUserInput((String) findKeyClaimsInData(USERINPUT, claims));
        userPrincipal.setMfaKey((String) findKeyClaimsInData(MFA_KEY, claims));
        userPrincipal.setIdUsuario(((Integer) findKeyClaimsInData(ID_USUARIO, claims)).longValue());

        return userPrincipal;
    }

    @Loguer
    public String getJwtSecretByKey() {
        return Base64.getEncoder().encodeToString(apiKey.getBytes());
    }

    @Loguer
    public boolean validateToken(String authToken) throws IOException, SignatureException {
        try {
            SecretKey secretKey = new SecretKeySpec(Base64.getDecoder().decode(apiKey), "HmacSHA256");
            Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(authToken)
            .getPayload();
            return Boolean.TRUE;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
            throw new MalformedJwtException("Error validating JWT token", ex);
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
            throw new UnsupportedJwtException("Error validating JWT token", ex);
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
            throw new IllegalArgumentException("Error validating JWT token", ex);
        }
        return Boolean.FALSE;
    }

    @Loguer
    public Collection<? extends GrantedAuthority> getAuthorityList(String token) throws IOException {
        String auth = (String) getDataByKeyClaims(AUTHORITIES, token);

        if (Objects.nonNull(auth) && !auth.isEmpty() && !auth.equalsIgnoreCase("null")) {
            mapper.addMixIn(SimpleGrantedAuthority.class, JwtSimpleGrantedAuthorityMixin.class);
            return Arrays.asList(mapper.readValue(auth, SimpleGrantedAuthority[].class));
        }
        return Collections.emptyList();
    }

    @Loguer
    public List<JwtProfile> getRoles(String token) throws IOException {
        String profileList = (String) getDataByKeyClaims(ROLES, token);
        if (Objects.nonNull(profileList) && !profileList.equals("null"))
            return Arrays.asList(mapper.readValue(profileList, JwtProfile[].class));

        return Collections.emptyList();
    }

    @Loguer
    public String generateToken(Authentication authentication) throws UserNotAuthException {
        String jwt = "";
        try {
            jwt = generateToken((JwtUserPrincipal) authentication.getPrincipal(), apiKey);
        } catch (IOException e) {
            throw new UserNotAuthException("no se puede generar token");
        }
        return jwt;
    }

    @Loguer
    private Object getDataByKeyClaims(String key, String token) {
        Claims claims = getClaims(token);
        return findKeyClaimsInData(key, claims);
    }

    @Loguer
    private Object findKeyClaimsInData(String key, Claims claims) {
        return claims.get(key, Object.class);
    }

    @Loguer
    private Long getUserIdFromJWT(String token) {
        Claims claims = getClaims(token);
        return Long.parseLong(claims.getSubject());
    }

    @Loguer
    private Claims getClaims(String token) {
    	SecretKey secretKey = new SecretKeySpec(Base64.getDecoder().decode(apiKey), "HmacSHA256");
        
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
