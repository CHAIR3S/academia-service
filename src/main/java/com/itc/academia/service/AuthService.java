package com.itc.academia.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.json.webtoken.JsonWebSignature;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.TokenVerifier;
import com.google.auth.oauth2.TokenVerifier.VerificationException;
import com.itc.academia.dto.AuthUserDTO;
import com.itc.academia.dto.RespuestaDTO;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.auth.openidconnect.IdToken;
import com.google.api.client.auth.openidconnect.IdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.itc.academia.dto.UsuarioDTO;
import com.google.api.client.json.jackson2.JacksonFactory;
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
	public RespuestaDTO autenticarGoogle(AuthUserDTO credenciales) {
		RespuestaDTO respuesta = new RespuestaDTO();
		GoogleIdToken idToken = null;
		
		respuesta = userService.obtenerPorEmail(credenciales.getCorreo());
		
		//Si el usuario no fue encontrado por el email
		if("0".equals(respuesta.getEstatus())) {
			log.info("Guardar usuario google");
			log.info(credenciales.getGoogleJWT());
			
			try {
				idToken = verifyGoogleToken(credenciales.getGoogleJWT());
				log.info("=========== SIGNATURE ==============");
			} catch (GeneralSecurityException e) {
				log.error(e.getMessage());
			} catch (IOException e) {
				log.error(e.getMessage());
			}


	        if (idToken != null) {
	            Payload payload = idToken.getPayload();

	            // Obteniendo información del usuario desde el payload
	            String name = (String) payload.get("name");
	            String pictureUrl = (String) payload.get("picture");

	            // Utiliza los datos obtenidos como sea necesario
	            log.info("Email: " + credenciales.getCorreo());
	            log.info("Nombre: " + name);
	            log.info("URL de imagen: " + pictureUrl);

			
				UsuarioDTO usuario = new UsuarioDTO();
				usuario.setCorreo(credenciales.getCorreo());
				usuario.setNombre(name);
				usuario.setFoto(pictureUrl);
				
				UsuarioDTO usuariobd = (UsuarioDTO) userService.crea(usuario).getObject();
				
				usuariobd.setGoogleJWT(credenciales.getGoogleJWT());
	
				respuesta = authProvider.createToken(usuariobd);
	        }
				
			return respuesta;
		}
		
		UsuarioDTO userEncontrado = (UsuarioDTO) respuesta.getObject();
		
		//Si las contraseñas no coinciden
//		if(!passwordEncoderUtil.matches(credenciales.getContrasena(), userEncontrado.getContrasena())) {
//			log.error("Contraseña no coincide con el correo");
//			throw new ContrasenaIncorrectaException();
//		}
		
		userEncontrado.setGoogleJWT(credenciales.getGoogleJWT());
		respuesta = authProvider.createToken(userEncontrado);
				
		return respuesta;
	}
	
	
	private GoogleIdToken verifyGoogleToken (String googleToken) throws GeneralSecurityException, IOException {

	    JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
	    NetHttpTransport transport = new NetHttpTransport();
	    
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
			    // Specify the CLIENT_ID of the app that accesses the backend:
			    .setAudience(Collections.singletonList("604629377452-9fqbfs9mmkld6i1be05snhnonv18vfb0.apps.googleusercontent.com"))
			    // Or, if multiple clients access the backend:
			    //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
			    .build();

			// (Receive idTokenString by HTTPS POST)
		log.info("verifyyy");

		GoogleIdToken idToken = verifier.verify(googleToken);
			
		return (idToken != null) ? idToken : null;
	}

	

	@Override
	public RespuestaDTO validaToken (String token) {
		
		RespuestaDTO respuesta = new RespuestaDTO();
		
		respuesta = authProvider.validaToken(token);
		
		return respuesta;
	}
	
}
