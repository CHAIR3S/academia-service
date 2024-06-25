package com.itc.academia.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatDTO {
	
	private Long idChat;
	
	private String nombre;
	
	private LocalDateTime fecha;


	private UsuarioDTO usuario;

}
