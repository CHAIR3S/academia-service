package com.itc.academia.service;

import com.itc.academia.dto.RespuestaDTO;
import com.itc.academia.dto.TipoAprendizajeDTO;

public interface ITipoAprendizajeService {
	
	public RespuestaDTO obtener();
	
	public RespuestaDTO obtenerPorId(Integer id);
	
	public RespuestaDTO elimina(Integer id);
	
	public RespuestaDTO crea(TipoAprendizajeDTO tipoAprendizajeDTO);
	
	public RespuestaDTO actualiza(TipoAprendizajeDTO tipoAprendizajeDTO);
	

}
