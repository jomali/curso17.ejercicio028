package es.cic.curso.curso17.ejercicio028.repositorio;

import java.util.List;

import es.cic.curso.curso17.ejercicio028.modelo.Medicamento;

public interface RepositorioMedicamento extends Repositorio<Long, Medicamento> {

	List<Medicamento> listByType(Long idType);
}
