package com.magadiflo.commons.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Estamos haciendo esta clase de servicio, una clase genérica, con la finalidad
 * de poder reutilizarlo en los microservicios de Usuarios, cursos, etc..
 * 
 * R extends CrudRepository<E, Long>, con ese código, estamos diciendo que
 * nuestro segundo genéric (del AlumnoServiceImpl) es un repository, es decir,
 * es cualquier tipo que herede de la interfaz CrudRepository
 * 
 * La anotación @Service, lo eliminamos ya que esta clase no será un componente
 * que vayamos a inyectar, sino más bien una clase que vamos a heredar
 */

public class CommonServiceImpl<E, R extends PagingAndSortingRepository<E, Long>> implements ICommonService<E> {

	protected final R repository;

	public CommonServiceImpl(R repository) {
		this.repository = repository;
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<E> findAll() {
		return this.repository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<E> findById(Long id) {
		return this.repository.findById(id);
	}

	@Override
	@Transactional
	public E save(E entity) {
		return this.repository.save(entity);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		this.repository.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<E> findAll(Pageable pageable) {
		return this.repository.findAll(pageable);
	}

}
