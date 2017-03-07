package es.cic.curso.curso17.ejercicio028.iu.administracion;

import java.util.Collection;

import org.springframework.web.context.ContextLoader;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import com.vaadin.ui.Notification.Type;

import es.cic.curso.curso17.ejercicio028.iu.VistaAdministracion;
import es.cic.curso.curso17.ejercicio028.modelo.TipoMedicamento;
import es.cic.curso.curso17.ejercicio028.servicio.ServicioTipoMedicamento;

public class LayoutTiposMedicamento extends LayoutAbstracto<TipoMedicamento> {
	private static final long serialVersionUID = 514378371321635909L;

	public static final float POSICION_DIVISOR = 30.0F;

	/** Lógica de negocio con acceso a BB.DD.: tipos de medicamento */
	private ServicioTipoMedicamento servicioTipoMedicamento;

	private TextField textFieldNombre;

	private TextArea textAreaDescripcion;

	private Button botonAcepta;

	public LayoutTiposMedicamento(VistaAdministracion padre) {
		super(padre, POSICION_DIVISOR);
		servicioTipoMedicamento = ContextLoader.getCurrentWebApplicationContext()
				.getBean(ServicioTipoMedicamento.class);
	}

	private boolean validaFormulario() {
		boolean resultado = true;
		if ("".equals(textFieldNombre.getValue())) {
			resultado = false;
			textFieldNombre.focus();
			Notification.show("Es necesario especificar un nombre.", Type.WARNING_MESSAGE);
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
		grid.setColumns("nombre");
		grid.addSelectionListener(e -> {
			elementoSeleccionado = (e.getSelected().isEmpty()) ? null
					: (TipoMedicamento) e.getSelected().iterator().next();
			cargaFormulario(elementoSeleccionado);
		});

		return grid;
	}

	@Override
	protected Button generaBotonAceptarVentanaConfirmacionBorrado(Window ventana) {
		Button botonAceptar = new Button("Aceptar");
		botonAceptar.addClickListener(e -> {
			String nombre = elementoSeleccionado.getNombre();
			servicioTipoMedicamento.eliminaTipoMedicamento(elementoSeleccionado.getId());
			cargaGrid();
			ventana.close();
			new Notification(String.format("Entrada eliminada: <strong>\"%s\"</strong>", nombre), "",
					Type.TRAY_NOTIFICATION, true).show(Page.getCurrent());
		});
		return botonAceptar;
	}

	@Override
	protected FormLayout generaLayoutFormulario() {
		FormLayout layoutFormulario = new FormLayout();
		layoutFormulario.setMargin(false);
		layoutFormulario.setSpacing(true);

		textFieldNombre = new TextField("Nombre:");
		textFieldNombre.setInputPrompt("Nombre");
		textFieldNombre.setNullRepresentation("");
		textFieldNombre.setNullSettingAllowed(false);
		textFieldNombre.setRequired(true);
		textFieldNombre.setRequiredError("Se debe especificar un nombre");
		textFieldNombre.setSizeFull();
		textFieldNombre.addTextChangeListener(e -> botonAcepta.setEnabled(true));

		textAreaDescripcion = new TextArea("Descripción:");
		textAreaDescripcion.setInputPrompt("Descripción");
		textAreaDescripcion.setNullRepresentation("");
		textAreaDescripcion.setNullSettingAllowed(true);
		textAreaDescripcion.setRows(5);
		textAreaDescripcion.setSizeFull();
		textAreaDescripcion.addTextChangeListener(e -> botonAcepta.setEnabled(true));

		botonAcepta = new Button("Aceptar");
		botonAcepta.setEnabled(false);
		botonAcepta.addClickListener(e -> {
			if (!validaFormulario()) {
				return;
			}
			TipoMedicamento nuevoTipo = new TipoMedicamento();
			nuevoTipo.setNombre(textFieldNombre.getValue());
			nuevoTipo.setDescripcion(textAreaDescripcion.getValue());
			activaFormulario(false);
			// Agregar elemento:
			if (elementoSeleccionado == null) {
				servicioTipoMedicamento.agregaTipoMedicamento(nuevoTipo);
				botonAgrega.setEnabled(true);
				botonEdita.setEnabled(false);
				botonElimina.setEnabled(false);
				new Notification(String.format("Entrada añadida: <strong>\"%s\"</strong>", nuevoTipo.getNombre()), "",
						Type.TRAY_NOTIFICATION, true).show(Page.getCurrent());
			}
			// Editar elemento:
			else {
				servicioTipoMedicamento.modificaTipoMedicamento(elementoSeleccionado.getId(), nuevoTipo);
				elementoSeleccionado = null;
				botonAgrega.setEnabled(true);
				botonEdita.setEnabled(false);
				botonElimina.setEnabled(false);
				new Notification(String.format("Entrada modificada: <strong>\"%s\"</strong>", nuevoTipo.getNombre()), "",
						Type.TRAY_NOTIFICATION, true).show(Page.getCurrent());
			}
			padre.cargaDatos();
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
	protected void cargaFormulario(TipoMedicamento elemento) {
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
		Collection<TipoMedicamento> elementos = servicioTipoMedicamento.listaTiposMedicamento();
		grid.setContainerDataSource(new BeanItemContainer<>(TipoMedicamento.class, elementos));

	}

}
