package es.cic.curso.curso17.ejercicio028.repositorio;

import java.util.List;

import es.cic.curso.curso17.ejercicio028.modelo.TipoMedicamento;

public interface RepositorioTipoMedicamento extends Repositorio<Long, TipoMedicamento> {

	List<TipoMedicamento> listInOrder();
	
}
