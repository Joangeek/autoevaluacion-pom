package encuestas.util;

import java.io.Serializable;

import encuestas.TblencAspecto;
import encuestas.TblencOpcione;

public class ResultadoEncuesta implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7592706065896989319L;

	private TblencAspecto aspecto;
	private TblencOpcione respuesta;

	public TblencAspecto getAspecto() {
		return aspecto;
	}

	public void setAspecto(TblencAspecto aspecto) {
		this.aspecto = aspecto;
	}

	public TblencOpcione getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(TblencOpcione respuesta) {
		this.respuesta = respuesta;
	}
}
