package es.cic.curso.curso17.ejercicio028.servicio;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.curso17.ejercicio028.dto.MedicamentoDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:es/cic/curso/curso17/ejercicio028/applicationContext.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class })
@Transactional
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
