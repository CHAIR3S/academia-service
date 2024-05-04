package com.itc.academia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itc.academia.dto.AuthUserDTO;
import com.itc.academia.dto.RespuestaDTO;
import com.itc.academia.dto.UsuarioDTO;
import com.itc.academia.exception.ContrasenaIncorrectaException;
import com.itc.academia.security.jwt.JwtAuthenticationProvider;
import com.itc.academia.util.PasswordEncoderUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthService implements IAuthService {
	
	@Autowired
	IUsuarioService userService;
	
	@Autowired
	PasswordEncoderUtil passwordEncoderUtil;

    private final JwtAuthenticationProvider authProvider;
    
    public AuthService(JwtAuthenticationProvider authProvider) {
        this.authProvider = authProvider;
    }
	
	@Override
	public RespuestaDTO autenticar(AuthUserDTO credenciales) throws ContrasenaIncorrectaException{
		RespuestaDTO respuesta = new RespuestaDTO();
		
		respuesta = userService.obtenerPorEmail(credenciales.getCorreo());
		
		//Si el usuario no fue encontrado por el email
		if("0".equals(respuesta.getEstatus())) {
			log.error(respuesta.getMensaje());
			return respuesta;
		}
		
		UsuarioDTO userEncontrado = (UsuarioDTO) respuesta.getObject();
		
		//Si las contraseñas no coinciden
		if(!passwordEncoderUtil.matches(credenciales.getContrasena(), userEncontrado.getContrasena())) {
			log.error("Contraseña no coincide con el correo");
			throw new ContrasenaIncorrectaException();
		}
		
		respuesta = authProvider.createToken(userEncontrado);
				
		return respuesta;
	}

	

	@Override
	public RespuestaDTO validaToken (String token) {
		
		RespuestaDTO respuesta = new RespuestaDTO();
		
		respuesta = authProvider.validaToken(token);
		
		return respuesta;
	}
	
}
