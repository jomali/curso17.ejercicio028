package es.cic.curso.curso17.ejercicio028.iu;

import java.io.File;

import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;

public class LayoutCabecera extends HorizontalLayout implements Component {
	private static final long serialVersionUID = 8582063438617779671L;

	public LayoutCabecera(String titulo) {
		setMargin(false);
		setSpacing(true);
		setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);

		String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
		FileResource resource = new FileResource(new File(basepath + "/WEB-INF/images/cic_logo.png"));
		Image imagen = new Image(null, resource);
		imagen.setHeight(38.0F, Unit.PIXELS);

		Label labelTitulo = new Label("<span style=\"font-size: 150%;\">" + titulo + "</span>");
		labelTitulo.setContentMode(ContentMode.HTML);

		addComponents(imagen, labelTitulo);
	}
	
}
