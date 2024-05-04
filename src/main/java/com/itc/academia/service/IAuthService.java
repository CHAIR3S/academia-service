package com.itc.academia.service;

import com.itc.academia.dto.AuthUserDTO;
import com.itc.academia.dto.RespuestaDTO;
import com.itc.academia.exception.ContrasenaIncorrectaException;

public interface IAuthService {
	
	public RespuestaDTO autenticar(AuthUserDTO credenciales) throws ContrasenaIncorrectaException;

	public RespuestaDTO validaToken(String token);

}
