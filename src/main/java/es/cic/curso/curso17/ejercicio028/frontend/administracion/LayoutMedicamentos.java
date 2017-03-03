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
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import com.vaadin.ui.Notification.Type;

import es.cic.curso.curso17.ejercicio028.dto.MedicamentoDTO;
import es.cic.curso.curso17.ejercicio028.frontend.VistaAdministracion;
import es.cic.curso.curso17.ejercicio028.modelo.Medicamento;
import es.cic.curso.curso17.ejercicio028.servicio.ServicioMedicamento;

public class LayoutMedicamentos extends LayoutAbstracto<Medicamento> {
	private static final long serialVersionUID = -9164756494824050779L;

	/** Lógica de negocio con acceso a BB.DD.: medicamentos */
	private ServicioMedicamento servicioMedicamento;

	private TextField textFieldNombre;

	private TextArea textAreaDescripcion;

	private Button botonAcepta;

	public LayoutMedicamentos(VistaAdministracion padre) {
		super(padre);
		servicioMedicamento = ContextLoader.getCurrentWebApplicationContext().getBean(ServicioMedicamento.class);
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
			if (!e.getSelected().isEmpty()) {
				elementoSeleccionado = (Medicamento) e.getSelected().iterator().next();
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
			MedicamentoDTO nuevoMedicamento = new MedicamentoDTO();
			nuevoMedicamento.setNombre(textFieldNombre.getValue());
			nuevoMedicamento.setDescripcion(textAreaDescripcion.getValue());
			activaFormulario(false);
			// Agregar elemento:
			if (elementoSeleccionado == null) {
				servicioMedicamento.agregaMedicamento(nuevoMedicamento);
				cargaGrid();
				botonAgrega.setEnabled(true);
				botonEdita.setEnabled(false);
				botonElimina.setEnabled(false);
				new Notification("Entrada añadida: <strong>\"" + nuevoMedicamento.getNombre() + "\"</strong>", "",
						Type.TRAY_NOTIFICATION, true).show(Page.getCurrent());
			}
			// Editar elemento:
			else {
				servicioMedicamento.modificaMedicamento(elementoSeleccionado.getId(), nuevoMedicamento);
				cargaGrid();
				elementoSeleccionado = null;
				botonAgrega.setEnabled(true); // botonAgrega.setEnabled(false);
				botonEdita.setEnabled(false); // botonEdita.setEnabled(true);
				botonElimina.setEnabled(false); // botonElimina.setEnabled(true);
				new Notification("Entrada modificada: <strong>\"" + nuevoMedicamento.getNombre() + "\"</strong>", "",
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
			servicioMedicamento.eliminaMedicamento(elementoSeleccionado.getId());
			cargaGrid();
			ventana.close();
		});
		return botonAceptar;
	}

	@Override
	protected void cargaFormulario(Medicamento elemento) {
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
		Collection<MedicamentoDTO> elementos = servicioMedicamento.listaMedicamentos();
		grid.setContainerDataSource(new BeanItemContainer<>(MedicamentoDTO.class, elementos));

	}

}
