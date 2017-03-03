package es.cic.curso.curso17.ejercicio028.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "MEDICAMENTO")
public class Medicamento implements Cloneable, Identificable<Long> {
	private static final long serialVersionUID = -8140507350269003336L;

	/** Identificador. Rango de valores: <code>[-2^63, 2^63)</code>. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/** Referencia al tipo de medicamento. */
	@JoinColumn(name = "id_tipo_medicamento")
	@ManyToOne(fetch = FetchType.LAZY)
	private TipoMedicamento tipo;

	/** Nombre del medicamento. */
	@Column(name = "nombre")
	private String nombre;

	/** Descripción del medicamento. */
	@Column(name = "descripcion")
	private String descripcion;

	/**
	 * @return the id
	 */
	@Override
	public Long getId() {
		return id;
	}

	/**
	 * @return the tipo
	 */
	public TipoMedicamento getTipo() {
		return tipo;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
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
	 * @param tipo
	 *            the tipo to set
	 */
	public void setTipo(TipoMedicamento tipo) {
		this.tipo = tipo;
	}

	/**
	 * @param nombre
	 *            the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @param descripcion
	 *            the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Medicamento other = (Medicamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		Long idTipo = tipo == null ? null : tipo.getId();
		return "Medicamento [id=" + id + ", tipo=" + idTipo + ", nombre=" + nombre + ", descripcion="
				+ descripcion + "]";
	}

	@Override
	public Medicamento clone() {
		Medicamento clon = new Medicamento();
		clon.id = id;
		clon.tipo = tipo;
		clon.nombre = nombre;
		clon.descripcion = descripcion;
		return clon;
	}

}
