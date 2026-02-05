package com.br.biblioteca.dto.user;


import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class UserCreateDTO {
	
	   @NotBlank(message = "O nome é obrigatório.")
	   String name;
	   
	   @NotBlank(message = "O email é obrigatório.")
	   String email;
}
