package es.cic.curso.curso17.ejercicio028.servicio;

import java.util.List;

import es.cic.curso.curso17.ejercicio028.dto.MedicamentoDTO;
import es.cic.curso.curso17.ejercicio028.modelo.Receta;

public interface ServicioGestorRecetas {

	void agregaReceta(List<MedicamentoDTO> medicamentos);
	
	Receta obtenReceta(Long id);
	
	List<Receta> listaRecetas();

}
