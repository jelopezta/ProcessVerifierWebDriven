package data;

import java.util.Date;

/**
 * DTO con la información de respuesta del servicio de consulta.
 * 
 * @author Usuario
 *
 */
public class ResponseDataDto {

	/** Ciudad donde se consultó el proceso. */
	private String ciudad;

	/** Entidad en la cual se consultó el proceso. */
	private String entidad;

	/** Número del radicado del proceso. */
	private String noRadicado;

	/** Fecha de la actuación. */
	private String fechaActuacion;

	/** Fecha de la actuación como un date de java.util. */
	private Date fechaActuacionDate;

	/**
	 * Indica si la actuación corresponde a una actuación realizada en el día
	 * actual .
	 */
	private boolean delDiaActual;

	/** Primera fila de la tabla de actuaciones. */
	private String primeraFilaActuacion;

	/**
	 * @return the ciudad
	 */
	public String getCiudad() {
		return ciudad;
	}

	/**
	 * @param ciudad
	 *            the ciudad to set
	 */
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	/**
	 * @return the entidad
	 */
	public String getEntidad() {
		return entidad;
	}

	/**
	 * @param entidad
	 *            the entidad to set
	 */
	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}

	/**
	 * @return the noRadicado
	 */
	public String getNoRadicado() {
		return noRadicado;
	}

	/**
	 * @param noRadicado
	 *            the noRadicado to set
	 */
	public void setNoRadicado(String noRadicado) {
		this.noRadicado = noRadicado;
	}

	/**
	 * @return the fechaActuacion
	 */
	public String getFechaActuacion() {
		return fechaActuacion;
	}

	/**
	 * @param fechaActuacion
	 *            the fechaActuacion to set
	 */
	public void setFechaActuacion(String fechaActuacion) {
		this.fechaActuacion = fechaActuacion;
	}

	/**
	 * @return the fechaActuacionDate
	 */
	public Date getFechaActuacionDate() {
		return fechaActuacionDate;
	}

	/**
	 * @param fechaActuacionDate
	 *            the fechaActuacionDate to set
	 */
	public void setFechaActuacionDate(Date fechaActuacionDate) {
		this.fechaActuacionDate = fechaActuacionDate;
	}

	/**
	 * @return the delDiaActual
	 */
	public boolean isDelDiaActual() {
		return delDiaActual;
	}

	/**
	 * @param delDiaActual
	 *            the delDiaActual to set
	 */
	public void setDelDiaActual(boolean delDiaActual) {
		this.delDiaActual = delDiaActual;
	}

	/**
	 * @return the primeraFilaActuacion
	 */
	public String getPrimeraFilaActuacion() {
		return primeraFilaActuacion;
	}

	/**
	 * @param primeraFilaActuacion
	 *            the primeraFilaActuacion to set
	 */
	public void setPrimeraFilaActuacion(String primeraFilaActuacion) {
		this.primeraFilaActuacion = primeraFilaActuacion;
	}
}
