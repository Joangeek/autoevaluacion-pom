package encuestas;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tblenc_opciones database table.
 * 
 */
@Entity
@Table(name="tblenc_opciones",schema="academico")
@NamedQuery(name="TblencOpcione.findAll", query="SELECT t FROM TblencOpcione t")
public class TblencOpcione implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBLENC_OPCIONES_OID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBLENC_OPCIONES_OID_GENERATOR")
	private Integer oid;

	private Integer estado;

	private String nombre;

	private Integer orden;

	private Integer puntos;

	//bi-directional many-to-one association to TblencAspectoEvaluacion
	@OneToMany(mappedBy="tblencOpcione")
	private List<TblencAspectoEvaluacion> tblencAspectoEvaluacions;

	//bi-directional many-to-one association to TblencAspecto
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idaspecto")
	private TblencAspecto tblencAspecto;

	//bi-directional many-to-one association to TblencTipoAspecto
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idtipo_aspecto")
	private TblencTipoAspecto tblencTipoAspecto;

	public TblencOpcione() {
	}

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
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

	public Integer getOrden() {
		return this.orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public Integer getPuntos() {
		return this.puntos;
	}

	public void setPuntos(Integer puntos) {
		this.puntos = puntos;
	}

	public List<TblencAspectoEvaluacion> getTblencAspectoEvaluacions() {
		return this.tblencAspectoEvaluacions;
	}

	public void setTblencAspectoEvaluacions(List<TblencAspectoEvaluacion> tblencAspectoEvaluacions) {
		this.tblencAspectoEvaluacions = tblencAspectoEvaluacions;
	}

	public TblencAspectoEvaluacion addTblencAspectoEvaluacion(TblencAspectoEvaluacion tblencAspectoEvaluacion) {
		getTblencAspectoEvaluacions().add(tblencAspectoEvaluacion);
		tblencAspectoEvaluacion.setTblencOpcione(this);

		return tblencAspectoEvaluacion;
	}

	public TblencAspectoEvaluacion removeTblencAspectoEvaluacion(TblencAspectoEvaluacion tblencAspectoEvaluacion) {
		getTblencAspectoEvaluacions().remove(tblencAspectoEvaluacion);
		tblencAspectoEvaluacion.setTblencOpcione(null);

		return tblencAspectoEvaluacion;
	}

	public TblencAspecto getTblencAspecto() {
		return this.tblencAspecto;
	}

	public void setTblencAspecto(TblencAspecto tblencAspecto) {
		this.tblencAspecto = tblencAspecto;
	}

	public TblencTipoAspecto getTblencTipoAspecto() {
		return this.tblencTipoAspecto;
	}

	public void setTblencTipoAspecto(TblencTipoAspecto tblencTipoAspecto) {
		this.tblencTipoAspecto = tblencTipoAspecto;
	}

}