package es.cic.curso.curso17.ejercicio028.iu;

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
		servicioTipoMedicamento = ContextLoader.getCurrentWebApplicationContext()
				.getBean(ServicioTipoMedicamento.class);
		servicioMedicamento = ContextLoader.getCurrentWebApplicationContext().getBean(ServicioMedicamento.class);
	}

	@Override
	public void enter(ViewChangeEvent event) {

		// Inicialización de enfermedades
		if (servicioEnfermedad.listaEnfermedades().isEmpty()) {
			servicioEnfermedad.agregaEnfermedad(new EnfermedadDTO("lupus eritematoso sistémico", "M32", ""));
			servicioEnfermedad.agregaEnfermedad(new EnfermedadDTO("sarampión", "B05", ""));
			servicioEnfermedad.agregaEnfermedad(new EnfermedadDTO("tripanosomiasis africana", "B56; B50", ""));
			servicioEnfermedad.agregaEnfermedad(new EnfermedadDTO("osteomielitis", "M86", ""));
			servicioEnfermedad.agregaEnfermedad(new EnfermedadDTO("fascitis", "M72.9", ""));
			servicioEnfermedad.agregaEnfermedad(new EnfermedadDTO("porfiria", "E80.0; E80.2", ""));
		}

		// Inicialización de tipos de medicamento
		if (servicioTipoMedicamento.listaTiposMedicamento().isEmpty()) {
			TipoMedicamento tipo1 = new TipoMedicamento();
			tipo1.setNombre("antibiótico");
			tipo1.setDescripcion(
					"Fármacos usados generalmente en el tratamiento de infecciones por bacterias, de ahí que también se les conozca como antibacterianos");
			servicioTipoMedicamento.agregaTipoMedicamento(tipo1);
			TipoMedicamento tipo2 = new TipoMedicamento();
			tipo2.setNombre("mucolítico");
			tipo2.setDescripcion(
					"Los mucolíticos son sustancias que tienen la capacidad de destruir las distintas estructuras quimicofísicas de la secreción bronquial anormal, consiguiendo una disminución de la viscosidad y, de esta forma, una más fácil y pronta eliminación.");
			servicioTipoMedicamento.agregaTipoMedicamento(tipo2);
			TipoMedicamento tipo3 = new TipoMedicamento();
			tipo3.setNombre("antihistamínico");
			tipo3.setDescripcion(
					"Fármacos utilizados para reducir o eliminar los efectos de las alergias, que actúa bloqueando la acción de la histamina en las reacciones alérgicas, a través del bloqueo de sus receptores.");
			servicioTipoMedicamento.agregaTipoMedicamento(tipo3);
			TipoMedicamento tipo4 = new TipoMedicamento();
			tipo4.setNombre("laxante");
			tipo4.setDescripcion(
					"Un laxante es una preparación usada para provocar la defecación o la eliminación de heces.");
			servicioTipoMedicamento.agregaTipoMedicamento(tipo4);
			TipoMedicamento tipo5 = new TipoMedicamento();
			tipo5.setNombre("analgésico");
			tipo5.setDescripcion(
					"Medicamentos para calmar o eliminar el dolor, ya sea de cabeza, muscular, de artrítis, etc.");
			servicioTipoMedicamento.agregaTipoMedicamento(tipo5);
			TipoMedicamento tipo6 = new TipoMedicamento();
			tipo6.setNombre("broncodilatador");
			tipo6.setDescripcion(
					"Medicamentos que causan que los bronquios y bronquiolos de los pulmones se dilaten, provocando una disminución en la resistencia aérea y permitiendo así el flujo de aire.");
			servicioTipoMedicamento.agregaTipoMedicamento(tipo6);
			TipoMedicamento tipo7 = new TipoMedicamento();
			tipo7.setNombre("antiséptico");
			tipo7.setDescripcion(
					"Son sustancias antimicrobianas que se aplican a un tejido vivo o sobre la piel para reducir la posibilidad de infección, sepsis o putrefacción.");
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
