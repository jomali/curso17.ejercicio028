package es.cic.curso.curso17.ejercicio028.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import es.cic.curso.curso17.ejercicio028.modelo.Enfermedad;

@Service
public class EnfermedadDTOTraductor implements Traductor<Enfermedad, EnfermedadDTO> {

	@Override
	public EnfermedadDTO traduceADTO(Enfermedad entidad) {
		EnfermedadDTO resultado = new EnfermedadDTO();
		resultado.setId(entidad.getId());
		resultado.setNombre(entidad.getNombre());
		resultado.setDescripcion(entidad.getDescripcion());
		return resultado;
	}

	@Override
	public Enfermedad traduceAEntidad(EnfermedadDTO dto) {
		Enfermedad resultado = new Enfermedad();
		resultado.setId(dto.getId());
		resultado.setNombre(dto.getNombre());
		resultado.setDescripcion(dto.getDescripcion());
		return resultado;
	}

	@Override
	public List<EnfermedadDTO> traduceAListaDTOs(List<Enfermedad> entidades) {
		List<EnfermedadDTO> resultado = new ArrayList<>();
		for (Enfermedad enfermedad : entidades) {
			resultado.add(traduceADTO(enfermedad));
		}
		return resultado;
	}

	@Override
	public List<Enfermedad> traduceAListaEntidades(List<EnfermedadDTO> dtos) {
		List<Enfermedad> resultado = new ArrayList<>();
		for (EnfermedadDTO dto : dtos) {
			resultado.add(traduceAEntidad(dto));
		}
		return resultado;
	}

}
