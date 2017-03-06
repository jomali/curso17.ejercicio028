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

	public static final float POSICION_DIVISOR = 35.0F;

	/** Lógica de negocio con acceso a BB.DD.: medicamentos */
	private ServicioMedicamento servicioMedicamento;

	/** Lógica de negocio con acceso a BB.DD.: tipos de medicamento */
	private ServicioTipoMedicamento servicioTipoMedicamento;

	private TextField textFieldNombre;

	private ComboBox comboBoxTipoMedicamento;

	private TextArea textAreaDescripcion;

	private Button botonAcepta;

	public LayoutMedicamentos(VistaAdministracion padre) {
		super(padre, POSICION_DIVISOR);
		servicioMedicamento = ContextLoader.getCurrentWebApplicationContext().getBean(ServicioMedicamento.class);
		servicioTipoMedicamento = ContextLoader.getCurrentWebApplicationContext()
				.getBean(ServicioTipoMedicamento.class);
		cargaComboBox();
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
		textFieldNombre.setNullRepresentation("");
		textFieldNombre.setNullSettingAllowed(false);
		textFieldNombre.setRequired(true);
		textFieldNombre.setSizeFull();
		textFieldNombre.addTextChangeListener(e -> botonAcepta.setEnabled(true));

		comboBoxTipoMedicamento = new ComboBox("Tipo:");
		comboBoxTipoMedicamento.setFilteringMode(FilteringMode.CONTAINS);
		comboBoxTipoMedicamento.setInvalidAllowed(false);
		comboBoxTipoMedicamento.setNullSelectionAllowed(false);
		comboBoxTipoMedicamento.setPageLength(5);
		comboBoxTipoMedicamento.setRequired(true);
		comboBoxTipoMedicamento.setTextInputAllowed(false);
		comboBoxTipoMedicamento.setWidth(250.0F, Unit.PIXELS);
		comboBoxTipoMedicamento.addValueChangeListener(e -> botonAcepta.setEnabled(true));

		textAreaDescripcion = new TextArea("Descripción:");
		textAreaDescripcion.setInputPrompt("Descripción");
		textAreaDescripcion.setNullRepresentation("");
		textAreaDescripcion.setNullSettingAllowed(false);
		textAreaDescripcion.setRows(5);
		textAreaDescripcion.setSizeFull();
		textAreaDescripcion.addTextChangeListener(e -> botonAcepta.setEnabled(true));

		botonAcepta = new Button("Aceptar");
		botonAcepta.setEnabled(false);
		botonAcepta.addClickListener(e -> {
			if (!validaFormulario()) {
				return;
			}
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
				new Notification(
						String.format("Entrada añadida: <strong>\"%s\"</strong>", nuevoMedicamento.getNombre()), "",
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
				new Notification(
						String.format("Entrada modificada: <strong>\"%s\"</strong>", nuevoMedicamento.getNombre()), "",
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
		layoutBotonesFormulario.addComponents(botonAcepta);
		layoutBotonesFormulario.addComponents(botonCancela);

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
			String nombre = elementoSeleccionado.getNombre();
			servicioMedicamento.eliminaMedicamento(elementoSeleccionado.getId());
			padre.refrescaDatos();
			cargaGrid();
			ventana.close();
			new Notification(String.format("Entrada eliminada: <strong>\"%s\"</strong>", nombre), "",
					Type.TRAY_NOTIFICATION, true).show(Page.getCurrent());
		});
		return botonAceptar;
	}

	@Override
	protected void cargaFormulario(MedicamentoDTO elemento) {
		if (elemento == null) {
			textFieldNombre.clear();
			cargaComboBox();
			textAreaDescripcion.clear();
		} else {
			String descripcion = elemento.getDescripcion();
			textFieldNombre.setValue(elemento.getNombre());
			if (elemento.getTipo() != null) {
				comboBoxTipoMedicamento.setValue(elemento.getTipo());
			} else {
				cargaComboBox();
			}
			textAreaDescripcion.setValue(descripcion == null ? "" : descripcion);
		}
		botonAcepta.setEnabled(false);
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
		botonAcepta.setEnabled(false);
	}

}
