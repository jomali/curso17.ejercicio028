package es.cic.curso.curso17.ejercicio028.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.curso17.ejercicio028.dto.EnfermedadDTO;
import es.cic.curso.curso17.ejercicio028.dto.MedicamentoDTO;
import es.cic.curso.curso17.ejercicio028.dto.MedicamentoDTOTraductor;
import es.cic.curso.curso17.ejercicio028.modelo.Receta;
import es.cic.curso.curso17.ejercicio028.modelo.Tratamiento;
import es.cic.curso.curso17.ejercicio028.repositorio.RepositorioReceta;
import es.cic.curso.curso17.ejercicio028.repositorio.RepositorioTratamiento;

@Service
@Transactional
public class ServicioGestorRecetasImpl implements ServicioGestorRecetas {

	private static final String ERROR_RECETA_ID = "No existe ninguna receta en BB.DD. con ese ID";

	@Autowired
	private RepositorioReceta repositorioReceta;

	@Autowired
	private RepositorioTratamiento repositorioTratamiento;

	@Autowired
	private MedicamentoDTOTraductor traductorMedicamento;
	
	@Autowired
	private ServicioEnfermedad servicioEnfermedad;
	
	@Override
	public boolean comprueba(List<EnfermedadDTO> enfermedades, MedicamentoDTO medicamento) {
		// TODO - Refactorizar
		for (EnfermedadDTO enfermedad : enfermedades) {
			List<MedicamentoDTO> medicamentos = servicioEnfermedad.listaMedicacion(enfermedad.getId());
			for (MedicamentoDTO m : medicamentos) {
				if (m.equals(medicamento)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public Receta agregaReceta(List<MedicamentoDTO> medicamentos) {
		Receta receta = new Receta();
		repositorioReceta.create(receta);
		for (MedicamentoDTO medicamento : medicamentos) {
			Tratamiento tratamiento = new Tratamiento();
			tratamiento.setReceta(receta);
			tratamiento.setMedicamento(traductorMedicamento.traduceAEntidad(medicamento));
			repositorioTratamiento.create(tratamiento);
		}
		return receta;
	}

	@Override
	public Receta obtenReceta(Long id) {
		Receta receta = repositorioReceta.read(id);
		if (receta == null) {
			throw new IllegalArgumentException(ERROR_RECETA_ID + ": " + id);
		}
		return receta;
	}

	@Override
	public List<Receta> listaRecetas() {
		return repositorioReceta.list();
	}

}
