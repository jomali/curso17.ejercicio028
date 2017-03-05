package es.cic.curso.curso17.ejercicio028.servicio;

import java.util.List;

import es.cic.curso.curso17.ejercicio028.dto.MedicamentoDTO;
import es.cic.curso.curso17.ejercicio028.modelo.EnfermedadMedicamento;

public interface ServicioEnfermedadMedicamento {
	
	void agregaPorEnfermedad(Long idEnfermedad, MedicamentoDTO ... dtos);
	
	List<EnfermedadMedicamento> eliminaPorEnfermedad(Long idEnfermedad);
	
	List<EnfermedadMedicamento> listaPorEnfermedad(Long idEnfermedad);

}
