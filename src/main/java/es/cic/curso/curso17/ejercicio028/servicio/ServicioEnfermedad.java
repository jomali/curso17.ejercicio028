package es.cic.curso.curso17.ejercicio028.servicio;

import java.util.List;

import es.cic.curso.curso17.ejercicio028.dto.EnfermedadDTO;
import es.cic.curso.curso17.ejercicio028.dto.MedicamentoDTO;

public interface ServicioEnfermedad {

	Long agregaEnfermedad(EnfermedadDTO enfermedad);

	EnfermedadDTO obtenEnfermedad(Long id);

	EnfermedadDTO modificaEnfermedad(Long id, EnfermedadDTO enfermedad);

	EnfermedadDTO eliminaEnfermedad(Long id);

	List<EnfermedadDTO> listaEnfermedades();

	void agregaMedicacion(Long idEnfermedad, List<MedicamentoDTO> medicacionRecomendada);
	
	List<MedicamentoDTO> eliminaTotalMedicacion(Long idEnfermedad);
	
	List<MedicamentoDTO> listaMedicacion(Long idEnfermedad);
	
}
