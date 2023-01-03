/**
 * 
 */
package comun.controlador.util.core;

/**
 * @author
 * 
 */
public final class UtilidadJNDI {

	/**
	 * 
	 * Retorna el jndi del ejb implementado para una interfaz en particular
	 * 
	 * @param classBO
	 * @return String representación del jndi
	 *         java:global/vpm-commons-app-ear/vpm
	 *         -commons-app-ejb-0.0.1-SNAPSHOT/
	 *         CategoriaBOEJB!com.business.CategoriaBO
	 */
	public static <T> String getJNDAutoevaluacion(Class<T> classBO) {
		final String jndiAutoevaluacion = "java:global/autoevaluacion-ear/autoevaluacion-ejb-0.0.1-SNAPSHOT/";
		// System
		// .getProperty("jboss.vpm.commons.app.jndi");
		final StringBuilder jndi = new StringBuilder();
		String statelessName = classBO.getSimpleName();
		jndi.append(jndiAutoevaluacion).append(statelessName).append("Impl")
				.append("!").append(classBO.getName());
		// System.out.println("---------------------------"+jndi.toString());
		return jndi.toString();
	}

	/*
	 * public static <T> String getJNDAutoevaluacion(Class<T> classBO) { final
	 * String jndiAutoevaluacion =
	 * "java:global/autoevaluacion-ear/autoevaluacion-ejb-0.0.1-SNAPSHOT/"; //
	 * System // .getProperty("jboss.vpm.commons.app.jndi"); final StringBuilder
	 * jndi = new StringBuilder(); String pLetra =
	 * classBO.getSimpleName().substring(0, 1); String statelessName =
	 * classBO.getSimpleName().replaceFirst(pLetra, pLetra.toLowerCase());
	 * jndi.append(jndiAutoevaluacion).append(statelessName).append("!")
	 * .append(classBO.getName());
	 * System.out.println("---------------------------"+jndi.toString()); return
	 * jndi.toString(); }
	 */

	/**
	 * Retorna el jndi del ejb implementado para una interfaz en particular
	 * 
	 * @param classBO
	 * @return String representación del jndi
	 *         java:global/carga-masiva-ear/carga-masiva-ejb-0.0.3-SNAPSHOT/
	 */
	public static <T> String getJNDICargaMasiva(Class<T> classBO) {
		final String jndiCommonsApp = System
				.getProperty("jboss.vpm.carga.masiva.jndi");
		final StringBuilder jndi = new StringBuilder();
		String pLetra = classBO.getSimpleName().substring(0, 1);
		String statelessName = classBO.getSimpleName().replaceFirst(pLetra,
				pLetra.toLowerCase());
		jndi.append(jndiCommonsApp).append(statelessName).append("!")
				.append(classBO.getName());
		return jndi.toString();
	}

	/**
	 * Retorna el jndi del ejb implementado para una interfaz en particular
	 * 
	 * @param classBO
	 * @return String representación del jndi
	 *         java:global/carga-masiva-ear/carga-masiva-ejb-0.0.3-SNAPSHOT/
	 */
	public static <T> String getJNDIAseguradora(Class<T> classBO) {
		final String jndiCommonsApp = System
				.getProperty("jboss.vpm.aseguradora.jndi");
		final StringBuilder jndi = new StringBuilder();
		String pLetra = classBO.getSimpleName().substring(0, 1);
		String statelessName = classBO.getSimpleName().replaceFirst(pLetra,
				pLetra.toLowerCase());
		jndi.append(jndiCommonsApp).append(statelessName).append("!")
				.append(classBO.getName());
		return jndi.toString();
	}

	/**
	 * Retorna el JNDI de un ejb que implementa la interfaz indicada como
	 * parametro
	 * 
	 * @autor gustavo.leyton
	 * @date: 11/09/2014
	 * @param classBO
	 * @return
	 */
	public static <T> String getJNDIGestionReglas(Class<T> classBO) {
		final String jndiGestionReglas = System
				.getProperty("jboss.vpm.gestion.reglas.jndi");
		final StringBuilder jndi = new StringBuilder();
		String pLetra = classBO.getSimpleName().substring(0, 1);
		String statelessName = classBO.getSimpleName().replaceFirst(pLetra,
				pLetra.toLowerCase());
		jndi.append(jndiGestionReglas).append(statelessName).append("!")
				.append(classBO.getName());
		return jndi.toString();
	}

	/**
	 * Retorna el jndi del ejb implementado para una interfaz en particular
	 * 
	 * @param classBO
	 * @return String representación del jndi
	 *         java:global/recepcion-solicitudes-ear
	 *         /recepcion-solicitudes-ejb-1.0-SNAPSHOT/
	 */
	public static <T> String getJNDIRecepciones(Class<T> classBO) {
		final String jndiCommonsApp = System
				.getProperty("jboss.vpm.recepcion.solicitud.jndi");
		final StringBuilder jndi = new StringBuilder();
		String pLetra = "AdminNivelServicioBO".substring(0, 1);
		String statelessName = "adminNivelServicioBO".replaceFirst(pLetra,
				pLetra.toLowerCase());
		jndi.append(jndiCommonsApp).append(statelessName).append("!")
				.append(classBO.getName());
		return jndi.toString();
	}
}
