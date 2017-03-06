package es.cic.curso.curso17.ejercicio028.repositorio;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.curso17.ejercicio028.modelo.Receta;

@Repository
@Transactional
public class RepositorioRecetaImpl extends RepositorioAbstractoImpl<Long, Receta> implements RepositorioReceta {

	@Override
	public Class<Receta> obtenClaseT() {
		return Receta.class;
	}

	@Override
	public String obtenNombreTabla() {
		return "RECETA";
	}

}
