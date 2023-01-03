/**
 * 
 */
package encuestas.util;

import java.io.Serializable;

/**
 * @author EDUAR
 * 
 */
public class ReporteEncuestaResultados implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3364772091468346405L;

	/**
	 * OID - Campo que guardará la referencia del oid llave principal del
	 * aspecto
	 */
	private Integer oid;
	/**
	 * NOMBRE de la opción
	 */
	private String nombre;
	/**
	 * CANTIDAD - valor del count (conteo de respuestas dada a la opción)
	 * 
	 */

	private Integer cantidad;

	private Integer total;

	public ReporteEncuestaResultados(Integer oid, String nombre,
			Integer cantidad) {
		super();
		this.oid = oid;
		this.nombre = nombre;
		this.cantidad = cantidad;
		/**
		 * si inicia en 1 para poder usar el sumaryRow en resultados de informes
		 * autoevaluación, valor de la propiedas srtyBy
		 */
		this.total = 1;
	}

	public Integer getOid() {
		return oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

}
