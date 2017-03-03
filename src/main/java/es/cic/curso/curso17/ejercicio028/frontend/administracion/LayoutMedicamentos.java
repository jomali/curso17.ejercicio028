package es.cic.curso.curso17.ejercicio028.frontend.administracion;

import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Window;

import es.cic.curso.curso17.ejercicio028.frontend.VistaAdministracion;
import es.cic.curso.curso17.ejercicio028.modelo.Medicamento;

public class LayoutMedicamentos extends LayoutAbstracto<Medicamento> {
	private static final long serialVersionUID = -9164756494824050779L;

	public LayoutMedicamentos(VistaAdministracion padre) {
		super(padre);
	}

	@Override
	protected Grid generaGrid() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected FormLayout generaLayoutFormulario() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Button generaBotonAceptarVentanaConfirmacionBorrado(Window ventana) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void cargaFormulario(Medicamento elemento) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cargaGrid() {
		// TODO Auto-generated method stub
		
	}

}
