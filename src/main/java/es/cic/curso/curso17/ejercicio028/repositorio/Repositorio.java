package es.cic.curso.curso17.ejercicio028.repositorio;

import java.util.List;

import es.cic.curso.curso17.ejercicio028.modelo.Identificable;

public interface Repositorio<K extends Number, T extends Identificable<K>> {

	T create(T element);

	T read(K id);

	T update(T element);

	T delete(K id);

	void delete(T element);

	List<T> list();
	
	List<T> listColumnLike(String columnName, String value);

}
