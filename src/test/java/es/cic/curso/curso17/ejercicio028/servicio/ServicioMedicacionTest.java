package es.cic.curso.curso17.ejercicio028.servicio;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import es.cic.curso.curso17.ejercicio028.dto.MedicamentoDTO;

public class ServicioMedicacionTest {

	@Autowired
	private ServicioMedicacion sut;

	@Ignore
	@Test
	public void testAgregaPorEnfermedad() {
		Long id = null;
		List<MedicamentoDTO> lista = null;
		sut.agregaPorEnfermedad(id, lista);
	}

	@Ignore
	@Test
	public void testEliminaPorEnfermedad() {
		Long id = null;
		List<MedicamentoDTO> lista = sut.eliminaPorEnfermedad(id);
	}

	@Ignore
	@Test
	public void testListaPorEnfermedad() {
		Long id = null;
		List<MedicamentoDTO> lista = sut.listaPorEnfermedad(id);
	}

}
