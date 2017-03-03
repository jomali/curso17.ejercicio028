package es.cic.curso.curso17.ejercicio028.vista;

import java.io.File;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

import es.cic.curso.curso17.ejercicio028.vista.administracion.LayoutEnfermedades;
import es.cic.curso.curso17.ejercicio028.vista.administracion.LayoutMedicamentos;
import es.cic.curso.curso17.ejercicio028.vista.administracion.LayoutTiposMedicamento;

public class VistaAdministracion extends VerticalLayout implements View {
	private static final long serialVersionUID = -4543919716590901538L;
	
	private LayoutEnfermedades layoutEnfermedades;
		
	public VistaAdministracion() {
		// layout. ENCABEZADO
		HorizontalLayout layoutEncabezado = inicializaLayoutEncabezado();
		// layout. CONTENIDO
		VerticalLayout layoutContenido = new VerticalLayout();
		layoutContenido.setMargin(new MarginInfo(false, true, false, true)); // false, true, true, true
		layoutContenido.setSpacing(true);
		
		// layout. CONTENIDO > GESTIÓN ENFERMEDADES
		layoutEnfermedades = new LayoutEnfermedades();
		// layout. CONTENIDO > GESTIÓN TIPOS de MEDICAMENTO
		VerticalLayout layoutTiposMedicamento = new LayoutTiposMedicamento();
		// layout. CONTENIDO > GESTIÓN MEDICAMENTOS
		VerticalLayout layoutMedicamento = new LayoutMedicamentos();
		
		// tabSheet. PRINCIPAL
		TabSheet tabSheetPrincipal = new TabSheet();
		tabSheetPrincipal.addTab(layoutEnfermedades, "Enfermedades");
		tabSheetPrincipal.addTab(layoutTiposMedicamento, "Tipos Medicamento");
		tabSheetPrincipal.addTab(layoutMedicamento, "Medicamentos");
		
		layoutContenido.addComponent(tabSheetPrincipal);
		addComponents(layoutEncabezado, layoutContenido);
	}

	// /////////////////////////////////////////////////////////////////////////
	// Inicialización de componentes gráficos:

	private HorizontalLayout inicializaLayoutEncabezado() {
		String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
		FileResource resource = new FileResource(new File(basepath + "/WEB-INF/images/cic_logo.png"));
		Image imagen = new Image(null, resource);
		imagen.setHeight(38.0F, Unit.PIXELS);
		
		Label titulo = new Label("<span style=\"font-size: 150%;\">Administración</span>");
		titulo.setContentMode(ContentMode.HTML);

		HorizontalLayout layoutEncabezado = new HorizontalLayout();
		layoutEncabezado.setMargin(new MarginInfo(true, true, true, true));
		layoutEncabezado.setSpacing(false);
		layoutEncabezado.addComponents(imagen, titulo);
		layoutEncabezado.setComponentAlignment(imagen, Alignment.MIDDLE_LEFT);
		layoutEncabezado.setComponentAlignment(titulo, Alignment.MIDDLE_LEFT);
		return layoutEncabezado;
	}

	// /////////////////////////////////////////////////////////////////////////

	@Override
	public void enter(ViewChangeEvent event) {
		layoutEnfermedades.cargaGrid();
		
	}

}
