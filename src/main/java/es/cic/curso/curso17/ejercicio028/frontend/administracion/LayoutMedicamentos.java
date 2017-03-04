package es.cic.curso.curso17.ejercicio028.frontend.administracion;

import java.util.Collection;
import java.util.List;

import org.springframework.web.context.ContextLoader;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
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
import es.cic.curso.curso17.ejercicio028.modelo.TipoMedicamento;
import es.cic.curso.curso17.ejercicio028.servicio.ServicioMedicamento;
import es.cic.curso.curso17.ejercicio028.servicio.ServicioTipoMedicamento;

public class LayoutMedicamentos extends LayoutAbstracto<MedicamentoDTO> {
	private static final long serialVersionUID = -9164756494824050779L;

	/** Lógica de negocio con acceso a BB.DD.: medicamentos */
	private ServicioMedicamento servicioMedicamento;

	/** Lógica de negocio con acceso a BB.DD.: tipos de medicamento */
	private ServicioTipoMedicamento servicioTipoMedicamento;

	private TextField textFieldNombre;

	private ComboBox comboBoxTipoMedicamento;

	private TextArea textAreaDescripcion;

	private Button botonAcepta;

	public LayoutMedicamentos(VistaAdministracion padre) {
		super(padre);
		servicioMedicamento = ContextLoader.getCurrentWebApplicationContext().getBean(ServicioMedicamento.class);
		servicioTipoMedicamento = ContextLoader.getCurrentWebApplicationContext()
				.getBean(ServicioTipoMedicamento.class);
		cargaComboBox();
	}

	@Override
	protected String obtenDescripcionElementoSeleccionado() {
		return elementoSeleccionado.getNombre();
	}

	@Override
	protected Grid generaGrid() {
		Grid grid = new Grid();
		grid.setColumns("nombre", "nombreTipo");
		grid.addSelectionListener(e -> {
			elementoSeleccionado = (e.getSelected().isEmpty()) ? null
					: (MedicamentoDTO) e.getSelected().iterator().next();
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
		textFieldNombre.addTextChangeListener(e -> botonAcepta.setEnabled(true));

		textAreaDescripcion = new TextArea("Descripción:");
		textAreaDescripcion.setInputPrompt("Descripción");
		textAreaDescripcion.setRows(5);
		textAreaDescripcion.setSizeFull();
		textAreaDescripcion.addTextChangeListener(e -> botonAcepta.setEnabled(true));

		comboBoxTipoMedicamento = new ComboBox("Tipo:");
		comboBoxTipoMedicamento.setFilteringMode(FilteringMode.CONTAINS);
		comboBoxTipoMedicamento.setInvalidAllowed(false);
		comboBoxTipoMedicamento.setNullSelectionAllowed(false);
		comboBoxTipoMedicamento.setPageLength(5);
		comboBoxTipoMedicamento.setRequired(true);
		comboBoxTipoMedicamento.setTextInputAllowed(false);
		comboBoxTipoMedicamento.setWidth(250.0F, Unit.PIXELS);
		comboBoxTipoMedicamento.addValueChangeListener(e -> botonAcepta.setEnabled(true));

		botonAcepta = new Button("Aceptar");
		botonAcepta.addClickListener(e -> {
			TipoMedicamento tipo = (TipoMedicamento) comboBoxTipoMedicamento.getValue();
			MedicamentoDTO nuevoMedicamento = new MedicamentoDTO();
			nuevoMedicamento.setNombre(textFieldNombre.getValue());
			nuevoMedicamento.setTipo(tipo.getId() == -1 ? null : tipo);
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
				botonAgrega.setEnabled(true);
				botonEdita.setEnabled(false);
				botonElimina.setEnabled(false);
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

		layoutFormulario.addComponent(textFieldNombre);
		layoutFormulario.addComponent(comboBoxTipoMedicamento);
		layoutFormulario.addComponent(textAreaDescripcion);
		layoutFormulario.addComponent(layoutBotonesFormulario);
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
	protected void cargaFormulario(MedicamentoDTO elemento) {
		if (elemento == null) {
			textFieldNombre.clear();
//			comboBoxTipoMedicamento.setValue(comboBoxTipoMedicamento.getItemIds().iterator().next());
			cargaComboBox();
			textAreaDescripcion.clear();
		} else {
			TipoMedicamento tipo = elemento.getTipo();
			String descripcion = elemento.getDescripcion();
			textFieldNombre.setValue(elemento.getNombre());
			if (elemento.getTipo() != null) {
				comboBoxTipoMedicamento.setValue(elemento.getTipo());
			} else {
				cargaComboBox();
			}
			textAreaDescripcion.setValue(descripcion == null ? "" : descripcion);
			botonAcepta.setEnabled(false);
		}
	}

	@Override
	public void cargaGrid() {
		Collection<MedicamentoDTO> elementos = servicioMedicamento.listaMedicamentos();
		grid.setContainerDataSource(new BeanItemContainer<>(MedicamentoDTO.class, elementos));
	}

	public void cargaComboBox() {
		List<TipoMedicamento> elementos = servicioTipoMedicamento.listaTiposMedicamentoOrdenada();
		comboBoxTipoMedicamento.setContainerDataSource(new BeanItemContainer<>(TipoMedicamento.class, elementos));
		comboBoxTipoMedicamento.setItemCaptionPropertyId("nombre");
		comboBoxTipoMedicamento.select(elementos.get(0));
	}

}
