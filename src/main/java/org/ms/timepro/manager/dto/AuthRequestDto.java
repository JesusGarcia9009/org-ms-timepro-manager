package org.ms.timepro.manager.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequestDto {
	
	@JsonProperty
	@NotNull(message = "Es obligatorio")
	@NotBlank(message = "Es obligatorio")
	private String username;
	
	@JsonProperty
	@NotNull(message = "Es obligatorio")
	@NotBlank(message = "Es obligatorio")
	private String password;

	@JsonProperty
	private String captchaToken;

}
