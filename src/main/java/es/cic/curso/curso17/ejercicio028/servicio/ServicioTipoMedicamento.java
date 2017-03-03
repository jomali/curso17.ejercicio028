package es.cic.curso.curso17.ejercicio028.servicio;

import java.util.List;

import es.cic.curso.curso17.ejercicio028.modelo.TipoMedicamento;

public interface ServicioTipoMedicamento {

	void agregaTipoMedicamento(TipoMedicamento tipoMedicamento);

	TipoMedicamento obtenTipoMedicamento(Long id);

	TipoMedicamento modificaTipoMedicamento(Long id, TipoMedicamento tipoMedicamento);

	TipoMedicamento eliminaTipoMedicamento(Long id);

	List<TipoMedicamento> listaTiposMedicamento();

}
