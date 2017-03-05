package es.cic.curso.curso17.ejercicio028.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.curso17.ejercicio028.dto.MedicamentoDTO;
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
		Enfermedad enfermedad = repositorioEnfermedad.read(id);
		if (enfermedad == null) {
			throw new IllegalArgumentException(ERROR_ID + ": " + id);
		}
		return enfermedad;
	}

	private Medicamento obtenMedicamento(MedicamentoDTO dto) {
		Medicamento medicamento = repositorioMedicamento.read(dto.getId());
		if (medicamento == null) {
			throw new IllegalArgumentException(ERROR_ID + ": " + dto.getId());
		}
		return medicamento;
	}
	
	@Override
	public void agregaPorEnfermedad(Long idEnfermedad, MedicamentoDTO ... dtos) {
		Enfermedad enfermedad = obtenEnfermedad(idEnfermedad);
		for (MedicamentoDTO dto : dtos) {
			Medicamento medicamento = obtenMedicamento(dto);
			EnfermedadMedicamento entrada = new EnfermedadMedicamento();
			entrada.setEnfermedad(enfermedad);
			entrada.setMedicamento(medicamento);
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
