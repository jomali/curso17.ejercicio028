package es.cic.curso.curso17.ejercicio028.frontend;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.VerticalLayout;

public class VistaPrincipal extends VerticalLayout implements View {
	private static final long serialVersionUID = -8229167069516384540L;

	/** Permite navegar entre las vistas de la aplicación. */
	private Navigator navegador;

	public VistaPrincipal(Navigator navegador) {
		this.navegador = navegador;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		navegador.navigateTo(IUPrincipal.VISTA_DEMO);
	}

}
