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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:es/cic/curso/curso17/ejercicio028/applicationContext.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class })
@Transactional
public class RepositorioEnfermedadTest {

	public static final int NUMERO_ELEMENTOS = 100;

	@Autowired
	private RepositorioEnfermedad sut;

	@PersistenceContext
	protected EntityManager em;

	private Enfermedad generaElementoPrueba(String nombre) {
		Enfermedad elemento = new Enfermedad();
		elemento.setNombre(nombre == null ? "enfermedad" : nombre);
		elemento.setCie10("U00-U99");
		elemento.setDescripcion("descripción de la enfermedad");
		em.persist(elemento);
		em.flush();
		return elemento;
	}

	@Test
	public void testCreate() {		
		Enfermedad elemento  = new Enfermedad();
		elemento.setNombre("enfermedad");
		elemento.setCie10("U00-U99");
		sut.create(elemento);
		assertNotNull(elemento.getId());
	}

	@Test
	public void testRead() {
		Enfermedad elemento1 = generaElementoPrueba(null);
		Enfermedad elemento2 = sut.read(elemento1.getId());

		assertTrue(elemento1.getId().equals(elemento2.getId()));

		try {
			@SuppressWarnings("unused")
			Enfermedad elemento3 = sut.read(Long.MIN_VALUE);
			fail("No deberían existir elementos con el ID pasado");
		} catch (PersistenceException pe) {

		}
	}

	@Test
	public void testUpdate() {
		Enfermedad original = generaElementoPrueba(null);
		Enfermedad clon = original.clone();

		original.setNombre("Modificado");
		sut.update(original);

		Enfermedad modificado = sut.read(original.getId());
		assertTrue(original.getNombre().equals(modificado.getNombre()));
		assertFalse(clon.getNombre().equals(modificado.getNombre()));
	}

	@Test
	public void testDelete() {
		Enfermedad elemento = generaElementoPrueba(null);
		sut.delete(elemento.getId());

		Enfermedad resultado = sut.read(elemento.getId());
		assertNull(resultado);
	}

	@Test
	public void testList() {
		for (int i = 0; i < NUMERO_ELEMENTOS; i++) {
			generaElementoPrueba(null);
		}

		List<Enfermedad> lista = sut.list();
		assertEquals(NUMERO_ELEMENTOS, lista.size());
	}

}
