package so.view.partita.pane.util.menu;

import java.util.ArrayList;
import java.util.Hashtable;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MenuBar;

public class SDMenuBar {
	private MenuBar menuBar;
	private Hashtable<Menu, ArrayList<MenuItem>> items;
	
	public SDMenuBar() {
		menuBar=new MenuBar();
		items=new Hashtable<Menu, ArrayList<MenuItem>>();
	}
	
	/**
	 * aggiunge i componenti della menu bar
	 * @param menuName
	 * @param menuItems
	 */
	public void addItems(String menuName, String... menuItems) {
		Menu menu=new Menu(menuName);
		MenuItem mi;
		ArrayList<MenuItem> mIs=new ArrayList<MenuItem>();
		for(String s:menuItems) {
			mi=new MenuItem(s);
			mIs.add(mi);
		}
		menu.getItems().addAll(mIs);
	}
	
	public void addItems(Menu menuName, MenuItem... menuItems) {
		menuName.getItems().addAll(menuItems);
		menuBar.getMenus().add(menuName);
	}

	public Hashtable<Menu, ArrayList<MenuItem>> getItems() {
		return items;
	}

	public void setItems(Hashtable<Menu, ArrayList<MenuItem>> items) {
		this.items = items;
	}

	public void setMenuBar(MenuBar menuBar) {
		this.menuBar = menuBar;
	}

	/**
	 * Restituisce la barra di menu
	 * @return
	 */
	public MenuBar getMenuBar() {
		return menuBar;
	}
	
}
