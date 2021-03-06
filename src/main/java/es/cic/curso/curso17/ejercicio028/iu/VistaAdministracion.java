package es.cic.curso.curso17.ejercicio028.iu;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

import es.cic.curso.curso17.ejercicio028.iu.administracion.LayoutEnfermedades;
import es.cic.curso.curso17.ejercicio028.iu.administracion.LayoutMedicamentos;
import es.cic.curso.curso17.ejercicio028.iu.administracion.LayoutTiposMedicamento;

import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public class VistaAdministracion extends VerticalLayout implements View {
	private static final long serialVersionUID = -4543919716590901538L;

	public static final String PESTANNA_ENFERMEDADES = "Enfermedades";
	public static final String PESTANNA_TIPOS_MEDICAMENTO = "Tipos de Medicamento";
	public static final String PESTANNA_MEDICAMENTOS = "Medicamentos";

	/** Hoja de pestañas. */
	private TabSheet tabSheetPrincipal;

	/** Registra la pestaña actual. */
	private Component componenteActual;

	/** Indica si la hoja de pestañas tiene la navegación bloqueada. */
	private boolean tabSheetPrincipalBloqueado;

	/** Contenidos de la pestaña: Gestión enfermedades. */
	private LayoutEnfermedades layoutEnfermedades;

	/** Contenidos de la pestaña: Gestión tipos medicamento. */
	private LayoutTiposMedicamento layoutTiposMedicamento;

	/** Contenidos de la pestaña: Gestión medicamentos. */
	private LayoutMedicamentos layoutMedicamentos;

	public VistaAdministracion() {
		setMargin(true);
		setSpacing(true);

		// Layout : ENCABEZADO
		HorizontalLayout layoutEncabezado = new LayoutCabecera("Administración");

		// Layout : CONTENIDO
		VerticalLayout layoutContenido = inicializaLayoutContenido();

		addComponents(layoutEncabezado, layoutContenido);
	}

	// /////////////////////////////////////////////////////////////////////////
	// Inicialización de componentes gráficos:

	private VerticalLayout inicializaLayoutContenido() {
		VerticalLayout layoutContenido = new VerticalLayout();
		layoutContenido.setMargin(false);
		layoutContenido.setSpacing(true);

		// Layout : GESTIÓN ENFERMEDADES
		layoutEnfermedades = new LayoutEnfermedades(this);

		// Layout : GESTIÓN TIPOS de MEDICAMENTO
		layoutTiposMedicamento = new LayoutTiposMedicamento(this);

		// Layout : GESTIÓN MEDICAMENTOS
		layoutMedicamentos = new LayoutMedicamentos(this);

		// TabSheet : PRINCIPAL
		tabSheetPrincipal = new TabSheet();
		tabSheetPrincipal.addTab(layoutMedicamentos, PESTANNA_MEDICAMENTOS);
		tabSheetPrincipal.addTab(layoutTiposMedicamento, PESTANNA_TIPOS_MEDICAMENTO);
		tabSheetPrincipal.addTab(layoutEnfermedades, PESTANNA_ENFERMEDADES);
		tabSheetPrincipal.addSelectedTabChangeListener(e -> {
			if (tabSheetPrincipalBloqueado) {
				tabSheetPrincipal.setSelectedTab(componenteActual);
				Notification.show("No se puede cambiar de pestaña con el formulario abierto.", Type.WARNING_MESSAGE);
			}
		});

		layoutContenido.addComponent(tabSheetPrincipal);
		return layoutContenido;
	}

	// /////////////////////////////////////////////////////////////////////////

	@Override
	public void enter(ViewChangeEvent event) {
		cargaDatos();
	}

	public void cargaDatos() {
		layoutEnfermedades.cargaGrid(null);
		layoutTiposMedicamento.cargaGrid(null);
		layoutMedicamentos.cargaGrid(null);
		layoutMedicamentos.cargaComboBox();
	}

	public void refrescaDatos() {
		layoutEnfermedades.refrescaDatos();
	}

	public void activaPestannas(boolean activado, Component componenteActual) {
		this.componenteActual = componenteActual;
		tabSheetPrincipalBloqueado = !activado;
	}

}
