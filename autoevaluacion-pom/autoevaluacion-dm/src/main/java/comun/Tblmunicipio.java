package comun;

import java.io.Serializable;

import javax.persistence.*;

import academico.TblestEstudiante;

import java.util.List;


/**
 * The persistent class for the tblmunicipios database table.
 * 
 */
@Entity
@Table(name="tbl_municipios",schema="academico")
@NamedQuery(name="Tblmunicipio.findAll", query="SELECT t FROM Tblmunicipio t")
public class Tblmunicipio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBLMUNICIPIOS_MUNICCODE_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBLMUNICIPIOS_MUNICCODE_GENERATOR")
	@Column(name="munic_code")
	private String municCode;

	@Column(name="depa_code")
	private String depaCode;

	@Column(name="munic_descr")
	private String municDescr;

	//bi-directional many-to-one association to TblestEstudiante
	@OneToMany(mappedBy="tblmunicipio")
	private List<TblestEstudiante> tblestEstudiantes;

	public Tblmunicipio() {
	}

	public String getMunicCode() {
		return this.municCode;
	}

	public void setMunicCode(String municCode) {
		this.municCode = municCode;
	}

	public String getDepaCode() {
		return this.depaCode;
	}

	public void setDepaCode(String depaCode) {
		this.depaCode = depaCode;
	}

	public String getMunicDescr() {
		return this.municDescr;
	}

	public void setMunicDescr(String municDescr) {
		this.municDescr = municDescr;
	}

	public List<TblestEstudiante> getTblestEstudiantes() {
		return this.tblestEstudiantes;
	}

	public void setTblestEstudiantes(List<TblestEstudiante> tblestEstudiantes) {
		this.tblestEstudiantes = tblestEstudiantes;
	}

	public TblestEstudiante addTblestEstudiante(TblestEstudiante tblestEstudiante) {
		getTblestEstudiantes().add(tblestEstudiante);
		tblestEstudiante.setTblmunicipio(this);

		return tblestEstudiante;
	}

	public TblestEstudiante removeTblestEstudiante(TblestEstudiante tblestEstudiante) {
		getTblestEstudiantes().remove(tblestEstudiante);
		tblestEstudiante.setTblmunicipio(null);

		return tblestEstudiante;
	}

}