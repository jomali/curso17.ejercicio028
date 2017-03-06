package es.cic.curso.curso17.ejercicio028.repositorio;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.curso17.ejercicio028.modelo.Tratamiento;

@Repository
@Transactional
public class RepositorioTratamientoImpl extends RepositorioAbstractoImpl<Long, Tratamiento>
		implements RepositorioTratamiento {

	@Override
	public Class<Tratamiento> obtenClaseT() {
		return Tratamiento.class;
	}

	@Override
	public String obtenNombreTabla() {
		return "TRATAMIENTO";
	}


}
