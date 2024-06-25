package com.itc.academia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itc.academia.entity.Chat;
import com.itc.academia.entity.Mensaje;

public interface MensajeRepository extends JpaRepository<Mensaje, Long>{
	

	public List<Mensaje> findByChat(Chat chat);

}
