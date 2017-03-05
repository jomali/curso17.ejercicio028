package es.cic.curso.curso17.ejercicio028.repositorio;

import java.util.List;

import es.cic.curso.curso17.ejercicio028.modelo.Medicacion;

public interface RepositorioMedicacion extends Repositorio<Long, Medicacion> {
	
	List<Medicacion> deleteByDisease(Long idDisease);

	List<Medicacion> listByDisease(Long idDisease);

}
