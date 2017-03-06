package es.cic.curso.curso17.ejercicio028.frontend;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

public class VistaPrincipal extends VerticalLayout implements View {
	private static final long serialVersionUID = -8229167069516384540L;
	
	public static final String TITULO = "Gestión de Recetas";

	/** Permite navegar entre las vistas de la aplicación. */
	private Navigator navegador;
	
	private Grid gridRecetas;

	public VistaPrincipal(Navigator navegador) {
		this.navegador = navegador;
		setMargin(true);
		setSpacing(true);

		// Layout : ENCABEZADO
		addComponent(new LayoutCabecera(TITULO));

		// Layout : CONTENIDO
		addComponent(inicializaLayoutContenido());
	}

	private VerticalLayout inicializaLayoutContenido() {
		gridRecetas = new Grid();
		gridRecetas.setSizeFull();
		
		VerticalLayout layoutPrincipal = new VerticalLayout();
		layoutPrincipal.addComponent(gridRecetas);

		Button botonNuevaReceta = new Button("Añadir Receta");
		botonNuevaReceta.setIcon(FontAwesome.PLUS);
		botonNuevaReceta.setStyleName("primary");
		botonNuevaReceta.addClickListener(e-> navegador.navigateTo(IUPrincipal.VISTA_NUEVA_RECETA));
		
		HorizontalLayout layoutBotones = new HorizontalLayout();
		layoutBotones.setMargin(new MarginInfo(true, false, false, false));
		layoutBotones.setWidth(100.0F, Unit.PERCENTAGE);
		layoutBotones.setDefaultComponentAlignment(Alignment.MIDDLE_RIGHT);
		layoutBotones.addComponent(botonNuevaReceta);
		
		VerticalLayout layoutContenido = new VerticalLayout();
		layoutContenido.setMargin(false);
		layoutContenido.setSpacing(false);
		layoutContenido.addComponent(creaMigasDePan());
		layoutContenido.addComponent(layoutPrincipal);
		layoutContenido.addComponent(layoutBotones);
		
		return layoutContenido;
	}
	
	private HorizontalLayout creaMigasDePan() {		
		Button botonPrincipal = new Button(TITULO);
		botonPrincipal.setEnabled(false);
		botonPrincipal.setStyleName("borderless");

		HorizontalLayout layoutMigasDePan = new HorizontalLayout();
		layoutMigasDePan.setMargin(false);
		layoutMigasDePan.setSpacing(false);
		layoutMigasDePan.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
		layoutMigasDePan.addComponent(botonPrincipal);
		
		return layoutMigasDePan;
	}

	@Override
	public void enter(ViewChangeEvent event) {

	}

}
