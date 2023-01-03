package docente;

import java.io.Serializable;

import javax.persistence.*;

import academico.TblacaPrograma;
import academico.TblacaSede;

import java.math.BigDecimal;

/**
 * The persistent class for the tbldoc_docente_h database table.
 * 
 */
@Entity
@Table(name = "tbldoc_docente_h", schema="academico")
@NamedQuery(name = "TbldocDocenteH.findAll", query = "SELECT t FROM TbldocDocenteH t")
public class TbldocDocenteH implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "TBLDOC_DOCENTE_H_IDDOCENTEH_GENERATOR")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBLDOC_DOCENTE_H_IDDOCENTEH_GENERATOR")
	@Column(name = "iddocente_h")
	private Integer iddocenteH;

	private Integer annio;

	@Column(name = "cod_uni_org")
	private String codUniOrg;

	@Column(name = "codigo_unico")
	private String codigoUnico;

	private String dedicacion;

	@Column(name = "duracion_en_horas")
	private BigDecimal duracionEnHoras;

	private Integer estado;

	@Column(name = "id_docente")
	private Integer idDocente;

	@Column(name = "ies_code")
	private String iesCode;

	@Column(name = "libros_pub_investigacion")
	private BigDecimal librosPubInvestigacion;

	@Column(name = "libros_pub_texto")
	private BigDecimal librosPubTexto;

	@Column(name = "libros_publ_texto_calificados")
	private BigDecimal librosPublTextoCalificados;

	@Column(name = "patentes_obtenidas_semestre")
	private BigDecimal patentesObtenidasSemestre;

	@Column(name = "porcentaje_administrativa")
	private BigDecimal porcentajeAdministrativa;

	@Column(name = "porcentaje_bienestar")
	private BigDecimal porcentajeBienestar;

	@Column(name = "porcentaje_docencia")
	private BigDecimal porcentajeDocencia;

	@Column(name = "porcentaje_edu_no_formal_ycont")
	private BigDecimal porcentajeEduNoFormalYcont;

	@Column(name = "porcentaje_investigacion")
	private BigDecimal porcentajeInvestigacion;

	@Column(name = "porcentaje_otras_actividades")
	private BigDecimal porcentajeOtrasActividades;

	@Column(name = "porcentaje_proy_no_remun")
	private BigDecimal porcentajeProyNoRemun;

	@Column(name = "porcentaje_proy_progr_remun")
	private BigDecimal porcentajeProyProgrRemun;

	@Column(name = "premios_semestre_internal")
	private BigDecimal premiosSemestreInternal;

	@Column(name = "premios_semestre_nal")
	private BigDecimal premiosSemestreNal;

	@Column(name = "reportes_investigacion")
	private BigDecimal reportesInvestigacion;

	private String semestre;

	@Column(name = "tipo_contrato")
	private String tipoContrato;

	@Column(name = "tipo_doc_unico")
	private String tipoDocUnico;

	// bi-directional many-to-one association to TblacaPrograma
	@ManyToOne
	@JoinColumn(name = "idprograma")
	private TblacaPrograma tblacaPrograma;

	// bi-directional many-to-one association to TblacaSede
	@ManyToOne
	@JoinColumn(name = "idsede")
	private TblacaSede tblacaSede;

	public TbldocDocenteH() {
	}

	public Integer getIddocenteH() {
		return this.iddocenteH;
	}

	public void setIddocenteH(Integer iddocenteH) {
		this.iddocenteH = iddocenteH;
	}

	public Integer getAnnio() {
		return this.annio;
	}

	public void setAnnio(Integer annio) {
		this.annio = annio;
	}

	public String getCodUniOrg() {
		return this.codUniOrg;
	}

	public void setCodUniOrg(String codUniOrg) {
		this.codUniOrg = codUniOrg;
	}

	public String getCodigoUnico() {
		return this.codigoUnico;
	}

	public void setCodigoUnico(String codigoUnico) {
		this.codigoUnico = codigoUnico;
	}

	public String getDedicacion() {
		return this.dedicacion;
	}

	public void setDedicacion(String dedicacion) {
		this.dedicacion = dedicacion;
	}

	public BigDecimal getDuracionEnHoras() {
		return this.duracionEnHoras;
	}

	public void setDuracionEnHoras(BigDecimal duracionEnHoras) {
		this.duracionEnHoras = duracionEnHoras;
	}

	public Integer getEstado() {
		return this.estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public Integer getIdDocente() {
		return this.idDocente;
	}

	public void setIdDocente(Integer idDocente) {
		this.idDocente = idDocente;
	}

	public String getIesCode() {
		return this.iesCode;
	}

	public void setIesCode(String iesCode) {
		this.iesCode = iesCode;
	}

	public BigDecimal getLibrosPubInvestigacion() {
		return this.librosPubInvestigacion;
	}

	public void setLibrosPubInvestigacion(BigDecimal librosPubInvestigacion) {
		this.librosPubInvestigacion = librosPubInvestigacion;
	}

	public BigDecimal getLibrosPubTexto() {
		return this.librosPubTexto;
	}

	public void setLibrosPubTexto(BigDecimal librosPubTexto) {
		this.librosPubTexto = librosPubTexto;
	}

	public BigDecimal getLibrosPublTextoCalificados() {
		return this.librosPublTextoCalificados;
	}

	public void setLibrosPublTextoCalificados(
			BigDecimal librosPublTextoCalificados) {
		this.librosPublTextoCalificados = librosPublTextoCalificados;
	}

	public BigDecimal getPatentesObtenidasSemestre() {
		return this.patentesObtenidasSemestre;
	}

	public void setPatentesObtenidasSemestre(
			BigDecimal patentesObtenidasSemestre) {
		this.patentesObtenidasSemestre = patentesObtenidasSemestre;
	}

	public BigDecimal getPorcentajeAdministrativa() {
		return this.porcentajeAdministrativa;
	}

	public void setPorcentajeAdministrativa(BigDecimal porcentajeAdministrativa) {
		this.porcentajeAdministrativa = porcentajeAdministrativa;
	}

	public BigDecimal getPorcentajeBienestar() {
		return this.porcentajeBienestar;
	}

	public void setPorcentajeBienestar(BigDecimal porcentajeBienestar) {
		this.porcentajeBienestar = porcentajeBienestar;
	}

	public BigDecimal getPorcentajeDocencia() {
		return this.porcentajeDocencia;
	}

	public void setPorcentajeDocencia(BigDecimal porcentajeDocencia) {
		this.porcentajeDocencia = porcentajeDocencia;
	}

	public BigDecimal getPorcentajeEduNoFormalYcont() {
		return this.porcentajeEduNoFormalYcont;
	}

	public void setPorcentajeEduNoFormalYcont(
			BigDecimal porcentajeEduNoFormalYcont) {
		this.porcentajeEduNoFormalYcont = porcentajeEduNoFormalYcont;
	}

	public BigDecimal getPorcentajeInvestigacion() {
		return this.porcentajeInvestigacion;
	}

	public void setPorcentajeInvestigacion(BigDecimal porcentajeInvestigacion) {
		this.porcentajeInvestigacion = porcentajeInvestigacion;
	}

	public BigDecimal getPorcentajeOtrasActividades() {
		return this.porcentajeOtrasActividades;
	}

	public void setPorcentajeOtrasActividades(
			BigDecimal porcentajeOtrasActividades) {
		this.porcentajeOtrasActividades = porcentajeOtrasActividades;
	}

	public BigDecimal getPorcentajeProyNoRemun() {
		return this.porcentajeProyNoRemun;
	}

	public void setPorcentajeProyNoRemun(BigDecimal porcentajeProyNoRemun) {
		this.porcentajeProyNoRemun = porcentajeProyNoRemun;
	}

	public BigDecimal getPorcentajeProyProgrRemun() {
		return this.porcentajeProyProgrRemun;
	}

	public void setPorcentajeProyProgrRemun(BigDecimal porcentajeProyProgrRemun) {
		this.porcentajeProyProgrRemun = porcentajeProyProgrRemun;
	}

	public BigDecimal getPremiosSemestreInternal() {
		return this.premiosSemestreInternal;
	}

	public void setPremiosSemestreInternal(BigDecimal premiosSemestreInternal) {
		this.premiosSemestreInternal = premiosSemestreInternal;
	}

	public BigDecimal getPremiosSemestreNal() {
		return this.premiosSemestreNal;
	}

	public void setPremiosSemestreNal(BigDecimal premiosSemestreNal) {
		this.premiosSemestreNal = premiosSemestreNal;
	}

	public BigDecimal getReportesInvestigacion() {
		return this.reportesInvestigacion;
	}

	public void setReportesInvestigacion(BigDecimal reportesInvestigacion) {
		this.reportesInvestigacion = reportesInvestigacion;
	}

	public String getSemestre() {
		return this.semestre;
	}

	public void setSemestre(String semestre) {
		this.semestre = semestre;
	}

	public String getTipoContrato() {
		return this.tipoContrato;
	}

	public void setTipoContrato(String tipoContrato) {
		this.tipoContrato = tipoContrato;
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

}