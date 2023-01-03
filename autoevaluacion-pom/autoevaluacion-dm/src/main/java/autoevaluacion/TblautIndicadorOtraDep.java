package autoevaluacion;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the tblaut_indicador_otra_dep database table.
 * 
 */
@Entity
@Table(name="tblaut_indicador_otra_dep", schema="academico")
@NamedQuery(name="TblautIndicadorOtraDep.findAll", query="SELECT t FROM TblautIndicadorOtraDep t")
public class TblautIndicadorOtraDep implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBLAUT_INDICADOR_OTRA_DEP_OID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBLAUT_INDICADOR_OTRA_DEP_OID_GENERATOR")
	private Integer oid;

	private String usuario;

	private String vigencia;

	//bi-directional many-to-one association to TblautIndicadorFuente
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idindicador_fuente")
	private TblautIndicadorFuente tblautIndicadorFuente;

	//bi-directional many-to-one association to TblautOtrasDependencia
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idotras_dependencias")
	private TblautOtrasDependencia tblautOtrasDependencia;

	public TblautIndicadorOtraDep() {
	}

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getVigencia() {
		return this.vigencia;
	}

	public void setVigencia(String vigencia) {
		this.vigencia = vigencia;
	}

	public TblautIndicadorFuente getTblautIndicadorFuente() {
		return this.tblautIndicadorFuente;
	}

	public void setTblautIndicadorFuente(TblautIndicadorFuente tblautIndicadorFuente) {
		this.tblautIndicadorFuente = tblautIndicadorFuente;
	}

	public TblautOtrasDependencia getTblautOtrasDependencia() {
		return this.tblautOtrasDependencia;
	}

	public void setTblautOtrasDependencia(TblautOtrasDependencia tblautOtrasDependencia) {
		this.tblautOtrasDependencia = tblautOtrasDependencia;
	}

}