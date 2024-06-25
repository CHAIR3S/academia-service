package com.itc.academia.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {

	private Long idUsuario;
	
	private String correo;
	
	private String contrasena;
	
	private String nombre;
	
	private Integer edad;
	
	private String sexo;
	
	private String grado;
	
	private String nivelEstudios;

	private String foto;

	private TipoAprendizajeDTO tipoAprendizaje;

}
