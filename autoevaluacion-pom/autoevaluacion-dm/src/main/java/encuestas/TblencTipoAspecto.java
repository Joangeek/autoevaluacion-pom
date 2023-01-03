package encuestas;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the tblenc_tipo_aspectos database table.
 * 
 */
@Entity
@Table(name = "tblenc_tipo_aspectos", schema = "academico")
@NamedQueries({
		@NamedQuery(name = "TblencTipoAspecto.findAll", query = "SELECT t FROM TblencTipoAspecto t ORDER BY  t.oid DESC"),
		@NamedQuery(name = "TblencTipoAspecto.findAllEstado", query = "SELECT t FROM TblencTipoAspecto t WHERE t.estado=:estado ") })
public class TblencTipoAspecto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "TBLENC_TIPO_ASPECTOS_OID_GENERATOR",sequenceName="ACADEMICO.TBLENC_TIPO_ASPECTOS_OID_SEQ", allocationSize =1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBLENC_TIPO_ASPECTOS_OID_GENERATOR")
	private Integer oid;

	private String descripcion;

	private String nombre;

	private Integer estado;
	
	private String usuario;

	// bi-directional many-to-one association to TblencAspecto
	@OneToMany(mappedBy = "tblencTipoAspecto")
	private List<TblencAspecto> tblencAspectos;

	// bi-directional many-to-one association to TblencOpcione
	@OneToMany(mappedBy = "tblencTipoAspecto")
	private List<TblencOpcione> tblencOpciones;

	public TblencTipoAspecto() {
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

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public List<TblencAspecto> getTblencAspectos() {
		return this.tblencAspectos;
	}

	public void setTblencAspectos(List<TblencAspecto> tblencAspectos) {
		this.tblencAspectos = tblencAspectos;
	}

	public TblencAspecto addTblencAspecto(TblencAspecto tblencAspecto) {
		getTblencAspectos().add(tblencAspecto);
		tblencAspecto.setTblencTipoAspecto(this);

		return tblencAspecto;
	}

	public TblencAspecto removeTblencAspecto(TblencAspecto tblencAspecto) {
		getTblencAspectos().remove(tblencAspecto);
		tblencAspecto.setTblencTipoAspecto(null);

		return tblencAspecto;
	}

	public List<TblencOpcione> getTblencOpciones() {
		return this.tblencOpciones;
	}

	public void setTblencOpciones(List<TblencOpcione> tblencOpciones) {
		this.tblencOpciones = tblencOpciones;
	}

	public TblencOpcione addTblencOpcione(TblencOpcione tblencOpcione) {
		getTblencOpciones().add(tblencOpcione);
		tblencOpcione.setTblencTipoAspecto(this);

		return tblencOpcione;
	}

	public TblencOpcione removeTblencOpcione(TblencOpcione tblencOpcione) {
		getTblencOpciones().remove(tblencOpcione);
		tblencOpcione.setTblencTipoAspecto(null);

		return tblencOpcione;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	
}