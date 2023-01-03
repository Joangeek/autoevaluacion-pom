package encuestas;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the tblenc_tipo_evaluacion database table.
 * 
 */
@Entity
@Table(name = "tblenc_tipo_evaluacion", schema = "academico")
@NamedQueries({
		@NamedQuery(name = "TblencTipoEvaluacion.findAll", query = "SELECT t FROM TblencTipoEvaluacion t ORDER BY  t.oid DESC"),
		@NamedQuery(name = "TblencTipoEvaluacion.findAllEstado", query = "SELECT t FROM TblencTipoEvaluacion t WHERE t.estado=:estado "),
		@NamedQuery(name = "TblencTipoEvaluacion.findById", query = "SELECT t FROM TblencTipoEvaluacion t WHERE t.oid=:oid ") })
public class TblencTipoEvaluacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "TBLENC_TIPO_EVALUACION_OID_GENERATOR", sequenceName = "ACADEMICO.TBLENC_TIPO_EVALUACION_OID_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBLENC_TIPO_EVALUACION_OID_GENERATOR")
	private Integer oid;

	private String descripcion;

	private Integer estado;

	private String nombre;

	private String usuario;

	// bi-directional many-to-one association to TblencEvaluacionInstitucion
	@OneToMany(mappedBy = "tblencTipoEvaluacion")
	private List<TblencEvaluacionInstitucion> tblencEvaluacionInstitucions;

	// bi-directional many-to-one association to TblencGrupoAspecto
	@OneToMany(mappedBy = "tblencTipoEvaluacion")
	private List<TblencGrupoAspecto> tblencGrupoAspectos;

	// bi-directional many-to-one association to TblencProgramacionEncuesta
	@OneToMany(mappedBy = "tblencTipoEvaluacion")
	private List<TblencProgramacionEncuesta> tblencProgramacionEncuestas;

	// bi-directional one-to-one association to TblencDirigidoa
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "iddirigidoa")
	private TblencDirigidoa tblenc_dirigidoa;

	// bi-directional one-to-one association to TblencModuloTipoEvaluacion
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idmodulo")
	private TblencModuloTipoEvaluacion tblenc_modulo_tipo_evaluacion;


	public TblencTipoEvaluacion() {
	}

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
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

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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
		tblencEvaluacionInstitucion.setTblencTipoEvaluacion(this);

		return tblencEvaluacionInstitucion;
	}

	public TblencEvaluacionInstitucion removeTblencEvaluacionInstitucion(
			TblencEvaluacionInstitucion tblencEvaluacionInstitucion) {
		getTblencEvaluacionInstitucions().remove(tblencEvaluacionInstitucion);
		tblencEvaluacionInstitucion.setTblencTipoEvaluacion(null);

		return tblencEvaluacionInstitucion;
	}

	public List<TblencGrupoAspecto> getTblencGrupoAspectos() {
		return this.tblencGrupoAspectos;
	}

	public void setTblencGrupoAspectos(
			List<TblencGrupoAspecto> tblencGrupoAspectos) {
		this.tblencGrupoAspectos = tblencGrupoAspectos;
	}

	public TblencGrupoAspecto addTblencGrupoAspecto(
			TblencGrupoAspecto tblencGrupoAspecto) {
		getTblencGrupoAspectos().add(tblencGrupoAspecto);
		tblencGrupoAspecto.setTblencTipoEvaluacion(this);

		return tblencGrupoAspecto;
	}

	public TblencGrupoAspecto removeTblencGrupoAspecto(
			TblencGrupoAspecto tblencGrupoAspecto) {
		getTblencGrupoAspectos().remove(tblencGrupoAspecto);
		tblencGrupoAspecto.setTblencTipoEvaluacion(null);

		return tblencGrupoAspecto;
	}

	public List<TblencProgramacionEncuesta> getTblencProgramacionEncuestas() {
		return this.tblencProgramacionEncuestas;
	}

	public void setTblencProgramacionEncuestas(
			List<TblencProgramacionEncuesta> tblencProgramacionEncuestas) {
		this.tblencProgramacionEncuestas = tblencProgramacionEncuestas;
	}

	public TblencProgramacionEncuesta addTblencProgramacionEncuesta(
			TblencProgramacionEncuesta tblencProgramacionEncuesta) {
		getTblencProgramacionEncuestas().add(tblencProgramacionEncuesta);
		tblencProgramacionEncuesta.setTblencTipoEvaluacion(this);

		return tblencProgramacionEncuesta;
	}

	public TblencProgramacionEncuesta removeTblencProgramacionEncuesta(
			TblencProgramacionEncuesta tblencProgramacionEncuesta) {
		getTblencProgramacionEncuestas().remove(tblencProgramacionEncuesta);
		tblencProgramacionEncuesta.setTblencTipoEvaluacion(null);

		return tblencProgramacionEncuesta;
	}

	public TblencDirigidoa getTblenc_dirigidoa() {
		return tblenc_dirigidoa;
	}

	public void setTblenc_dirigidoa(TblencDirigidoa tblenc_dirigidoa) {
		this.tblenc_dirigidoa = tblenc_dirigidoa;
	}

	public TblencModuloTipoEvaluacion getTblenc_modulo_tipo_evaluacion() {
		return tblenc_modulo_tipo_evaluacion;
	}

	public void setTblenc_modulo_tipo_evaluacion(
			TblencModuloTipoEvaluacion tblenc_modulo_tipo_evaluacion) {
		this.tblenc_modulo_tipo_evaluacion = tblenc_modulo_tipo_evaluacion;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

}