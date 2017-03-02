package es.cic.curso.curso17.ejercicio028.repositorio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import es.cic.curso.curso17.ejercicio028.modelo.Identificable;


public abstract class RepositorioAbstractoImpl<K extends Number, T extends Identificable<K>>
		implements Repositorio<K, T> {

	@PersistenceContext
	protected EntityManager entityManager;

	public RepositorioAbstractoImpl() {
		super();
	}

	public abstract Class<T> obtenClaseT();

	public abstract String obtenNombreTabla();

	@Override
	public T create(T elemento) {
		entityManager.persist(elemento);
		entityManager.flush();
		return elemento;
	}

	@Override
	public T read(K id) {
		return entityManager.find(obtenClaseT(), id);
	}

	@Override
	public T update(T elemento) {
		return entityManager.merge(elemento);
	}

	@Override
	public T delete(K id) {
		T resultado = entityManager.find(obtenClaseT(), id);
		delete(resultado);
		return resultado;
	}

	@Override
	public void delete(T elemento) {
		entityManager.remove(elemento);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> list() {
		return entityManager.createNativeQuery("SELECT * FROM " + obtenNombreTabla(), obtenClaseT()).getResultList();
	}

}
