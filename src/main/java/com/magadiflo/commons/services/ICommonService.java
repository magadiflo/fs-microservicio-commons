package com.magadiflo.commons.services;

import java.util.Optional;

public interface ICommonService<E> {
	Iterable<E> findAll();

	Optional<E> findById(Long id);

	E save(E entity);

	void deleteById(Long id);
}
