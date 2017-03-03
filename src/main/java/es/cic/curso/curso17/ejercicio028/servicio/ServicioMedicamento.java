package es.cic.curso.curso17.ejercicio028.servicio;

import java.util.List;

import es.cic.curso.curso17.ejercicio028.dto.MedicamentoDTO;

public interface ServicioMedicamento {

	void agregaMedicamento(MedicamentoDTO medicamento);

	MedicamentoDTO obtenMedicamento(Long id);
	
	MedicamentoDTO modificaMedicamento(Long id, MedicamentoDTO medicamento);
	
	MedicamentoDTO eliminaMedicamento(Long id);
	
	List<MedicamentoDTO> listaMedicamentos();
	
	List<MedicamentoDTO> listaMedicamentosPorTipo(Long idTipoMedicamento);

}
