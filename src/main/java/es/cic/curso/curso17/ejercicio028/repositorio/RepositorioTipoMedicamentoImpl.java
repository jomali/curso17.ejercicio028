package es.cic.curso.curso17.ejercicio028.repositorio;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.curso17.ejercicio028.modelo.TipoMedicamento;

@Repository
@Transactional
public class RepositorioTipoMedicamentoImpl extends RepositorioAbstractoImpl<Long, TipoMedicamento>
		implements RepositorioTipoMedicamento {

	@Override
	public Class<TipoMedicamento> obtenClaseT() {
		return TipoMedicamento.class;
	}

	@Override
	public String obtenNombreTabla() {
		return "TIPO_MEDICAMENTO";
	}

}
