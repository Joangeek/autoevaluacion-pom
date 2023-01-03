package autoevaluacion;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tblaut_unidad database table.
 * 
 */
@Entity
@Table(name="tblaut_unidad",schema="academico")
@NamedQuery(name="TblautUnidad.findAll", query="SELECT t FROM TblautUnidad t")
public class TblautUnidad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBLAUT_UNIDAD_OID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBLAUT_UNIDAD_OID_GENERATOR")
	private Integer oid;

	private String abreviatura;

	private String descripcion;

	private String nombre;

	public TblautUnidad() {
	}

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getAbreviatura() {
		return this.abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}