package es.cic.curso.curso17.ejercicio028.dto;

public class EnfermedadDTO {

	/** Identificador. Rango de valores: <code>[-2^63, 2^63)</code>. */
	private Long id;

	/** Nombre de la enfermedad. */
	private String nombre;

	/** Clasificación internacional de enfermedades. 10ed. */
	private String cie10;

	/** Descripción de la enfermedad. */
	private String descripcion;

	/**
	 * Constructor vacío.
	 */
	public EnfermedadDTO() {

	}

	/**
	 * Constructor. Crea un nuevo objeto a partir de los parámetros dados.
	 * 
	 * @param nombre
	 * @param cie10
	 */
	public EnfermedadDTO(String nombre, String cie10, String descripcion) {
		this();
		this.nombre = nombre;
		this.cie10 = cie10;
		this.descripcion = descripcion;
	}

	/**
	 * @return the id
	 */
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
		EnfermedadDTO other = (EnfermedadDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EnfermedadDTO [id=" + id + ", nombre=" + nombre + ", CIE-10=" + cie10 + ", descripcion=" + descripcion
				+ "]";
	}

}
