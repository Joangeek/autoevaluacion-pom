package encuestas;

import java.io.Serializable;

import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the tblenc_aspectos database table.
 * 
 */
@Entity
@Table(name = "tblenc_aspectos", schema = "academico")
@NamedQuery(name = "TblencAspecto.findAll", query = "SELECT t FROM TblencAspecto t")
public class TblencAspecto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "TBLENC_ASPECTOS_OID_GENERATOR", sequenceName = "ACADEMICO.TBLENC_ASPECTOS_OID_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBLENC_ASPECTOS_OID_GENERATOR")
	private Integer oid;

	private String codigo;

	private String descripcion;

	private Integer estado;

	@Column(name = "id_ginstitucional")
	private Integer idGinstitucional;

	// bi-directional many-to-one association to TblencAspecto
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idsubaspecto")
	private TblencAspecto padre;

	// bi-directional many-to-one association to TblencAspecto
	@OneToMany(mappedBy = "padre")
	private List<TblencAspecto> subAspectos;

	private Integer orden;

	private double peso;

	private String usuario;

	// bi-directional many-to-one association to TblencAspectoEvaluacion
	@OneToMany(mappedBy = "tblencAspecto")
	private List<TblencAspectoEvaluacion> tblencAspectoEvaluacions;

	// bi-directional many-to-one association to TblencGrupoAspecto
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idgrupo_aspecto")
	private TblencGrupoAspecto tblencGrupoAspecto;

	// bi-directional many-to-one association to TblencTipoAspecto
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idtipo_aspecto")
	private TblencTipoAspecto tblencTipoAspecto;

	// bi-directional many-to-one association to TblencOpcione
	@OneToMany(mappedBy = "tblencAspecto")
	private List<TblencOpcione> tblencOpciones;

	public TblencAspecto() {
	}

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
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

	public Integer getIdGinstitucional() {
		return this.idGinstitucional;
	}

	public void setIdGinstitucional(Integer idGinstitucional) {
		this.idGinstitucional = idGinstitucional;
	}

	public Integer getOrden() {
		return this.orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public double getPeso() {
		return this.peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
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
		tblencAspectoEvaluacion.setTblencAspecto(this);

		return tblencAspectoEvaluacion;
	}

	public TblencAspectoEvaluacion removeTblencAspectoEvaluacion(
			TblencAspectoEvaluacion tblencAspectoEvaluacion) {
		getTblencAspectoEvaluacions().remove(tblencAspectoEvaluacion);
		tblencAspectoEvaluacion.setTblencAspecto(null);

		return tblencAspectoEvaluacion;
	}

	public TblencGrupoAspecto getTblencGrupoAspecto() {
		return this.tblencGrupoAspecto;
	}

	public void setTblencGrupoAspecto(TblencGrupoAspecto tblencGrupoAspecto) {
		this.tblencGrupoAspecto = tblencGrupoAspecto;
	}

	public TblencTipoAspecto getTblencTipoAspecto() {
		return this.tblencTipoAspecto;
	}

	public void setTblencTipoAspecto(TblencTipoAspecto tblencTipoAspecto) {
		this.tblencTipoAspecto = tblencTipoAspecto;
	}

	public List<TblencOpcione> getTblencOpciones() {
		return this.tblencOpciones;
	}

	public void setTblencOpciones(List<TblencOpcione> tblencOpciones) {
		this.tblencOpciones = tblencOpciones;
	}

	public TblencOpcione addTblencOpcione(TblencOpcione tblencOpcione) {
		getTblencOpciones().add(tblencOpcione);
		tblencOpcione.setTblencAspecto(this);

		return tblencOpcione;
	}

	public TblencOpcione removeTblencOpcione(TblencOpcione tblencOpcione) {
		getTblencOpciones().remove(tblencOpcione);
		tblencOpcione.setTblencAspecto(null);

		return tblencOpcione;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public TblencAspecto getPadre() {
		return padre;
	}

	public void setPadre(TblencAspecto padre) {
		this.padre = padre;
	}

	public List<TblencAspecto> getSubAspectos() {
		return this.subAspectos;
	}

	public void setSubAspectos(List<TblencAspecto> subAspectos) {
		this.subAspectos = subAspectos;
	}
}