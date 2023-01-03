package autoevaluacion;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;

/**
 * The persistent class for the tblaut_indicadores_caracteristicas database
 * table.
 * 
 */
@Entity
@Table(name = "tblaut_indicadores_caracteristicas", schema = "academico")
@NamedQuery(name = "TblautIndicadoresCaracteristica.findAll", query = "SELECT t FROM TblautIndicadoresCaracteristica t")
public class TblautIndicadoresCaracteristica implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "TBLAUT_INDICADORES_CARACTERISTICAS_OID_GENERATOR", sequenceName = "ACADEMICO.TBLAUT_INDICADORES_CARACTERISTICAS_OID_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBLAUT_INDICADORES_CARACTERISTICAS_OID_GENERATOR")
	private Integer oid;

	// private Integer idcaracteristica;

	private String indicador;

	private String resumen;

	private Integer orden;

	private Integer estado;

	private String usuario;

	// bi-directional many-to-one association to TblautIndicadorFuente
	@OneToMany(mappedBy = "tblautIndicadoresCaracteristica")
	private List<TblautIndicadorFuente> tblautIndicadorFuentes;

	// bi-directional many-to-one association to TblautIndicadorVigencia
	@OneToMany(mappedBy = "tblautIndicadoresCaracteristica")
	private List<TblautIndicadorVigencia> tblautIndicadorVigencias;

	// bi-directional many-to-one association to TblacaPrograma
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idcaracteristica")
	private TblautCaracteristica tblautCaracteristica;

	public TblautIndicadoresCaracteristica() {
	}

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getIndicador() {
		return this.indicador;
	}

	public void setIndicador(String indicador) {
		this.indicador = indicador;
	}

	public String getResumen() {
		return this.resumen;
	}

	public void setResumen(String resumen) {
		this.resumen = resumen;
	}

	public List<TblautIndicadorFuente> getTblautIndicadorFuentes() {
		return this.tblautIndicadorFuentes;
	}

	public void setTblautIndicadorFuentes(
			List<TblautIndicadorFuente> tblautIndicadorFuentes) {
		this.tblautIndicadorFuentes = tblautIndicadorFuentes;
	}

	public TblautIndicadorFuente addTblautIndicadorFuente(
			TblautIndicadorFuente tblautIndicadorFuente) {
		getTblautIndicadorFuentes().add(tblautIndicadorFuente);
		tblautIndicadorFuente.setTblautIndicadoresCaracteristica(this);

		return tblautIndicadorFuente;
	}

	public TblautIndicadorFuente removeTblautIndicadorFuente(
			TblautIndicadorFuente tblautIndicadorFuente) {
		getTblautIndicadorFuentes().remove(tblautIndicadorFuente);
		tblautIndicadorFuente.setTblautIndicadoresCaracteristica(null);

		return tblautIndicadorFuente;
	}

	public List<TblautIndicadorVigencia> getTblautIndicadorVigencias() {
		return this.tblautIndicadorVigencias;
	}

	public void setTblautIndicadorVigencias(
			List<TblautIndicadorVigencia> tblautIndicadorVigencias) {
		this.tblautIndicadorVigencias = tblautIndicadorVigencias;
	}

	public TblautIndicadorVigencia addTblautIndicadorVigencia(
			TblautIndicadorVigencia tblautIndicadorVigencia) {
		getTblautIndicadorVigencias().add(tblautIndicadorVigencia);
		tblautIndicadorVigencia.setTblautIndicadoresCaracteristica(this);

		return tblautIndicadorVigencia;
	}

	public TblautIndicadorVigencia removeTblautIndicadorVigencia(
			TblautIndicadorVigencia tblautIndicadorVigencia) {
		getTblautIndicadorVigencias().remove(tblautIndicadorVigencia);
		tblautIndicadorVigencia.setTblautIndicadoresCaracteristica(null);

		return tblautIndicadorVigencia;
	}

	public TblautCaracteristica getTblautCaracteristica() {
		return this.tblautCaracteristica;
	}

	public void setTblautCaracteristica(
			TblautCaracteristica tblautCaracteristica) {
		this.tblautCaracteristica = tblautCaracteristica;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

}