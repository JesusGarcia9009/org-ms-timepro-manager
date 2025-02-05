package org.ms.timepro.manager.dto.request;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.jwt.security.entity.Profile;
import org.ms.timepro.manager.anotations.Rut;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class RegisterUserRequestDTO {

    @Rut
    private String rut;
    private String name;
    private String lastName;
    private String email;
    private String cellphone;
    private String username;
    private String pass;
    private String mailingAdd;
    private String profile;
}
