package es.cic.curso.curso17.ejercicio028.vista.administracion;

import java.util.Collection;

import org.springframework.web.context.ContextLoader;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

import es.cic.curso.curso17.ejercicio028.dto.EnfermedadDTO;
import es.cic.curso.curso17.ejercicio028.servicio.ServicioEnfermedad;
import es.cic.curso.curso17.ejercicio028.vista.VistaAdministracion;

public class LayoutEnfermedades extends LayoutAbstracto<EnfermedadDTO> {
	private static final long serialVersionUID = 6467264543844871753L;

	private ServicioEnfermedad servicioEnfermedad;

	private TextField textFieldNombre;

	private TextArea textAreaDescripcion;

	public LayoutEnfermedades(VistaAdministracion padre) {
		super(padre);
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
				botonAgrega.setEnabled(false);
				botonEdita.setEnabled(true);
				botonElimina.setEnabled(true);
			} else {
				elementoSeleccionado = null;
				botonAgrega.setEnabled(true);
				botonEdita.setEnabled(false);
				botonElimina.setEnabled(false);
			}
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
			if (elementoSeleccionado == null) {
				servicioEnfermedad.agregaEnfermedad(enfermedad);
				cargaGrid();
				botonAgrega.setEnabled(true);
				botonEdita.setEnabled(false);
				botonElimina.setEnabled(false);
			} else {
				servicioEnfermedad.modificaEnfermedad(elementoSeleccionado.getId(), enfermedad);
				botonAgrega.setEnabled(false);
				botonEdita.setEnabled(true);
				botonElimina.setEnabled(true);
			}
			cargaFormulario(elementoSeleccionado);
			padre.activaPestannas(true, this);
			grid.setEnabled(true);
			layoutFormulario.setEnabled(false);
		});
		Button botonCancela = new Button("Cancelar");
		botonCancela.addClickListener(e -> {
			botonAgrega.setEnabled(true);
			botonEdita.setEnabled(elementoSeleccionado == null ? false : true);
			botonElimina.setEnabled(elementoSeleccionado == null ? false : true);
			cargaFormulario(elementoSeleccionado);
			padre.activaPestannas(true, this);
			grid.setEnabled(true);
			layoutFormulario.setEnabled(false);
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
		} else {
			String descripcion = elemento.getDescripcion();
			textFieldNombre.setValue(elemento.getNombre());
			textAreaDescripcion.setValue(descripcion == null ? "" : descripcion);
		}
	}

	@Override
	protected Window creaVentanaConfirmacionBorrado() {
		Window resultado = new Window();
		resultado.setWidth(350.0F, Unit.PIXELS);
		resultado.setModal(true);
		resultado.setClosable(false);
		resultado.setResizable(false);
		resultado.setDraggable(false);

		Label label = new Label(
				"¿Está seguro de que desea borrar: <strong>\"" + elementoSeleccionado.getNombre() + "\"</strong>?");
		label.setContentMode(ContentMode.HTML);

		Button botonAceptar = new Button("Aceptar");
		botonAceptar.addClickListener(e -> {
			servicioEnfermedad.eliminaEnfermedad(elementoSeleccionado.getId());
			cargaGrid();
			resultado.close();
		});

		Button botonCancelar = new Button("Cancelar");
		botonCancelar.addClickListener(e -> resultado.close());

		HorizontalLayout layoutBotones = new HorizontalLayout();
		layoutBotones.setMargin(true);
		layoutBotones.setSpacing(true);
		layoutBotones.setWidth(100.0F, Unit.PERCENTAGE);
		layoutBotones.addComponents(botonAceptar, botonCancelar);

		final FormLayout content = new FormLayout();
		content.setMargin(true);
		content.addComponents(label, layoutBotones);
		resultado.setContent(content);
		resultado.center();
		return resultado;
	}

	@Override
	public void cargaGrid() {
		Collection<EnfermedadDTO> enfermedades = servicioEnfermedad.listaEnfermedades();
		grid.setContainerDataSource(new BeanItemContainer<>(EnfermedadDTO.class, enfermedades));
	}

}
