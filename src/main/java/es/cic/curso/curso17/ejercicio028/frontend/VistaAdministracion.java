package es.cic.curso.curso17.ejercicio028.frontend;

import java.io.File;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

import es.cic.curso.curso17.ejercicio028.frontend.administracion.LayoutEnfermedades;
import es.cic.curso.curso17.ejercicio028.frontend.administracion.LayoutMedicamentos;
import es.cic.curso.curso17.ejercicio028.frontend.administracion.LayoutTiposMedicamento;

import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public class VistaAdministracion extends VerticalLayout implements View {
	private static final long serialVersionUID = -4543919716590901538L;

	public static final String PESTANNA_ENFERMEDADES = "Enfermedades";
	public static final String PESTANNA_TIPOS_MEDICAMENTO = "Tipos de Medicamento";
	public static final String PESTANNA_MEDICAMENTOS = "Medicamentos";

	/** Hoja de pestañas. */
	private TabSheet tabSheetPrincipal;

	private Component componenteActual;

	/**
	 * Indica si <code>tabSheetPrincipal</code> tiene la navegación bloqueada.
	 */
	private boolean tabSheetPrincipalBloqueado;

	/** Contenidos de la pestaña: Gestión enfermedades. */
	private LayoutEnfermedades layoutEnfermedades;

	/** Contenidos de la pestaña: Gestión tipos medicamento. */
	private LayoutEnfermedades layoutTiposMedicamento;

	/** Contenidos de la pestaña: Gestión medicamentos. */
	private LayoutEnfermedades layoutMedicamentos;

	public VistaAdministracion() {
		setMargin(true);
		setSpacing(true);

		// Layout : ENCABEZADO
		HorizontalLayout layoutEncabezado = inicializaLayoutEncabezado();

		// Layout : CONTENIDO
		VerticalLayout layoutContenido = inicializaLayoutContenido();

		addComponents(layoutEncabezado, layoutContenido);
	}

	// /////////////////////////////////////////////////////////////////////////
	// Inicialización de componentes gráficos:

	private HorizontalLayout inicializaLayoutEncabezado() {
		HorizontalLayout layoutEncabezado = new HorizontalLayout();
		layoutEncabezado.setMargin(false);
		layoutEncabezado.setSpacing(true);
		layoutEncabezado.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);

		String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
		FileResource resource = new FileResource(new File(basepath + "/WEB-INF/images/cic_logo.png"));
		Image imagen = new Image(null, resource);
		imagen.setHeight(38.0F, Unit.PIXELS);

		Label titulo = new Label("<span style=\"font-size: 150%;\">Administración</span>");
		titulo.setContentMode(ContentMode.HTML);

		layoutEncabezado.addComponents(imagen, titulo);
		return layoutEncabezado;
	}

	private VerticalLayout inicializaLayoutContenido() {
		VerticalLayout layoutContenido = new VerticalLayout();
		layoutContenido.setMargin(false);
		layoutContenido.setSpacing(true);

		// Layout : GESTIÓN ENFERMEDADES
		layoutEnfermedades = new LayoutEnfermedades(this);

		// Layout : GESTIÓN TIPOS de MEDICAMENTO
		layoutTiposMedicamento = new LayoutEnfermedades(this);

		// Layout : GESTIÓN MEDICAMENTOS
		layoutMedicamentos = new LayoutEnfermedades(this);

		// TabSheet : PRINCIPAL
		tabSheetPrincipal = new TabSheet();
		tabSheetPrincipal.addTab(layoutEnfermedades, PESTANNA_ENFERMEDADES);
		tabSheetPrincipal.addTab(layoutTiposMedicamento, PESTANNA_TIPOS_MEDICAMENTO);
		tabSheetPrincipal.addTab(layoutMedicamentos, PESTANNA_MEDICAMENTOS);
		tabSheetPrincipal.addSelectedTabChangeListener(e -> {
			if (tabSheetPrincipalBloqueado) {
				tabSheetPrincipal.setSelectedTab(componenteActual);
				Notification.show("No se puede cambiar de pestaña con el formulario abierto.", Type.WARNING_MESSAGE);
			}
		});
		tabSheetPrincipal.setSelectedTab(layoutEnfermedades);
		componenteActual = layoutEnfermedades;

		layoutContenido.addComponent(tabSheetPrincipal);
		return layoutContenido;
	}

	// /////////////////////////////////////////////////////////////////////////

	@Override
	public void enter(ViewChangeEvent event) {
		layoutEnfermedades.cargaGrid();
	}

	public void activaPestannas(boolean activado, Component componenteActual) {
		this.componenteActual = componenteActual;
		tabSheetPrincipalBloqueado = !activado;
	}

}
