package com.itc.academia.service;

import com.itc.academia.dto.ChatDTO;
import com.itc.academia.dto.RespuestaDTO;

public interface IChatService {
	
	public RespuestaDTO obtener();
	
	public RespuestaDTO obtenerPorId(Long id);
	
	public RespuestaDTO elimina(Long id);
	
	public RespuestaDTO crea(ChatDTO chatDTO);
	
	public RespuestaDTO actualiza(ChatDTO chatDTO);

	public RespuestaDTO obtenerPorUsuario(Long idUsuario);

}
