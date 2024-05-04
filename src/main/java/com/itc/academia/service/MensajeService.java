package com.itc.academia.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itc.academia.dto.ChatDTO;
import com.itc.academia.dto.MensajeDTO;
import com.itc.academia.dto.RespuestaDTO;
import com.itc.academia.entity.Mensaje;
import com.itc.academia.repository.MensajeRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MensajeService implements IMensajeService{

    @Autowired
    private MensajeRepository repository;
    
    @Autowired
    private ChatService chatService;

    @Autowired
    private ModelMapper modelMapper;

    private MensajeDTO convertToDto(Mensaje mensaje) {
        return modelMapper.map(mensaje, MensajeDTO.class);
    }

    private Mensaje convertToEntity(MensajeDTO mensajeDTO) {
        return modelMapper.map(mensajeDTO, Mensaje.class);
    }


	@Override
	public RespuestaDTO obtener() {
        RespuestaDTO respuesta = new RespuestaDTO();
        List<Mensaje> mensajes = repository.findAll();
        List<MensajeDTO> mensajeDTOs = new ArrayList<>();

        mensajes.forEach(mensaje -> {
            mensajeDTOs.add(convertToDto(mensaje));
        });

        log.info("Mensajes encontrados -> " + mensajes.size());

        respuesta.setEstatus("1");
        respuesta.setMensaje("Mensajes encontrados correctamente");
        respuesta.setObject(null);
        respuesta.setLista(mensajeDTOs);

        return respuesta;
	}

	@Override
	public RespuestaDTO obtenerPorId(Long id) {
        RespuestaDTO respuesta = new RespuestaDTO();
        Optional<Mensaje> mensajeOptional = repository.findById(id);

        if (!mensajeOptional.isPresent()) {
            log.error("Mensaje no encontrado por ID : " + id);
            respuesta.setEstatus("0");
            respuesta.setMensaje("No se encontró el mensaje por ID");
            respuesta.setObject(null);
            respuesta.setLista(null);

            return respuesta;
        }

        Mensaje mensajeFound = mensajeOptional.get();
        log.debug("getMensajeById returns {} ", mensajeFound.toString());

        respuesta.setEstatus("1");
        respuesta.setMensaje("Mensaje encontrado por ID");
        respuesta.setObject(convertToDto(mensajeFound));
        respuesta.setLista(null);

        return respuesta;
	}

	@Override
	public RespuestaDTO elimina(Long id) {
        RespuestaDTO respuesta = new RespuestaDTO();
        if (!repository.existsById(id)) {
            log.error("Mensaje no encontrado");
            respuesta.setEstatus("0");
            respuesta.setMensaje("Error, mensaje no encontrado");
            respuesta.setLista(null);
            respuesta.setObject(null);

            return respuesta;
        }

        repository.deleteById(id);
        respuesta.setEstatus("1");
        respuesta.setMensaje("Mensaje eliminado correctamente");

        return respuesta;
	}

	@Override
	public RespuestaDTO crea(MensajeDTO mensajeDTO) {
        RespuestaDTO respuesta = new RespuestaDTO();
        Mensaje mensajeToCreate = convertToEntity(mensajeDTO);
        Mensaje mensajeCreated = repository.save(mensajeToCreate);

        respuesta.setEstatus("1");
        respuesta.setObject(convertToDto(mensajeCreated));
        respuesta.setMensaje("Mensaje creado correctamente");
        respuesta.setLista(null);

        return respuesta;
	}

	@Override
	public RespuestaDTO actualiza(MensajeDTO mensajeDTO) {
        RespuestaDTO respuesta = new RespuestaDTO();
        Optional<Mensaje> mensajeBd = repository.findById(mensajeDTO.getIdMensaje());

        if (!mensajeBd.isPresent()) {
            log.error("Error, mensaje no encontrado");
            respuesta.setEstatus("0");
            respuesta.setMensaje("Error, no se encontró el mensaje para actualizar");
            respuesta.setObject(null);
            respuesta.setLista(null);

            return respuesta;
        }

        Mensaje mensajeToUpdate = convertToEntity(mensajeDTO);
        Mensaje mensajeUpdated = repository.save(mensajeToUpdate);

        log.info("Mensaje actualizado correctamente");

        respuesta.setEstatus("1");
        respuesta.setMensaje("Mensaje actualizado correctamente");
        respuesta.setObject(convertToDto(mensajeUpdated));
        respuesta.setLista(null);

        return respuesta;
	}

	@Override
	public RespuestaDTO obtenerPorChat(Long idChat) {
        RespuestaDTO respuesta = new RespuestaDTO();
        ChatDTO chatDTO = new ChatDTO();
        
        respuesta = chatService.obtenerPorId(idChat);
        
        if("0".equals(respuesta.getEstatus())) {
        	return respuesta;
        }
        
        chatDTO = (ChatDTO) respuesta.getObject();
        
        List<Mensaje> mensajes = repository.findByChat(chatService.convertToEntity(chatDTO));
        List<MensajeDTO> mensajeDTOs = new ArrayList<>();

        if (mensajes.isEmpty()) {
            log.warn("No se encontraron mensajes para el chat con ID: " + idChat);
            respuesta.setEstatus("0");
            respuesta.setMensaje("No se encontraron mensajes para el chat especificado");
            respuesta.setObject(null);
            respuesta.setLista(null);
            return respuesta;
        }

        mensajes.forEach(mensaje -> {
            mensajeDTOs.add(convertToDto(mensaje));
        });

        log.info("Mensajes encontrados para el chat -> " + idChat + ": " + mensajes.size());
        respuesta.setEstatus("1");
        respuesta.setMensaje("Mensajes encontrados correctamente para el chat");
        respuesta.setObject(null);
        respuesta.setLista(mensajeDTOs);

        return respuesta;
	}

}
