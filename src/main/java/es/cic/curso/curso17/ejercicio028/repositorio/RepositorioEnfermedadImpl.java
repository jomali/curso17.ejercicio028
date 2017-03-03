package es.cic.curso.curso17.ejercicio028.repositorio;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.curso17.ejercicio028.modelo.Enfermedad;

@Repository
@Transactional
public class RepositorioEnfermedadImpl extends RepositorioAbstractoImpl<Long, Enfermedad>
		implements RepositorioEnfermedad {

	@Override
	public Class<Enfermedad> obtenClaseT() {
		return Enfermedad.class;
	}

	@Override
	public String obtenNombreTabla() {
		return "ENFERMEDAD";
	}

}
