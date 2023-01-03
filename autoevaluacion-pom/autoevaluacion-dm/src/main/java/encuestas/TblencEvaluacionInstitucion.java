package encuestas;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import talentoHumano.TblthParticipante;
import academico.TblacaPeriodo;
import academico.TblacaPrograma;
import academico.TblacaSede;
import academico.TblestEstudiante;
import comun.Tblempleadore;

/**
 * The persistent class for the tblenc_evaluacion_institucion database table.
 * 
 */
@Entity
@Table(name = "tblenc_evaluacion_institucion", schema = "academico")
@NamedQuery(name = "TblencEvaluacionInstitucion.findAll", query = "SELECT t FROM TblencEvaluacionInstitucion t")
public class TblencEvaluacionInstitucion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "TBLENC_EVALUACION_INSTITUCION_OID_GENERATOR", sequenceName = "ACADEMICO.TBLENC_EVALUACION_INSTITUCION_OID_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBLENC_EVALUACION_INSTITUCION_OID_GENERATOR")
	private Integer oid;

	private Integer estado;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;

	// private Integer idegresado;

	private Integer vigencia;
	
	private String usuario;

	// bi-directional many-to-one association to TblencAspectoEvaluacion
	@OneToMany(mappedBy = "tblencEvaluacionInstitucion")
	private List<TblencAspectoEvaluacion> tblencAspectoEvaluacions;

	// bi-directional many-to-one association to TblacaPeriodo
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idsede")
	private TblacaSede tblacaSede;

	// bi-directional many-to-one association to TblacaPeriodo
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idprograma")
	private TblacaPrograma tblacaPrograma;

	// bi-directional many-to-one association to TblacaPeriodo
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idperiodo")
	private TblacaPeriodo tblacaPeriodo;

	// bi-directional many-to-one association to Tblempleadore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idempleador")
	private Tblempleadore tblempleadore;

	// bi-directional many-to-one association to TblencTipoEvaluacion
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idtipo_evaluacion")
	private TblencTipoEvaluacion tblencTipoEvaluacion;

	// bi-directional many-to-one association to TblestEstudiante
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idegresado")
	private TblestEstudiante egresado;

	// bi-directional many-to-one association to TblestEstudiante
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idestudiante")
	private TblestEstudiante tblestEstudiante;

	// bi-directional many-to-one association to TblthParticipante
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idparticipante")
	private TblthParticipante tblthParticipante;

	public TblencEvaluacionInstitucion() {
		setEstado(1);
	}

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public Integer getEstado() {
		return this.estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public Integer getVigencia() {
		return this.vigencia;
	}

	public void setVigencia(Integer vigencia) {
		this.vigencia = vigencia;
	}

	public List<TblencAspectoEvaluacion> getTblencAspectoEvaluacions() {
		return this.tblencAspectoEvaluacions;
	}

	public void setTblencAspectoEvaluacions(
			List<TblencAspectoEvaluacion> tblencAspectoEvaluacions) {
		this.tblencAspectoEvaluacions = tblencAspectoEvaluacions;
	}

	public TblencAspectoEvaluacion addTblencAspectoEvaluacion(
			TblencAspectoEvaluacion tblencAspectoEvaluacion) {
		getTblencAspectoEvaluacions().add(tblencAspectoEvaluacion);
		tblencAspectoEvaluacion.setTblencEvaluacionInstitucion(this);

		return tblencAspectoEvaluacion;
	}

	public TblencAspectoEvaluacion removeTblencAspectoEvaluacion(
			TblencAspectoEvaluacion tblencAspectoEvaluacion) {
		getTblencAspectoEvaluacions().remove(tblencAspectoEvaluacion);
		tblencAspectoEvaluacion.setTblencEvaluacionInstitucion(null);

		return tblencAspectoEvaluacion;
	}

	public TblacaPeriodo getTblacaPeriodo() {
		return this.tblacaPeriodo;
	}

	public void setTblacaPeriodo(TblacaPeriodo tblacaPeriodo) {
		this.tblacaPeriodo = tblacaPeriodo;
	}

	public Tblempleadore getTblempleadore() {
		return this.tblempleadore;
	}

	public void setTblempleadore(Tblempleadore tblempleadore) {
		this.tblempleadore = tblempleadore;
	}

	public TblencTipoEvaluacion getTblencTipoEvaluacion() {
		return this.tblencTipoEvaluacion;
	}

	public void setTblencTipoEvaluacion(
			TblencTipoEvaluacion tblencTipoEvaluacion) {
		this.tblencTipoEvaluacion = tblencTipoEvaluacion;
	}

	public TblestEstudiante getTblestEstudiante() {
		return this.tblestEstudiante;
	}

	public void setTblestEstudiante(TblestEstudiante tblestEstudiante) {
		this.tblestEstudiante = tblestEstudiante;
	}

	public TblthParticipante getTblthParticipante() {
		return this.tblthParticipante;
	}

	public void setTblthParticipante(TblthParticipante tblthParticipante) {
		this.tblthParticipante = tblthParticipante;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public TblacaSede getTblacaSede() {
		return tblacaSede;
	}

	public void setTblacaSede(TblacaSede tblacaSede) {
		this.tblacaSede = tblacaSede;
	}

	public TblacaPrograma getTblacaPrograma() {
		return tblacaPrograma;
	}

	public void setTblacaPrograma(TblacaPrograma tblacaPrograma) {
		this.tblacaPrograma = tblacaPrograma;
	}

	public TblestEstudiante getEgresado() {
		return egresado;
	}

	public void setEgresado(TblestEstudiante egresado) {
		this.egresado = egresado;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

}