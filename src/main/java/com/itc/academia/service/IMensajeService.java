package com.itc.academia.service;

import com.itc.academia.dto.MensajeDTO;
import com.itc.academia.dto.RespuestaDTO;

public interface IMensajeService {
	
	public RespuestaDTO obtener();
	
	public RespuestaDTO obtenerPorId(Long id);
	
	public RespuestaDTO elimina(Long id);
	
	public RespuestaDTO crea(MensajeDTO mensajeDTO);
	
	public RespuestaDTO actualiza(MensajeDTO mensajeDTO);

	public RespuestaDTO obtenerPorChat(Long idChat);

}
