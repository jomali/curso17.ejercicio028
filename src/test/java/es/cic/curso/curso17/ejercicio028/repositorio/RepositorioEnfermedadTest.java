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

import es.cic.curso.curso17.ejercicio028.modelo.Medicamento;
import es.cic.curso.curso17.ejercicio028.modelo.TipoMedicamento;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:es/cic/curso/curso17/ejercicio028/applicationContext.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class })
@Transactional
public class RepositorioEnfermedadTest {

	public static final int NUMERO_ELEMENTOS = 100;

	@Autowired
	private RepositorioMedicamento sut;

	@PersistenceContext
	protected EntityManager em;
	
	private TipoMedicamento generaSubElementoPrueba() {
		TipoMedicamento subElemento = new TipoMedicamento();
		subElemento.setNombre("tipo medicamento");
		subElemento.setDescripcion("descripción del tipo de medicamento");
		em.persist(subElemento);
		em.flush();
		return subElemento;
	}

	private Medicamento generaElementoPrueba() {
		Medicamento elemento = new Medicamento();
		elemento.setTipo(generaSubElementoPrueba());
		elemento.setNombre("medicamento");
		elemento.setDescripcion("descripción del medicamento");
		em.persist(elemento);
		em.flush();
		return elemento;
	}

	@Test
	public void testCreate() {
		Medicamento elemento;
		
		// 1) Medicamento con tipo vacío
		elemento  = new Medicamento();
		elemento.setNombre("medicamento");
		sut.create(elemento);
		assertNotNull(elemento.getId());
		assertNull(elemento.getTipo());
	}

	@Test
	public void testRead() {
		Medicamento elemento1 = generaElementoPrueba();
		Medicamento elemento2 = sut.read(elemento1.getId());

		assertTrue(elemento1.getId().equals(elemento2.getId()));

		try {
			@SuppressWarnings("unused")
			Medicamento elemento3 = sut.read(Long.MIN_VALUE);
			fail("No deberían existir elementos con el ID pasado");
		} catch (PersistenceException pe) {

		}
	}

	@Test
	public void testUpdate() {
		Medicamento original = generaElementoPrueba();
		Medicamento clon = original.clone();

		original.setNombre("Modificado");
		sut.update(original);

		Medicamento modificado = sut.read(original.getId());
		assertTrue(original.getNombre().equals(modificado.getNombre()));
		assertFalse(clon.getNombre().equals(modificado.getNombre()));
	}

	@Test
	public void testDelete() {
		Medicamento elemento = generaElementoPrueba();
		sut.delete(elemento.getId());

		Medicamento resultado = sut.read(elemento.getId());
		assertNull(resultado);
	}

	@Test
	public void testList() {
		for (int i = 0; i < NUMERO_ELEMENTOS; i++) {
			generaElementoPrueba();
		}

		List<Medicamento> lista = sut.list();
		assertEquals(NUMERO_ELEMENTOS, lista.size());
	}

}
