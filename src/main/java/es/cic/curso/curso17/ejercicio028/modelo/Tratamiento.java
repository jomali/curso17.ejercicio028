package es.cic.curso.curso17.ejercicio028.modelo;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TRATAMIENTO")
public class Tratamiento implements Cloneable, Identificable<Long> {
	private static final long serialVersionUID = -6049183860457208472L;

	/** Identificador. Rango de valores: <code>[-2^63, 2^63)</code>. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/** Referencia a la receta. */
	@JoinColumn(name = "id_receta")
	@ManyToOne(fetch = FetchType.LAZY)
	private Receta receta;

	/** Referencia al tipo de medicamento. */
	@JoinColumn(name = "id_medicamento")
	@ManyToOne(fetch = FetchType.LAZY)
	private Medicamento medicamento;

	/**
	 * @return the id
	 */
	@Override
	public Long getId() {
		return id;
	}

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
	 * @param id
	 *            the id to set
	 */
	@Override
	public void setId(Long id) {
		this.id = id;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((medicamento == null) ? 0 : medicamento.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tratamiento other = (Tratamiento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (medicamento == null) {
			if (other.medicamento != null)
				return false;
		} else if (!medicamento.equals(other.medicamento))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Tratamiento [id=" + id + ", id_receta=" + receta.getId() + ", id_medicamento=" + medicamento.getId()
				+ "]";
	}
	
	@Override
	public Tratamiento clone() {
		Tratamiento tratamiento = new Tratamiento();
		tratamiento.id = id;
		tratamiento.receta = receta;
		tratamiento.medicamento = medicamento;
		return tratamiento;
	}

}
