package es.cic.curso.curso17.ejercicio028.servicio;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
import es.cic.curso.curso17.ejercicio028.dto.MedicamentoDTO;
import es.cic.curso.curso17.ejercicio028.modelo.TipoMedicamento;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:es/cic/curso/curso17/ejercicio028/applicationContext.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class })
@Transactional
public class ServicioEnfermedadTest {

	public static final int NUMERO_ELEMENTOS = 100;
	
	@Autowired
	private ServicioEnfermedad sut;

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

	private MedicamentoDTO generaMedicamentoPrueba(String nombre) {
		MedicamentoDTO elemento = new MedicamentoDTO();
		elemento.setTipo(generaTipoMedicamentoPrueba());
		elemento.setNombre(nombre == null ? "medicamento" : nombre);
		elemento.setDescripcion("descripción del medicamento");
		return elemento;
	}

	private Long creaElementoPrueba(String nombre) {
		EnfermedadDTO elemento = new EnfermedadDTO();
		elemento.setNombre(nombre == null ? "enfermedad" : nombre);
		elemento.setCie10("U00-U99");
		elemento.setDescripcion("descripción de la enfermedad");
		return sut.agregaEnfermedad(elemento);
	}

	@Test
	public void testAgregaEnfermedad() {
		EnfermedadDTO elemento = new EnfermedadDTO();
		elemento.setNombre("enfermedad");
		elemento.setCie10("U00-U99");
		Long resultado = sut.agregaEnfermedad(elemento);
		assertNotNull(resultado);
	}

	@Test
	public void testObtenEnfermedad() {
		EnfermedadDTO resultado;

		try {
			@SuppressWarnings("unused")
			EnfermedadDTO otro = sut.obtenEnfermedad(0L);
			fail("No deberían existir elementos con el ID pasado");
		} catch (IllegalArgumentException iae) {

		}
		

		Long id = creaElementoPrueba(null);
		resultado = sut.obtenEnfermedad(id);
		assertTrue(id.equals(resultado.getId()));
	}

	@Test
	public void testModificaEnfermedad() {
		Long id = creaElementoPrueba(null);
		
		EnfermedadDTO resultado1 = sut.obtenEnfermedad(id);
		assertTrue("enfermedad".equals(resultado1.getNombre()));
		resultado1.setNombre("modificado");
		sut.modificaEnfermedad(id, resultado1);
		
		EnfermedadDTO resultado2 = sut.obtenEnfermedad(id);
		assertTrue("modificado".equals(resultado2.getNombre()));
	}
	

	@Test
	public void testEliminaEnfermedad() {
		EnfermedadDTO resultado;
		Long id = creaElementoPrueba(null);
		
		resultado = sut.obtenEnfermedad(id);
		assertNotNull(resultado);
		
		sut.eliminaEnfermedad(id);
		try {
			resultado = sut.obtenEnfermedad(id);
			fail("El elemento no debería existir en el sistema");
		} catch (IllegalArgumentException iae) {
			
		}
	}

	@Test
	public void testListaEnfermedades() {
		for (int i = 0; i < NUMERO_ELEMENTOS; i++) {
			creaElementoPrueba("" + i);
		}
		List<EnfermedadDTO> lista = sut.listaEnfermedades();
		assertEquals(NUMERO_ELEMENTOS, lista.size());
	}

	@Ignore
	@Test
	public void testMedicacion() {
		List<MedicamentoDTO> lista = new ArrayList<>();
		for (int i = 0; i < NUMERO_ELEMENTOS; i++) {
			lista.add(generaMedicamentoPrueba("" + i));
		}
		// 1.a) Agrega Medicación. ERROR
		try {
			sut.agregaMedicacion(0L, lista);
			fail("No debería exisitr esa enfermedad en el sistema");
		} catch (IllegalArgumentException iae) {
			
		}
		
		// 1.b) Agrega Medicación. ÉXITO
		Long id = creaElementoPrueba(null);
		sut.agregaMedicacion(id, lista);
		
		// 2) Lista medicación
		lista = sut.listaMedicacion(id);
		assertEquals(NUMERO_ELEMENTOS, lista.size());
		
		// 3) Elimina medicación
		sut.eliminaTotalMedicacion(id);
		lista = sut.listaMedicacion(id);
		assertEquals(0, lista.size());
	}

}
