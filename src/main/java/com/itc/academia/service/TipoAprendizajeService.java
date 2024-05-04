package com.itc.academia.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itc.academia.dto.RespuestaDTO;
import com.itc.academia.dto.TipoAprendizajeDTO;
import com.itc.academia.entity.TipoAprendizaje;
import com.itc.academia.repository.TipoAprendizajeRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TipoAprendizajeService implements ITipoAprendizajeService{

    @Autowired
    private TipoAprendizajeRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    private TipoAprendizajeDTO convertToDto(TipoAprendizaje tipoAprendizaje) {
        return modelMapper.map(tipoAprendizaje, TipoAprendizajeDTO.class);
    }

    private TipoAprendizaje convertToEntity(TipoAprendizajeDTO tipoAprendizajeDTO) {
        return modelMapper.map(tipoAprendizajeDTO, TipoAprendizaje.class);
    }

	@Override
	public RespuestaDTO obtener() {
        RespuestaDTO respuesta = new RespuestaDTO();
        List<TipoAprendizaje> tipos = repository.findAll();
        List<TipoAprendizajeDTO> tipoDTOs = new ArrayList<>();

        tipos.forEach(tipo -> {
            tipoDTOs.add(convertToDto(tipo));
        });

        log.info("Tipos de aprendizaje encontrados -> " + tipos.size());

        respuesta.setEstatus("1");
        respuesta.setMensaje("Tipos de aprendizaje encontrados correctamente");
        respuesta.setObject(null);
        respuesta.setLista(tipoDTOs);

        return respuesta;
	}

	@Override
	public RespuestaDTO obtenerPorId(Integer id) {
        RespuestaDTO respuesta = new RespuestaDTO();
        Optional<TipoAprendizaje> tipoOptional = repository.findById(id);

        if (!tipoOptional.isPresent()) {
            log.error("Tipo de aprendizaje no encontrado por ID : " + id);
            respuesta.setEstatus("0");
            respuesta.setMensaje("No se encontró el tipo de aprendizaje por ID");
            respuesta.setObject(null);
            respuesta.setLista(null);

            return respuesta;
        }

        TipoAprendizaje tipoFound = tipoOptional.get();
        log.debug("getTipoAprendizajeById returns {} ", tipoFound.toString());

        respuesta.setEstatus("1");
        respuesta.setMensaje("Tipo de aprendizaje encontrado por ID");
        respuesta.setObject(convertToDto(tipoFound));
        respuesta.setLista(null);

        return respuesta;
	}

	@Override
	public RespuestaDTO elimina(Integer id) {
        RespuestaDTO respuesta = new RespuestaDTO();
        if (!repository.existsById(id)) {
            log.error("Tipo de aprendizaje no encontrado");
            respuesta.setEstatus("0");
            respuesta.setMensaje("Error, tipo de aprendizaje no encontrado");
            respuesta.setLista(null);
            respuesta.setObject(null);

            return respuesta;
        }

        repository.deleteById(id);
        respuesta.setEstatus("1");
        respuesta.setMensaje("Tipo de aprendizaje eliminado correctamente");

        return respuesta;
	}

	@Override
	public RespuestaDTO crea(TipoAprendizajeDTO tipoAprendizajeDTO) {
        RespuestaDTO respuesta = new RespuestaDTO();
        TipoAprendizaje tipoToCreate = convertToEntity(tipoAprendizajeDTO);
        TipoAprendizaje tipoCreated = repository.save(tipoToCreate);

        respuesta.setEstatus("1");
        respuesta.setObject(convertToDto(tipoCreated));
        respuesta.setMensaje("Tipo de aprendizaje creado correctamente");
        respuesta.setLista(null);

        return respuesta;
	}

	@Override
	public RespuestaDTO actualiza(TipoAprendizajeDTO tipoAprendizajeDTO) {
        RespuestaDTO respuesta = new RespuestaDTO();
        Optional<TipoAprendizaje> tipoBd = repository.findById(tipoAprendizajeDTO.getIdTipoAprendizaje());

        if (!tipoBd.isPresent()) {
            log.error("Error, tipo de aprendizaje no encontrado");
            respuesta.setEstatus("0");
            respuesta.setMensaje("Error, no se encontró el tipo de aprendizaje para actualizar");
            respuesta.setObject(null);
            respuesta.setLista(null);

            return respuesta;
        }

        TipoAprendizaje tipoToUpdate = convertToEntity(tipoAprendizajeDTO);
        TipoAprendizaje tipoUpdated = repository.save(tipoToUpdate);

        log.info("Tipo de aprendizaje actualizado correctamente");

        respuesta.setEstatus("1");
        respuesta.setMensaje("Tipo de aprendizaje actualizado correctamente");
        respuesta.setObject(convertToDto(tipoUpdated));
        respuesta.setLista(null);

        return respuesta;
	}

}
