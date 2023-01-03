package autoevaluacion;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tblaut_variable database table.
 * 
 */
@Entity
@Table(name="tblaut_variable",schema="academico")
@NamedQuery(name="TblautVariable.findAll", query="SELECT t FROM TblautVariable t")
public class TblautVariable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBLAUT_VARIABLE_OID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBLAUT_VARIABLE_OID_GENERATOR")
	private Integer oid;

	private String codigo;

	private String descripcion;

	private String funcion;

	private Integer idunidad;

	private String nombre;

	@Column(name="tipo_variable")
	private Integer tipoVariable;

	//bi-directional one-to-one association to TblautUnidad
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="oid")
	private TblautUnidad tblautUnidad;

	//bi-directional one-to-one association to TblautVariablesIndicadore
	@OneToOne(mappedBy="tblautVariable", fetch=FetchType.LAZY)
	private TblautVariablesIndicadore tblautVariablesIndicadore;

	public TblautVariable() {
	}

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getFuncion() {
		return this.funcion;
	}

	public void setFuncion(String funcion) {
		this.funcion = funcion;
	}

	public Integer getIdunidad() {
		return this.idunidad;
	}

	public void setIdunidad(Integer idunidad) {
		this.idunidad = idunidad;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getTipoVariable() {
		return this.tipoVariable;
	}

	public void setTipoVariable(Integer tipoVariable) {
		this.tipoVariable = tipoVariable;
	}

	public TblautUnidad getTblautUnidad() {
		return this.tblautUnidad;
	}

	public void setTblautUnidad(TblautUnidad tblautUnidad) {
		this.tblautUnidad = tblautUnidad;
	}

	public TblautVariablesIndicadore getTblautVariablesIndicadore() {
		return this.tblautVariablesIndicadore;
	}

	public void setTblautVariablesIndicadore(TblautVariablesIndicadore tblautVariablesIndicadore) {
		this.tblautVariablesIndicadore = tblautVariablesIndicadore;
	}

}