package es.cic.curso.curso17.ejercicio028.servicio;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.curso17.ejercicio028.dto.EnfermedadDTO;
import es.cic.curso.curso17.ejercicio028.dto.EnfermedadDTOTraductor;
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
public class ServicioEnfermedadImpl implements ServicioEnfermedad {

	private static final String ERROR_ENFERMEDAD_ID = "No existe ninguna enfermedad en BB.DD. con ese ID";
	private static final String ERROR_ID = "No existe ningún registro en BB.DD. con ese ID";

	@Autowired
	private RepositorioEnfermedad repositorioEnfermedad;

	@Autowired
	private RepositorioMedicamento repositorioMedicamento;

	@Autowired
	private RepositorioMedicacion repositorioMedicacion;

	@Autowired
	private EnfermedadDTOTraductor traductorEnfermedad;

	@Autowired
	private MedicamentoDTOTraductor traductorMedicamento;

	private Enfermedad obtenEntidadEnfermedad(Long id) {
		Enfermedad enfermedad = repositorioEnfermedad.read(id);
		if (enfermedad == null) {
			throw new IllegalArgumentException(ERROR_ENFERMEDAD_ID + ": " + id);
		}
		return enfermedad;
	}

	private Medicamento obtenEntidadMedicamento(MedicamentoDTO dto) {
		Medicamento medicamento = repositorioMedicamento.read(dto.getId());
		if (medicamento == null) {
			throw new IllegalArgumentException(ERROR_ID + ": " + dto.getId());
		}
		return medicamento;
	}

	@Override
	public Long agregaEnfermedad(EnfermedadDTO enfermedad) {
		return repositorioEnfermedad.create(traductorEnfermedad.traduceAEntidad(enfermedad)).getId();
	}

	@Override
	public EnfermedadDTO obtenEnfermedad(Long id) {
		return traductorEnfermedad.traduceADTO(obtenEntidadEnfermedad(id));
	}

	@Override
	public EnfermedadDTO modificaEnfermedad(Long id, EnfermedadDTO enfermedad) {
		EnfermedadDTO resultado = obtenEnfermedad(id);
		enfermedad.setId(id);
		repositorioEnfermedad.update(traductorEnfermedad.traduceAEntidad(enfermedad));
		return resultado;
	}

	@Override
	public EnfermedadDTO eliminaEnfermedad(Long id) {
		EnfermedadDTO resultado = obtenEnfermedad(id);
		repositorioEnfermedad.delete(id);
		return resultado;
	}

	@Override
	public List<EnfermedadDTO> listaEnfermedades() {
		return traductorEnfermedad.traduceAListaDTOs(repositorioEnfermedad.list());
	}

	@Override
	public void agregaMedicacion(Long idEnfermedad, List<MedicamentoDTO> dtos) {
		Enfermedad enfermedad = obtenEntidadEnfermedad(idEnfermedad);
		for (MedicamentoDTO dto : dtos) {
			Medicamento medicamento = obtenEntidadMedicamento(dto);
			Medicacion entrada = new Medicacion();
			entrada.setEnfermedad(enfermedad);
			entrada.setMedicamento(medicamento);
			repositorioMedicacion.create(entrada);
		}
	}

	@Override
	public List<MedicamentoDTO> eliminaTotalMedicacion(Long idEnfermedad) {
		List<MedicamentoDTO> medicamentos = listaMedicacion(idEnfermedad);
		repositorioMedicacion.deleteByDisease(idEnfermedad);
		return medicamentos;
	}

	@Override
	public List<MedicamentoDTO> listaMedicacion(Long idEnfermedad) {
		obtenEntidadEnfermedad(idEnfermedad);
		List<Medicamento> medicamentos = repositorioMedicamento.listByDisease(idEnfermedad);
		return traductorMedicamento.traduceAListaDTOs(medicamentos);
	}

	@Override
	public List<EnfermedadDTO> listaAlVuelo(String cadena) {
		return listaEnfermedades().stream()
				.parallel()
				.filter(obj -> obj.getNombre().contains(cadena))
				.sequential()
				.collect(Collectors.toList());
	}

}
