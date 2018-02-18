package data;

/**
 * Dto con la informaci�n necesaria para la consulta del proceso.
 * 
 * @author jelopezta
 *
 */
public class QueryDataDto {

	/** Posici�n de la ciudad en el array de strings fuente del dto. */
	private static final int POSICION_CIUDAD = 0;

	/** Posici�n de la entidad en el array de strings fuente del dto. */
	private static final int POSICION_ENTIDAD = 1;

	/**
	 * Posici�n del n�mero de radicaci�n en el array de strings fuente del dto.
	 */
	private static final int POSICION_NORADICACION = 2;

	/** N�mero de radicaci�n del proceso. */
	private String noRadicacion;

	/** Nombre de la ciudad que se est� consultando en el proceso. */
	private String nombreCiudad;

	/** Nombre de la entidad que se est� consultando en el proceso. */
	private String nombreEntidad;

	/**
	 * Constructor basado en un array de strings.
	 * 
	 * @param split
	 *            el array de strings a usar como fuente de datos para el dto
	 */
	public QueryDataDto(String[] split) {
		nombreCiudad = split[POSICION_CIUDAD].trim().intern();
		nombreEntidad = split[POSICION_ENTIDAD].trim().intern();
		noRadicacion = split[POSICION_NORADICACION].trim();

		// Correcciones para ciudades con caracter ',' que impiden su lectura
		// directa desde el archivo fuente
		if (nombreCiudad.equals("BOGOTA")) {
			nombreCiudad = "BOGOTA, D.C.";
		}
	}

	/**
	 * @return the noRadicacion
	 */
	public String getNoRadicacion() {
		return noRadicacion;
	}

	/**
	 * @param noRadicacion
	 *            the noRadicacion to set
	 */
	public void setNoRadicacion(String noRadicacion) {
		this.noRadicacion = noRadicacion;
	}

	/**
	 * @return the nombreCiudad
	 */
	public String getNombreCiudad() {
		return nombreCiudad;
	}

	/**
	 * @return the nombreEntidad
	 */
	public String getNombreEntidad() {
		return nombreEntidad;
	}

}
