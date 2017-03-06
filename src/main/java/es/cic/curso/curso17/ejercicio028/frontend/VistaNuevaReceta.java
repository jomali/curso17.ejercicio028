package es.cic.curso.curso17.ejercicio028.frontend;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class VistaNuevaReceta extends VerticalLayout implements View {
	private static final long serialVersionUID = -8229167069516384540L;
	
	public static final String TITULO = "Nueva Receta";

	/** Permite navegar entre las vistas de la aplicación. */
	private Navigator navegador;
	
	public VistaNuevaReceta(Navigator navegador) {
		this.navegador = navegador;
		setMargin(true);
		setSpacing(true);

		// Layout : ENCABEZADO
		addComponent(new LayoutCabecera(TITULO));

		// Layout : CONTENIDO
		addComponent(inicializaLayoutContenido());
	}

	private VerticalLayout inicializaLayoutContenido() {
		HorizontalSplitPanel splitPanelPrincipal = new HorizontalSplitPanel();
		splitPanelPrincipal.setSplitPosition(50.0F, Unit.PERCENTAGE);
		splitPanelPrincipal.setMinSplitPosition(20.0F, Unit.PERCENTAGE);
		splitPanelPrincipal.setMaxSplitPosition(80.0F, Unit.PERCENTAGE);
		splitPanelPrincipal.setLocked(false);
		splitPanelPrincipal.setSizeFull();
		splitPanelPrincipal.setFirstComponent(new Grid());
		splitPanelPrincipal.setSecondComponent(new Grid());
		
		Button botonAceptar = new Button("Aceptar");
		botonAceptar.setStyleName("friendly");
		botonAceptar.addClickListener(e-> navegador.navigateTo(IUPrincipal.VISTA_PRINCIPAL));
		
		Button botonCancelar = new Button("Cancelar");
		botonCancelar.setStyleName("danger");
		botonCancelar.addClickListener(e -> navegador.navigateTo(IUPrincipal.VISTA_PRINCIPAL));
		
		HorizontalLayout layoutBotones = new HorizontalLayout();
		layoutBotones.setMargin(new MarginInfo(true, false, false, false));
		layoutBotones.setSpacing(true);
		layoutBotones.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
		layoutBotones.addComponent(botonAceptar);
		layoutBotones.addComponent(botonCancelar);
		
		VerticalLayout layoutContenido = new VerticalLayout();
		layoutContenido.setMargin(false);
		layoutContenido.setSpacing(false);
		layoutContenido.addComponent(creaMigasDePan());
		layoutContenido.addComponent(splitPanelPrincipal);
		layoutContenido.addComponent(layoutBotones);
		layoutContenido.setComponentAlignment(layoutBotones, Alignment.BOTTOM_RIGHT);
		
		return layoutContenido;
	}
	
	private HorizontalLayout creaMigasDePan() {		
		Button botonPrincipal = new Button(VistaPrincipal.TITULO);
		botonPrincipal.setEnabled(true);
		botonPrincipal.setStyleName("borderless");
		botonPrincipal.addClickListener(e -> navegador.navigateTo(""));
		
		Button botonSecundario = new Button(TITULO);
		botonSecundario.setEnabled(false);
		botonSecundario.setStyleName("borderless");

		HorizontalLayout layoutMigasDePan = new HorizontalLayout();
		layoutMigasDePan.setMargin(false);
		layoutMigasDePan.setSpacing(false);
		layoutMigasDePan.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
		layoutMigasDePan.addComponent(botonPrincipal);
		layoutMigasDePan.addComponent(new Label(">"));
		layoutMigasDePan.addComponent(botonSecundario);
		
		return layoutMigasDePan;
	}

	@Override
	public void enter(ViewChangeEvent event) {

	}

}
