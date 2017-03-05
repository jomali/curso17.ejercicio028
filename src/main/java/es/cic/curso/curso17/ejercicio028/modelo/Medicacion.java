package es.cic.curso.curso17.ejercicio028.modelo;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ENFERMEDAD_MEDICAMENTO")
public class Medicacion implements Identificable<Long>, Cloneable {
	private static final long serialVersionUID = -1834404426900148883L;

	/** Identificador. Rango de valores: <code>[-2^63, 2^63)</code>. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/** Referencia a la enfermedad. */
	@JoinColumn(name = "id_enfermedad")
	@OneToOne(fetch = FetchType.EAGER)
	private Enfermedad enfermedad;
	
	/** Referencia al medicamento. */
	@JoinColumn(name = "id_medicamento")
	@OneToOne(fetch = FetchType.EAGER)
	private Medicamento medicamento;

	/**
	 * @return the id
	 */
	@Override
	public Long getId() {
		return id;
	}

	/**
	 * @return the enfermedad
	 */
	public Enfermedad getEnfermedad() {
		return enfermedad;
	}

	/**
	 * @return the medicamento
	 */
	public Medicamento getMedicamento() {
		return medicamento;
	}

	/**
	 * @param id the id to set
	 */
	@Override
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param enfermedad the enfermedad to set
	 */
	public void setEnfermedad(Enfermedad enfermedad) {
		this.enfermedad = enfermedad;
	}

	/**
	 * @param medicamento the medicamento to set
	 */
	public void setMedicamento(Medicamento medicamento) {
		this.medicamento = medicamento;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((enfermedad == null) ? 0 : enfermedad.hashCode());
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
		Medicacion other = (Medicacion) obj;
		if (enfermedad == null) {
			if (other.enfermedad != null)
				return false;
		} else if (!enfermedad.equals(other.enfermedad))
			return false;
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
	public Medicacion clone() {
		Medicacion clon = new Medicacion();
		clon.id = id;
		clon.enfermedad = enfermedad;
		clon.medicamento = medicamento;
		return clon;
	}

}
