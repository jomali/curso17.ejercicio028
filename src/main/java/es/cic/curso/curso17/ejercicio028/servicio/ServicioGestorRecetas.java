package es.cic.curso.curso17.ejercicio028.servicio;

import java.util.List;

import es.cic.curso.curso17.ejercicio028.dto.EnfermedadDTO;
import es.cic.curso.curso17.ejercicio028.dto.MedicamentoDTO;
import es.cic.curso.curso17.ejercicio028.modelo.Receta;

public interface ServicioGestorRecetas {
	
	boolean comprueba(List<EnfermedadDTO> enfermedades, MedicamentoDTO medicamento);

	Receta agregaReceta(List<MedicamentoDTO> medicamentos);
	
	Receta obtenReceta(Long id);
	
	List<Receta> listaRecetas();

}
