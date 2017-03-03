package es.cic.curso.curso17.ejercicio028.vista.administracion;

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
import com.vaadin.ui.Grid.SelectionMode;

@SuppressWarnings("serial")
public abstract class LayoutAbstracto<K> extends VerticalLayout implements Component {
	
	/** Referencia al elemento seleccionado en el grid. */
	protected K elementoSeleccionado;

	/** Campo de texto para el filtro de la tabla. */
	protected TextField textFieldFiltro = new TextField();

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
	
	public LayoutAbstracto() {
		setMargin(new MarginInfo(false, false, true, false));
		setSpacing(false);
		
		HorizontalLayout h = new HorizontalLayout();
		h.setMargin(false);
		h.setSpacing(false);
		Label label = new Label("&nbsp;", ContentMode.HTML);
		h.addComponent(label);
		
		HorizontalSplitPanel s = new HorizontalSplitPanel();
		s.setSplitPosition(30.0F, Unit.PERCENTAGE);
		s.setMinSplitPosition(220.0F, Unit.PIXELS);
		s.setMaxSplitPosition(70.0F, Unit.PERCENTAGE);
		s.setLocked(false);
		s.setFirstComponent(generaLayoutTabla());
		s.setSecondComponent(generaLayoutEdicion());
		
		addComponents(h, s);
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
		textFieldFiltro.setInputPrompt("Buscar...");
		
		// Button : LIMPIAR
		Button botonLimpia = new Button();
		botonLimpia.setIcon(FontAwesome.FILTER); // FontAwesome.ERASER
		botonLimpia.setVisible(false);

		// Grid : GRID
		grid = generaGrid();
		grid.setSizeFull();
		grid.setSelectionMode(SelectionMode.SINGLE);
		
		layoutFiltro.addComponents(textFieldFiltro, botonLimpia);
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
		botonAgrega.setWidth(100.0F, Unit.PERCENTAGE);
		botonAgrega.setIcon(FontAwesome.PLUS);
		botonAgrega.addClickListener(e -> {
			activaBotonesFormulario(false);
			layoutFormulario.setEnabled(true);
		});

		// Button : EDITAR ELEMENTO
		botonEdita.setWidth(100.0F, Unit.PERCENTAGE);
		botonEdita.setIcon(FontAwesome.EDIT);
		botonEdita.setEnabled(false);

		// Button : ELIMINAR ELEMENTO
		botonElimina.setWidth(100.0F, Unit.PERCENTAGE);
		botonElimina.setIcon(FontAwesome.REMOVE);
		botonElimina.setEnabled(false);
		
		layoutFormulario = generaLayoutFormulario();
		layoutFormulario.setEnabled(false);
				
		layoutBotones.addComponents(botonAgrega, botonEdita, botonElimina);
		layoutBotones.setExpandRatio(botonAgrega, 1);
		layoutBotones.setExpandRatio(botonEdita, 1);
		layoutBotones.setExpandRatio(botonElimina, 1);
		layoutEdicion.addComponents(layoutBotones, layoutFormulario);
		
		return layoutEdicion;
	}
	
	protected void activaBotonesFormulario(boolean activado) {
		botonAgrega.setEnabled(activado);
		botonEdita.setEnabled(activado);
		botonElimina.setEnabled(activado);
	}

	// /////////////////////////////////////////////////////////////////////////

	protected abstract Grid generaGrid();
	
	protected abstract FormLayout generaLayoutFormulario();
		
	protected abstract void cargaFormulario(K elemento);
	
	public abstract void cargaGrid();

}
