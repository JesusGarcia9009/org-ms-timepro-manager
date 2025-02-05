package org.ms.timepro.manager.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
public class UserResponseDTO {

    private String id;
    private String name;
    private String username;
}
