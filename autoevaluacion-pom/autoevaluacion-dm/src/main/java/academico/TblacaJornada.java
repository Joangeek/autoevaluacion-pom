package academico;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the tblaca_jornada database table.
 * 
 */
@Entity
@Table(name = "tblaca_jornada",schema="academico")
@NamedQuery(name = "TblacaJornada.findAll", query = "SELECT t FROM TblacaJornada t")
public class TblacaJornada implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "TBLACA_JORNADA_OID_GENERATOR", sequenceName = "ACADEMICO.TBLACA_JORNADA_IDJORNADA_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBLACA_JORNADA_OID_GENERATOR")
	private Integer oid;

	private String descripcion;

	// bi-directional many-to-one association to TblacaMatriculado
	@OneToMany(mappedBy = "tblacaJornada")
	private List<TblacaMatriculado> tblacaMatriculados;

	public TblacaJornada() {
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

	public List<TblacaMatriculado> getTblacaMatriculados() {
		return this.tblacaMatriculados;
	}

	public void setTblacaMatriculados(List<TblacaMatriculado> tblacaMatriculados) {
		this.tblacaMatriculados = tblacaMatriculados;
	}

}