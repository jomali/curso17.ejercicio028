package es.cic.curso.curso17.ejercicio028.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.curso17.ejercicio028.modelo.TipoMedicamento;
import es.cic.curso.curso17.ejercicio028.repositorio.RepositorioTipoMedicamento;

@Service
@Transactional
public class ServicioTipoMedicamentoImpl implements ServicioTipoMedicamento {

	private static final String ERROR_TIPO_MEDICAMENTO_ID = "No existe ningún tipo de medicamento en BB.DD. con ese ID";

	@Autowired
	private RepositorioTipoMedicamento repositorioTipoMedicamento;

	@Override
	public void agregaTipoMedicamento(TipoMedicamento tipoMedicamento) {
		repositorioTipoMedicamento.create(tipoMedicamento);
	}

	@Override
	public TipoMedicamento obtenTipoMedicamento(Long id) {
		TipoMedicamento tipoMedicamento = repositorioTipoMedicamento.read(id);
		if (tipoMedicamento == null) {
			throw new IllegalArgumentException(ERROR_TIPO_MEDICAMENTO_ID + ": " + id);
		}
		return tipoMedicamento;
	}

	@Override
	public TipoMedicamento modificaTipoMedicamento(Long id, TipoMedicamento tipoMedicamento) {
		TipoMedicamento resultado = obtenTipoMedicamento(id);
		tipoMedicamento.setId(id);
		repositorioTipoMedicamento.update(tipoMedicamento);
		return resultado;
	}

	@Override
	public TipoMedicamento eliminaTipoMedicamento(Long id) {
		TipoMedicamento resultado = obtenTipoMedicamento(id);
		repositorioTipoMedicamento.delete(id);
		return resultado;
	}

	@Override
	public List<TipoMedicamento> listaTiposMedicamento() {
		return repositorioTipoMedicamento.list();
	}
	
	@Override
	public List<TipoMedicamento> listaTiposMedicamentoOrdenada() {
		List<TipoMedicamento> resultado = repositorioTipoMedicamento.listInOrder();
		TipoMedicamento tipoVacio = new TipoMedicamento();
		tipoVacio.setNombre("-- General --");
		tipoVacio.setId(-1L);
		resultado.add(0, tipoVacio);
		return resultado;
	}

}
