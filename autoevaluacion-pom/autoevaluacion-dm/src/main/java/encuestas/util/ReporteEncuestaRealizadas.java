package encuestas.util;

import java.io.Serializable;

public class ReporteEncuestaRealizadas implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6281002760707914869L;

	private String persona;
	private String identificacion;
	private String email;
	private String celular;
	private String programa;
	private String sede;
	private String fecha;

	public String getPersona() {
		return persona;
	}

	public void setPersona(String persona) {
		this.persona = persona;
	}

	public String getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getPrograma() {
		return programa;
	}

	public void setPrograma(String programa) {
		this.programa = programa;
	}

	public String getSede() {
		return sede;
	}

	public void setSede(String sede) {
		this.sede = sede;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

}
