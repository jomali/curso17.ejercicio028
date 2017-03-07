package es.cic.curso.curso17.ejercicio028.repositorio;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.junit.Before;
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
import es.cic.curso.curso17.ejercicio028.modelo.Receta;
import es.cic.curso.curso17.ejercicio028.modelo.TipoMedicamento;
import es.cic.curso.curso17.ejercicio028.modelo.Tratamiento;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:es/cic/curso/curso17/ejercicio028/applicationContext.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class })
@Transactional
public class RepositorioTratamientoTest {


	public static final int NUMERO_ELEMENTOS = 100;

	@Autowired
	private RepositorioTratamiento sut;

	@PersistenceContext
	protected EntityManager em;
	
	private Receta receta;

	private Enfermedad generaEnfermedadPrueba() {
		Enfermedad elemento = new Enfermedad();
		elemento.setNombre("enfermedad");
		elemento.setCie10("U00-U99");
		elemento.setDescripcion("descripción de la enfermedad");
		em.persist(elemento);
		em.flush();
		return elemento;
	}
	
	private Receta generaReceta() {
		Receta elemento = new Receta();
		elemento.setNombre("Receta");
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

	private Tratamiento generaElementoPrueba() {
		Tratamiento elemento = new Tratamiento();
		elemento.setReceta(receta);
		elemento.setEnfermedad(generaEnfermedadPrueba());
		elemento.setMedicamento(generaMedicamentoPrueba());
		em.persist(elemento);
		em.flush();
		return elemento;
	}
	
	@Before
	public void setUp() {
		receta = generaReceta();
	}

	@Test
	public void testCreate() {		
		Tratamiento elemento  = new Tratamiento();
		elemento.setReceta(receta);
		elemento.setEnfermedad(generaEnfermedadPrueba());
		elemento.setMedicamento(generaMedicamentoPrueba());
		sut.create(elemento);
		assertNotNull(elemento.getId());
	}

	@Test
	public void testRead() {
		Tratamiento elemento1 = generaElementoPrueba();
		Tratamiento elemento2 = sut.read(elemento1.getId());

		assertTrue(elemento1.getId().equals(elemento2.getId()));

		try {
			@SuppressWarnings("unused")
			Tratamiento elemento3 = sut.read(Long.MIN_VALUE);
			fail("No deberían existir elementos con el ID pasado");
		} catch (PersistenceException pe) {

		}
	}

	@Test
	public void testDelete() {
		Tratamiento elemento = generaElementoPrueba();
		sut.delete(elemento.getId());

		Tratamiento resultado = sut.read(elemento.getId());
		assertNull(resultado);
	}

	@Test
	public void testList() {
		for (int i = 0; i < NUMERO_ELEMENTOS; i++) {
			generaElementoPrueba();
		}

		List<Tratamiento> lista = sut.list();
		assertEquals(NUMERO_ELEMENTOS, lista.size());
	}

}
