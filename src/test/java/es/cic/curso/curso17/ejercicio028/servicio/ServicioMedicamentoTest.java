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
public class ServicioMedicamentoTest {

	@Autowired
	private ServicioMedicamento sut;
	
	@Ignore
	@Test
	public void testAgregaMedicamento() {
		MedicamentoDTO medicamento = null;
		sut.agregaMedicamento(medicamento);
	}

	@Ignore
	@Test
	public void testObtenMedicamento() {
		Long id = null;
		MedicamentoDTO resultado = sut.obtenMedicamento(id);
	}

	@Ignore
	@Test
	public void testModificaMedicamento() {
		Long id = null;
		MedicamentoDTO medicamento = null;
		MedicamentoDTO resultado = sut.modificaMedicamento(id, medicamento);
	}

	@Ignore
	@Test
	public void testEliminaMedicamento() {
		Long id = null;
		MedicamentoDTO resultado = sut.eliminaMedicamento(id);
	}

	@Ignore
	@Test
	public void testListaMedicamentos() {
		List<MedicamentoDTO> lista = sut.listaMedicamentos();
	}

	@Ignore
	@Test
	public void testListaMedicamentosPorTipo() {
		Long id = null;
		List<MedicamentoDTO> lista = sut.listaMedicamentosPorTipo(id);
	}

}
