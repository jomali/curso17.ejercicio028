package es.cic.curso.curso17.ejercicio028.dto;

public class EnfermedadDTO {
	
	/** Identificador. Rango de valores: <code>[-2^63, 2^63)</code>. */
	private Long id;
	
	/** Nombre de la enfermedad. */
	private String nombre;

	/** Descripción de la enfermedad. */
	private String descripcion;

	/**
	 * Constructor vacío.
	 */
	public EnfermedadDTO() {
		
	}
	
	/**
	 * Constructor. Crea un nuevo objeto con el nombre pasado como parámetro.
	 * @param nombre
	 */
	public EnfermedadDTO(String nombre) {
		this.nombre = nombre;
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
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
