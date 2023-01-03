package encuestas;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tblenc_grupo_aspecto database table.
 * 
 */
@Entity
@Table(name="tblenc_grupo_aspecto",schema="academico")
@NamedQuery(name="TblencGrupoAspecto.findAll", query="SELECT t FROM TblencGrupoAspecto t")
public class TblencGrupoAspecto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBLENC_GRUPO_ASPECTO_OID_GENERATOR", sequenceName="ACADEMICO.TBLENC_GRUPOASPECTO_OID_SEQ",allocationSize=1 )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBLENC_GRUPO_ASPECTO_OID_GENERATOR")
	private Integer oid;

	private String mensaje;

	private String nombre;

	private Integer orden;
	
	private String usuario;

	//bi-directional many-to-one association to TblencAspecto
	@OneToMany(mappedBy="tblencGrupoAspecto")
	private List<TblencAspecto> tblencAspectos;

	//bi-directional many-to-one association to TblencTipoEvaluacion
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idtipo_evaluacion")
	private TblencTipoEvaluacion tblencTipoEvaluacion;

	public TblencGrupoAspecto() {
	}

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getMensaje() {
		return this.mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getOrden() {
		return this.orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public List<TblencAspecto> getTblencAspectos() {
		return this.tblencAspectos;
	}

	public void setTblencAspectos(List<TblencAspecto> tblencAspectos) {
		this.tblencAspectos = tblencAspectos;
	}

	public TblencAspecto addTblencAspecto(TblencAspecto tblencAspecto) {
		getTblencAspectos().add(tblencAspecto);
		tblencAspecto.setTblencGrupoAspecto(this);

		return tblencAspecto;
	}

	public TblencAspecto removeTblencAspecto(TblencAspecto tblencAspecto) {
		getTblencAspectos().remove(tblencAspecto);
		tblencAspecto.setTblencGrupoAspecto(null);

		return tblencAspecto;
	}

	public TblencTipoEvaluacion getTblencTipoEvaluacion() {
		return this.tblencTipoEvaluacion;
	}

	public void setTblencTipoEvaluacion(TblencTipoEvaluacion tblencTipoEvaluacion) {
		this.tblencTipoEvaluacion = tblencTipoEvaluacion;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	
}