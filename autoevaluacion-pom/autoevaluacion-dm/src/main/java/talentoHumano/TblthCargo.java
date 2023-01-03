package talentoHumano;

import java.io.Serializable;

import javax.persistence.*;

import autoevaluacion.TblautOtrasDependencia;

import java.util.List;


/**
 * The persistent class for the tblth_cargo database table.
 * 
 */
@Entity
@Table(name="tblth_cargo", schema="academico")
@NamedQuery(name="TblthCargo.findAll", query="SELECT t FROM TblthCargo t")
public class TblthCargo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBLTH_CARGO_OID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBLTH_CARGO_OID_GENERATOR")
	private Integer oid;

	private String cargo;

	private Integer estado;

	private String usuario;

	//bi-directional many-to-one association to TblautOtrasDependencia
	@OneToMany(mappedBy="tblthCargo")
	private List<TblautOtrasDependencia> tblautOtrasDependencias;

	public TblthCargo() {
	}

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getCargo() {
		return this.cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public Integer getEstado() {
		return this.estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public List<TblautOtrasDependencia> getTblautOtrasDependencias() {
		return this.tblautOtrasDependencias;
	}

	public void setTblautOtrasDependencias(List<TblautOtrasDependencia> tblautOtrasDependencias) {
		this.tblautOtrasDependencias = tblautOtrasDependencias;
	}

	public TblautOtrasDependencia addTblautOtrasDependencia(TblautOtrasDependencia tblautOtrasDependencia) {
		getTblautOtrasDependencias().add(tblautOtrasDependencia);
		tblautOtrasDependencia.setTblthCargo(this);

		return tblautOtrasDependencia;
	}

	public TblautOtrasDependencia removeTblautOtrasDependencia(TblautOtrasDependencia tblautOtrasDependencia) {
		getTblautOtrasDependencias().remove(tblautOtrasDependencia);
		tblautOtrasDependencia.setTblthCargo(null);

		return tblautOtrasDependencia;
	}

}