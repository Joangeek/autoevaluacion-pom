package academico;

import java.io.Serializable;

import javax.persistence.*;

import academico.TblacaPrograma;
import academico.TblacaSede;

import java.util.Date;


/**
 * The persistent class for the tblest_estudiante_programa database table.
 * 
 */
@Entity
@Table(name="tblest_estudiante_programa",schema="academico")
@NamedQuery(name="TblestEstudiantePrograma.findAll", query="SELECT t FROM TblestEstudiantePrograma t")
public class TblestEstudiantePrograma implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBLEST_ESTUDIANTE_PROGRAMA_IDEP_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBLEST_ESTUDIANTE_PROGRAMA_IDEP_GENERATOR")
	@Column(name="id_ep")
	private Integer idEp;

	private String acta;

	private Integer anio;

	@Column(name="codigo_unico")
	private String codigoUnico;

	private String departamento;

	@Column(name="ecaes_observaciones")
	private String ecaesObservaciones;

	@Column(name="ecaes_resultados")
	private String ecaesResultados;

	@Column(name="es_transferencia")
	private String esTransferencia;

	private Integer estado;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_grado")
	private Date fechaGrado;

	@Temporal(TemporalType.DATE)
	private Date fechaingreso;

	private String folio;

	@Column(name="grad_annio")
	private Integer gradAnnio;

	@Column(name="grad_semestre")
	private String gradSemestre;

	private Integer idplan;

	@Column(name="ies_code")
	private String iesCode;

	@Column(name="ins_annio")
	private Integer insAnnio;

	@Column(name="ins_semestre")
	private String insSemestre;

	private String municipio;

	@Column(name="pro_consecutivo")
	private Integer proConsecutivo;

	private String semestre;

	private String snp;

	@Column(name="tipo_doc_unico")
	private String tipoDocUnico;

	//bi-directional many-to-one association to TblacaPrograma
	@ManyToOne
	@JoinColumn(name="idprograma")
	private TblacaPrograma tblacaPrograma;

	//bi-directional many-to-one association to TblacaSede
	@ManyToOne
	@JoinColumn(name="idsede")
	private TblacaSede tblacaSede;

	//bi-directional many-to-one association to TblestEstudiante
	@ManyToOne
	@JoinColumn(name="idestudiante")
	private TblestEstudiante tblestEstudiante;

	public TblestEstudiantePrograma() {
	}

	public Integer getIdEp() {
		return this.idEp;
	}

	public void setIdEp(Integer idEp) {
		this.idEp = idEp;
	}

	public String getActa() {
		return this.acta;
	}

	public void setActa(String acta) {
		this.acta = acta;
	}

	public Integer getAnio() {
		return this.anio;
	}

	public void setAnio(Integer anio) {
		this.anio = anio;
	}

	public String getCodigoUnico() {
		return this.codigoUnico;
	}

	public void setCodigoUnico(String codigoUnico) {
		this.codigoUnico = codigoUnico;
	}

	public String getDepartamento() {
		return this.departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getEcaesObservaciones() {
		return this.ecaesObservaciones;
	}

	public void setEcaesObservaciones(String ecaesObservaciones) {
		this.ecaesObservaciones = ecaesObservaciones;
	}

	public String getEcaesResultados() {
		return this.ecaesResultados;
	}

	public void setEcaesResultados(String ecaesResultados) {
		this.ecaesResultados = ecaesResultados;
	}

	public String getEsTransferencia() {
		return this.esTransferencia;
	}

	public void setEsTransferencia(String esTransferencia) {
		this.esTransferencia = esTransferencia;
	}

	public Integer getEstado() {
		return this.estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public Date getFechaGrado() {
		return this.fechaGrado;
	}

	public void setFechaGrado(Date fechaGrado) {
		this.fechaGrado = fechaGrado;
	}

	public Date getFechaingreso() {
		return this.fechaingreso;
	}

	public void setFechaingreso(Date fechaingreso) {
		this.fechaingreso = fechaingreso;
	}

	public String getFolio() {
		return this.folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public Integer getGradAnnio() {
		return this.gradAnnio;
	}

	public void setGradAnnio(Integer gradAnnio) {
		this.gradAnnio = gradAnnio;
	}

	public String getGradSemestre() {
		return this.gradSemestre;
	}

	public void setGradSemestre(String gradSemestre) {
		this.gradSemestre = gradSemestre;
	}

	public Integer getIdplan() {
		return this.idplan;
	}

	public void setIdplan(Integer idplan) {
		this.idplan = idplan;
	}

	public String getIesCode() {
		return this.iesCode;
	}

	public void setIesCode(String iesCode) {
		this.iesCode = iesCode;
	}

	public Integer getInsAnnio() {
		return this.insAnnio;
	}

	public void setInsAnnio(Integer insAnnio) {
		this.insAnnio = insAnnio;
	}

	public String getInsSemestre() {
		return this.insSemestre;
	}

	public void setInsSemestre(String insSemestre) {
		this.insSemestre = insSemestre;
	}

	public String getMunicipio() {
		return this.municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public Integer getProConsecutivo() {
		return this.proConsecutivo;
	}

	public void setProConsecutivo(Integer proConsecutivo) {
		this.proConsecutivo = proConsecutivo;
	}

	public String getSemestre() {
		return this.semestre;
	}

	public void setSemestre(String semestre) {
		this.semestre = semestre;
	}

	public String getSnp() {
		return this.snp;
	}

	public void setSnp(String snp) {
		this.snp = snp;
	}

	public String getTipoDocUnico() {
		return this.tipoDocUnico;
	}

	public void setTipoDocUnico(String tipoDocUnico) {
		this.tipoDocUnico = tipoDocUnico;
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

	public TblestEstudiante getTblestEstudiante() {
		return this.tblestEstudiante;
	}

	public void setTblestEstudiante(TblestEstudiante tblestEstudiante) {
		this.tblestEstudiante = tblestEstudiante;
	}

}