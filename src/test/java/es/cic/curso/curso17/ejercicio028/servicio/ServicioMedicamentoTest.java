package es.cic.curso.curso17.ejercicio028.servicio;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
import es.cic.curso.curso17.ejercicio028.modelo.TipoMedicamento;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:es/cic/curso/curso17/ejercicio028/applicationContext.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class })
@Transactional
public class ServicioMedicamentoTest {

	public static final int NUMERO_ELEMENTOS = 100;

	@Autowired
	private ServicioMedicamento sut;

	@PersistenceContext
	protected EntityManager em;

	private TipoMedicamento generaTipoMedicamentoPrueba() {
		TipoMedicamento elemento = new TipoMedicamento();
		elemento.setNombre("tipo de medicamento");
		elemento.setDescripcion("descripción del tipo de medicamento");
		em.persist(elemento);
		em.flush();
		return elemento;
	}

	private Long creaElementoPrueba(String nombre, TipoMedicamento tipo) {
		MedicamentoDTO elemento = new MedicamentoDTO();
		elemento.setTipo(tipo == null ? generaTipoMedicamentoPrueba() : tipo);
		elemento.setNombre(nombre == null ? "medicamento" : nombre);
		elemento.setDescripcion("descripción del medicamento");
		return sut.agregaMedicamento(elemento);
	}

	@Test
	public void testAgregaMedicamento() {
		MedicamentoDTO elemento = new MedicamentoDTO();
		elemento.setNombre("medicamento");
		elemento.setTipo(generaTipoMedicamentoPrueba());
		Long resultado = sut.agregaMedicamento(elemento);
		assertNotNull(resultado);
	}

	@Test
	public void testObtenMedicamento() {
		MedicamentoDTO resultado;

		try {
			@SuppressWarnings("unused")
			MedicamentoDTO otro = sut.obtenMedicamento(0L);
			fail("No deberían existir elementos con el ID pasado");
		} catch (IllegalArgumentException iae) {

		}

		Long id = creaElementoPrueba(null, null);
		resultado = sut.obtenMedicamento(id);
		assertTrue(id.equals(resultado.getId()));
	}

	@Test
	public void testModificaEnfermedad() {
	}

	@Test
	public void testModificaMedicamento() {
		Long id = creaElementoPrueba(null, null);

		MedicamentoDTO resultado1 = sut.obtenMedicamento(id);
		assertTrue("medicamento".equals(resultado1.getNombre()));
		resultado1.setNombre("modificado");
		sut.modificaMedicamento(id, resultado1);

		MedicamentoDTO resultado2 = sut.obtenMedicamento(id);
		assertTrue("modificado".equals(resultado2.getNombre()));
	}

	@Test
	public void testEliminaMedicamento() {
		MedicamentoDTO resultado;
		Long id = creaElementoPrueba(null, null);

		resultado = sut.obtenMedicamento(id);
		assertNotNull(resultado);

		sut.eliminaMedicamento(id);
		try {
			resultado = sut.obtenMedicamento(id);
			fail("El elemento no debería existir en el sistema");
		} catch (IllegalArgumentException iae) {

		}
	}

	@Test
	public void testListaMedicamentos() {
		TipoMedicamento tipo = generaTipoMedicamentoPrueba();
		for (int i = 0; i < NUMERO_ELEMENTOS; i++) {
			creaElementoPrueba("" + (i * 1), tipo);
		}
		List<MedicamentoDTO> lista = sut.listaMedicamentosPorTipo(tipo.getId());
		assertEquals(NUMERO_ELEMENTOS, lista.size());
	}

	@Test
	public void testListaMedicamentosPorTipo() {
		TipoMedicamento tipo1 = generaTipoMedicamentoPrueba();
		TipoMedicamento tipo2 = generaTipoMedicamentoPrueba();
		for (int i = 0; i < NUMERO_ELEMENTOS; i++) {
			creaElementoPrueba("" + (i * 1), tipo1);
			creaElementoPrueba("" + (i * 1000), tipo1);
			creaElementoPrueba("" + (i * 2000), tipo2);
		}
		List<MedicamentoDTO> lista1 = sut.listaMedicamentosPorTipo(tipo1.getId());
		List<MedicamentoDTO> lista2 = sut.listaMedicamentosPorTipo(tipo2.getId());
		assertEquals(NUMERO_ELEMENTOS * 2, lista1.size());
		assertEquals(NUMERO_ELEMENTOS, lista2.size());
	}

}
