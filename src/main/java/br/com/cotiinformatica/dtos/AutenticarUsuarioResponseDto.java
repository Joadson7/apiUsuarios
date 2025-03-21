package br.com.cotiinformatica.dtos;

import java.time.Instant;
import java.util.UUID;

import lombok.Data;

@Data
public class AutenticarUsuarioResponseDto {

	private UUID id;
	private String nome;
	private String email;
	private String perfil;
	private String token;
	
}
