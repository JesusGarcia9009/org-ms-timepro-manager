package org.ms.timepro.manager.services;


import org.jwt.security.dto.JwtProfile;
import org.jwt.security.dto.JwtUserPrincipal;
import org.jwt.security.repository.UserRepository;
import org.ms.timepro.manager.log.Logger;
import org.ms.timepro.manager.utils.ConstantUtil;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsJisusService implements UserDetailsService {

	private final UserRepository userRepository;

	@Logger
	@Override
	public JwtUserPrincipal loadUserByUsername(String username) {
		JwtUserPrincipal result = new JwtUserPrincipal();
		/*try {
			GesUsers model = usersService.buscarUserByNameOrRut(username);
			
			result.setIdUsuario(model.getIdUser().longValue());
			result.setDscEmail(Objects.isNull(model.getEmail()) || model.getEmail().isEmpty() ? ConstantUtil.NO_INFORMADO : model.getEmail() );
			result.setFullName(model.getFullName());
			result.setUsername(Objects.isNull(model.getUsername()) ? username : model.getUsername());
			result.setRut(model.getRut());
			result.setUserInput(username);
			result.setPassword(passwordEncoder.encode(password));
			result.setMfaKey(model.getMfaKey());
			result.setIsMfa(model.getMfa());
			
			List<JwtProfile> profileList = new ArrayList<>();
			if(Boolean.TRUE.equals(isInit) && model.getMfa())
				profileList = Arrays.asList(new JwtProfile(-1l, ConstantUtil.KEY_AUTH ));
			else
				profileList = profile.findJwtProfileActiveByRut(model.getRut());
			
			if (Objects.nonNull(profileList) && !profileList.isEmpty()) {
				result.setListRoles(profileList);
				result.setAuthorities(profileList.stream().map(permiso -> new SimpleGrantedAuthority(permiso.getDscRol())).collect(Collectors.toList()));
			}else {
				result.setListRoles(new ArrayList<>());
				result.setAuthorities(new ArrayList<>());
			}
		} catch (Exception e) {
			log.error("Error en createUserPrincipal", e);
		}
		*/
		return result;
	}

}
