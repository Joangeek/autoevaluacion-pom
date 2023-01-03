/**
 * 
 */
package encuestas.util;

import java.io.Serializable;
import java.util.List;

/**
 * @author EDUAR TROYANO
 * 
 */
public class ReporteEncuestaResultadosFinal implements Serializable {

	public ReporteEncuestaResultadosFinal() {
		super();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 3364772091468346405L;

	private Integer total;

	private List<ReporteEncuestaResultados> listaresultados;

	public ReporteEncuestaResultadosFinal(Integer total,
			List<ReporteEncuestaResultados> listaresultados) {
		super();
		this.total = total;
		this.listaresultados = listaresultados;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<ReporteEncuestaResultados> getListaresultados() {
		return listaresultados;
	}

	public void setListaresultados(
			List<ReporteEncuestaResultados> listaresultados) {
		this.listaresultados = listaresultados;
	}
}
