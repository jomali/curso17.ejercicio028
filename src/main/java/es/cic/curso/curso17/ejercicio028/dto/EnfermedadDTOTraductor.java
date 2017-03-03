package es.cic.curso.curso17.ejercicio028.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import es.cic.curso.curso17.ejercicio028.modelo.Enfermedad;

@Service
public class EnfermedadDTOTraductor {
	
	public EnfermedadDTO entidad2dto(Enfermedad enfermedad) {
		EnfermedadDTO resultado = new EnfermedadDTO();
		resultado.setId(enfermedad.getId());
		resultado.setNombre(enfermedad.getNombre());
		resultado.setDescripcion(enfermedad.getDescripcion());
		return resultado;
	}
	
	public Enfermedad dto2entidad(EnfermedadDTO enfermedadDTO) {
		Enfermedad resultado = new Enfermedad();
		resultado.setId(enfermedadDTO.getId());
		resultado.setNombre(enfermedadDTO.getNombre());
		resultado.setDescripcion(enfermedadDTO.getDescripcion());
		return resultado;
	}
	
	public List<EnfermedadDTO> entidad2dto(List<Enfermedad> enfermedades) {
		List<EnfermedadDTO> resultado = new ArrayList<>();
		for (Enfermedad enfermedad : enfermedades) {
			resultado.add(entidad2dto(enfermedad));
		}
		return resultado;
	}
	
	public List<Enfermedad> dto2entidad(List<EnfermedadDTO> enfermedadesDTO) {
		List<Enfermedad> resultado = new ArrayList<>();
		for (EnfermedadDTO dto : enfermedadesDTO) {
			resultado.add(dto2entidad(dto));
		}
		return resultado;
	}

}
