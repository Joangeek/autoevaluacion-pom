package comun;

import java.io.Serializable;

import javax.persistence.*;

import academico.TblestEstudiante;

import java.util.List;


/**
 * The persistent class for the tbldepartamentos database table.
 * 
 */
@Entity
@Table(name="tbl_departamentos",schema="academico")
@NamedQuery(name="Tbldepartamento.findAll", query="SELECT t FROM Tbldepartamento t")
public class Tbldepartamento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TBLDEPARTAMENTOS_DEPTOCODE_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TBLDEPARTAMENTOS_DEPTOCODE_GENERATOR")
	@Column(name="depto_code")
	private String deptoCode;

	@Column(name="depto_descr")
	private String deptoDescr;

	//bi-directional many-to-one association to TblestEstudiante
	@OneToMany(mappedBy="tbldepartamento")
	private List<TblestEstudiante> tblestEstudiantes;

	public Tbldepartamento() {
	}

	public String getDeptoCode() {
		return this.deptoCode;
	}

	public void setDeptoCode(String deptoCode) {
		this.deptoCode = deptoCode;
	}

	public String getDeptoDescr() {
		return this.deptoDescr;
	}

	public void setDeptoDescr(String deptoDescr) {
		this.deptoDescr = deptoDescr;
	}

	public List<TblestEstudiante> getTblestEstudiantes() {
		return this.tblestEstudiantes;
	}

	public void setTblestEstudiantes(List<TblestEstudiante> tblestEstudiantes) {
		this.tblestEstudiantes = tblestEstudiantes;
	}

	public TblestEstudiante addTblestEstudiante(TblestEstudiante tblestEstudiante) {
		getTblestEstudiantes().add(tblestEstudiante);
		tblestEstudiante.setTbldepartamento(this);

		return tblestEstudiante;
	}

	public TblestEstudiante removeTblestEstudiante(TblestEstudiante tblestEstudiante) {
		getTblestEstudiantes().remove(tblestEstudiante);
		tblestEstudiante.setTbldepartamento(null);

		return tblestEstudiante;
	}

}