package academico;

import java.io.Serializable;
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

import comun.Tblempleadore;
import comun.Tblnbc;
import docente.TbldocDocenteH;


/**
 * The persistent class for the tblaca_programa database table.
 * 
 */
@Entity
@Table(name="tblaca_programa",schema="academico")
@NamedQuery(name="TblacaPrograma.findAll", query="SELECT t FROM TblacaPrograma t")
public class TblacaPrograma implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBLACA_PROGRAMA_OID_GENERATOR",sequenceName = "ACADEMICO.TBLACA_PROGRAMA_OID_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBLACA_PROGRAMA_OID_GENERATOR")
	private Integer oid;

	@Column(name="acto_admitivo")
	private String actoAdmitivo;

	@Column(name="ciclo_propedeut")
	private String cicloPropedeut;

	private Integer competencias;

	private String duracion;

	private String idadmision;

	private String idclavepro;

	private String idmodalidad;

	@Column(name="nivel_code")
	private String nivelCode;

	private String nombre;

	@Column(name="num_creditos")
	private Integer numCreditos;

	@Column(name="num_periodos")
	private Integer numPeriodos;

	private String objetivos;

	private String pensum;

	@Column(name="perfil_programa")
	private String perfilPrograma;

	private String requisitos;

	private String resolucionmen;

	@Column(name="tipo_acreditacion")
	private String tipoAcreditacion;

	private String titulootorga;

	private String url;

	//bi-directional many-to-one association to TblacaAreasConocimiento
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="nbc_prim_area")
	private TblacaAreasConocimiento tblacaAreasConocimiento;

	//bi-directional many-to-one association to TblacaMetodologia
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="metodologia_code")
	private TblacaMetodologia tblacaMetodologia;

	//bi-directional many-to-one association to TblacaPeriodicidad
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="periodicidad_code")
	private TblacaPeriodicidad tblacaPeriodicidad;

	//bi-directional many-to-one association to Tblnbc
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="nbc_prim_code")
	private Tblnbc tblnbc1;

	//bi-directional many-to-one association to Tblnbc
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="nbc_prim_especific")
	private Tblnbc tblnbc2;

	//bi-directional many-to-one association to TblacaSedePrograma
	@OneToMany(mappedBy="tblacaPrograma")
	private List<TblacaSedePrograma> tblacaSedeProgramas;

	// bi-directional many-to-one association to TblacaSedePrograma
	@OneToMany(mappedBy = "tblacaPrograma")
	private List<Tblempleadore> tblempleadore;
	
	//bi-directional many-to-one association to TbldocDocenteH
	@OneToMany(mappedBy="tblacaPrograma")
	private List<TbldocDocenteH> tbldocDocenteHs;
	
	public TblacaPrograma() {
	}

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getActoAdmitivo() {
		return this.actoAdmitivo;
	}

	public void setActoAdmitivo(String actoAdmitivo) {
		this.actoAdmitivo = actoAdmitivo;
	}

	public String getCicloPropedeut() {
		return this.cicloPropedeut;
	}

	public void setCicloPropedeut(String cicloPropedeut) {
		this.cicloPropedeut = cicloPropedeut;
	}

	public Integer getCompetencias() {
		return this.competencias;
	}

	public void setCompetencias(Integer competencias) {
		this.competencias = competencias;
	}

	public String getDuracion() {
		return this.duracion;
	}

	public void setDuracion(String duracion) {
		this.duracion = duracion;
	}

	public String getIdadmision() {
		return this.idadmision;
	}

	public void setIdadmision(String idadmision) {
		this.idadmision = idadmision;
	}

	public String getIdclavepro() {
		return this.idclavepro;
	}

	public void setIdclavepro(String idclavepro) {
		this.idclavepro = idclavepro;
	}

	public String getIdmodalidad() {
		return this.idmodalidad;
	}

	public void setIdmodalidad(String idmodalidad) {
		this.idmodalidad = idmodalidad;
	}

	public String getNivelCode() {
		return this.nivelCode;
	}

	public void setNivelCode(String nivelCode) {
		this.nivelCode = nivelCode;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getNumCreditos() {
		return this.numCreditos;
	}

	public void setNumCreditos(Integer numCreditos) {
		this.numCreditos = numCreditos;
	}

	public Integer getNumPeriodos() {
		return this.numPeriodos;
	}

	public void setNumPeriodos(Integer numPeriodos) {
		this.numPeriodos = numPeriodos;
	}

	public String getObjetivos() {
		return this.objetivos;
	}

	public void setObjetivos(String objetivos) {
		this.objetivos = objetivos;
	}

	public String getPensum() {
		return this.pensum;
	}

	public void setPensum(String pensum) {
		this.pensum = pensum;
	}

	public String getPerfilPrograma() {
		return this.perfilPrograma;
	}

	public void setPerfilPrograma(String perfilPrograma) {
		this.perfilPrograma = perfilPrograma;
	}

	public String getRequisitos() {
		return this.requisitos;
	}

	public void setRequisitos(String requisitos) {
		this.requisitos = requisitos;
	}

	public String getResolucionmen() {
		return this.resolucionmen;
	}

	public void setResolucionmen(String resolucionmen) {
		this.resolucionmen = resolucionmen;
	}

	public String getTipoAcreditacion() {
		return this.tipoAcreditacion;
	}

	public void setTipoAcreditacion(String tipoAcreditacion) {
		this.tipoAcreditacion = tipoAcreditacion;
	}

	public String getTitulootorga() {
		return this.titulootorga;
	}

	public void setTitulootorga(String titulootorga) {
		this.titulootorga = titulootorga;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public TblacaAreasConocimiento getTblacaAreasConocimiento() {
		return this.tblacaAreasConocimiento;
	}

	public void setTblacaAreasConocimiento(TblacaAreasConocimiento tblacaAreasConocimiento) {
		this.tblacaAreasConocimiento = tblacaAreasConocimiento;
	}

	public TblacaMetodologia getTblacaMetodologia() {
		return this.tblacaMetodologia;
	}

	public void setTblacaMetodologia(TblacaMetodologia tblacaMetodologia) {
		this.tblacaMetodologia = tblacaMetodologia;
	}

	public TblacaPeriodicidad getTblacaPeriodicidad() {
		return this.tblacaPeriodicidad;
	}

	public void setTblacaPeriodicidad(TblacaPeriodicidad tblacaPeriodicidad) {
		this.tblacaPeriodicidad = tblacaPeriodicidad;
	}

	public Tblnbc getTblnbc1() {
		return this.tblnbc1;
	}

	public void setTblnbc1(Tblnbc tblnbc1) {
		this.tblnbc1 = tblnbc1;
	}

	public Tblnbc getTblnbc2() {
		return this.tblnbc2;
	}

	public void setTblnbc2(Tblnbc tblnbc2) {
		this.tblnbc2 = tblnbc2;
	}

	public List<TblacaSedePrograma> getTblacaSedeProgramas() {
		return this.tblacaSedeProgramas;
	}

	public void setTblacaSedeProgramas(List<TblacaSedePrograma> tblacaSedeProgramas) {
		this.tblacaSedeProgramas = tblacaSedeProgramas;
	}

	public TblacaSedePrograma addTblacaSedePrograma(TblacaSedePrograma tblacaSedePrograma) {
		getTblacaSedeProgramas().add(tblacaSedePrograma);
		tblacaSedePrograma.setTblacaPrograma(this);

		return tblacaSedePrograma;
	}

	public TblacaSedePrograma removeTblacaSedePrograma(TblacaSedePrograma tblacaSedePrograma) {
		getTblacaSedeProgramas().remove(tblacaSedePrograma);
		tblacaSedePrograma.setTblacaPrograma(null);

		return tblacaSedePrograma;
	}

	public List<TbldocDocenteH> getTbldocDocenteHs() {
		return tbldocDocenteHs;
	}

	public void setTbldocDocenteHs(List<TbldocDocenteH> tbldocDocenteHs) {
		this.tbldocDocenteHs = tbldocDocenteHs;
	}


}