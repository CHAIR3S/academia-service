package com.itc.academia.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatDTO {
	
	private Long idChat;
	
	private String nombre;
	
	private LocalDateTime fecha;
	
	private UsuarioDTO usuario;

}
