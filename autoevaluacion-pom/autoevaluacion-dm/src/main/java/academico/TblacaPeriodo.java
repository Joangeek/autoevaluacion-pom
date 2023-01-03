package academico;

import java.io.Serializable;

import javax.persistence.*;

import encuestas.TblencEvaluacionInstitucion;
import encuestas.TblencProgramacionEncuesta;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the tblaca_periodo database table.
 * 
 */
@Entity
@Table(name="tblaca_periodo",schema="academico")
@NamedQuery(name="TblacaPeriodo.findAll", query="SELECT t FROM TblacaPeriodo t")
public class TblacaPeriodo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBLACA_PERIODO_OID_GENERATOR", sequenceName = "ACADEMICO.PERIODO_OID_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBLACA_PERIODO_OID_GENERATOR")
	private Integer oid;

	private Integer anio;

	@Column(name="apertura_matricula")
	private Integer aperturaMatricula;

	@Column(name="apertura_recibo")
	private Integer aperturaRecibo;

	@Column(name="autocontrol_datos")
	private Integer autocontrolDatos;

	@Column(name="autocontrol_datos_h")
	private Integer autocontrolDatosH;

	private String descripcion;

	private Integer estado;

	@Temporal(TemporalType.DATE)
	private Date fechacreacion;

	private String semestre;

	//bi-directional many-to-one association to TblencEvaluacionInstitucion
	@OneToMany(mappedBy="tblacaPeriodo")
	private List<TblencEvaluacionInstitucion> tblencEvaluacionInstitucions;

	//bi-directional many-to-one association to TblencProgramacionEncuesta
	@OneToMany(mappedBy="tblacaPeriodo")
	private List<TblencProgramacionEncuesta> tblencProgramacionEncuestas;

	public TblacaPeriodo() {
	}

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public Integer getAnio() {
		return this.anio;
	}

	public void setAnio(Integer anio) {
		this.anio = anio;
	}

	public Integer getAperturaMatricula() {
		return this.aperturaMatricula;
	}

	public void setAperturaMatricula(Integer aperturaMatricula) {
		this.aperturaMatricula = aperturaMatricula;
	}

	public Integer getAperturaRecibo() {
		return this.aperturaRecibo;
	}

	public void setAperturaRecibo(Integer aperturaRecibo) {
		this.aperturaRecibo = aperturaRecibo;
	}

	public Integer getAutocontrolDatos() {
		return this.autocontrolDatos;
	}

	public void setAutocontrolDatos(Integer autocontrolDatos) {
		this.autocontrolDatos = autocontrolDatos;
	}

	public Integer getAutocontrolDatosH() {
		return this.autocontrolDatosH;
	}

	public void setAutocontrolDatosH(Integer autocontrolDatosH) {
		this.autocontrolDatosH = autocontrolDatosH;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getEstado() {
		return this.estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public Date getFechacreacion() {
		return this.fechacreacion;
	}

	public void setFechacreacion(Date fechacreacion) {
		this.fechacreacion = fechacreacion;
	}

	public String getSemestre() {
		return this.semestre;
	}

	public void setSemestre(String semestre) {
		this.semestre = semestre;
	}

	public List<TblencEvaluacionInstitucion> getTblencEvaluacionInstitucions() {
		return this.tblencEvaluacionInstitucions;
	}

	public void setTblencEvaluacionInstitucions(List<TblencEvaluacionInstitucion> tblencEvaluacionInstitucions) {
		this.tblencEvaluacionInstitucions = tblencEvaluacionInstitucions;
	}

	public TblencEvaluacionInstitucion addTblencEvaluacionInstitucion(TblencEvaluacionInstitucion tblencEvaluacionInstitucion) {
		getTblencEvaluacionInstitucions().add(tblencEvaluacionInstitucion);
		tblencEvaluacionInstitucion.setTblacaPeriodo(this);

		return tblencEvaluacionInstitucion;
	}

	public TblencEvaluacionInstitucion removeTblencEvaluacionInstitucion(TblencEvaluacionInstitucion tblencEvaluacionInstitucion) {
		getTblencEvaluacionInstitucions().remove(tblencEvaluacionInstitucion);
		tblencEvaluacionInstitucion.setTblacaPeriodo(null);

		return tblencEvaluacionInstitucion;
	}

	public List<TblencProgramacionEncuesta> getTblencProgramacionEncuestas() {
		return this.tblencProgramacionEncuestas;
	}

	public void setTblencProgramacionEncuestas(List<TblencProgramacionEncuesta> tblencProgramacionEncuestas) {
		this.tblencProgramacionEncuestas = tblencProgramacionEncuestas;
	}

	public TblencProgramacionEncuesta addTblencProgramacionEncuesta(TblencProgramacionEncuesta tblencProgramacionEncuesta) {
		getTblencProgramacionEncuestas().add(tblencProgramacionEncuesta);
		tblencProgramacionEncuesta.setTblacaPeriodo(this);

		return tblencProgramacionEncuesta;
	}

	public TblencProgramacionEncuesta removeTblencProgramacionEncuesta(TblencProgramacionEncuesta tblencProgramacionEncuesta) {
		getTblencProgramacionEncuestas().remove(tblencProgramacionEncuesta);
		tblencProgramacionEncuesta.setTblacaPeriodo(null);

		return tblencProgramacionEncuesta;
	}

}