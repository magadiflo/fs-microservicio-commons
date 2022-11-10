package com.magadiflo.commons.controllers;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.magadiflo.commons.services.ICommonService;

/**
 * El editar no lo colocamos ya que cada Entity tiene una manera de actualizar
 * distinta, eso iría en el controlador que vamos a implementar en cada
 * microservicio y que a su vez herede de esta clase.
 * 
 */

public class CommonController<E, S extends ICommonService<E>> {

	protected final S service;

	public CommonController(S service) {
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<?> listar() {
		return ResponseEntity.ok(this.service.findAll());
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<?> ver(@PathVariable Long id) {
		Optional<E> opEntity = this.service.findById(id);
		if (opEntity.isEmpty()) {
			// build(), construye la respuesta con un body vacío, sin contenido
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(opEntity.get());
	}

	@PostMapping
	public ResponseEntity<?> crear(@RequestBody E entity) {
		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(entity));
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<?> eliminar(@PathVariable Long id) {
		this.service.deleteById(id);
		return ResponseEntity.noContent().build();
	}

}
