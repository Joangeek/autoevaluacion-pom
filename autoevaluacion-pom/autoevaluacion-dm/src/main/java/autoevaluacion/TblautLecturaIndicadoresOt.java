package autoevaluacion;

import java.io.Serializable;

import javax.persistence.*;

import talentoHumano.TblthParticipante;
import academico.TblacaPrograma;
import academico.TblacaSede;

import java.util.Date;

/**
 * The persistent class for the tblaut_lectura_indicadores_ot database table.
 * 
 */
@Entity
@Table(name = "tblaut_lectura_indicadores_ot", schema = "academico")
@NamedQuery(name = "TblautLecturaIndicadoresOt.findAll", query = "SELECT t FROM TblautLecturaIndicadoresOt t")
public class TblautLecturaIndicadoresOt implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "TBLAUT_LECTURA_INDICADORES_OT_OID_GENERATOR", sequenceName = "'ACADEMICO.TBLAUT_LECTURA_INDICADORES_OT_OID_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBLAUT_LECTURA_INDICADORES_OT_OID_GENERATOR")
	private Integer oid;

	private Integer aprobado;

	@Column(name = "fecha_creacion")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaCreacion;

	@Column(name = "fecha_lectura")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaLectura;

	// bi-directional many-to-one association to TblautIndicadorFuente
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idcaracteristica")
	private TblautCaracteristica tblautCaracteristica;

	// bi-directional many-to-one association to TblautIndicadorFuente
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idfactor")
	private TblautFactore tblautFactore;

	// bi-directional many-to-one association to TblautIndicadorFuente
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idfuente")
	private TblautFuente tblautFuente;

	// bi-directional many-to-one association to TblautIndicadorFuente
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idindicador")
	private TblautIndicadoresCaracteristica TblautIndicadoresCaracteristica;

	// bi-directional many-to-one association to TblautIndicadorFuente
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idmecanismo")
	private TblautMecanismo tblautMecanismo;

	// bi-directional many-to-one association to TblautIndicadorFuente
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idparticipante")
	private TblthParticipante tblthParticipante;

	// bi-directional many-to-one association to TblacaPrograma
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idprograma")
	private TblacaPrograma tblacaPrograma;

	// bi-directional many-to-one association to TblacaSede
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idsede")
	private TblacaSede tblacaSede;

	// bi-directional many-to-one association to TblautIndicadorFuente
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "iddependencia")
	private TblautOtrasDependencia tblautOtrasDependencia;

	private Integer lectura;

	private String usuario;

	// private Integer vigencia;
	// bi-directional many-to-one association to TblacaPrograma
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vigencia")
	private TblautVigencia tblautVigencia;

	public TblautLecturaIndicadoresOt() {
	}

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public Integer getAprobado() {
		return this.aprobado;
	}

	public void setAprobado(Integer aprobado) {
		this.aprobado = aprobado;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaLectura() {
		return fechaLectura;
	}

	public void setFechaLectura(Date fechaLectura) {
		this.fechaLectura = fechaLectura;
	}

	public TblautOtrasDependencia getTblautOtrasDependencia() {
		return tblautOtrasDependencia;
	}

	public void setTblautOtrasDependencia(
			TblautOtrasDependencia tblautOtrasDependencia) {
		this.tblautOtrasDependencia = tblautOtrasDependencia;
	}

	public TblacaPrograma getTblacaPrograma() {
		return this.tblacaPrograma;
	}

	public void setTblacaPrograma(TblacaPrograma tblacaPrograma) {
		this.tblacaPrograma = tblacaPrograma;
	}

	public TblacaSede getTblacaSede() {
		return this.tblacaSede;
	}

	public void setTblacaSede(TblacaSede tblacaSede) {
		this.tblacaSede = tblacaSede;
	}

	public Integer getLectura() {
		return this.lectura;
	}

	public void setLectura(Integer lectura) {
		this.lectura = lectura;
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

	public TblautCaracteristica getTblautCaracteristica() {
		return tblautCaracteristica;
	}

	public void setTblautCaracteristica(
			TblautCaracteristica tblautCaracteristica) {
		this.tblautCaracteristica = tblautCaracteristica;
	}

	public TblautFactore getTblautFactore() {
		return tblautFactore;
	}

	public void setTblautFactore(TblautFactore tblautFactore) {
		this.tblautFactore = tblautFactore;
	}

	public TblautFuente getTblautFuente() {
		return tblautFuente;
	}

	public void setTblautFuente(TblautFuente tblautFuente) {
		this.tblautFuente = tblautFuente;
	}

	public TblautIndicadoresCaracteristica getTblautIndicadoresCaracteristica() {
		return TblautIndicadoresCaracteristica;
	}

	public void setTblautIndicadoresCaracteristica(
			TblautIndicadoresCaracteristica tblautIndicadoresCaracteristica) {
		TblautIndicadoresCaracteristica = tblautIndicadoresCaracteristica;
	}

	public TblautMecanismo getTblautMecanismo() {
		return tblautMecanismo;
	}

	public void setTblautMecanismo(TblautMecanismo tblautMecanismo) {
		this.tblautMecanismo = tblautMecanismo;
	}

	public TblthParticipante getTblthParticipante() {
		return tblthParticipante;
	}

	public void setTblthParticipante(TblthParticipante tblthParticipante) {
		this.tblthParticipante = tblthParticipante;
	}

}