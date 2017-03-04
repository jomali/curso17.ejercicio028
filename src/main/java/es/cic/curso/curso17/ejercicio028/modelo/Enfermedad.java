package es.cic.curso.curso17.ejercicio028.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ENFERMEDAD")
public class Enfermedad implements Cloneable, Identificable<Long> {
	private static final long serialVersionUID = -4853042887127874371L;

	/** Identificador. Rango de valores: <code>[-2^63, 2^63)</code>. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/** Nombre de la enfermedad. */
	@Column(name = "nombre")
	private String nombre;

	/** Clasificación internacional de enfermedades. 10ed. */
	@Column(name = "cie_10")
	private String cie10;

	/** Descripción de la enfermedad. */
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
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @return the cie10
	 */
	public String getCie10() {
		return cie10;
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
	 * @param nombre
	 *            the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @param cie10
	 *            the cie10 to set
	 */
	public void setCie10(String cie10) {
		this.cie10 = cie10;
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
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
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
		Enfermedad other = (Enfermedad) obj;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Enfermedad [id=" + id + ", nombre=" + nombre + ", cie10=" + cie10 + ", descripcion=" + descripcion
				+ "]";
	}

	@Override
	public Enfermedad clone() {
		Enfermedad clon = new Enfermedad();
		clon.id = id;
		clon.nombre = nombre;
		clon.cie10 = cie10;
		clon.descripcion = descripcion;
		return clon;
	}

}
