package es.cic.curso.curso17.ejercicio028.servicio;

import java.util.List;

import es.cic.curso.curso17.ejercicio028.dto.MedicamentoDTO;

public interface ServicioMedicacion {
	
	void agregaPorEnfermedad(Long idEnfermedad, List<MedicamentoDTO> medicacionRecomendada);
	
	List<MedicamentoDTO> eliminaPorEnfermedad(Long idEnfermedad);
	
	List<MedicamentoDTO> listaPorEnfermedad(Long idEnfermedad);

}
