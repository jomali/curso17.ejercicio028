package es.cic.curso.curso17.ejercicio028.iu.administracion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.web.context.ContextLoader;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.Window;
import com.vaadin.ui.Grid.SelectionMode;

import es.cic.curso.curso17.ejercicio028.dto.EnfermedadDTO;
import es.cic.curso.curso17.ejercicio028.dto.MedicamentoDTO;
import es.cic.curso.curso17.ejercicio028.iu.VistaAdministracion;
import es.cic.curso.curso17.ejercicio028.servicio.ServicioEnfermedad;
import es.cic.curso.curso17.ejercicio028.servicio.ServicioMedicacion;
import es.cic.curso.curso17.ejercicio028.servicio.ServicioMedicamento;

public class LayoutEnfermedades extends LayoutAbstracto<EnfermedadDTO> {
	private static final long serialVersionUID = 6467264543844871753L;

	public static final float POSICION_DIVISOR = 35.0F;

	/** Lógica de negocio con acceso a BB.DD.: enfermedades */
	private ServicioEnfermedad servicioEnfermedad;

	/** Lógica de negocio con acceso a BB.DD.: medicamentos */
	private ServicioMedicamento servicioMedicamento;

	/** Lógica de negocio con acceso a BB.DD.: medicación recomendada */
	private ServicioMedicacion servicioMedicacion;

	private TextField textFieldNombre;

	private TextField textFieldCie10;

	private List<MedicamentoDTO> medicacionRecomendada;

	private TextArea textAreaDescripcion;

	private Button botonAcepta;

	public LayoutEnfermedades(VistaAdministracion padre) {
		super(padre, POSICION_DIVISOR);
		servicioEnfermedad = ContextLoader.getCurrentWebApplicationContext().getBean(ServicioEnfermedad.class);
		servicioMedicamento = ContextLoader.getCurrentWebApplicationContext().getBean(ServicioMedicamento.class);
		servicioMedicacion = ContextLoader.getCurrentWebApplicationContext().getBean(ServicioMedicacion.class);
		refrescaDatos();
	}

	private Window creaVentanaMedicacionRecomendada() {
		Window resultado = new Window();
		String titulo = "Medicación recomendada";
		resultado.setCaption(elementoSeleccionado == null ? titulo
				: String.format("%s: <strong>\"%s\"</strong>", titulo, elementoSeleccionado.getNombre()));
		resultado.setCaptionAsHtml(true);
		resultado.setModal(true);
		resultado.setClosable(true);
		resultado.setResizable(true);
		resultado.setDraggable(true);
		resultado.setHeight(400.0F, Unit.PIXELS);
		resultado.setWidth(600.0F, Unit.PIXELS);

		Grid gridMedicacion = new Grid();
		gridMedicacion.setColumns("nombre", "nombreTipo");
		gridMedicacion.setSelectionMode(SelectionMode.MULTI);
		gridMedicacion.setSizeFull();
		Collection<MedicamentoDTO> medicamentos = servicioMedicamento.listaMedicamentos();
		gridMedicacion.setContainerDataSource(new BeanItemContainer<>(MedicamentoDTO.class, medicamentos));
		for (MedicamentoDTO medicamento : medicacionRecomendada) {
			gridMedicacion.select(medicamento);
		}
		gridMedicacion.addSelectionListener(e -> botonAcepta.setEnabled(true));

		VerticalLayout layoutGrid = new VerticalLayout();
		layoutGrid.setMargin(new MarginInfo(false, true, false, true));
		layoutGrid.setSpacing(false);
		layoutGrid.setSizeFull();
		layoutGrid.addComponent(gridMedicacion);

		Button botonAceptar = new Button("Aceptar");
		botonAceptar.addClickListener(e -> {
			Collection<Object> seleccion = gridMedicacion.getSelectedRows();
			medicacionRecomendada.clear();
			for (Object obj : seleccion) {
				medicacionRecomendada.add((MedicamentoDTO) obj);
			}
			resultado.close();
		});

		Button botonCancelar = new Button("Cancelar");
		botonCancelar.addClickListener(e -> resultado.close());

		HorizontalLayout layoutBotones = new HorizontalLayout();
		layoutBotones.setMargin(new MarginInfo(false, true, false, true));
		layoutBotones.setSpacing(true);
		layoutBotones.setHeight(100.0F, Unit.PERCENTAGE);
		layoutBotones.setDefaultComponentAlignment(Alignment.MIDDLE_RIGHT);
		layoutBotones.addComponents(botonAceptar, botonCancelar);

		VerticalSplitPanel layoutPrincipal = new VerticalSplitPanel();
		layoutPrincipal.setSplitPosition(90.0F, Unit.PIXELS, true);
		layoutPrincipal.setLocked(true);
		layoutPrincipal.setFirstComponent(layoutGrid);
		layoutPrincipal.setSecondComponent(layoutBotones);

		resultado.setContent(layoutPrincipal);
		resultado.center();
		return resultado;
	}

	private boolean validaFormulario() {
		boolean resultado = true;
		if ("".equals(textFieldNombre.getValue())) {
			resultado = false;
			textFieldNombre.focus();
			Notification.show("Es necesario especificar un nombre.", Type.WARNING_MESSAGE);
		} else if ("".equals(textFieldCie10.getValue())) {
			resultado = false;
			textFieldCie10.focus();
			Notification.show("Es necesario especificar un código CIE-10.", Type.WARNING_MESSAGE);
		}
		return resultado;
	}

	@Override
	protected String obtenDescripcionElementoSeleccionado() {
		return elementoSeleccionado.getNombre();
	}

	@Override
	protected Grid generaGrid() {
		Grid grid = new Grid();
		grid.setColumns("nombre", "cie10");
		grid.addSelectionListener(e -> {
			elementoSeleccionado = e.getSelected().isEmpty() ? null : (EnfermedadDTO) e.getSelected().iterator().next();
			refrescaDatos();
			cargaFormulario(elementoSeleccionado);
		});

		return grid;
	}

	@Override
	protected FormLayout generaLayoutFormulario() {
		FormLayout layoutFormulario = new FormLayout();
		layoutFormulario.setMargin(false);
		layoutFormulario.setSpacing(true);

		// TextField : NOMBRE
		textFieldNombre = new TextField("Nombre:");
		textFieldNombre.setInputPrompt("Nombre");
		textFieldNombre.setNullRepresentation("");
		textFieldNombre.setNullSettingAllowed(false);
		textFieldNombre.setRequired(true);
		textFieldNombre.setSizeFull();
		textFieldNombre.addTextChangeListener(e -> botonAcepta.setEnabled(true));

		// TextField : CIE-10
		textFieldCie10 = new TextField("CIE-10:");
		textFieldCie10.setInputPrompt("Código CIE-10");
		textFieldCie10.setNullRepresentation("");
		textFieldCie10.setNullSettingAllowed(false);
		textFieldCie10.setRequired(true);
		textFieldCie10.setSizeFull();
		textFieldCie10.addTextChangeListener(e -> botonAcepta.setEnabled(true));

		// Button : MEDICACIÓN RECOMENDADA
		Button botonMedicacion = new Button("Medicación recomendada");
		botonMedicacion.setDescription("Selecciona la medicación recomendada");
		botonMedicacion.setStyleName("link");
		botonMedicacion.addClickListener(e -> this.getUI().getUI().addWindow(creaVentanaMedicacionRecomendada()));

		// TextArea : DESCRIPCIÓN
		textAreaDescripcion = new TextArea("Descripción:");
		textAreaDescripcion.setInputPrompt("Descripción");
		textAreaDescripcion.setNullRepresentation("");
		textAreaDescripcion.setNullSettingAllowed(true);
		textAreaDescripcion.setRows(5);
		textAreaDescripcion.setSizeFull();
		textAreaDescripcion.addTextChangeListener(e -> botonAcepta.setEnabled(true));

		// Button : ACEPTAR
		botonAcepta = new Button("Aceptar");
		botonAcepta.setEnabled(false);

		botonAcepta.addClickListener(e -> {
			if (!validaFormulario()) {
				return;
			}
			EnfermedadDTO nuevaEnfermedad = new EnfermedadDTO(textFieldNombre.getValue(), textFieldCie10.getValue(),
					textAreaDescripcion.getValue());
			activaFormulario(false);
			// Agregar elemento:
			if (elementoSeleccionado == null) {
				Long id = servicioEnfermedad.agregaEnfermedad(nuevaEnfermedad);
				servicioMedicacion.eliminaPorEnfermedad(id);
				servicioMedicacion.agregaPorEnfermedad(id, medicacionRecomendada);
				botonAgrega.setEnabled(true);
				botonEdita.setEnabled(false);
				botonElimina.setEnabled(false);
				new Notification(String.format("Entrada añadida: <strong>\"%s\"</strong>", nuevaEnfermedad.getNombre()),
						"", Type.TRAY_NOTIFICATION, true).show(Page.getCurrent());
			}
			// Editar elemento:
			else {
				servicioEnfermedad.modificaEnfermedad(elementoSeleccionado.getId(), nuevaEnfermedad);
				servicioMedicacion.eliminaPorEnfermedad(elementoSeleccionado.getId());
				servicioMedicacion.agregaPorEnfermedad(elementoSeleccionado.getId(), medicacionRecomendada);
				elementoSeleccionado = null;
				botonAgrega.setEnabled(false);
				botonEdita.setEnabled(true);
				botonElimina.setEnabled(true);
				new Notification(
						String.format("Entrada modificada: <strong>\"%s\"</strong>", nuevaEnfermedad.getNombre()), "",
						Type.TRAY_NOTIFICATION, true).show(Page.getCurrent());
			}
			cargaGrid();
			cargaFormulario(elementoSeleccionado);
		});

		// Button : CANCELAR
		Button botonCancela = new Button("Cancelar");
		botonCancela.addClickListener(e -> {
			activaFormulario(false);
			botonAgrega.setEnabled(elementoSeleccionado == null ? true : false);
			botonEdita.setEnabled(elementoSeleccionado == null ? false : true);
			botonElimina.setEnabled(elementoSeleccionado == null ? false : true);
			refrescaDatos();
			cargaFormulario(elementoSeleccionado);
		});

		HorizontalLayout layoutBotonesFormulario = new HorizontalLayout();
		layoutBotonesFormulario.setSpacing(true);
		layoutBotonesFormulario.addComponent(botonAcepta);
		layoutBotonesFormulario.addComponent(botonCancela);

		layoutFormulario.addComponent(textFieldNombre);
		layoutFormulario.addComponent(textFieldCie10);
		layoutFormulario.addComponent(botonMedicacion);
		layoutFormulario.addComponent(textAreaDescripcion);
		layoutFormulario.addComponent(layoutBotonesFormulario);
		return layoutFormulario;
	}

	@Override
	protected Button generaBotonAceptarVentanaConfirmacionBorrado(Window ventana) {
		Button botonAceptar = new Button("Aceptar");
		botonAceptar.addClickListener(e -> {
			String nombre = elementoSeleccionado.getNombre();
			servicioEnfermedad.eliminaEnfermedad(elementoSeleccionado.getId());
			cargaGrid();
			ventana.close();
			new Notification(String.format("Entrada eliminada: <strong>\"%s\"</strong>", nombre), "",
					Type.TRAY_NOTIFICATION, true).show(Page.getCurrent());
		});
		return botonAceptar;
	}

	@Override
	protected void cargaFormulario(EnfermedadDTO elemento) {
		if (elemento == null) {
			textFieldNombre.clear();
			textFieldCie10.clear();
			textAreaDescripcion.clear();
		} else {
			textFieldNombre.setValue(elemento.getNombre());
			textFieldCie10.setValue(elemento.getCie10());
			textAreaDescripcion.setValue(elemento.getDescripcion() == null ? "" : elemento.getDescripcion());
		}
		botonAcepta.setEnabled(false);
	}

	@Override
	public void cargaGrid() {
		Collection<EnfermedadDTO> elementos = servicioEnfermedad.listaEnfermedades();
		grid.setContainerDataSource(new BeanItemContainer<>(EnfermedadDTO.class, elementos));
	}

	public void refrescaDatos() {
		this.medicacionRecomendada = (elementoSeleccionado == null) ? new ArrayList<>()
				: servicioMedicacion.listaPorEnfermedad(elementoSeleccionado.getId());
	}

}
