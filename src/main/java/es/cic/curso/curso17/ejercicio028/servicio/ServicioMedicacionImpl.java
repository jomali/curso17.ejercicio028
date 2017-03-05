package es.cic.curso.curso17.ejercicio028.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.curso17.ejercicio028.dto.MedicamentoDTO;
import es.cic.curso.curso17.ejercicio028.dto.MedicamentoDTOTraductor;
import es.cic.curso.curso17.ejercicio028.modelo.Enfermedad;
import es.cic.curso.curso17.ejercicio028.modelo.Medicacion;
import es.cic.curso.curso17.ejercicio028.modelo.Medicamento;
import es.cic.curso.curso17.ejercicio028.repositorio.RepositorioEnfermedad;
import es.cic.curso.curso17.ejercicio028.repositorio.RepositorioMedicacion;
import es.cic.curso.curso17.ejercicio028.repositorio.RepositorioMedicamento;

@Service
@Transactional
public class ServicioMedicacionImpl implements ServicioMedicacion {

	private static final String ERROR_ID = "No existe ningún registro en BB.DD. con ese ID";

	@Autowired
	private RepositorioEnfermedad repositorioEnfermedad;

	@Autowired
	private RepositorioMedicamento repositorioMedicamento;

	@Autowired
	private RepositorioMedicacion repositorioMedicacion;

	@Autowired
	private MedicamentoDTOTraductor traductor;

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
	public void agregaPorEnfermedad(Long idEnfermedad, List<MedicamentoDTO> dtos) {
		Enfermedad enfermedad = obtenEnfermedad(idEnfermedad);
		for (MedicamentoDTO dto : dtos) {
			Medicamento medicamento = obtenMedicamento(dto);
			Medicacion entrada = new Medicacion();
			entrada.setEnfermedad(enfermedad);
			entrada.setMedicamento(medicamento);
			repositorioMedicacion.create(entrada);
		}
	}

	@Override
	public List<MedicamentoDTO> eliminaPorEnfermedad(Long idEnfermedad) {
		List<MedicamentoDTO> medicamentos = listaPorEnfermedad(idEnfermedad);
		repositorioMedicacion.deleteByDisease(idEnfermedad);
		return medicamentos;
	}

	@Override
	public List<MedicamentoDTO> listaPorEnfermedad(Long idEnfermedad) {
		obtenEnfermedad(idEnfermedad);
		List<Medicamento> medicamentos = repositorioMedicamento.listByDisease(idEnfermedad);
		return traductor.traduceAListaDTOs(medicamentos);
	}

}
