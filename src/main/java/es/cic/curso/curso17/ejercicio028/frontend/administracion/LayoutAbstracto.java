package es.cic.curso.curso17.ejercicio028.frontend.administracion;

import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.Window;
import com.vaadin.ui.Grid.SelectionMode;

import es.cic.curso.curso17.ejercicio028.frontend.VistaAdministracion;

@SuppressWarnings("serial")
public abstract class LayoutAbstracto<K> extends VerticalLayout implements Component {

	protected static final float ANCHO_VENTANA_CONFIRMACION = 400.0F; // px
	protected static final float ALTO_VENTANA_CONFIRMACION = 190.0F; // px

	/** Referencia a la vista principal. */
	protected VistaAdministracion padre;

	/** Referencia al elemento seleccionado en el grid. */
	protected K elementoSeleccionado;

	/** Campo de texto para el filtro de la tabla. */
	protected TextField textFieldFiltro;

	/** Botón para acción: cancelar selección */
	protected Button botonLimpiaSeleccion;

	/** Tabla con datos de BB.DD. */
	protected Grid grid = new Grid();

	/** Botón para acción: añadir elemento. */
	protected Button botonAgrega = new Button("Añadir");

	/** Botón para acción: editar elemento. */
	protected Button botonEdita = new Button("Editar");

	/** Botón para acción: eliminar elemento. */
	protected Button botonElimina = new Button("Borrar");

	/** Formulario. */
	protected FormLayout layoutFormulario = new FormLayout();

	public LayoutAbstracto(VistaAdministracion padre, float posicionDivisor) {
		this.padre = padre;
		setMargin(new MarginInfo(true, false, false, false));
		setSpacing(false);

		HorizontalSplitPanel splitPanelPrincipal = new HorizontalSplitPanel();
		splitPanelPrincipal.setSplitPosition(posicionDivisor, Unit.PERCENTAGE);
		splitPanelPrincipal.setMinSplitPosition(275.0F, Unit.PIXELS); // 224
		splitPanelPrincipal.setMaxSplitPosition(70.0F, Unit.PERCENTAGE);
		splitPanelPrincipal.setLocked(false);
		splitPanelPrincipal.setFirstComponent(generaLayoutTabla());
		splitPanelPrincipal.setSecondComponent(generaLayoutEdicion());

		addComponent(splitPanelPrincipal);
	}

	// /////////////////////////////////////////////////////////////////////////
	// Inicialización de componentes gráficos:

	private VerticalLayout generaLayoutTabla() {
		// Layout : TABLA
		VerticalLayout layoutTabla = new VerticalLayout();
		layoutTabla.setMargin(new MarginInfo(false, true, false, false));
		layoutTabla.setSpacing(true);

		// Layout : FILTRO
		HorizontalLayout layoutFiltro = new HorizontalLayout();
		layoutFiltro.setMargin(false);
		layoutFiltro.setSpacing(true);

		// TextField : FILTRO
		textFieldFiltro = new TextField();
		textFieldFiltro.setInputPrompt("Buscar...");
		textFieldFiltro.addTextChangeListener(e -> {
			botonLimpiaSeleccion.setEnabled(true);
		});

		// Button : LIMPIAR SELECCIÓN
		botonLimpiaSeleccion = new Button();
		botonLimpiaSeleccion.setDescription("Cancela la selección actual");
		botonLimpiaSeleccion.setEnabled(false);
		botonLimpiaSeleccion.setIcon(FontAwesome.ERASER);
		// botonLimpiaSeleccion.setStyleName("primary");
		botonLimpiaSeleccion.addClickListener(e -> {
			botonAgrega.setEnabled(true);
			botonEdita.setEnabled(false);
			botonElimina.setEnabled(false);
			textFieldFiltro.clear();
			botonLimpiaSeleccion.setEnabled(false);
			grid.deselectAll();
			elementoSeleccionado = null;
			cargaFormulario(elementoSeleccionado);
		});

		// Grid : GRID
		grid = generaGrid();
		grid.setSizeFull();
		grid.setSelectionMode(SelectionMode.SINGLE);
		grid.addSelectionListener(e -> {
			if (!e.getSelected().isEmpty()) {
				botonLimpiaSeleccion.setEnabled(true);
				botonAgrega.setEnabled(false);
				botonEdita.setEnabled(true);
				botonElimina.setEnabled(true);
			} else {
				botonLimpiaSeleccion.setEnabled(false);
				elementoSeleccionado = null;
				botonAgrega.setEnabled(true);
				botonEdita.setEnabled(false);
				botonElimina.setEnabled(false);
			}
		});

		layoutFiltro.addComponents(textFieldFiltro, botonLimpiaSeleccion);
		layoutTabla.addComponents(layoutFiltro, grid);
		layoutTabla.setComponentAlignment(layoutFiltro, Alignment.TOP_RIGHT);

		return layoutTabla;
	}

	private VerticalLayout generaLayoutEdicion() {
		// Layout : EDICIÓN
		VerticalLayout layoutEdicion = new VerticalLayout();
		layoutEdicion.setMargin(new MarginInfo(false, false, false, true));
		layoutEdicion.setSpacing(true);

		// Layout : BOTONES
		HorizontalLayout layoutBotones = new HorizontalLayout();
		layoutBotones.setWidth(100.0F, Unit.PERCENTAGE);
		layoutBotones.setSpacing(true);

		// Button : AÑADIR ELEMENTO
		botonAgrega.setDescription("Agrega un nuevo elemento al sistema");
		botonAgrega.setIcon(FontAwesome.PLUS);
		botonAgrega.setStyleName("primary");
		botonAgrega.setWidth(100.0F, Unit.PERCENTAGE);
		botonAgrega.addClickListener(e -> activaFormulario(true));

		// Button : EDITAR ELEMENTO
		botonEdita.setDescription("Modifica el elemento seleccionado");
		botonEdita.setEnabled(false);
		botonEdita.setIcon(FontAwesome.EDIT);
		botonEdita.setStyleName("primary");
		botonEdita.setWidth(100.0F, Unit.PERCENTAGE);
		botonEdita.addClickListener(e -> activaFormulario(true));

		// Button : ELIMINAR ELEMENTO
		botonElimina.setDescription("Elimina el elemento seleccionado");
		botonElimina.setEnabled(false);
		botonElimina.setIcon(FontAwesome.REMOVE);
		botonElimina.setStyleName("danger");
		botonElimina.setWidth(100.0F, Unit.PERCENTAGE);
		botonElimina.addClickListener(e -> this.getUI().getUI().addWindow(creaVentanaConfirmacionBorrado()));

		layoutFormulario = generaLayoutFormulario();
		layoutFormulario.setEnabled(false);

		layoutBotones.addComponents(botonAgrega, botonEdita, botonElimina);
		layoutBotones.setExpandRatio(botonAgrega, 1);
		layoutBotones.setExpandRatio(botonEdita, 1);
		layoutBotones.setExpandRatio(botonElimina, 1);
		layoutEdicion.addComponents(layoutBotones, layoutFormulario);

		return layoutEdicion;
	}

	protected void activaFormulario(boolean activado) {
		botonAgrega.setEnabled(!activado);
		botonEdita.setEnabled(!activado);
		botonElimina.setEnabled(!activado);
		padre.activaPestannas(!activado, this);
		textFieldFiltro.setEnabled(!activado);
		botonLimpiaSeleccion.setEnabled(!activado);
		grid.setEnabled(!activado);
		layoutFormulario.setEnabled(activado);
	}

	protected Window creaVentanaConfirmacionBorrado() {
		Window resultado = new Window();
		resultado.setHeight(ALTO_VENTANA_CONFIRMACION, Unit.PIXELS);
		resultado.setWidth(ANCHO_VENTANA_CONFIRMACION, Unit.PIXELS);
		resultado.setModal(true);
		resultado.setClosable(false);
		resultado.setResizable(false);
		resultado.setDraggable(false);

		Label label = new Label(obtenDescripcionElementoSeleccionado() == null
				? "¿Está seguro de que desea borrar el elemento seleccionado?"
				: "Está a punto de borrar el siguiente elemento:<br><strong>\"" + obtenDescripcionElementoSeleccionado()
						+ "\"</strong>");
		label.setContentMode(ContentMode.HTML);

		Button botonAceptar = generaBotonAceptarVentanaConfirmacionBorrado(resultado);

		Button botonCancelar = new Button("Cancelar");
		botonCancelar.addClickListener(e -> resultado.close());

		VerticalLayout contenido = new VerticalLayout();
		contenido.setMargin(true);
		contenido.setSpacing(true);
		contenido.setSizeFull();
		contenido.addComponent(label);

		HorizontalLayout layoutBotones = new HorizontalLayout();
		layoutBotones.setMargin(new MarginInfo(false, true, false, true));
		layoutBotones.setSpacing(true);
		layoutBotones.setHeight(100.0F, Unit.PERCENTAGE);
		layoutBotones.setDefaultComponentAlignment(Alignment.MIDDLE_RIGHT);
		layoutBotones.addComponents(botonAceptar, botonCancelar);

		VerticalSplitPanel layoutPrincipal = new VerticalSplitPanel();
		layoutPrincipal.setSplitPosition(90.0F, Unit.PIXELS, true);
		layoutPrincipal.setLocked(true);
		layoutPrincipal.setFirstComponent(contenido);
		layoutPrincipal.setSecondComponent(layoutBotones);

		resultado.setContent(layoutPrincipal);
		resultado.center();
		return resultado;
	}

	protected String obtenDescripcionElementoSeleccionado() {
		return null;
	}

	// /////////////////////////////////////////////////////////////////////////

	protected abstract Grid generaGrid();

	protected abstract FormLayout generaLayoutFormulario();

	protected abstract Button generaBotonAceptarVentanaConfirmacionBorrado(Window ventana);

	protected abstract void cargaFormulario(K elemento);

	public abstract void cargaGrid();

}
