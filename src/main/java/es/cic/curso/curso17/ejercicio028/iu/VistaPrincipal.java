package es.cic.curso.curso17.ejercicio028.iu;

import java.util.Collection;

import org.springframework.web.context.ContextLoader;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Grid.SelectionMode;

import es.cic.curso.curso17.ejercicio028.modelo.Receta;
import es.cic.curso.curso17.ejercicio028.servicio.ServicioGestorRecetas;

public class VistaPrincipal extends VerticalLayout implements View {
	private static final long serialVersionUID = -8229167069516384540L;

	public static final String TITULO = "Gestión de Recetas";

	/** Permite navegar entre las vistas de la aplicación. */
	private Navigator navegador;

	/** Lógica de negocio con acceso a BB.DD.: medicamentos */
	private ServicioGestorRecetas servicioGestorRecetas;

	private Grid gridRecetas;
	private Label labelVacio;

	public VistaPrincipal(Navigator navegador) {
		this.navegador = navegador;
		servicioGestorRecetas = ContextLoader.getCurrentWebApplicationContext().getBean(ServicioGestorRecetas.class);

		setMargin(true);
		setSpacing(true);

		// Layout : ENCABEZADO
		addComponent(new LayoutCabecera(TITULO));

		// Layout : CONTENIDO
		addComponent(inicializaLayoutContenido());
	}

	private VerticalLayout inicializaLayoutContenido() {
		gridRecetas = new Grid();
		gridRecetas.setColumns("nombre");
		gridRecetas.setSelectionMode(SelectionMode.NONE);
		gridRecetas.setSizeFull();

		labelVacio = new Label();
		labelVacio.setCaption("<br/><strong>No hay entradas registras en el sistema</strong><br/>");
		labelVacio.setCaptionAsHtml(true);
		labelVacio.setSizeFull();

		VerticalLayout layoutPrincipal = new VerticalLayout();
		layoutPrincipal.addComponent(gridRecetas);
		layoutPrincipal.addComponent(labelVacio);
		layoutPrincipal.setComponentAlignment(labelVacio, Alignment.MIDDLE_CENTER);

		Button botonNuevaReceta = new Button("Añadir Receta");
		botonNuevaReceta.setIcon(FontAwesome.PLUS);
		botonNuevaReceta.setStyleName("primary");
		botonNuevaReceta.addClickListener(e -> navegador.navigateTo(IUPrincipal.VISTA_NUEVA_RECETA));

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

	private void cargaGrid() {
		Collection<Receta> elementos = servicioGestorRecetas.listaRecetas();
		labelVacio.setVisible(elementos.isEmpty() ? true : false);
		gridRecetas.setVisible(elementos.isEmpty() ? false : true);
		gridRecetas.setContainerDataSource(new BeanItemContainer<>(Receta.class, elementos));
	}

	@Override
	public void enter(ViewChangeEvent event) {
		cargaGrid();
	}

}
