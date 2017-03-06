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

import es.cic.curso.curso17.ejercicio028.dto.EnfermedadDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:es/cic/curso/curso17/ejercicio028/applicationContext.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class })
@Transactional
public class ServicioEnfermedadTest {
	
	@Autowired
	private ServicioEnfermedad sut;

	@Ignore
	@Test
	public void testAgregaEnfermedad() {
		EnfermedadDTO dto = null;
		sut.agregaEnfermedad(dto);
	}

	@Ignore
	@Test
	public void testObtenEnfermedad() {
		Long id = null;
		EnfermedadDTO resultado = sut.obtenEnfermedad(id);
	}

	@Ignore
	@Test
	public void testModificaEnfermedad() {
		Long id = null;
		EnfermedadDTO dto = null;
		EnfermedadDTO resultado = sut.modificaEnfermedad(id, dto);
	}

	@Ignore
	@Test
	public void testEliminaEnfermedad() {
		Long id = null;
		EnfermedadDTO resultado = sut.eliminaEnfermedad(id);
	}

	@Ignore
	@Test
	public void testListaEnfermedades() {
		List<EnfermedadDTO> lista = sut.listaEnfermedades();
	}

}
