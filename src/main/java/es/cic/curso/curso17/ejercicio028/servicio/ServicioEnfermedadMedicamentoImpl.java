package es.cic.curso.curso17.ejercicio028.servicio;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.curso17.ejercicio028.modelo.Enfermedad;
import es.cic.curso.curso17.ejercicio028.modelo.EnfermedadMedicamento;
import es.cic.curso.curso17.ejercicio028.modelo.Medicamento;
import es.cic.curso.curso17.ejercicio028.repositorio.RepositorioEnfermedad;
import es.cic.curso.curso17.ejercicio028.repositorio.RepositorioEnfermedadMedicamento;
import es.cic.curso.curso17.ejercicio028.repositorio.RepositorioMedicamento;

@Service
@Transactional
public class ServicioEnfermedadMedicamentoImpl implements ServicioEnfermedadMedicamento {

	private static final String ERROR_ID = "No existe ningún registro en BB.DD. con ese ID";

	@Autowired
	private RepositorioEnfermedad repositorioEnfermedad;

	@Autowired
	private RepositorioMedicamento repositorioMedicamento;

	@Autowired
	private RepositorioEnfermedadMedicamento repositorioEnfermedadMedicamento;

	private Enfermedad obtenEnfermedad(Long id) {
		Enfermedad resultado = repositorioEnfermedad.read(id);
		if (resultado == null) {
			throw new IllegalArgumentException(ERROR_ID + ": " + id);
		}
		return resultado;
	}

	private Medicamento compruebaMedicamento(Medicamento medicamento) {
		Medicamento resultado = repositorioMedicamento.read(medicamento.getId());
		if (resultado == null) {
			throw new IllegalArgumentException(ERROR_ID + ": " + medicamento.getId());
		}
		return resultado;
	}

	@Override
	public void agregaPorEnfermedad(Long idEnfermedad, Collection<Medicamento> medicamentos) {
		Enfermedad e = obtenEnfermedad(idEnfermedad);
		for (Medicamento medicamento : medicamentos) {
			Medicamento m = compruebaMedicamento(medicamento);
			EnfermedadMedicamento entrada = new EnfermedadMedicamento();
			entrada.setEnfermedad(e);
			entrada.setMedicamento(m);
			repositorioEnfermedadMedicamento.create(entrada);
		}
	}

	@Override
	public List<EnfermedadMedicamento> eliminaPorEnfermedad(Long idEnfermedad) {
		obtenEnfermedad(idEnfermedad);
		return repositorioEnfermedadMedicamento.deleteByDisease(idEnfermedad);
	}

	@Override
	public List<EnfermedadMedicamento> listaPorEnfermedad(Long idEnfermedad) {
		obtenEnfermedad(idEnfermedad);
		return repositorioEnfermedadMedicamento.listByDisease(idEnfermedad);
	}

}
