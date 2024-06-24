package com.itc.academia.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.itc.academia.entity.TipoAprendizaje;
import com.itc.academia.repository.TipoAprendizajeRepository;
import com.itc.academia.repository.UsuarioRepository;
import com.itc.academia.util.PasswordEncoderUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class InitializationDBConfig {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	TipoAprendizajeRepository tipoAprendizajeRepository;
	
	@Autowired
	PasswordEncoderUtil passwordEncoderUtil;
	

    @EventListener(ApplicationReadyEvent.class)
    public void iniciarUsuario() {
    	if(usuarioRepository.count() == 0) {
    		log.debug("Creando usuario admin");
    		
    		
//    		Usuario usuario = new Usuario();
//    		
//    		usuario.setCorreo("admin@academia.com");
//    		usuario.setContrasena(passwordEncoderUtil.encode("contrasena"));
//
//    		usuarioRepository.save(usuario);
    		
    	}
    	
    	if(tipoAprendizajeRepository.count() == 0) {
    		log.debug("Creando tipos de aprendizaje");
    		
    		TipoAprendizaje tipoAprendizaje = new TipoAprendizaje();
    		
    		tipoAprendizaje.setNombre("Visual");
    		tipoAprendizaje.setDescripcion("Prefiere ver para aprender, utiliza diagramas y esquemas para entender mejor los conceptos.");
    		tipoAprendizaje.setConsejo("Utiliza colores y mapas mentales para mejorar la retención de información.");
    		
    		tipoAprendizajeRepository.save(tipoAprendizaje);

    		tipoAprendizaje.setIdTipoAprendizaje(0);
    		tipoAprendizaje.setNombre("Auditivo");
    		tipoAprendizaje.setDescripcion("Prefiere escuchar para aprender, se beneficia de discusiones y explicaciones orales.");
    		tipoAprendizaje.setConsejo("Intenta usar grabaciones de audio para revisar temas y participa activamente en debates.");


    		tipoAprendizajeRepository.save(tipoAprendizaje);
    		

    		tipoAprendizaje.setIdTipoAprendizaje(0);
    		tipoAprendizaje.setNombre("kinestésico");
    		tipoAprendizaje.setDescripcion("Prefiere aprender haciendo y tocando, aprende mejor a través de la actividad física y la práctica.");
    		tipoAprendizaje.setConsejo("Incorpora actividades prácticas en tu estudio, como experimentos o construyendo modelos.");
    		

    		tipoAprendizajeRepository.save(tipoAprendizaje);
    		
    	}
    	
    	
    }

}
