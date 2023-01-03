package comun;

import java.io.Serializable;

import javax.persistence.*;

import academico.TblacaPrograma;
import academico.TblacaSede;
import encuestas.TblencEvaluacionInstitucion;

import java.util.List;

/**
 * The persistent class for the tblempleadores database table.
 * 
 */
@Entity
@Table(name = "tbl_empleadores", schema = "academico")
@NamedQuery(name = "Tblempleadore.findAll", query = "SELECT t FROM Tblempleadore t")
public class Tblempleadore implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "TBLEMPLEADORES_OID_GENERATOR")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBLEMPLEADORES_OID_GENERATOR")
	private Integer oid;

	@Column(name = "actividad_econo")
	private String actividadEcono;

	private String correo;

	private String direccion;

	private String nit;

	private String password;

	@Column(name = "nombre_empresa")
	private String nombreEmpresa;

	private String telefono;

	private Integer estado;

	// bi-directional many-to-one association to TblencEvaluacionInstitucion
	@OneToMany(mappedBy = "tblempleadore")
	private List<TblencEvaluacionInstitucion> tblencEvaluacionInstitucions;

	// bi-directional many-to-one association to TblacaPrograma
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idprograma")
	private TblacaPrograma tblacaPrograma;

	// bi-directional many-to-one association to TblacaSede
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idsede")
	private TblacaSede tblacaSede;

	public Tblempleadore() {
	}

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getActividadEcono() {
		return this.actividadEcono;
	}

	public void setActividadEcono(String actividadEcono) {
		this.actividadEcono = actividadEcono;
	}

	public String getCorreo() {
		return this.correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getNombreEmpresa() {
		return this.nombreEmpresa;
	}

	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getNit() {
		return nit;
	}

	public void setNit(String nit) {
		this.nit = nit;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<TblencEvaluacionInstitucion> getTblencEvaluacionInstitucions() {
		return this.tblencEvaluacionInstitucions;
	}

	public void setTblencEvaluacionInstitucions(
			List<TblencEvaluacionInstitucion> tblencEvaluacionInstitucions) {
		this.tblencEvaluacionInstitucions = tblencEvaluacionInstitucions;
	}

	public TblencEvaluacionInstitucion addTblencEvaluacionInstitucion(
			TblencEvaluacionInstitucion tblencEvaluacionInstitucion) {
		getTblencEvaluacionInstitucions().add(tblencEvaluacionInstitucion);
		tblencEvaluacionInstitucion.setTblempleadore(this);

		return tblencEvaluacionInstitucion;
	}

	public TblencEvaluacionInstitucion removeTblencEvaluacionInstitucion(
			TblencEvaluacionInstitucion tblencEvaluacionInstitucion) {
		getTblencEvaluacionInstitucions().remove(tblencEvaluacionInstitucion);
		tblencEvaluacionInstitucion.setTblempleadore(null);

		return tblencEvaluacionInstitucion;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public TblacaPrograma getTblacaPrograma() {
		return tblacaPrograma;
	}

	public void setTblacaPrograma(TblacaPrograma tblacaPrograma) {
		this.tblacaPrograma = tblacaPrograma;
	}

	public TblacaSede getTblacaSede() {
		return tblacaSede;
	}

	public void setTblacaSede(TblacaSede tblacaSede) {
		this.tblacaSede = tblacaSede;
	}

}