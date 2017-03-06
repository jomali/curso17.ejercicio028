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

import es.cic.curso.curso17.ejercicio028.modelo.TipoMedicamento;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:es/cic/curso/curso17/ejercicio028/applicationContext.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class })
@Transactional
public class ServicioTipoMedicamentoTest {

	@Autowired
	private ServicioTipoMedicamento sut;
	
	@Ignore
	@Test
	public void testAgregaTipoMedicamento() {
		TipoMedicamento tipoMedicamento = null;
		sut.agregaTipoMedicamento(tipoMedicamento);
	}

	@Ignore
	@Test
	public void testObtenTipoMedicamento() {
		Long id = null;
		TipoMedicamento resultado = sut.obtenTipoMedicamento(id);
	}

	@Ignore
	@Test
	public void testModificaTipoMedicamento() {
		Long id = null;
		TipoMedicamento tipoMedicamento = null;
		TipoMedicamento resultado = sut.modificaTipoMedicamento(id, tipoMedicamento);
	}

	@Ignore
	@Test
	public void testEliminaTipoMedicamento() {
		Long id = null;
		TipoMedicamento resultado = sut.eliminaTipoMedicamento(id);
	}

	@Ignore
	@Test
	public void testListaTiposMedicamento() {
		List<TipoMedicamento> lista = sut.listaTiposMedicamento();
	}

	@Ignore
	@Test
	public void testListaTiposMedicamentoOrdenada() {
		List<TipoMedicamento> lista = sut.listaTiposMedicamentoOrdenada();
	}

}
