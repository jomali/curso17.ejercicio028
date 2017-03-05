package es.cic.curso.curso17.ejercicio028.repositorio;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.curso17.ejercicio028.modelo.Enfermedad;
import es.cic.curso.curso17.ejercicio028.modelo.Medicacion;
import es.cic.curso.curso17.ejercicio028.modelo.Medicamento;
import es.cic.curso.curso17.ejercicio028.modelo.TipoMedicamento;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:es/cic/curso/curso17/ejercicio028/applicationContext.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class })
@Transactional
public class RepositorioEnfermedadMedicamentoTest {

	public static final int NUMERO_ELEMENTOS = 100;

	@Autowired
	private RepositorioMedicacion sut;

	@PersistenceContext
	protected EntityManager em;

	private Enfermedad generaEnfermedadPrueba() {
		Enfermedad elemento = new Enfermedad();
		elemento.setNombre("enfermedad");
		elemento.setCie10("U00-U99");
		elemento.setDescripcion("descripción de la enfermedad");
		em.persist(elemento);
		em.flush();
		return elemento;
	}
	private TipoMedicamento generaTipoMedicamentoPrueba() {
		TipoMedicamento elemento = new TipoMedicamento();
		elemento.setNombre("tipo de medicamento");
		elemento.setDescripcion("descripción del tipo de medicamento");
		em.persist(elemento);
		em.flush();
		return elemento;
	}

	private Medicamento generaMedicamentoPrueba() {
		Medicamento elemento = new Medicamento();
		elemento.setTipo(generaTipoMedicamentoPrueba());
		elemento.setNombre("medicamento");
		elemento.setDescripcion("descripción del medicamento");
		em.persist(elemento);
		em.flush();
		return elemento;
	}

	private Medicacion generaElementoPrueba() {
		Medicacion elemento = new Medicacion();
		elemento.setEnfermedad(generaEnfermedadPrueba());
		elemento.setMedicamento(generaMedicamentoPrueba());
		em.persist(elemento);
		em.flush();
		return elemento;
	}

	@Test
	public void testCreate() {		
		Medicacion elemento  = new Medicacion();
		elemento.setEnfermedad(generaEnfermedadPrueba());
		elemento.setMedicamento(generaMedicamentoPrueba());
		sut.create(elemento);
		assertNotNull(elemento.getId());
	}

	@Test
	public void testRead() {
		Medicacion elemento1 = generaElementoPrueba();
		Medicacion elemento2 = sut.read(elemento1.getId());

		assertTrue(elemento1.getId().equals(elemento2.getId()));

		try {
			@SuppressWarnings("unused")
			Medicacion elemento3 = sut.read(Long.MIN_VALUE);
			fail("No deberían existir elementos con el ID pasado");
		} catch (PersistenceException pe) {

		}
	}

	@Test
	public void testDelete() {
		Medicacion elemento = generaElementoPrueba();
		sut.delete(elemento.getId());

		Medicacion resultado = sut.read(elemento.getId());
		assertNull(resultado);
	}

	@Test
	public void testList() {
		for (int i = 0; i < NUMERO_ELEMENTOS; i++) {
			generaElementoPrueba();
		}

		List<Medicacion> lista = sut.list();
		assertEquals(NUMERO_ELEMENTOS, lista.size());
	}

}
