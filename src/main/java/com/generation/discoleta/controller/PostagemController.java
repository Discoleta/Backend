package com.generation.discoleta.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.discoleta.model.Postagem;
import com.generation.discoleta.repository.PostagemRepository;
import com.generation.discoleta.repository.TemaRepository;

@RestController
@RequestMapping("/postagens")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostagemController {
	@Autowired
	private PostagemRepository postagemRepository;
	
	@Autowired
	private TemaRepository temaRepository;

	@GetMapping
	public ResponseEntity<List<Postagem>> getAll() {
		return ResponseEntity.ok(postagemRepository.findAll());
	}

	@GetMapping("/{id}") // Método get para listar uma postagem pelo id
	public ResponseEntity<Postagem> getById(@PathVariable Long id) {
		return postagemRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/titulo/{titulo}") // Método get para listar postagems pelo titulo
	public ResponseEntity<List<Postagem>> getByTitulo(@PathVariable String titulo) {
		return ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(titulo));
	}

	@PostMapping // Método post para criar uma postagem
	public ResponseEntity <Postagem> postPostagem(@Valid @RequestBody Postagem postagem){
		if(temaRepository.existsById(postagem.getTema().getId()))
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(postagemRepository.save(postagem));
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

	@PutMapping // Método put para atualizar uma postagem
	public ResponseEntity<Postagem> putpostagem(@Valid @RequestBody Postagem postagem) {
		if (postagemRepository.existsById(postagem.getId())) {
			return ResponseEntity.status(HttpStatus.OK).body(postagemRepository.save(postagem));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

	@DeleteMapping("/{id}") // Método delete para deletar uma postagem
	public ResponseEntity<?> deletepostagem(@PathVariable Long id) {
		if (postagemRepository.existsById(id)) {
			postagemRepository.deleteById(id);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}

}
