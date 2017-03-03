package es.cic.curso.curso17.ejercicio028.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import es.cic.curso.curso17.ejercicio028.modelo.Medicamento;

@Service
public class MedicamentoDTOTraductor {
	
	public MedicamentoDTO entidad2dto(Medicamento medicamento) {
		MedicamentoDTO resultado = new MedicamentoDTO();
		resultado.setId(medicamento.getId());
		resultado.setTipo(medicamento.getTipo());
		resultado.setNombreTipo(medicamento.getTipo().getNombre());
		resultado.setNombre(medicamento.getNombre());
		resultado.setDescripcion(medicamento.getDescripcion());
		return resultado;
	}
	
	public Medicamento dto2entidad(MedicamentoDTO medicamentoDTO) {
		Medicamento resultado = new Medicamento();
		resultado.setId(medicamentoDTO.getId());
		resultado.setTipo(medicamentoDTO.getTipo());
		resultado.setNombre(medicamentoDTO.getNombre());
		resultado.setDescripcion(medicamentoDTO.getDescripcion());
		return resultado;
	}

	public List<MedicamentoDTO> entidad2dto(List<Medicamento>  medicamentos) {
		List<MedicamentoDTO> resultado = new ArrayList<>();
		for (Medicamento medicamento : medicamentos) {
			resultado.add(entidad2dto(medicamento));
		}
		return resultado;
	}
	
	public List<Medicamento> dto2entidad(List<MedicamentoDTO> medicamentosDTO) {
		List<Medicamento> resultado = new ArrayList<>();
		for (MedicamentoDTO dto : medicamentosDTO) {
			resultado.add(dto2entidad(dto));
		}
		return resultado;
	}

}
