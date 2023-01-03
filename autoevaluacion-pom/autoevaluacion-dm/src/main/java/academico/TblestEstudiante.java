package academico;

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

import comun.Tbldepartamento;
import comun.TblestadoCivil;
import comun.Tblgenero;
import comun.Tblmunicipio;
import comun.Tblpais;

import talentoHumano.TblthTipoIdentificacion;
import encuestas.TblencEvaluacionInstitucion;


/**
 * The persistent class for the tblest_estudiante database table.
 * 
 */
@Entity
@Table(name="tblest_estudiante",schema="academico")
@NamedQuery(name="TblestEstudiante.findAll", query="SELECT t FROM TblestEstudiante t")
public class TblestEstudiante implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBLEST_ESTUDIANTE_OID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBLEST_ESTUDIANTE_OID_GENERATOR")
	private Integer oid;

	private String ap;

	private String apellido1;

	private String apellido2;

	@Column(name="area_tel")
	private String areaTel;

	private Integer autocontrol;

	@Column(name="bloqueo_encuesta")
	private Integer bloqueoEncuesta;

	@Column(name="bloqueo_financiero")
	private Integer bloqueoFinanciero;

	private String celular;

	private String clave;

	@Column(name="cod_ies")
	private String codIes;

	@Column(name="codigo_id_anterior")
	private String codigoIdAnterior;

	@Column(name="codigo_unico")
	private String codigoUnico;

	private String direccion;

	private String dirtrabajo;

	private String email;

	@Column(name="email_inst")
	private String emailInst;

	private Integer estado;

	private String estrato;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_bloqueofinanciero")
	private Date fechaBloqueofinanciero;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_cambiodocumento")
	private Date fechaCambiodocumento;

	@Temporal(TemporalType.DATE)
	@Column(name="fehca_bloqueoencuesta")
	private Date fehcaBloqueoencuesta;

	private Integer idsede;

	@Temporal(TemporalType.DATE)
	private Date inscripcion;

	@Column(name="lugar_espedicion")
	private String lugarEspedicion;

	@Temporal(TemporalType.DATE)
	private Date nacimiento;

	@Column(name="nombre_")
	private String nombre;

	private String nombre2;

	private String nombres;

	@Column(name="pais_tel")
	private String paisTel;

	private String telefono;

	//bi-directional many-to-one association to TblencEvaluacionInstitucion
	@OneToMany(mappedBy="tblestEstudiante")
	private List<TblencEvaluacionInstitucion> tblencEvaluacionInstitucions;

	//bi-directional many-to-one association to TblencEvaluacionInstitucion
	@OneToMany(mappedBy="egresado")
	private List<TblencEvaluacionInstitucion> tblencEvaluacionInstitucionsEgresados;
	
	//bi-directional one-to-one association to TblacaSede
//	@OneToOne(fetch=FetchType.LAZY)
//	@JoinColumn(name="oid")
//	private TblacaSede tblacaSede;

	//bi-directional many-to-one association to Tbldepartamento
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="departamento_ln")
	private Tbldepartamento tbldepartamento;

	//bi-directional many-to-one association to TblestadoCivil
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idestadocivil")
	private TblestadoCivil tblestadoCivil;

	//bi-directional many-to-one association to Tblgenero
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idsexo")
	private Tblgenero tblgenero;

	//bi-directional many-to-one association to Tblmunicipio
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="municipio_ln")
	private Tblmunicipio tblmunicipio;

	//bi-directional many-to-one association to Tblpais
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="pais_ln")
	private Tblpais tblpais;

	//bi-directional many-to-one association to TblthTipoIdentificacion
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="tipo_id_anterior")
	private TblthTipoIdentificacion tblthTipoIdentificacion1;

	//bi-directional many-to-one association to TblthTipoIdentificacion
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idtipo_docuemnto")
	private TblthTipoIdentificacion tblthTipoIdentificacion2;
	
	//----------------------------------------------------------------------------------------------	
	

		//relacion de tablas:
	
	//bi-directional many-to-one association to TblestEstudiantePrograma
		@OneToMany(mappedBy="tblestEstudiante")
		private List<TblestEstudiantePrograma> tblestEstudianteProgramas;
	

	//----------------------------------------------------------------------------------------------	
		
	public TblestEstudiante() {
	}

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getAp() {
		return this.ap;
	}

	public void setAp(String ap) {
		this.ap = ap;
	}

	public String getApellido1() {
		return this.apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return this.apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public String getAreaTel() {
		return this.areaTel;
	}

	public void setAreaTel(String areaTel) {
		this.areaTel = areaTel;
	}

	public Integer getAutocontrol() {
		return this.autocontrol;
	}

	public void setAutocontrol(Integer autocontrol) {
		this.autocontrol = autocontrol;
	}

	public Integer getBloqueoEncuesta() {
		return this.bloqueoEncuesta;
	}

	public void setBloqueoEncuesta(Integer bloqueoEncuesta) {
		this.bloqueoEncuesta = bloqueoEncuesta;
	}

	public Integer getBloqueoFinanciero() {
		return this.bloqueoFinanciero;
	}

	public void setBloqueoFinanciero(Integer bloqueoFinanciero) {
		this.bloqueoFinanciero = bloqueoFinanciero;
	}

	public String getCelular() {
		return this.celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getClave() {
		return this.clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getCodIes() {
		return this.codIes;
	}

	public void setCodIes(String codIes) {
		this.codIes = codIes;
	}

	public String getCodigoIdAnterior() {
		return this.codigoIdAnterior;
	}

	public void setCodigoIdAnterior(String codigoIdAnterior) {
		this.codigoIdAnterior = codigoIdAnterior;
	}

	public String getCodigoUnico() {
		return this.codigoUnico;
	}

	public void setCodigoUnico(String codigoUnico) {
		this.codigoUnico = codigoUnico;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getDirtrabajo() {
		return this.dirtrabajo;
	}

	public void setDirtrabajo(String dirtrabajo) {
		this.dirtrabajo = dirtrabajo;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmailInst() {
		return this.emailInst;
	}

	public void setEmailInst(String emailInst) {
		this.emailInst = emailInst;
	}

	public Integer getEstado() {
		return this.estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public String getEstrato() {
		return this.estrato;
	}

	public void setEstrato(String estrato) {
		this.estrato = estrato;
	}

	public Date getFechaBloqueofinanciero() {
		return this.fechaBloqueofinanciero;
	}

	public void setFechaBloqueofinanciero(Date fechaBloqueofinanciero) {
		this.fechaBloqueofinanciero = fechaBloqueofinanciero;
	}

	public Date getFechaCambiodocumento() {
		return this.fechaCambiodocumento;
	}

	public void setFechaCambiodocumento(Date fechaCambiodocumento) {
		this.fechaCambiodocumento = fechaCambiodocumento;
	}

	public Date getFehcaBloqueoencuesta() {
		return this.fehcaBloqueoencuesta;
	}

	public void setFehcaBloqueoencuesta(Date fehcaBloqueoencuesta) {
		this.fehcaBloqueoencuesta = fehcaBloqueoencuesta;
	}

	public Integer getIdsede() {
		return this.idsede;
	}

	public void setIdsede(Integer idsede) {
		this.idsede = idsede;
	}

	public Date getInscripcion() {
		return this.inscripcion;
	}

	public void setInscripcion(Date inscripcion) {
		this.inscripcion = inscripcion;
	}

	public String getLugarEspedicion() {
		return this.lugarEspedicion;
	}

	public void setLugarEspedicion(String lugarEspedicion) {
		this.lugarEspedicion = lugarEspedicion;
	}

	public Date getNacimiento() {
		return this.nacimiento;
	}

	public void setNacimiento(Date nacimiento) {
		this.nacimiento = nacimiento;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre2() {
		return this.nombre2;
	}

	public void setNombre2(String nombre2) {
		this.nombre2 = nombre2;
	}

	public String getNombres() {
		return this.nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getPaisTel() {
		return this.paisTel;
	}

	public void setPaisTel(String paisTel) {
		this.paisTel = paisTel;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public List<TblencEvaluacionInstitucion> getTblencEvaluacionInstitucions() {
		return this.tblencEvaluacionInstitucions;
	}

	public void setTblencEvaluacionInstitucions(List<TblencEvaluacionInstitucion> tblencEvaluacionInstitucions) {
		this.tblencEvaluacionInstitucions = tblencEvaluacionInstitucions;
	}

	public TblencEvaluacionInstitucion addTblencEvaluacionInstitucion(TblencEvaluacionInstitucion tblencEvaluacionInstitucion) {
		getTblencEvaluacionInstitucions().add(tblencEvaluacionInstitucion);
		tblencEvaluacionInstitucion.setTblestEstudiante(this);

		return tblencEvaluacionInstitucion;
	}

	public TblencEvaluacionInstitucion removeTblencEvaluacionInstitucion(TblencEvaluacionInstitucion tblencEvaluacionInstitucion) {
		getTblencEvaluacionInstitucions().remove(tblencEvaluacionInstitucion);
		tblencEvaluacionInstitucion.setTblestEstudiante(null);
		

		return tblencEvaluacionInstitucion;
		
	}

//	public TblacaSede getTblacaSede() {
//		return this.tblacaSede;
//	}
//
//	public void setTblacaSede(TblacaSede tblacaSede) {
//		this.tblacaSede = tblacaSede;
//	}

	public Tbldepartamento getTbldepartamento() {
		return this.tbldepartamento;
	}

	public void setTbldepartamento(Tbldepartamento tbldepartamento) {
		this.tbldepartamento = tbldepartamento;
	}

	public TblestadoCivil getTblestadoCivil() {
		return this.tblestadoCivil;
	}

	public void setTblestadoCivil(TblestadoCivil tblestadoCivil) {
		this.tblestadoCivil = tblestadoCivil;
	}

	public Tblgenero getTblgenero() {
		return this.tblgenero;
	}

	public void setTblgenero(Tblgenero tblgenero) {
		this.tblgenero = tblgenero;
	}

	public Tblmunicipio getTblmunicipio() {
		return this.tblmunicipio;
	}

	public void setTblmunicipio(Tblmunicipio tblmunicipio) {
		this.tblmunicipio = tblmunicipio;
	}

	public Tblpais getTblpais() {
		return this.tblpais;
	}

	public void setTblpais(Tblpais tblpais) {
		this.tblpais = tblpais;
	}

	public TblthTipoIdentificacion getTblthTipoIdentificacion1() {
		return this.tblthTipoIdentificacion1;
	}

	public void setTblthTipoIdentificacion1(TblthTipoIdentificacion tblthTipoIdentificacion1) {
		this.tblthTipoIdentificacion1 = tblthTipoIdentificacion1;
	}

	public TblthTipoIdentificacion getTblthTipoIdentificacion2() {
		return this.tblthTipoIdentificacion2;
	}

	public void setTblthTipoIdentificacion2(TblthTipoIdentificacion tblthTipoIdentificacion2) {
		this.tblthTipoIdentificacion2 = tblthTipoIdentificacion2;
	}
	
	
	//---------------------RELACION DE TABLAS:
	
	
	public List<TblestEstudiantePrograma> getTblestEstudianteProgramas() {
		return this.tblestEstudianteProgramas;
	}

	public void setTblestEstudianteProgramas(List<TblestEstudiantePrograma> tblestEstudianteProgramas) {
		this.tblestEstudianteProgramas = tblestEstudianteProgramas;
	}

	public TblestEstudiantePrograma addTblestEstudiantePrograma(TblestEstudiantePrograma tblestEstudiantePrograma) {
		getTblestEstudianteProgramas().add(tblestEstudiantePrograma);
		tblestEstudiantePrograma.setTblestEstudiante(this);

		return tblestEstudiantePrograma;
	}

	public TblestEstudiantePrograma removeTblestEstudiantePrograma(TblestEstudiantePrograma tblestEstudiantePrograma) {
		getTblestEstudianteProgramas().remove(tblestEstudiantePrograma);
		tblestEstudiantePrograma.setTblestEstudiante(null);

		return tblestEstudiantePrograma;
	
	}
	

}