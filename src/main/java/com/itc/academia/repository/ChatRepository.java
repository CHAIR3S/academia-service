package com.itc.academia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itc.academia.entity.Chat;
import com.itc.academia.entity.Usuario;

public interface ChatRepository extends JpaRepository<Chat, Long>{

	public List<Chat> findByUsuario(Usuario usuario);
	
}
