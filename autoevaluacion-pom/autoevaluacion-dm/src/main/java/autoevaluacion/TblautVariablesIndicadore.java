package autoevaluacion;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tblaut_variables_indicadores database table.
 * 
 */
@Entity
@Table(name="tblaut_variables_indicadores",schema="academico")
@NamedQuery(name="TblautVariablesIndicadore.findAll", query="SELECT t FROM TblautVariablesIndicadore t")
public class TblautVariablesIndicadore implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBLAUT_VARIABLES_INDICADORES_OID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBLAUT_VARIABLES_INDICADORES_OID_GENERATOR")
	private Integer oid;

	private Integer idindicador;

	private Integer idvariable;

	/*//bi-directional one-to-one association to TblautIndicadoresCaracteristica
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="oid")
	private TblautIndicadoresCaracteristica tblautIndicadoresCaracteristica;*/

	//bi-directional one-to-one association to TblautVariable
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="oid")
	private TblautVariable tblautVariable;

	public TblautVariablesIndicadore() {
	}

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public Integer getIdindicador() {
		return this.idindicador;
	}

	public void setIdindicador(Integer idindicador) {
		this.idindicador = idindicador;
	}

	public Integer getIdvariable() {
		return this.idvariable;
	}

	public void setIdvariable(Integer idvariable) {
		this.idvariable = idvariable;
	}

	/*public TblautIndicadoresCaracteristica getTblautIndicadoresCaracteristica() {
		return this.tblautIndicadoresCaracteristica;
	}

	public void setTblautIndicadoresCaracteristica(TblautIndicadoresCaracteristica tblautIndicadoresCaracteristica) {
		this.tblautIndicadoresCaracteristica = tblautIndicadoresCaracteristica;
	}*/

	public TblautVariable getTblautVariable() {
		return this.tblautVariable;
	}

	public void setTblautVariable(TblautVariable tblautVariable) {
		this.tblautVariable = tblautVariable;
	}

}