package es.cic.curso.curso17.ejercicio028.iu;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.context.ContextLoader;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.Window;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.Notification.Type;

import es.cic.curso.curso17.ejercicio028.dto.EnfermedadDTO;
import es.cic.curso.curso17.ejercicio028.dto.MedicamentoDTO;
import es.cic.curso.curso17.ejercicio028.modelo.Receta;
import es.cic.curso.curso17.ejercicio028.servicio.ServicioEnfermedad;
import es.cic.curso.curso17.ejercicio028.servicio.ServicioGestorRecetas;
import es.cic.curso.curso17.ejercicio028.servicio.ServicioMedicamento;

public class VistaNuevaReceta extends VerticalLayout implements View {
	private static final long serialVersionUID = -8229167069516384540L;

	protected static final float ANCHO_VENTANA_CONFIRMACION = 420.0F; // px
	protected static final float ALTO_VENTANA_CONFIRMACION = 260.0F; // px

	public static final String TITULO = "Nueva Receta";

	/** Permite navegar entre las vistas de la aplicación. */
	private Navigator navegador;

	/** Lógica de negocio con acceso a BB.DD.: medicamentos */
	private ServicioGestorRecetas servicioGestorRecetas;

	/** Lógica de negocio con acceso a BB.DD.: enfermedades */
	private ServicioEnfermedad servicioEnfermedad;

	/** Lógica de negocio con acceso a BB.DD.: medicamentos */
	private ServicioMedicamento servicioMedicamento;

	private Grid gridEnfermedades;
	private Grid gridMedicamentos;

	public VistaNuevaReceta(Navigator navegador) {
		this.navegador = navegador;
		servicioGestorRecetas = ContextLoader.getCurrentWebApplicationContext().getBean(ServicioGestorRecetas.class);
		servicioEnfermedad = ContextLoader.getCurrentWebApplicationContext().getBean(ServicioEnfermedad.class);
		servicioMedicamento = ContextLoader.getCurrentWebApplicationContext().getBean(ServicioMedicamento.class);

		setMargin(true);
		setSpacing(true);

		// Layout : ENCABEZADO
		addComponent(new LayoutCabecera(TITULO));

		// Layout : CONTENIDO
		addComponent(inicializaLayoutContenido());
	}

	private VerticalLayout inicializaLayoutContenido() {
		gridEnfermedades = creaGridEnfermedades();
		gridMedicamentos = creaGridMedicamentos();

		VerticalLayout layoutEnfermedades = new VerticalLayout();
		layoutEnfermedades.setMargin(new MarginInfo(false, true, false, false));
		layoutEnfermedades.setSizeFull();
		layoutEnfermedades.setSpacing(true);
		layoutEnfermedades.addComponent(gridEnfermedades);

		VerticalLayout layoutMedicamentos = new VerticalLayout();
		layoutMedicamentos.setMargin(new MarginInfo(false, false, false, true));
		layoutMedicamentos.setSizeFull();
		layoutMedicamentos.setSpacing(true);
		layoutMedicamentos.addComponent(gridMedicamentos);

		HorizontalSplitPanel splitPanelPrincipal = new HorizontalSplitPanel();
		splitPanelPrincipal.setSplitPosition(50.0F, Unit.PERCENTAGE);
		splitPanelPrincipal.setMinSplitPosition(20.0F, Unit.PERCENTAGE);
		splitPanelPrincipal.setMaxSplitPosition(80.0F, Unit.PERCENTAGE);
		splitPanelPrincipal.setLocked(false);
		splitPanelPrincipal.setSizeFull();
		splitPanelPrincipal.setFirstComponent(layoutEnfermedades);
		splitPanelPrincipal.setSecondComponent(layoutMedicamentos);

		Button botonAceptar = new Button("Aceptar");
		botonAceptar.setStyleName("friendly");
		botonAceptar.addClickListener(e -> {
			Collection<Object> seleccion = gridMedicamentos.getSelectedRows();
			// XXX - Ejemplo de uso de Streams en Java8
			List<MedicamentoDTO> medicamentos = seleccion.stream().filter(obj -> obj instanceof MedicamentoDTO)
					.map(obj -> (MedicamentoDTO) obj).collect(Collectors.toList());
			Receta receta = servicioGestorRecetas.agregaReceta(medicamentos);
			navegador.navigateTo(IUPrincipal.VISTA_PRINCIPAL);
			new Notification(String.format("Nueva receta añadida: <strong>\"%s\"</strong>", receta.getId()), "",
					Type.TRAY_NOTIFICATION, true).show(Page.getCurrent());

		});

		Button botonCancelar = new Button("Cancelar");
		botonCancelar.setStyleName("danger");
		botonCancelar.addClickListener(e -> navegador.navigateTo(IUPrincipal.VISTA_PRINCIPAL));

		HorizontalLayout layoutBotones = new HorizontalLayout();
		layoutBotones.setMargin(new MarginInfo(true, false, false, false));
		layoutBotones.setSpacing(true);
		layoutBotones.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
		layoutBotones.addComponent(botonAceptar);
		layoutBotones.addComponent(botonCancelar);

		VerticalLayout layoutContenido = new VerticalLayout();
		layoutContenido.setMargin(false);
		layoutContenido.setSpacing(false);
		layoutContenido.addComponent(creaMigasDePan());
		layoutContenido.addComponent(splitPanelPrincipal);
		layoutContenido.addComponent(layoutBotones);
		layoutContenido.setComponentAlignment(layoutBotones, Alignment.BOTTOM_RIGHT);

		return layoutContenido;
	}

	private HorizontalLayout creaMigasDePan() {
		Button botonPrincipal = new Button(VistaPrincipal.TITULO);
		botonPrincipal.setEnabled(true);
		botonPrincipal.setStyleName("borderless");
		botonPrincipal.addClickListener(e -> navegador.navigateTo(""));

		Button botonSecundario = new Button(TITULO);
		botonSecundario.setEnabled(false);
		botonSecundario.setStyleName("borderless");

		HorizontalLayout layoutMigasDePan = new HorizontalLayout();
		layoutMigasDePan.setMargin(false);
		layoutMigasDePan.setSpacing(false);
		layoutMigasDePan.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
		layoutMigasDePan.addComponent(botonPrincipal);
		layoutMigasDePan.addComponent(new Label(">"));
		layoutMigasDePan.addComponent(botonSecundario);

		return layoutMigasDePan;
	}

	private Grid creaGridEnfermedades() {
		Grid resultado = new Grid();
		resultado.setColumns("nombre", "cie10");
		resultado.setSelectionMode(SelectionMode.MULTI);
		resultado.setSizeFull();
		return resultado;
	}

	private Grid creaGridMedicamentos() {
		Grid resultado = new Grid();
		resultado.setColumns("nombre", "nombreTipo");
		resultado.setSelectionMode(SelectionMode.MULTI);
		resultado.setSizeFull();
		resultado.addSelectionListener(e -> {
			Collection<Object> seleccionEnfermedades = gridEnfermedades.getSelectedRows();
			Collection<Object> seleccionMedicamentos = e.getAdded();
			// XXX - Ejemplo de uso de Streams en Java8
			List<EnfermedadDTO> enfermedades = seleccionEnfermedades.stream()
					.filter(obj -> obj instanceof EnfermedadDTO).map(obj -> (EnfermedadDTO) obj)
					.collect(Collectors.toList());
			List<MedicamentoDTO> medicamentos = seleccionMedicamentos.stream()
					.filter(obj -> obj instanceof MedicamentoDTO).map(obj -> (MedicamentoDTO) obj)
					.collect(Collectors.toList());
			for (MedicamentoDTO medicamento : medicamentos) {
				if (!servicioGestorRecetas.comprueba(enfermedades, medicamento)) {
					this.getUI().getUI().addWindow(creaVentanaAdvertencia(medicamento));
				}
			}
		});
		return resultado;
	}

	private void cargaGrids() {
		Collection<EnfermedadDTO> enfermedades = servicioEnfermedad.listaEnfermedades();
		Collection<MedicamentoDTO> medicamentos = servicioMedicamento.listaMedicamentos();
		gridEnfermedades.setContainerDataSource(new BeanItemContainer<>(EnfermedadDTO.class, enfermedades));
		gridMedicamentos.setContainerDataSource(new BeanItemContainer<>(MedicamentoDTO.class, medicamentos));
	}

	private Window creaVentanaAdvertencia(MedicamentoDTO medicamento) {
		Window resultado = new Window("Aviso");
		resultado.setModal(true);
		resultado.setClosable(false);
		resultado.setResizable(false);
		resultado.setDraggable(false);
		resultado.setHeight(ALTO_VENTANA_CONFIRMACION, Unit.PIXELS);
		resultado.setWidth(ANCHO_VENTANA_CONFIRMACION, Unit.PIXELS);

		Label label = new Label(
				"Ha indicado una medicación que no está recomendada para el cuadro presentado por el paciente:<br><br><strong>\""
						+ medicamento.getNombre() + "\"</strong>");
		label.setContentMode(ContentMode.HTML);

		VerticalLayout layoutContenido = new VerticalLayout();
		layoutContenido.setMargin(new MarginInfo(false, true, false, true));
		layoutContenido.setSpacing(false);
		layoutContenido.setSizeFull();
		layoutContenido.addComponent(label);

		Button botonAceptar = new Button("Aceptar");
		botonAceptar.addClickListener(e -> resultado.close());

		Button botonCancelar = new Button("Cancelar");
		botonCancelar.addClickListener(e -> {
			gridMedicamentos.deselect(medicamento);
			resultado.close();
		});

		HorizontalLayout layoutBotones = new HorizontalLayout();
		layoutBotones.setMargin(new MarginInfo(false, true, false, true));
		layoutBotones.setSpacing(true);
		layoutBotones.setHeight(100.0F, Unit.PERCENTAGE);
		layoutBotones.setDefaultComponentAlignment(Alignment.MIDDLE_RIGHT);
		layoutBotones.addComponents(botonAceptar, botonCancelar);

		VerticalSplitPanel layoutPrincipal = new VerticalSplitPanel();
		layoutPrincipal.setSplitPosition(90.0F, Unit.PIXELS, true);
		layoutPrincipal.setLocked(true);
		layoutPrincipal.setFirstComponent(layoutContenido);
		layoutPrincipal.setSecondComponent(layoutBotones);

		resultado.setContent(layoutPrincipal);
		resultado.center();
		return resultado;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		cargaGrids();
	}

}
