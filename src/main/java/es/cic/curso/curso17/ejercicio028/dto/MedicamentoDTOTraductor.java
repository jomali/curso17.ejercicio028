package es.cic.curso.curso17.ejercicio028.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import es.cic.curso.curso17.ejercicio028.modelo.Medicamento;

@Service
public class MedicamentoDTOTraductor implements Traductor<Medicamento, MedicamentoDTO> {

	@Override
	public MedicamentoDTO traduceADTO(Medicamento entidad) {
		MedicamentoDTO resultado = new MedicamentoDTO();
		resultado.setId(entidad.getId());
		resultado.setTipo(entidad.getTipo());
		resultado.setNombreTipo(entidad.getTipo() == null ? "general" : entidad.getTipo().getNombre());
		resultado.setNombre(entidad.getNombre());
		resultado.setDescripcion(entidad.getDescripcion());
		return resultado;
	}

	@Override
	public Medicamento traduceAEntidad(MedicamentoDTO dto) {
		Medicamento resultado = new Medicamento();
		resultado.setId(dto.getId());
		resultado.setTipo(dto.getTipo());
		resultado.setNombre(dto.getNombre());
		resultado.setDescripcion(dto.getDescripcion());
		return resultado;
	}

	@Override
	public List<MedicamentoDTO> traduceAListaDTOs(List<Medicamento> entidades) {
		List<MedicamentoDTO> resultado = new ArrayList<>();
		for (Medicamento medicamento : entidades) {
			resultado.add(traduceADTO(medicamento));
		}
		return resultado;
	}

	@Override
	public List<Medicamento> traduceAListaEntidades(List<MedicamentoDTO> dtos) {
		List<Medicamento> resultado = new ArrayList<>();
		for (MedicamentoDTO dto : dtos) {
			resultado.add(traduceAEntidad(dto));
		}
		return resultado;
	}

}
