package es.cic.curso.curso17.ejercicio028.repositorio;

import es.cic.curso.curso17.ejercicio028.modelo.Medicamento;

public class RepositorioMedicamentoImpl extends RepositorioAbstractoImpl<Long, Medicamento> implements RepositorioMedicamento {

	@Override
	public Class<Medicamento> obtenClaseT() {
		return Medicamento.class;
	}

	@Override
	public String obtenNombreTabla() {
		return "MEDICAMENTO";
	}

}
