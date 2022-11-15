package com.magadiflo.commons.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICommonService<E> {

	// El Page, es el resultado de la paginación, contiene un subconsjunto del listado total
	Page<E> findAll(Pageable pageable);

	Iterable<E> findAll();

	Optional<E> findById(Long id);

	E save(E entity);

	void deleteById(Long id);
}
