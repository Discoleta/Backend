package com.generation.discoleta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.generation.discoleta.model.Tema;

@Repository 
public interface TemaRepository extends JpaRepository <Tema, Long>{
	
	public List <Tema> findAllByNomeContainingIgnoreCase(@Param("nome")String nome);
	
	

}
