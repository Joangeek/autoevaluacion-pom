package encuestas;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
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

import academico.TblacaPeriodo;

/**
 * The persistent class for the tblenc_programacion_encuestas database table.
 * 
 */
@Entity
@Table(name = "tblenc_programacion_encuestas", schema = "academico")
@NamedQuery(name = "TblencProgramacionEncuesta.findAll", query = "SELECT t FROM TblencProgramacionEncuesta t")
public class TblencProgramacionEncuesta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "TBLENC_PROGRAMACION_ENCUESTAS_OID_GENERATOR", sequenceName = "ACADEMICO.TBLENC_PROGRAMACION_ENCUESTAS_OID_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBLENC_PROGRAMACION_ENCUESTAS_OID_GENERATOR")
	private Integer oid;

	private Integer concierre;

	private Integer estado;

	private String usuario;

	@Temporal(TemporalType.DATE)
	private Date desde;

	@Temporal(TemporalType.DATE)
	private Date hasta;

	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_creacion")
	private Date fechaCreacion;

	// bi-directional many-to-one association to TblacaPeriodo
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idperiodo")
	private TblacaPeriodo tblacaPeriodo;

	// bi-directional many-to-one association to TblencTipoEvaluacion
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idtipo_evaluacion")
	private TblencTipoEvaluacion tblencTipoEvaluacion;

	// bi-directional many-to-one association to TblencProgramacionEncSedeProg
	@OneToMany(mappedBy = "tblencProgramacionEncuesta")
	private List<TblencProgramacionEncSedeProg> tblencProgramacionEncSedeProgs;

	public TblencProgramacionEncuesta() {
		fechaCreacion = new Date();
		concierre = 0;
	}

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public Integer getConcierre() {
		return this.concierre;
	}

	public void setConcierre(Integer concierre) {
		this.concierre = concierre;
	}

	public Integer getEstado() {
		return this.estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public TblacaPeriodo getTblacaPeriodo() {
		return this.tblacaPeriodo;
	}

	public void setTblacaPeriodo(TblacaPeriodo tblacaPeriodo) {
		this.tblacaPeriodo = tblacaPeriodo;
	}

	public TblencTipoEvaluacion getTblencTipoEvaluacion() {
		return this.tblencTipoEvaluacion;
	}

	public void setTblencTipoEvaluacion(
			TblencTipoEvaluacion tblencTipoEvaluacion) {
		this.tblencTipoEvaluacion = tblencTipoEvaluacion;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public List<TblencProgramacionEncSedeProg> getTblencProgramacionEncSedeProgs() {
		return this.tblencProgramacionEncSedeProgs;
	}

	public void setTblencProgramacionEncSedeProgs(
			List<TblencProgramacionEncSedeProg> tblencProgramacionEncSedeProgs) {
		this.tblencProgramacionEncSedeProgs = tblencProgramacionEncSedeProgs;
	}

	public TblencProgramacionEncSedeProg addTblencProgramacionEncSedeProg(
			TblencProgramacionEncSedeProg tblencProgramacionEncSedeProg) {
		getTblencProgramacionEncSedeProgs().add(tblencProgramacionEncSedeProg);
		tblencProgramacionEncSedeProg.setTblencProgramacionEncuesta(this);

		return tblencProgramacionEncSedeProg;
	}

	public TblencProgramacionEncSedeProg removeTblencProgramacionEncSedeProg(
			TblencProgramacionEncSedeProg tblencProgramacionEncSedeProg) {
		getTblencProgramacionEncSedeProgs().remove(
				tblencProgramacionEncSedeProg);
		tblencProgramacionEncSedeProg.setTblencProgramacionEncuesta(null);

		return tblencProgramacionEncSedeProg;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getDesde() {
		return this.desde;
	}

	public void setDesde(Date desde) {
		this.desde = desde;
	}

	public Date getHasta() {
		return this.hasta;
	}

	public void setHasta(Date hasta) {
		this.hasta = hasta;
	}

}