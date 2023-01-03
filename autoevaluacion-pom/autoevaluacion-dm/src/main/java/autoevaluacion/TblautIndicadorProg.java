package autoevaluacion;

import java.io.Serializable;

import javax.persistence.*;

import talentoHumano.TblthParticipante;
import academico.TblacaSedePrograma;


/**
 * The persistent class for the tblaut_indicador_prog database table.
 * 
 */
@Entity
@Table(name="tblaut_indicador_prog", schema="academico")
@NamedQuery(name="TblautIndicadorProg.findAll", query="SELECT t FROM TblautIndicadorProg t")
public class TblautIndicadorProg implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBLAUT_INDICADOR_PROG_OID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBLAUT_INDICADOR_PROG_OID_GENERATOR")
	private Integer oid;

	//bi-directional many-to-one association to TblautIndicadorFuente
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idsede_programa")
	private TblacaSedePrograma sedePrograma;

	private String usuario;

	private String vigencia;

	//bi-directional many-to-one association to TblautIndicadorFuente
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idindicador_fuente")
	private TblautIndicadorFuente tblautIndicadorFuente;

	//bi-directional many-to-one association to TblthParticipante
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idparticipante")
	private TblthParticipante tblthParticipante;

	public TblautIndicadorProg() {
	}

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}


	public TblacaSedePrograma getSedePrograma() {
		return sedePrograma;
	}

	public void setSedePrograma(TblacaSedePrograma sedePrograma) {
		this.sedePrograma = sedePrograma;
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

	public TblthParticipante getTblthParticipante() {
		return this.tblthParticipante;
	}

	public void setTblthParticipante(TblthParticipante tblthParticipante) {
		this.tblthParticipante = tblthParticipante;
	}

}