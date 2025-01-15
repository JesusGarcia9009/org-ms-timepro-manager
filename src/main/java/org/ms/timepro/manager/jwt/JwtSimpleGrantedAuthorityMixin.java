package org.ms.timepro.manager.jwt;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class JwtSimpleGrantedAuthorityMixin {

    @JsonCreator
    public JwtSimpleGrantedAuthorityMixin(@JsonProperty("authority") String role) {}

}
