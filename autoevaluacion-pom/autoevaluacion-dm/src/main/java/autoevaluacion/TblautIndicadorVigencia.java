package autoevaluacion;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.*;

/**
 * The persistent class for the tblaut_indicador_vigencia database table.
 * 
 */
@Entity
@Table(name = "tblaut_indicador_vigencia", schema = "academico")
@NamedQuery(name = "TblautIndicadorVigencia.findAll", query = "SELECT t FROM TblautIndicadorVigencia t")
public class TblautIndicadorVigencia implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TblautIndicadorVigenciaPK id;
	
	private BigDecimal ponderacion;

	// bi-directional many-to-one association to TblautVigencia
	@ManyToOne
	@JoinColumn(name = "vigencia" , insertable=false, updatable=false)
	private TblautVigencia tblautVigencia;

	// bi-directional many-to-one association to TblautIndicadoresCaracteristica
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "oid", insertable = false, updatable = false)
	private TblautIndicadoresCaracteristica tblautIndicadoresCaracteristica;
	
	@Transient
	private Integer sorty;

	@Transient
	private List<Integer> fuentes;

	public TblautIndicadorVigencia() {
		sorty = 1;
	}

	public TblautIndicadorVigenciaPK getId() {
		return this.id;
	}

	public void setId(TblautIndicadorVigenciaPK id) {
		this.id = id;
	}

	public TblautVigencia getTblautVigencia() {
		return this.tblautVigencia;
	}

	public void setTblautVigencia(TblautVigencia tblautVigencia) {
		this.tblautVigencia = tblautVigencia;
	}

	public TblautIndicadoresCaracteristica getTblautIndicadoresCaracteristica() {
		return this.tblautIndicadoresCaracteristica;
	}

	public void setTblautIndicadoresCaracteristica(
			TblautIndicadoresCaracteristica tblautIndicadoresCaracteristica) {
		this.tblautIndicadoresCaracteristica = tblautIndicadoresCaracteristica;
	}

	public Integer getSorty() {
		return sorty;
	}

	public void setSorty(Integer sorty) {
		this.sorty = sorty;
	}

	public List<Integer> getFuentes() {
		return fuentes;
	}

	public void setFuentes(List<Integer> fuentes) {
		this.fuentes = fuentes;
	}

	public BigDecimal getPonderacion() {
		return ponderacion;
	}

	public void setPonderacion(BigDecimal ponderacion) {
		this.ponderacion = ponderacion;
	}

	
}