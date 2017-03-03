package es.cic.curso.curso17.ejercicio028.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.curso17.ejercicio028.dto.EnfermedadDTO;
import es.cic.curso.curso17.ejercicio028.dto.EnfermedadDTOTraductor;
import es.cic.curso.curso17.ejercicio028.modelo.Enfermedad;
import es.cic.curso.curso17.ejercicio028.repositorio.RepositorioEnfermedad;

@Service
@Transactional
public class ServicioEnfermedadImpl implements ServicioEnfermedad {
	
	private static final String ERROR_ENFERMEDAD_ID = "No existe ninguna enfermedad en BB.DD. con ese ID";

	@Autowired
	private RepositorioEnfermedad repositorioEnfermedad;
	
	@Autowired
	private EnfermedadDTOTraductor traductor;

	@Override
	public void agregaEnfermedad(EnfermedadDTO enfermedad) {
		repositorioEnfermedad.create(traductor.dto2entidad(enfermedad));
	}

	@Override
	public EnfermedadDTO obtenEnfermedad(Long id) {
		Enfermedad enfermedad = repositorioEnfermedad.read(id);
		if (enfermedad == null) {
			throw new IllegalArgumentException(ERROR_ENFERMEDAD_ID + ": " + id);
		}
		return traductor.entidad2dto(enfermedad);
	}
	
	@Override
	public EnfermedadDTO modificaEnfermedad(Long id, EnfermedadDTO enfermedad) {
		EnfermedadDTO resultado = obtenEnfermedad(id);
		enfermedad.setId(id);
		repositorioEnfermedad.update(traductor.dto2entidad(enfermedad));
		return resultado;
	}
	
	@Override
	public EnfermedadDTO eliminaEnfermedad(Long id) {
		EnfermedadDTO resultado = obtenEnfermedad(id);
		repositorioEnfermedad.delete(id);
		return resultado;
	}

	@Override
	public List<EnfermedadDTO> listaEnfermedades() {
		return traductor.entidad2dto(repositorioEnfermedad.list());
	}
	
}
