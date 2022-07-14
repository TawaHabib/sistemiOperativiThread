package so.model.nodo;


import java.beans.PropertyChangeSupport;
import java.util.Objects;

import so.model.giocatore.Giocatore;
import so.model.giocatore.Sistema;
import so.model.risorse.Risorsa;
import so.model.software.Software;

public abstract  class Nodo implements INodo{
	private int dist_base;
	private  Giocatore possessore;
	private int software_disponibile;
	private int software_max;
	@SuppressWarnings("unused")
	private int bonus_def;
	@SuppressWarnings("unused")
	private int e_disponibile;
	@SuppressWarnings("unused")
	private int lvl_cpu, lvl_ram, lvl_firewall, E_lvl;
	private String tipologia;
	private PropertyChangeSupport changes;
	public static final String NOME_POSS_PROP="possessore.nome";
	
	public Nodo () {
		software_disponibile=0;
		this.changes= new PropertyChangeSupport(this);
		this.possessore=new Sistema();
	}

	public Nodo (Giocatore possessore) {
		if (possessore==null||possessore.getNome()==null)
			possessore=new Sistema();
		this.possessore= possessore;
		this.software_disponibile=0;
		this.software_max=0;
		this.dist_base=0;
		this.changes= new PropertyChangeSupport(this);

	}

	public Nodo (Nodo nodo) {
		if (possessore==null||possessore.getNome()==null)
			possessore=new Sistema();
		this.possessore= nodo.getPossessore();
		this.software_disponibile=nodo.getSoftware_disponibile();
		this.software_max=nodo.getSoftware_max();
		this.dist_base=nodo.getDist_base();
		this.changes= new PropertyChangeSupport(this);
	}

	public boolean compra_risorsa(String nome) {
		boolean check=false;
		
		return check;
	}

	public boolean compra_software(String nome, int quantita) {
		boolean check=false;
		return check;
	}

	public void setTipologia(String tipologia) {
		this.tipologia=tipologia;
	}

	public String getTipologia() {
		return tipologia;
	}

	public int getDist_base() {
		return dist_base;
	}

	public void setDist_base(int dist_base) {
		this.dist_base = dist_base;
	}

	public synchronized Giocatore getPossessore() {
		return possessore;
	}

	public synchronized void setPossessore(Giocatore possessore) {
		Giocatore old=new Giocatore(this.possessore) {};
		this.possessore = possessore;
		changes.firePropertyChange(NOME_POSS_PROP, old, this.getPossessore());
	}

	public int getSoftware_disponibile() {
		return software_disponibile;
	}

	public void setSoftware_disponibile(int software_totali) {
		this.software_disponibile = software_totali;
	}

	public int getSoftware_max() {
		return software_max;
	}

	public void setSoftware_max(int software_max) {
		this.software_max = software_max;
	}

	public void setE_disponibile(int e_disponibile) {
		this.e_disponibile = e_disponibile;
	}

	public int getE_lvl() {
		return E_lvl;
	}

	public void setE_lvl(int e_lvl) {
		E_lvl = e_lvl;
	}

	public void setLvl_cpu(int lvl_cpu) {
		this.lvl_cpu = lvl_cpu;
	}

	public void setLvl_ram(int lvl_ram) {
		this.lvl_ram = lvl_ram;
	}

	public void setLvl_firewall(int lvl_firewall) {
		this.lvl_firewall = lvl_firewall;
	}

	public String getColore() {
		return possessore.getColore();
	}

	synchronized public  int getSpazio_Ram() {
		return this.getSoftware_max()-this.getSoftware_disponibile();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		Nodo other = (Nodo) obj;
		return Objects.equals(possessore.getNome(), other.possessore.getNome());
	}
	
	public PropertyChangeSupport getChanges() {
		return changes;
	}

	public abstract Software[] getStats_software_creati();
	public abstract Risorsa[] getRisorse();
	public abstract int getTempoRisorsa(String nome);
	public abstract int getLivelloRisorsa(String nome);
	public abstract int getMaxRisorsa(String nome);
	public abstract int getTempoSoftware(String nome);
	public abstract int getBonus_def();
	public abstract void setBonus_def(int bonus_def);
	public abstract int getLvl_cpu();
	public abstract int getLvl_ram();
	public abstract int getE_disponibile();
	public abstract int getLvl_firewall();
}