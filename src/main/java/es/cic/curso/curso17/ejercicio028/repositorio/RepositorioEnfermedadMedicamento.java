package es.cic.curso.curso17.ejercicio028.repositorio;

import java.util.List;

import es.cic.curso.curso17.ejercicio028.modelo.EnfermedadMedicamento;

public interface RepositorioEnfermedadMedicamento extends Repositorio<Long, EnfermedadMedicamento> {
	
	List<EnfermedadMedicamento> deleteByDisease(Long idDisease);

	List<EnfermedadMedicamento> listByDisease(Long idDisease);

}
