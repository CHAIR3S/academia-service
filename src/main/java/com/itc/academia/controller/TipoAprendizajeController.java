package com.itc.academia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itc.academia.dto.RespuestaDTO;
import com.itc.academia.dto.TipoAprendizajeDTO;
import com.itc.academia.service.ITipoAprendizajeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/tipo-aprendizaje")
public class TipoAprendizajeController {

    @Autowired
    private ITipoAprendizajeService tipoAprendizajeService;

    @GetMapping
    public ResponseEntity<RespuestaDTO> obtenerTodos() {
        log.info("Obteniendo todos los tipos de aprendizaje");
        RespuestaDTO respuesta = tipoAprendizajeService.obtener();
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespuestaDTO> obtenerPorId(@PathVariable Integer id) {
        log.info("Obteniendo tipo de aprendizaje con ID: {}", id);
        RespuestaDTO respuesta = tipoAprendizajeService.obtenerPorId(id);
        return ResponseEntity.ok(respuesta);
    }

    @PostMapping
    public ResponseEntity<RespuestaDTO> crear(@RequestBody TipoAprendizajeDTO tipoAprendizajeDTO) {
        log.info("Creando nuevo tipo de aprendizaje");
        RespuestaDTO respuesta = tipoAprendizajeService.crea(tipoAprendizajeDTO);
        return ResponseEntity.ok(respuesta);
    }

    @PutMapping
    public ResponseEntity<RespuestaDTO> actualizar(@RequestBody TipoAprendizajeDTO tipoAprendizajeDTO) {
        log.info("Actualizando tipo de aprendizaje con ID: {}", tipoAprendizajeDTO.getIdTipoAprendizaje());
        RespuestaDTO respuesta = tipoAprendizajeService.actualiza(tipoAprendizajeDTO);
        return ResponseEntity.ok(respuesta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RespuestaDTO> eliminar(@PathVariable Integer id) {
        log.info("Eliminando tipo de aprendizaje con ID: {}", id);
        RespuestaDTO respuesta = tipoAprendizajeService.elimina(id);
        return ResponseEntity.ok(respuesta);
    }

}
