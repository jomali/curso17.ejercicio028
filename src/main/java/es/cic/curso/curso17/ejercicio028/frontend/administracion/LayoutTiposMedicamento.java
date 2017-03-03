package es.cic.curso.curso17.ejercicio028.frontend.administracion;

import java.util.Collection;

import org.springframework.web.context.ContextLoader;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.Page;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import com.vaadin.ui.Notification.Type;

import es.cic.curso.curso17.ejercicio028.dto.EnfermedadDTO;
import es.cic.curso.curso17.ejercicio028.frontend.VistaAdministracion;
import es.cic.curso.curso17.ejercicio028.modelo.TipoMedicamento;
import es.cic.curso.curso17.ejercicio028.servicio.ServicioTipoMedicamento;

public class LayoutTiposMedicamento extends LayoutAbstracto<TipoMedicamento> {
	private static final long serialVersionUID = 514378371321635909L;

	/** Lógica de negocio con acceso a BB.DD.: tipos de medicamento */
	private ServicioTipoMedicamento servicioTipoMedicamento;

	private TextField textFieldNombre;

	private TextArea textAreaDescripcion;

	private Button botonAcepta;

	public LayoutTiposMedicamento(VistaAdministracion padre) {
		super(padre);
		servicioTipoMedicamento = ContextLoader.getCurrentWebApplicationContext().getBean(ServicioTipoMedicamento.class);
	}

	@Override
	protected Grid generaGrid() {
		Grid grid = new Grid();
		grid.setColumns("nombre");
		grid.addSelectionListener(e -> {
			if (!e.getSelected().isEmpty()) {
				elementoSeleccionado = (TipoMedicamento) e.getSelected().iterator().next();
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
			TipoMedicamento nuevoTipo = new TipoMedicamento();
			nuevoTipo.setNombre(textFieldNombre.getValue());
			nuevoTipo.setDescripcion(textAreaDescripcion.getValue());
			activaFormulario(false);
			// Agregar elemento:
			if (elementoSeleccionado == null) {
				servicioTipoMedicamento.agregaTipoMedicamento(nuevoTipo);
				cargaGrid();
				botonAgrega.setEnabled(true);
				botonEdita.setEnabled(false);
				botonElimina.setEnabled(false);
				new Notification("Entrada añadida: <strong>\"" + nuevoTipo.getNombre() + "\"</strong>", "",
						Type.TRAY_NOTIFICATION, true).show(Page.getCurrent());
			}
			// Editar elemento:
			else {
				servicioTipoMedicamento.modificaTipoMedicamento(elementoSeleccionado.getId(), nuevoTipo);
				cargaGrid();
				elementoSeleccionado = null;
				botonAgrega.setEnabled(true);
				botonEdita.setEnabled(false);
				botonElimina.setEnabled(false);
				new Notification("Entrada modificada: <strong>\"" + nuevoTipo.getNombre() + "\"</strong>", "",
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
	protected Window creaVentanaConfirmacionBorrado() {
		Window resultado = new Window();
		resultado.setWidth(350.0F, Unit.PIXELS);
		resultado.setModal(true);
		resultado.setClosable(false);
		resultado.setResizable(false);
		resultado.setDraggable(false);

		Label label = new Label(
				"¿Está seguro de que desea borrar siguiente elemento:<br><strong>\"" + elementoSeleccionado.getNombre() + "\"</strong>?");
		label.setContentMode(ContentMode.HTML);

		Button botonAceptar = new Button("Aceptar");
		botonAceptar.addClickListener(e -> {
			servicioTipoMedicamento.eliminaTipoMedicamento(elementoSeleccionado.getId());
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
		Collection<TipoMedicamento> elementos = servicioTipoMedicamento.listaTiposMedicamento();
		grid.setContainerDataSource(new BeanItemContainer<>(TipoMedicamento.class, elementos));

	}

}
