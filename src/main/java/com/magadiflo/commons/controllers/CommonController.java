package com.magadiflo.commons.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.magadiflo.commons.exceptions.ExceptionHandling;
import com.magadiflo.commons.services.ICommonService;

/**
 * El método editar no lo colocamos ya que cada Entity tiene una manera de
 * actualizar distinta, eso iría en el controlador que vamos a implementar en
 * cada microservicio y que a su vez herede de esta clase.
 * 
 * Forma 01 de implementar CrossOrigin
 * *********************************************************************
 * 
 * @CrossOrigin, es una de las maneras de permitir que se hagan peticiones de un
 * dominio determinado. En nuestro proyecto, permitimos que se hagan peticiones
 * desde localhost:4200, lugar donde está nuestra app de angular. Ahora, si se
 * hacen peticiones desde un dominio que no hemos definido aquí, lanzará un
 * error, pues el domino será bloqueado por la política de CORS.
 */

// Lo comentamos porque usaremos la configuración que aplica de manera distribuida a todos los controladores.
// Esa configuración que sería la Forma 02, está en el application.yml del fs-microservicio-spring-cloud-gateway
//@CrossOrigin({ "http://localhost:4200" })
public class CommonController<E, S extends ICommonService<E>> extends ExceptionHandling {

	protected final S service;

	public CommonController(S service) {
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<?> listar() {
		return ResponseEntity.ok(this.service.findAll());
	}

	/**
	 * Tenemos que enviar dos parámetros desde el frontend page: 0, 1, 2....etc
	 * (página actual) size: Tamaño de la paginación. Ejm. 5 por página, 10 por
	 * página, etc. Estos parámetros (page y size), aquí en el backend, son pasados
	 * por debajo, al objeto pageable y vinen desde el frontend vía url, ejmp:
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
	public ResponseEntity<?> crear(@Valid @RequestBody E entity, BindingResult result) {
		if (result.hasErrors()) {
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
