package es.cic.curso.curso17.ejercicio028.servicio;

import java.util.Collection;
import java.util.List;

import es.cic.curso.curso17.ejercicio028.modelo.EnfermedadMedicamento;
import es.cic.curso.curso17.ejercicio028.modelo.Medicamento;

public interface ServicioEnfermedadMedicamento {
	
	void agregaPorEnfermedad(Long idEnfermedad, Collection<Medicamento> medicamentos);
	
	List<EnfermedadMedicamento> eliminaPorEnfermedad(Long idEnfermedad);
	
	List<EnfermedadMedicamento> listaPorEnfermedad(Long idEnfermedad);

}
