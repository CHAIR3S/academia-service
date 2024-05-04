package com.itc.academia.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_usuario")
	private Long idUsuario;
	
	@Column(unique = true, nullable = false)
	private String correo;
	
	private String contrasena;
	
	private String nombre;
	
	private Integer edad;
	
	private String sexo;
	
	private String grado;
	
	@Column(name = "nivel_estudios")
	private String nivelEstudios;

	@Lob
	@Column(name = "foto", columnDefinition = "LONGBLOB")
	private String foto;

	@ManyToOne
	@JoinColumn(name = "id_tipo_aprendizaje")
	private TipoAprendizaje tipoAprendizaje;

}
