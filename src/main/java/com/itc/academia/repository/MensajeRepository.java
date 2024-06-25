package com.itc.academia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itc.academia.dto.RespuestaDTO;
import com.itc.academia.entity.Chat;
import com.itc.academia.entity.Mensaje;

public interface MensajeRepository extends JpaRepository<Mensaje, Long>{
	

	@Query("SELECT m FROM Mensaje m WHERE m.chat.idChat = ?1")
	public List<Mensaje> findByChat(Long id);

}
