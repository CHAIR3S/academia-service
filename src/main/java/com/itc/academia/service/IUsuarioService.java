package com.itc.academia.service;

import com.itc.academia.dto.RespuestaDTO;
import com.itc.academia.dto.UsuarioDTO;

public interface IUsuarioService {
	
	public RespuestaDTO obtener();
	
	public RespuestaDTO obtenerPorId(Long id);
	
	public RespuestaDTO elimina(Long id);
	
	public RespuestaDTO crea(UsuarioDTO usuarioDTO);
	
	public RespuestaDTO actualiza(UsuarioDTO usuarioDTO);
	
	public RespuestaDTO obtenerPorEmail(String email);
	

}
