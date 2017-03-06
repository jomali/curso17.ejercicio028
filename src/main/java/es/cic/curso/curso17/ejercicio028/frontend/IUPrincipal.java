package es.cic.curso.curso17.ejercicio028.frontend;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

/**
 * This UI is the application entry point. A UI may either represent a browser
 * window (or tab) or some part of a html page where a Vaadin application is
 * embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is
 * intended to be overridden to add component to the user interface and
 * initialize non-component functionality.
 * 
 * 
 * @author J. Francisco Martín
 * @version 1.0
 * @serial 2017/03/02
 *
 */
@SuppressWarnings("serial")
@Theme("mytheme")
public class IUPrincipal extends UI {

	public static final String VISTA_ADMINISTRACION = "admin";
	public static final String VISTA_DEMO = "demo";
	public static final String VISTA_NUEVA_RECETA = "nueva-receta";
	public static final String VISTA_PRINCIPAL = "";

	/** Gestiona una colección de implementaciones de <code>View</code>. */
	Navigator navegador;

	@WebServlet(urlPatterns = "/*", name = "ServletIUPrincipal", asyncSupported = true)
	@VaadinServletConfiguration(ui = IUPrincipal.class, productionMode = false)
	public static class Servlet extends VaadinServlet {

	}

	@Override
	protected void init(VaadinRequest request) {
		getPage().setTitle("Recetas Electrónicas");

		// Crea el navegador para controlar las vistas:
		navegador = new Navigator(this, this);

		// Crea y registra las vistas:
		navegador.addView(VISTA_PRINCIPAL, new VistaPrincipal(navegador));
		navegador.addView(VISTA_NUEVA_RECETA, new VistaNuevaReceta(navegador));
		navegador.addView(VISTA_ADMINISTRACION, new VistaAdministracion());
		navegador.addView(VISTA_DEMO, new VistaDemo(navegador));
	}

}
