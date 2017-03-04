package es.cic.curso.curso17.ejercicio028.frontend.administracion;

import java.util.Collection;

import org.springframework.web.context.ContextLoader;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

import es.cic.curso.curso17.ejercicio028.dto.EnfermedadDTO;
import es.cic.curso.curso17.ejercicio028.frontend.VistaAdministracion;
import es.cic.curso.curso17.ejercicio028.servicio.ServicioEnfermedad;

public class LayoutEnfermedades extends LayoutAbstracto<EnfermedadDTO> {
	private static final long serialVersionUID = 6467264543844871753L;

	/** Lógica de negocio con acceso a BB.DD.: enfermedades */
	private ServicioEnfermedad servicioEnfermedad;

	private TextField textFieldNombre;

	private TextArea textAreaDescripcion;

	private Button botonAcepta;

	public LayoutEnfermedades(VistaAdministracion padre) {
		super(padre);
		servicioEnfermedad = ContextLoader.getCurrentWebApplicationContext().getBean(ServicioEnfermedad.class);
	}
	
	@Override
	protected String obtenDescripcionElementoSeleccionado() {
		return elementoSeleccionado.getNombre();
	}

	@Override
	protected Grid generaGrid() {
		Grid grid = new Grid();
		grid.setColumns("nombre");
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

		textAreaDescripcion = new TextArea("Descripción:");
		textAreaDescripcion.setRows(5);
		textAreaDescripcion.setSizeFull();
		textAreaDescripcion.setInputPrompt("Descripción");
		textAreaDescripcion.addTextChangeListener(e -> botonAcepta.setEnabled(true));

		botonAcepta = new Button("Aceptar");
		botonAcepta.addClickListener(e -> {
			EnfermedadDTO nuevaEnfermedad = new EnfermedadDTO();
			nuevaEnfermedad.setNombre(textFieldNombre.getValue());
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
				botonAgrega.setEnabled(true); // botonAgrega.setEnabled(false);
				botonEdita.setEnabled(false); // botonEdita.setEnabled(true);
				botonElimina.setEnabled(false); // botonElimina.setEnabled(true);
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

		layoutFormulario.addComponents(textFieldNombre, textAreaDescripcion, layoutBotonesFormulario);
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
			textAreaDescripcion.clear();
		} else {
			String descripcion = elemento.getDescripcion();
			textFieldNombre.setValue(elemento.getNombre());
			textAreaDescripcion.setValue(descripcion == null ? "" : descripcion);
			botonAcepta.setEnabled(false);
		}
	}

	@Override
	public void cargaGrid() {
		Collection<EnfermedadDTO> elementos = servicioEnfermedad.listaEnfermedades();
		grid.setContainerDataSource(new BeanItemContainer<>(EnfermedadDTO.class, elementos));
	}

}
