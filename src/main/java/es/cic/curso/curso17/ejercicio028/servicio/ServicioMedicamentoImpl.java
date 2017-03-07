package es.cic.curso.curso17.ejercicio028.servicio;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.curso17.ejercicio028.dto.MedicamentoDTO;
import es.cic.curso.curso17.ejercicio028.dto.MedicamentoDTOTraductor;
import es.cic.curso.curso17.ejercicio028.modelo.Medicamento;
import es.cic.curso.curso17.ejercicio028.repositorio.RepositorioMedicamento;

@Service
@Transactional
public class ServicioMedicamentoImpl implements ServicioMedicamento {

	private static final String ERROR_MEDICAMENTO_ID = "No existe ningún medicamento en BB.DD. con ese ID";

	@Autowired
	private RepositorioMedicamento repositorioMedicamento;

	@Autowired
	private MedicamentoDTOTraductor traductor;

	@Autowired
	private ServicioTipoMedicamento servicioTipoMedicamento;

	/**
	 * Utiliza el <code>ServicioTipoMedicamento</code> para comprobar si el
	 * identificador pasado como parámetro se corresponde con un tipo de
	 * medicamento registrado en BB.DD.
	 * 
	 * @param id Identificador del tipo de medicamento
	 */
	private void compruebaTipoMedicamento(Long idTipoMedicamento) {
		servicioTipoMedicamento.obtenTipoMedicamento(idTipoMedicamento);

	}

	@Override
	public Long agregaMedicamento(MedicamentoDTO medicamento) {
		return repositorioMedicamento.create(traductor.traduceAEntidad(medicamento)).getId();
	}

	@Override
	public MedicamentoDTO obtenMedicamento(Long id) {
		Medicamento medicamento = repositorioMedicamento.read(id);
		if (medicamento == null) {
			throw new IllegalArgumentException(ERROR_MEDICAMENTO_ID + ": " + id);
		}
		return traductor.traduceADTO(medicamento);
	}

	@Override
	public MedicamentoDTO modificaMedicamento(Long id, MedicamentoDTO medicamento) {
		MedicamentoDTO resultado = obtenMedicamento(id);
		if (medicamento.getTipo() != null) {
			compruebaTipoMedicamento(medicamento.getTipo().getId());
		}
		medicamento.setId(id);
		repositorioMedicamento.update(traductor.traduceAEntidad(medicamento));
		return resultado;
	}

	@Override
	public MedicamentoDTO eliminaMedicamento(Long id) {
		MedicamentoDTO resultado = obtenMedicamento(id);
		repositorioMedicamento.delete(id);
		return resultado;
	}

	@Override
	public List<MedicamentoDTO> listaMedicamentos() {
		return traductor.traduceAListaDTOs(repositorioMedicamento.list());
	}

	@Override
	public List<MedicamentoDTO> listaMedicamentosPorTipo(Long idTipoMedicamento) {
		compruebaTipoMedicamento(idTipoMedicamento);
		return traductor.traduceAListaDTOs(repositorioMedicamento.listByType(idTipoMedicamento));
	}

	@Override
	public List<MedicamentoDTO> listaAlVuelo(String cadena) {
		return listaMedicamentos().stream()
				.parallel()
				.filter(obj -> obj.getNombre().contains(cadena))
				.sequential()
				.collect(Collectors.toList());
	}

}
