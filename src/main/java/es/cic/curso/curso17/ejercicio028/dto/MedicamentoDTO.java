package es.cic.curso.curso17.ejercicio028.dto;

import es.cic.curso.curso17.ejercicio028.modelo.TipoMedicamento;

public class MedicamentoDTO {

	/** Identificador. Rango de valores: <code>[-2^63, 2^63)</code>. */
	private Long id;

	/** Referencia al tipo de medicamento. */
	private TipoMedicamento tipo;

	/** Nombre del tipo de medicamento. */
	private String nombreTipo;

	/** Nombre del medicamento. */
	private String nombre;

	/** Descripción del medicamento. */
	private String descripcion;

	/**
	 * Constructor vacío.
	 */
	public MedicamentoDTO() {

	}

	/**
	 * Constructor. Crea un nuevo objeto a partir del tipo y el nombre pasados
	 * como parámetro.
	 * 
	 * @param tipo
	 * @param nombre
	 */
	public MedicamentoDTO(TipoMedicamento tipo, String nombre) {
		this.tipo = tipo;
		this.nombreTipo = tipo == null ? null : tipo.getNombre();
		this.nombre = nombre;
	}

	/**
	 * @return the id
	 */
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
	 * @return the nombreTipo
	 */
	public String getNombreTipo() {
		return nombreTipo;
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
	 * @param nombreTipo
	 *            the nombreTipo to set
	 */
	public void setNombreTipo(String nombreTipo) {
		this.nombreTipo = nombreTipo;
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
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((nombreTipo == null) ? 0 : nombreTipo.hashCode());
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
		MedicamentoDTO other = (MedicamentoDTO) obj;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (nombreTipo == null) {
			if (other.nombreTipo != null)
				return false;
		} else if (!nombreTipo.equals(other.nombreTipo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MedicamentoDTO [id=" + id + ", nombreTipo=" + nombreTipo + ", nombre=" + nombre + ", descripcion="
				+ descripcion + "]";
	}

}
