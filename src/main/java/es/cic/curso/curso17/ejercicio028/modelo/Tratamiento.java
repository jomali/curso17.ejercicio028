package es.cic.curso.curso17.ejercicio028.modelo;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TRATAMIENTO")
public class Tratamiento {

	/** Referencia a la receta. */
	@JoinColumn(name = "id_receta")
	@ManyToOne(fetch = FetchType.LAZY)
	private Receta receta;

	/** Referencia al tipo de medicamento. */
	@JoinColumn(name = "id_medicamento")
	@ManyToOne(fetch = FetchType.LAZY)
	private Medicamento medicamento;

	/**
	 * @return the receta
	 */
	public Receta getReceta() {
		return receta;
	}

	/**
	 * @return the medicamento
	 */
	public Medicamento getMedicamento() {
		return medicamento;
	}

	/**
	 * @param receta
	 *            the receta to set
	 */
	public void setReceta(Receta receta) {
		this.receta = receta;
	}

	/**
	 * @param medicamento
	 *            the medicamento to set
	 */
	public void setMedicamento(Medicamento medicamento) {
		this.medicamento = medicamento;
	}
}
