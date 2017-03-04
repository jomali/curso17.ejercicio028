package es.cic.curso.curso17.ejercicio028.frontend.administracion;

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
import es.cic.curso.curso17.ejercicio028.frontend.VistaAdministracion;
import es.cic.curso.curso17.ejercicio028.servicio.ServicioEnfermedad;
import es.cic.curso.curso17.ejercicio028.servicio.ServicioMedicamento;

public class LayoutEnfermedades extends LayoutAbstracto<EnfermedadDTO> {
	private static final long serialVersionUID = 6467264543844871753L;

	public static final float POSICION_DIVISOR = 35.0F;

	/** Lógica de negocio con acceso a BB.DD.: enfermedades */
	private ServicioEnfermedad servicioEnfermedad;

	/** Lógica de negocio con acceso a BB.DD.: medicamentos */
	private ServicioMedicamento servicioMedicamento;

	private TextField textFieldNombre;

	private TextField textFieldCie10;

	private TextArea textAreaDescripcion;

	private Button botonAcepta;

	public LayoutEnfermedades(VistaAdministracion padre) {
		super(padre, POSICION_DIVISOR);
		servicioEnfermedad = ContextLoader.getCurrentWebApplicationContext().getBean(ServicioEnfermedad.class);
		servicioMedicamento = ContextLoader.getCurrentWebApplicationContext().getBean(ServicioMedicamento.class);
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
			elementoSeleccionado = (e.getSelected().isEmpty()) ? null
					: (EnfermedadDTO) e.getSelected().iterator().next();
			cargaFormulario(elementoSeleccionado);
		});

		return grid;
	}

	@Override
	protected FormLayout generaLayoutFormulario() {
		FormLayout layoutFormulario = new FormLayout();
		layoutFormulario.setMargin(false);
		layoutFormulario.setSpacing(true);

		textFieldNombre = new TextField("Nombre:");
		textFieldNombre.setSizeFull();
		textFieldNombre.setInputPrompt("Nombre");
		textFieldNombre.setRequired(true);
		textFieldNombre.addTextChangeListener(e -> botonAcepta.setEnabled(true));

		textFieldCie10 = new TextField("CIE-10:");
		textFieldCie10.setInputPrompt("Código CIE-10");
		textFieldCie10.setRequired(true);
		textFieldCie10.setSizeFull();
		textFieldCie10.addTextChangeListener(e -> botonAcepta.setEnabled(true));

		Button botonMedicacion = new Button("Medicación recomendada");
		botonMedicacion.setDescription("Selecciona la medicación recomendada");
		botonMedicacion.setStyleName("link");
		botonMedicacion.addClickListener(e -> this.getUI().getUI().addWindow(creaVentanaMedicacionRecomendada()));

		textAreaDescripcion = new TextArea("Descripción:");
		textAreaDescripcion.setRows(5);
		textAreaDescripcion.setSizeFull();
		textAreaDescripcion.setInputPrompt("Descripción");
		textAreaDescripcion.addTextChangeListener(e -> botonAcepta.setEnabled(true));

		botonAcepta = new Button("Aceptar");
		botonAcepta.addClickListener(e -> {
			EnfermedadDTO nuevaEnfermedad = new EnfermedadDTO();
			nuevaEnfermedad.setNombre(textFieldNombre.getValue());
			nuevaEnfermedad.setCie10(textFieldCie10.getValue());
			nuevaEnfermedad.setDescripcion(textAreaDescripcion.getValue());
			activaFormulario(false);
			// Agregar elemento:
			if (elementoSeleccionado == null) {
				servicioEnfermedad.agregaEnfermedad(nuevaEnfermedad);
				cargaGrid();
				botonAgrega.setEnabled(true);
				botonEdita.setEnabled(false);
				botonElimina.setEnabled(false);
				new Notification("Entrada añadida: <strong>\"" + nuevaEnfermedad.getNombre() + "\"</strong>", "",
						Type.TRAY_NOTIFICATION, true).show(Page.getCurrent());
			}
			// Editar elemento:
			else {
				servicioEnfermedad.modificaEnfermedad(elementoSeleccionado.getId(), nuevaEnfermedad);
				cargaGrid();
				elementoSeleccionado = null;
				botonAgrega.setEnabled(true);
				botonEdita.setEnabled(false);
				botonElimina.setEnabled(false);
				new Notification("Entrada modificada: <strong>\"" + nuevaEnfermedad.getNombre() + "\"</strong>", "",
						Type.TRAY_NOTIFICATION, true).show(Page.getCurrent());
			}
			cargaFormulario(elementoSeleccionado);
		});
		Button botonCancela = new Button("Cancelar");
		botonCancela.addClickListener(e -> {
			activaFormulario(false);
			botonAgrega.setEnabled(elementoSeleccionado == null ? true : false);
			botonEdita.setEnabled(elementoSeleccionado == null ? false : true);
			botonElimina.setEnabled(elementoSeleccionado == null ? false : true);
			cargaFormulario(elementoSeleccionado);
		});

		HorizontalLayout layoutBotonesFormulario = new HorizontalLayout();
		layoutBotonesFormulario.setSpacing(true);
		layoutBotonesFormulario.addComponents(botonAcepta, botonCancela);

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
			servicioEnfermedad.eliminaEnfermedad(elementoSeleccionado.getId());
			cargaGrid();
			ventana.close();
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
			botonAcepta.setEnabled(false);
		}
	}

	@Override
	public void cargaGrid() {
		Collection<EnfermedadDTO> elementos = servicioEnfermedad.listaEnfermedades();
		grid.setContainerDataSource(new BeanItemContainer<>(EnfermedadDTO.class, elementos));
	}

	private Window creaVentanaMedicacionRecomendada() {
		String titulo = "Medicación recomendada";
		Window resultado = new Window();
		resultado.setCaption(elementoSeleccionado == null ? titulo
				: titulo + ": <strong>\"" + elementoSeleccionado.getNombre() + "\"</strong>");
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
		Collection<MedicamentoDTO> elementos = servicioMedicamento.listaMedicamentos();
		gridMedicacion.setContainerDataSource(new BeanItemContainer<>(MedicamentoDTO.class, elementos));

		// FIXME - Eliminar
		List<MedicamentoDTO> l = servicioMedicamento.listaMedicamentos();
		gridMedicacion.select(l.get(0));
		gridMedicacion.select(l.get(2));
		gridMedicacion.select(l.get(3));
		
		VerticalLayout layoutGrid = new VerticalLayout();
		layoutGrid.setMargin(new MarginInfo(false, true, false, true));
		layoutGrid.setSpacing(false);
		layoutGrid.setSizeFull();
		layoutGrid.addComponent(gridMedicacion);

		Button botonAceptar = new Button("Aceptar");
		botonAceptar.addClickListener(e -> resultado.close());

		Button botonCancelar = new Button("Cancelar");
		botonCancelar.addClickListener(e -> resultado.close());

		HorizontalLayout layoutBotones = new HorizontalLayout();
		layoutBotones.setMargin(new MarginInfo(false, true, false, true));
		layoutBotones.setSpacing(true);
		layoutBotones.setHeight(100.0F, Unit.PERCENTAGE);
		layoutBotones.setDefaultComponentAlignment(Alignment.MIDDLE_RIGHT);
		layoutBotones.addComponents(botonAceptar, botonCancelar);

		VerticalSplitPanel layoutPrincipal = new VerticalSplitPanel();
		layoutPrincipal.setSplitPosition(76.0F, Unit.PERCENTAGE);
		layoutPrincipal.setLocked(true);
		layoutPrincipal.setFirstComponent(layoutGrid);
		layoutPrincipal.setSecondComponent(layoutBotones);

		resultado.setContent(layoutPrincipal);
		resultado.center();
		return resultado;
	}

}
