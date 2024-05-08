package com.itc.academia.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itc.academia.dto.RespuestaDTO;
import com.itc.academia.dto.UsuarioDTO;
import com.itc.academia.entity.Usuario;
import com.itc.academia.repository.UsuarioRepository;
import com.itc.academia.util.PasswordEncoderUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UsuarioService implements IUsuarioService{

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private PasswordEncoderUtil passwordEncoder;

    UsuarioDTO convertToDto(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioDTO.class);
    }

    Usuario convertToEntity(UsuarioDTO usuarioDTO) {
        Usuario usuario = modelMapper.map(usuarioDTO, Usuario.class);
        return usuario;
    }
	
	@Override
	public RespuestaDTO obtener() {
        RespuestaDTO respuesta = new RespuestaDTO();
        List<Usuario> usuarios = repository.findAll();
        List<UsuarioDTO> usuarioDTOs = new ArrayList<>();

        usuarios.forEach(usuario -> {
            usuarioDTOs.add(convertToDto(usuario));
        });

        log.info("Usuarios encontrados -> " + usuarios.size());

        respuesta.setEstatus("1");
        respuesta.setMensaje("Usuarios encontrados correctamente");
        respuesta.setObject(null);
        respuesta.setLista(usuarioDTOs);

        return respuesta;
	}

	@Override
	public RespuestaDTO obtenerPorId(Long id) {
        RespuestaDTO respuesta = new RespuestaDTO();
        Optional<Usuario> usuarioOptional = repository.findById(id);

        if (!usuarioOptional.isPresent()) {
            log.error("Usuario no encontrado por ID : " + id);
            respuesta.setEstatus("0");
            respuesta.setMensaje("No se encontró el usuario por ID");
            respuesta.setObject(null);
            respuesta.setLista(null);

            return respuesta;
        }

        Usuario usuarioFound = usuarioOptional.get();
        log.debug("getUsuarioById returns {} ", usuarioFound.toString());

        respuesta.setEstatus("1");
        respuesta.setMensaje("Usuario encontrado por ID");
        respuesta.setObject(convertToDto(usuarioFound));
        respuesta.setLista(null);

        return respuesta;
	}

	@Override
	public RespuestaDTO elimina(Long id) {
        RespuestaDTO respuesta = new RespuestaDTO();
        if (!repository.existsById(id)) {
            log.error("Usuario no encontrado");
            respuesta.setEstatus("0");
            respuesta.setMensaje("Error, usuario no encontrado");
            respuesta.setLista(null);
            respuesta.setObject(null);

            return respuesta;
        }

        repository.deleteById(id);
        respuesta.setEstatus("1");
        respuesta.setMensaje("Usuario eliminado correctamente");

        return respuesta;
	}

	@Override
	public RespuestaDTO crea(UsuarioDTO usuarioDTO) {
        RespuestaDTO respuesta = new RespuestaDTO();
        usuarioDTO.setContrasena(passwordEncoder.encode(usuarioDTO.getContrasena()));
        
        
        Usuario usuarioToCreate = convertToEntity(usuarioDTO);
        Usuario usuarioCreated = repository.save(usuarioToCreate);

        respuesta.setEstatus("1");
        respuesta.setObject(convertToDto(usuarioCreated));
        respuesta.setMensaje("Usuario creado correctamente");
        respuesta.setLista(null);

        return respuesta;
	}

	@Override
	public RespuestaDTO actualiza(UsuarioDTO usuarioDTO) {
        RespuestaDTO respuesta = new RespuestaDTO();
        Optional<Usuario> usuarioBd = repository.findById(usuarioDTO.getIdUsuario());
        
        log.info(usuarioDTO.getContrasena());
        
        if(!usuarioBd.get().getContrasena().equals(usuarioDTO.getContrasena()) && !usuarioDTO.getContrasena().equals("")) {
            usuarioDTO.setContrasena(passwordEncoder.encode(usuarioDTO.getContrasena()));
            log.info("Nueva contraseña");
        } else {
        	usuarioDTO.setContrasena(usuarioBd.get().getContrasena());
            log.info("Misma contraseña");
        }
        

        if (!usuarioBd.isPresent()) {
            log.error("Error, usuario no encontrado");
            respuesta.setEstatus("0");
            respuesta.setMensaje("Error, no se encontró el usuario para actualizar");
            respuesta.setObject(null);
            respuesta.setLista(null);

            return respuesta;
        }

        Usuario usuarioToUpdate = convertToEntity(usuarioDTO);
        Usuario usuarioUpdated = repository.save(usuarioToUpdate);

        log.info("Usuario actualizado correctamente");

        respuesta.setEstatus("1");
        respuesta.setMensaje("Usuario actualizado correctamente");
        respuesta.setObject(convertToDto(usuarioUpdated));
        respuesta.setLista(null);

        return respuesta;
	}

	@Override
	public RespuestaDTO obtenerPorEmail(String email) {
        RespuestaDTO respuesta = new RespuestaDTO();
        Optional<Usuario> usuarioBd = repository.findByCorreo(email);
        		

        if (!usuarioBd.isPresent()) {
            log.error("Error, usuario no encontrado");
            respuesta.setEstatus("0");
            respuesta.setMensaje("Error, no se encontró el usuario por correo: " + email);
            respuesta.setObject(null);
            respuesta.setLista(null);

            return respuesta;
        }
        
        log.info("Usuario encontrado por email: ", email);


        respuesta.setEstatus("1");
        respuesta.setMensaje("Usuario encontrado por email");
        respuesta.setObject(convertToDto(usuarioBd.get()));
        respuesta.setLista(null);

        return respuesta;
	}

}
