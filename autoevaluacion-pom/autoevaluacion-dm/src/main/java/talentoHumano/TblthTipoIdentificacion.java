package talentoHumano;

import java.io.Serializable;

import javax.persistence.*;

import academico.TblestEstudiante;

import java.util.List;


/**
 * The persistent class for the tblth_tipo_identificacion database table.
 * 
 */
@Entity
@Table(name="tblth_tipo_identificacion",schema="academico")
@NamedQuery(name="TblthTipoIdentificacion.findAll", query="SELECT t FROM TblthTipoIdentificacion t")
public class TblthTipoIdentificacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBLTH_TIPO_IDENTIFICACION_TIIDCODE_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBLTH_TIPO_IDENTIFICACION_TIIDCODE_GENERATOR")
	@Column(name="tiid_code")
	private String tiidCode;

	@Column(name="tiid_descripcion")
	private String tiidDescripcion;

	//bi-directional many-to-one association to TblestEstudiante
	@OneToMany(mappedBy="tblthTipoIdentificacion1")
	private List<TblestEstudiante> tblestEstudiantes1;

	//bi-directional many-to-one association to TblestEstudiante
	@OneToMany(mappedBy="tblthTipoIdentificacion2")
	private List<TblestEstudiante> tblestEstudiantes2;

	//bi-directional many-to-one association to TblthParticipante
	@OneToMany(mappedBy="tblthTipoIdentificacion")
	private List<TblthParticipante> tblthParticipantes;

	public TblthTipoIdentificacion() {
	}

	public String getTiidCode() {
		return this.tiidCode;
	}

	public void setTiidCode(String tiidCode) {
		this.tiidCode = tiidCode;
	}

	public String getTiidDescripcion() {
		return this.tiidDescripcion;
	}

	public void setTiidDescripcion(String tiidDescripcion) {
		this.tiidDescripcion = tiidDescripcion;
	}

	public List<TblestEstudiante> getTblestEstudiantes1() {
		return this.tblestEstudiantes1;
	}

	public void setTblestEstudiantes1(List<TblestEstudiante> tblestEstudiantes1) {
		this.tblestEstudiantes1 = tblestEstudiantes1;
	}

	public TblestEstudiante addTblestEstudiantes1(TblestEstudiante tblestEstudiantes1) {
		getTblestEstudiantes1().add(tblestEstudiantes1);
		tblestEstudiantes1.setTblthTipoIdentificacion1(this);

		return tblestEstudiantes1;
	}

	public TblestEstudiante removeTblestEstudiantes1(TblestEstudiante tblestEstudiantes1) {
		getTblestEstudiantes1().remove(tblestEstudiantes1);
		tblestEstudiantes1.setTblthTipoIdentificacion1(null);

		return tblestEstudiantes1;
	}

	public List<TblestEstudiante> getTblestEstudiantes2() {
		return this.tblestEstudiantes2;
	}

	public void setTblestEstudiantes2(List<TblestEstudiante> tblestEstudiantes2) {
		this.tblestEstudiantes2 = tblestEstudiantes2;
	}

	public TblestEstudiante addTblestEstudiantes2(TblestEstudiante tblestEstudiantes2) {
		getTblestEstudiantes2().add(tblestEstudiantes2);
		tblestEstudiantes2.setTblthTipoIdentificacion2(this);

		return tblestEstudiantes2;
	}

	public TblestEstudiante removeTblestEstudiantes2(TblestEstudiante tblestEstudiantes2) {
		getTblestEstudiantes2().remove(tblestEstudiantes2);
		tblestEstudiantes2.setTblthTipoIdentificacion2(null);

		return tblestEstudiantes2;
	}

	public List<TblthParticipante> getTblthParticipantes() {
		return this.tblthParticipantes;
	}

	public void setTblthParticipantes(List<TblthParticipante> tblthParticipantes) {
		this.tblthParticipantes = tblthParticipantes;
	}

	public TblthParticipante addTblthParticipante(TblthParticipante tblthParticipante) {
		getTblthParticipantes().add(tblthParticipante);
		tblthParticipante.setTblthTipoIdentificacion(this);

		return tblthParticipante;
	}

	public TblthParticipante removeTblthParticipante(TblthParticipante tblthParticipante) {
		getTblthParticipantes().remove(tblthParticipante);
		tblthParticipante.setTblthTipoIdentificacion(null);

		return tblthParticipante;
	}

}