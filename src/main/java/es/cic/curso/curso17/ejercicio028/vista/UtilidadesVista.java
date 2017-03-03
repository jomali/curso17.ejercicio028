package es.cic.curso.curso17.ejercicio028.vista;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;

public class UtilidadesVista {
	
	public static Window creaVentanaModal(String mensaje) {
		Window resultado = new Window();
		resultado.setWidth(500.0F, Unit.PIXELS);
		resultado.setModal(true);
		resultado.setClosable(false);
		resultado.setResizable(false);
		resultado.setDraggable(false);

		Label label = new Label(mensaje);
		label.setContentMode(ContentMode.HTML);

		Button botonAceptar = new Button("Aceptar");
		botonAceptar.addClickListener(e -> resultado.close() );

		final FormLayout content = new FormLayout();
		content.setMargin(true);
		content.addComponents(label, botonAceptar);
		resultado.setContent(content);
		resultado.center();
		return resultado;
	}

}
