package es.cic.curso.curso17.ejercicio028.servicio;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.curso17.ejercicio028.dto.MedicamentoDTO;

@Service
@Transactional
public class ServicioMedicamentoImpl implements ServicioMedicamento {

	@Override
	public void agregaMedicamento(MedicamentoDTO medicamento) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public MedicamentoDTO obtenMedicamento(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MedicamentoDTO modificaMedicamento(Long id, MedicamentoDTO medicamento) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MedicamentoDTO eliminaMedicamento(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MedicamentoDTO> listaMedicamentos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MedicamentoDTO> listaMedicamentosPorTipo(Long idTipoMedicamento) {
		// TODO Auto-generated method stub
		return null;
	}

}
