package es.cic.curso.curso17.ejercicio028.vista;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

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

	@WebServlet(urlPatterns = "/*", name = "ServletIUPrincipal", asyncSupported = true)
	@VaadinServletConfiguration(ui = IUPrincipal.class, productionMode = false)
	public static class Servlet extends VaadinServlet {

	}

	@Override
	protected void init(VaadinRequest request) {
		final VerticalLayout layout = new VerticalLayout();
		setContent(layout);

		CssLayout topBar = new CssLayout();
		topBar.addStyleName("top");
		Label title = new Label("Hola mundo");
		title.addStyleName("h1");
		topBar.addComponent(title);

		layout.addComponent(topBar);

		//

		HorizontalLayout menuAndContent = new HorizontalLayout();
		menuAndContent.setSizeFull();
		layout.addComponent(menuAndContent);

		CssLayout menu = new CssLayout();
		menu.addStyleName("menu");
		menu.setWidth(100.0F, Unit.PERCENTAGE);
		menuAndContent.addComponent(menu);

		VerticalLayout content = new VerticalLayout();
		content.setMargin(true);
		content.setSpacing(true);
		menuAndContent.addComponent(content);

		menuAndContent.setExpandRatio(menu, 2);
		menuAndContent.setExpandRatio(content, 8);

		Label header = new Label("Lorem ipsum");
		header.addStyleName("h2");
		content.addComponent(header);

		Label text = new Label(
				"<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi vehicula odio vel dignissim iaculis. Donec lectus lacus, aliquet in facilisis ac, dignissim vel justo. Aenean bibendum lectus sit amet urna vestibulum bibendum. Nulla laoreet leo odio, viverra vehicula sem tristique vitae. Mauris euismod et mi eu cursus. Etiam iaculis nunc nec nisi imperdiet laoreet. Vestibulum at justo feugiat, euismod augue et, pharetra neque. Fusce sed felis neque. Ut consectetur nibh sed neque ullamcorper sodales. Fusce auctor ligula id lectus aliquam eleifend. Maecenas non diam at libero scelerisque consequat quis et ipsum. Suspendisse malesuada neque fermentum semper vestibulum. Cras vel risus gravida, consequat dui in, auctor ante.</p>"
						+ "<p>Curabitur feugiat sodales scelerisque. Etiam vitae magna risus. Nam tempor ligula in massa malesuada aliquam. Proin pellentesque lacus tincidunt velit volutpat faucibus. Cras condimentum nunc ac felis molestie congue. Nulla imperdiet dui sed magna interdum rhoncus. Duis lobortis eros aliquam pellentesque dapibus. Etiam sem nibh, consectetur eu feugiat eget, ultrices sit amet ipsum. Aenean condimentum leo lacus, at pellentesque nisi condimentum ac. Aliquam erat volutpat.</p>",
				ContentMode.HTML);
		content.addComponent(text);

		FormLayout form = new FormLayout();
		form.setMargin(false);
		form.setSpacing(true);
		content.addComponent(form);
		
		TextField firstName = new TextField("First name");
		form.addComponent(firstName);
		
		TextField lastName = new TextField("Lasst name");
		form.addComponent(lastName);
		
		TextField email = new TextField("Email");
		form.addComponent(email);
		
		Button submit = new Button("Submit");
		form.addComponent(submit);
	}

}
