package org.ms.timepro.manager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDto {
	
	private Long id;
	private String username;
	private String fullName;
	private String token;
	private Boolean isMfa;
	private String qrCodeImage;

}
