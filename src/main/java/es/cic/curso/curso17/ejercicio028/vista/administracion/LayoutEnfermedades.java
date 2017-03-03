package es.cic.curso.curso17.ejercicio028.vista.administracion;

import java.util.Collection;

import org.springframework.web.context.ContextLoader;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

import es.cic.curso.curso17.ejercicio028.dto.EnfermedadDTO;
import es.cic.curso.curso17.ejercicio028.servicio.ServicioEnfermedad;

public class LayoutEnfermedades extends LayoutAbstracto<EnfermedadDTO> {
	private static final long serialVersionUID = 6467264543844871753L;

	private ServicioEnfermedad servicioEnfermedad;

	private TextField textFieldNombre;

	private TextArea textAreaDescripcion;

	public LayoutEnfermedades() {
		super();

		servicioEnfermedad = ContextLoader.getCurrentWebApplicationContext().getBean(ServicioEnfermedad.class);
	}

	// /////////////////////////////////////////////////////////////////////////
	// Inicialización de componentes gráficos:

	@Override
	protected Grid generaGrid() {
		Grid grid = new Grid();
		grid.setColumns("nombre");
		grid.addSelectionListener(e -> {
			if (!e.getSelected().isEmpty()) {
				elementoSeleccionado = (EnfermedadDTO) e.getSelected().iterator().next();
				botonEdita.setEnabled(true);
				botonElimina.setEnabled(true);
			} else {
				elementoSeleccionado = null;
				botonEdita.setEnabled(false);
				botonElimina.setEnabled(false);
			}
		});

		return grid;
	}

	@Override
	protected FormLayout generaLayoutFormulario() {
		FormLayout layoutFormulario = new FormLayout();
		layoutFormulario.setMargin(false);
		layoutFormulario.setSpacing(true);

		textFieldNombre = new TextField("Nombre:");
		textFieldNombre.setInputPrompt("Nombre");
		textFieldNombre.setRequired(true);
		textFieldNombre.setSizeFull();

		textAreaDescripcion = new TextArea("Descripción:");
		textAreaDescripcion.setRows(5);
		textAreaDescripcion.setSizeFull();

		Button botonAcepta = new Button("Aceptar");
		botonAcepta.addClickListener(e -> {
			EnfermedadDTO enfermedad = new EnfermedadDTO();
			enfermedad.setNombre(textFieldNombre.getValue());
			enfermedad.setDescripcion(textAreaDescripcion.getValue());
			servicioEnfermedad.agregaEnfermedad(enfermedad);
			cargaGrid();
		});
		Button botonCancela = new Button("Cancela");
		botonCancela.addClickListener(e -> {
			botonAgrega.setEnabled(true);
			botonEdita.setEnabled(elementoSeleccionado == null ? false : true);
			botonElimina.setEnabled(elementoSeleccionado == null ? false : true);
			this.layoutFormulario.setEnabled(false);
		});

		HorizontalLayout layoutBotonesFormulario = new HorizontalLayout();
		layoutBotonesFormulario.setSpacing(true);
		layoutBotonesFormulario.addComponents(botonAcepta, botonCancela);

		layoutFormulario.addComponents(textFieldNombre, textAreaDescripcion, layoutBotonesFormulario);
		return layoutFormulario;
	}

	@Override
	protected void cargaFormulario(EnfermedadDTO elemento) {
		if (elemento == null) {
			textFieldNombre.clear();
			textAreaDescripcion.clear();
			 layoutFormulario.setEnabled(false);
		} else {
			textFieldNombre.setValue(elemento.getNombre());
			textAreaDescripcion.setValue(elemento.getDescripcion());
			 layoutFormulario.setEnabled(true);
		}
	}

	@Override
	public void cargaGrid() {
		Collection<EnfermedadDTO> enfermedades = servicioEnfermedad.listaEnfermedades();
		grid.setContainerDataSource(new BeanItemContainer<>(EnfermedadDTO.class, enfermedades));
	}

}
