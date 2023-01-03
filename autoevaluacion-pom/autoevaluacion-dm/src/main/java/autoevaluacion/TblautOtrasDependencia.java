package autoevaluacion;

import java.io.Serializable;

import javax.persistence.*;

import talentoHumano.TblthCargo;
import talentoHumano.TblthParticipante;

import java.util.List;


/**
 * The persistent class for the tblaut_otras_dependencias database table.
 * 
 */
@Entity
@Table(name="tblaut_otras_dependencias", schema="academico")
@NamedQuery(name="TblautOtrasDependencia.findAllEstado", query="SELECT t FROM TblautOtrasDependencia t WHERE t.estado=:estado")
public class TblautOtrasDependencia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBLAUT_OTRAS_DEPENDENCIAS_OID_GENERATOR", sequenceName="ACADEMICO.TBLAUT_OTRAS_DEPENDENCIAS_OID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBLAUT_OTRAS_DEPENDENCIAS_OID_GENERATOR")
	private Integer oid;

	private Integer estado;

	private String vigencia;
	
	private String usuario;

	//bi-directional many-to-one association to TblautIndicadorOtraDep
	@OneToMany(mappedBy="tblautOtrasDependencia")
	private List<TblautIndicadorOtraDep> tblautIndicadorOtraDeps;

	//bi-directional many-to-one association to TblthCargo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idcargo")
	private TblthCargo tblthCargo;

	//bi-directional many-to-one association to TblthParticipante
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idparticipante")
	private TblthParticipante tblthParticipante;

	public TblautOtrasDependencia() {
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

	public String getVigencia() {
		return this.vigencia;
	}

	public void setVigencia(String vigencia) {
		this.vigencia = vigencia;
	}

	public List<TblautIndicadorOtraDep> getTblautIndicadorOtraDeps() {
		return this.tblautIndicadorOtraDeps;
	}

	public void setTblautIndicadorOtraDeps(List<TblautIndicadorOtraDep> tblautIndicadorOtraDeps) {
		this.tblautIndicadorOtraDeps = tblautIndicadorOtraDeps;
	}

	public TblautIndicadorOtraDep addTblautIndicadorOtraDep(TblautIndicadorOtraDep tblautIndicadorOtraDep) {
		getTblautIndicadorOtraDeps().add(tblautIndicadorOtraDep);
		tblautIndicadorOtraDep.setTblautOtrasDependencia(this);

		return tblautIndicadorOtraDep;
	}

	public TblautIndicadorOtraDep removeTblautIndicadorOtraDep(TblautIndicadorOtraDep tblautIndicadorOtraDep) {
		getTblautIndicadorOtraDeps().remove(tblautIndicadorOtraDep);
		tblautIndicadorOtraDep.setTblautOtrasDependencia(null);

		return tblautIndicadorOtraDep;
	}

	public TblthCargo getTblthCargo() {
		return this.tblthCargo;
	}

	public void setTblthCargo(TblthCargo tblthCargo) {
		this.tblthCargo = tblthCargo;
	}

	public TblthParticipante getTblthParticipante() {
		return this.tblthParticipante;
	}

	public void setTblthParticipante(TblthParticipante tblthParticipante) {
		this.tblthParticipante = tblthParticipante;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

}