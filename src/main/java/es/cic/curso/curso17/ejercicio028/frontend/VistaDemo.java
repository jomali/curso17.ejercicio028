package es.cic.curso.curso17.ejercicio028.frontend;

import org.springframework.web.context.ContextLoader;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Notification.Type;

import es.cic.curso.curso17.ejercicio028.dto.EnfermedadDTO;
import es.cic.curso.curso17.ejercicio028.dto.MedicamentoDTO;
import es.cic.curso.curso17.ejercicio028.modelo.TipoMedicamento;
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
		servicioTipoMedicamento = ContextLoader.getCurrentWebApplicationContext().getBean(ServicioTipoMedicamento.class);
		servicioMedicamento = ContextLoader.getCurrentWebApplicationContext().getBean(ServicioMedicamento.class);
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
		if (servicioTipoMedicamento.listaTiposMedicamento().isEmpty()) {
			TipoMedicamento tipo1 = new TipoMedicamento();
			tipo1.setNombre("antibiótico");
			servicioTipoMedicamento.agregaTipoMedicamento(tipo1);
			TipoMedicamento tipo2 = new TipoMedicamento();
			tipo2.setNombre("mucolítico");
			servicioTipoMedicamento.agregaTipoMedicamento(tipo2);
			TipoMedicamento tipo3 = new TipoMedicamento();
			tipo3.setNombre("antihistamínico");
			servicioTipoMedicamento.agregaTipoMedicamento(tipo3);
			TipoMedicamento tipo4 = new TipoMedicamento();
			tipo4.setNombre("laxante");
			servicioTipoMedicamento.agregaTipoMedicamento(tipo4);
			TipoMedicamento tipo5 = new TipoMedicamento();
			tipo5.setNombre("analgésico");
			servicioTipoMedicamento.agregaTipoMedicamento(tipo5);
			TipoMedicamento tipo6 = new TipoMedicamento();
			tipo6.setNombre("broncodilatador");
			servicioTipoMedicamento.agregaTipoMedicamento(tipo6);
			TipoMedicamento tipo7 = new TipoMedicamento();
			tipo7.setNombre("antiséptico");
			servicioTipoMedicamento.agregaTipoMedicamento(tipo7);

			servicioMedicamento.agregaMedicamento(new MedicamentoDTO(tipo4, "cacolax"));
			servicioMedicamento.agregaMedicamento(new MedicamentoDTO(tipo5, "neobrufenol"));
			servicioMedicamento.agregaMedicamento(new MedicamentoDTO(tipo5, "ibuprofenol"));
			servicioMedicamento.agregaMedicamento(new MedicamentoDTO(tipo5, "paracetamol"));
			servicioMedicamento.agregaMedicamento(new MedicamentoDTO(tipo5, "sexinononol"));
			servicioMedicamento.agregaMedicamento(new MedicamentoDTO(null, "requetefortín 2000"));
		}
		
		Notification.show("Cargados datos de DEMOSTRACIÓN.", Type.WARNING_MESSAGE);
		navegador.navigateTo(IUPrincipal.VISTA_ADMINISTRACION);
	}
}
