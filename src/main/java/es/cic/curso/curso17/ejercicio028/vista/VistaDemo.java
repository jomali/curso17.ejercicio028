package es.cic.curso.curso17.ejercicio028.vista;

import org.springframework.web.context.ContextLoader;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.VerticalLayout;

import es.cic.curso.curso17.ejercicio028.dto.EnfermedadDTO;
import es.cic.curso.curso17.ejercicio028.servicio.ServicioEnfermedad;
import es.cic.curso.curso17.ejercicio028.servicio.ServicioMedicamento;
import es.cic.curso.curso17.ejercicio028.servicio.ServicioTipoMedicamento;

public class VistaDemo extends VerticalLayout implements View {
	private static final long serialVersionUID = -4944108531763554734L;

	/** Permite navegar entre las vistas de la aplicación. */
	private Navigator navegador;

	/** Lógica de negocio con acceso a BB.DD.: enfermedades */
	private ServicioEnfermedad servicioEnfermedad;
	
	/** Lógica de negocio con acceso a BB.DD.: tipos de medicamento */
	private ServicioTipoMedicamento servicioTipoMedicamento;
	
	/** Lógica de negocio con acceso a BB.DD.: medicamentos */
	private ServicioMedicamento servicioMedicamento;

	public VistaDemo(Navigator navegador) {
		this.navegador = navegador;
		servicioEnfermedad = ContextLoader.getCurrentWebApplicationContext().getBean(ServicioEnfermedad.class);
	}

	@Override
	public void enter(ViewChangeEvent event) {

		// Inicialización de enfermedades
		if (servicioEnfermedad.listaEnfermedades().isEmpty()) {
			servicioEnfermedad.agregaEnfermedad(new EnfermedadDTO("lupus eritematoso sistémico"));
			servicioEnfermedad.agregaEnfermedad(new EnfermedadDTO("sarampión"));
			servicioEnfermedad.agregaEnfermedad(new EnfermedadDTO("tipanosomiasis africana"));
			servicioEnfermedad.agregaEnfermedad(new EnfermedadDTO("osteomielitis"));
			servicioEnfermedad.agregaEnfermedad(new EnfermedadDTO("fascitis"));
			servicioEnfermedad.agregaEnfermedad(new EnfermedadDTO("porfiria"));
		}
		
		// Inicialización de tipos de medicamento

		navegador.navigateTo(IUPrincipal.VISTA_ADMINISTRACION);
	}
}
