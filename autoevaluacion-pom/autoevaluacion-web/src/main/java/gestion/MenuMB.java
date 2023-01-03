package gestion;

import gestion.Menu;
import gestion.MenuUsuarioGrupo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

import com.gestion.MenuBO;

import commons.exceptions.AutoevaluacionBOException;
import comun.controlador.util.core.BaseControlador;
import comun.controlador.util.core.LookupUtil;
import util.BOException;


@ManagedBean(name = "menuMB")
@ViewScoped
public class MenuMB<T> extends BaseControlador implements Serializable {

	private static final long serialVersionUID = -8434534413696267700L;

	private DefaultMenuModel model;

	@EJB
	private MenuBO menuBO;

	// VARIABLES
	private String idGrupoUsuario;

	@PostConstruct
	public void init() throws BOException {
		try {
			model = new DefaultMenuModel();
			LOGGER.info("INIT MENU");
			idGrupoUsuario = Util.getUserGrup();
			lookup();
			contruirMenu();

		} catch (Exception ex) {
			lanzarMensajeError(null, ex);
		}

	}

	@Override
	public void inicializar() {

	}

	private void lookup() {
		try {
			menuBO = LookupUtil.lookupRemoteStateless(MenuBO.class);
		} catch (NamingException e) {
			LOGGER.info("NamingException");
		}
	}

	private void contruirMenu() {
		List<Integer> listMenuUsuGrupo = new ArrayList<Integer>();
		try {
			
			LOGGER.info("Grupo Usuario" + idGrupoUsuario);
			List<Menu> listMenuP = menuBO.buscarMenu(idGrupoUsuario);
			LOGGER.info("Menu_P size " + listMenuP.size());

			for (Menu m : listMenuP) {
				DefaultSubMenu firstSubmenu = new DefaultSubMenu(m.getNombre());
				firstSubmenu.setIcon(m.getIcono());
				
				for (MenuUsuarioGrupo i : menuBO.buscarSub(m.getOid(),idGrupoUsuario)) {
					listMenuUsuGrupo.add(i.getOid());
					DefaultMenuItem item = new DefaultMenuItem(i.getMenu()
							.getNombre());
					item.setUrl(i.getMenu().getRuta());
					item.setIcon(i.getMenu().getIcono());
					
				
					firstSubmenu.addElement(item);
				}
				model.addElement(firstSubmenu);
			}

		} catch (AutoevaluacionBOException e) {
			LOGGER.info("contruirMenu--- ", e);
			e.printStackTrace();
		}
		// LOGGER.info("session ---");
		HttpSession session = Util.getSession();
		session.setAttribute("menuUsuarioGrupo", listMenuUsuGrupo);
		/*
		 * for(Integer v : listMenuUsuGrupo){
		 * LOGGER.info("listMenuUsuGrupo ---"+v);
		 * 
		 * }
		 */
		LOGGER.info(session.getAttribute("username"));
	}

	// SETTES AND GETTES

	public MenuModel getModel() {
		return model;
	}

	public void setModel(DefaultMenuModel model) {
		this.model = model;
	}

}
