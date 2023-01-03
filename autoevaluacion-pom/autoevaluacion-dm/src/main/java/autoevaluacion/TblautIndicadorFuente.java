package autoevaluacion;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the tblaut_indicador_fuente database table.
 * 
 */
@Entity
@Table(name="tblaut_indicador_fuente", schema="academico")
@NamedQuery(name="TblautIndicadorFuente.findAll", query="SELECT t FROM TblautIndicadorFuente t")
public class TblautIndicadorFuente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBLAUT_INDICADOR_FUENTE_OID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBLAUT_INDICADOR_FUENTE_OID_GENERATOR")
	private Integer oid;

	private String usuario;

	//private Integer vigencia;
	//bi-directional many-to-one association to TblautFuente
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="vigencia")
	private TblautVigencia tblautVigencia;

	//bi-directional many-to-one association to TblautFuente
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idfuente")
	private TblautFuente tblautFuente;

	//bi-directional many-to-one association to TblautIndicadoresCaracteristica
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idindicador")
	private TblautIndicadoresCaracteristica tblautIndicadoresCaracteristica;

	//bi-directional many-to-one association to TblautMecanismo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idmecanismo")
	private TblautMecanismo tblautMecanismo;

	//bi-directional many-to-one association to TblautIndicadorOtraDep
	@OneToMany(mappedBy="tblautIndicadorFuente")
	private List<TblautIndicadorOtraDep> tblautIndicadorOtraDeps;

	//bi-directional many-to-one association to TblautIndicadorProg
	@OneToMany(mappedBy="tblautIndicadorFuente")
	private List<TblautIndicadorProg> tblautIndicadorProgs;

	public TblautIndicadorFuente() {
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

	public TblautVigencia getTblautVigencia() {
		return tblautVigencia;
	}

	public void setTblautVigencia(TblautVigencia tblautVigencia) {
		this.tblautVigencia = tblautVigencia;
	}

	public TblautFuente getTblautFuente() {
		return this.tblautFuente;
	}

	public void setTblautFuente(TblautFuente tblautFuente) {
		this.tblautFuente = tblautFuente;
	}

	public TblautIndicadoresCaracteristica getTblautIndicadoresCaracteristica() {
		return this.tblautIndicadoresCaracteristica;
	}

	public void setTblautIndicadoresCaracteristica(TblautIndicadoresCaracteristica tblautIndicadoresCaracteristica) {
		this.tblautIndicadoresCaracteristica = tblautIndicadoresCaracteristica;
	}

	public TblautMecanismo getTblautMecanismo() {
		return this.tblautMecanismo;
	}

	public void setTblautMecanismo(TblautMecanismo tblautMecanismo) {
		this.tblautMecanismo = tblautMecanismo;
	}

	public List<TblautIndicadorOtraDep> getTblautIndicadorOtraDeps() {
		return this.tblautIndicadorOtraDeps;
	}

	public void setTblautIndicadorOtraDeps(List<TblautIndicadorOtraDep> tblautIndicadorOtraDeps) {
		this.tblautIndicadorOtraDeps = tblautIndicadorOtraDeps;
	}

	public TblautIndicadorOtraDep addTblautIndicadorOtraDep(TblautIndicadorOtraDep tblautIndicadorOtraDep) {
		getTblautIndicadorOtraDeps().add(tblautIndicadorOtraDep);
		tblautIndicadorOtraDep.setTblautIndicadorFuente(this);

		return tblautIndicadorOtraDep;
	}

	public TblautIndicadorOtraDep removeTblautIndicadorOtraDep(TblautIndicadorOtraDep tblautIndicadorOtraDep) {
		getTblautIndicadorOtraDeps().remove(tblautIndicadorOtraDep);
		tblautIndicadorOtraDep.setTblautIndicadorFuente(null);

		return tblautIndicadorOtraDep;
	}

	public List<TblautIndicadorProg> getTblautIndicadorProgs() {
		return this.tblautIndicadorProgs;
	}

	public void setTblautIndicadorProgs(List<TblautIndicadorProg> tblautIndicadorProgs) {
		this.tblautIndicadorProgs = tblautIndicadorProgs;
	}

	public TblautIndicadorProg addTblautIndicadorProg(TblautIndicadorProg tblautIndicadorProg) {
		getTblautIndicadorProgs().add(tblautIndicadorProg);
		tblautIndicadorProg.setTblautIndicadorFuente(this);

		return tblautIndicadorProg;
	}

	public TblautIndicadorProg removeTblautIndicadorProg(TblautIndicadorProg tblautIndicadorProg) {
		getTblautIndicadorProgs().remove(tblautIndicadorProg);
		tblautIndicadorProg.setTblautIndicadorFuente(null);

		return tblautIndicadorProg;
	}

}