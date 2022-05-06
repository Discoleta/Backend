package com.generation.discoleta.controller;

import java.util.List;

import javax.validation.Valid;

import com.generation.discoleta.model.Tema;
import com.generation.discoleta.repository.TemaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tema")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TemaController {
	@Autowired
	private TemaRepository temaRepository;

	@RequestMapping
	public ResponseEntity<List<Tema>> getAll() {
		return ResponseEntity.ok(temaRepository.findAll());
	}

	@RequestMapping("/{id}") // Método get para listar uma tema pelo id
	public ResponseEntity<Tema> getById(@PathVariable Long id) {
		return temaRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.notFound().build());
	}

	@RequestMapping("/nome/{nome}") // Método get para listar temas pelo gênero
	public ResponseEntity<List<Tema>> getBynome(@PathVariable String nome) {
		return ResponseEntity.ok(temaRepository.findAllByNomeContainingIgnoreCase(nome));
	}

	@PostMapping // Método post para criar uma tema
	public ResponseEntity<Tema> posttema(@Valid @RequestBody Tema tema) {
		return ResponseEntity.status(HttpStatus.CREATED).body(temaRepository.save(tema));
	}

	@PutMapping // Método put para atualizar uma tema
	public ResponseEntity<Tema> puttema(@Valid @RequestBody Tema tema) {
		if (temaRepository.existsById(tema.getId())) {
			return ResponseEntity.status(HttpStatus.OK).body(temaRepository.save(tema));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

	@DeleteMapping("/{id}") // Método delete para deletar uma tema
	public ResponseEntity<?> deletetema(@PathVariable Long id) {
		if (temaRepository.existsById(id)) {
			temaRepository.deleteById(id);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}

}
