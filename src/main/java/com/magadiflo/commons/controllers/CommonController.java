package com.magadiflo.commons.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
	
	/**
	 * Tenemos que enviar dos parámetros desde el frontend
	 * page: 0, 1, 2....etc (página actual)
	 * size: Tamaño de la paginación. Ejm. 5 por página, 10 por página, etc.
	 * Estos parámetros (page y size), aquí en el backend, son pasados 
	 * por debajo, al objeto pageable y vinen desde el frontend vía url, 
	 * ejmp: 
	 * http://localhost:8090/api/alumnos/pagina?page=0&size=7
	 */
	@GetMapping(path = "/pagina")
	public ResponseEntity<?> listar(Pageable pageable) {
		return ResponseEntity.ok(this.service.findAll(pageable));
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
	public ResponseEntity<?> crear(@Validated @RequestBody E entity, BindingResult result) {
		if(result.hasErrors()) {
			return this.validar(result);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(entity));
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<?> eliminar(@PathVariable Long id) {
		this.service.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	protected ResponseEntity<?> validar(BindingResult result) {
		Map<String, Object> errores = new HashMap<>();
		result.getFieldErrors().forEach(fieldError -> {
			String mensaje = String.format("El campo %s %s", fieldError.getField(), fieldError.getDefaultMessage());
			errores.put(fieldError.getField(), mensaje);
		});
		return ResponseEntity.badRequest().body(errores);
	}

}
