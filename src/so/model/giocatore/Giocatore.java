package so.model.giocatore;


import java.beans.PropertyChangeSupport;
import java.util.Objects;

import so.model.mappa.MappaDefinitiva;


public abstract class Giocatore extends Thread implements Comparable<Giocatore>{
	
	private int punteggio;//Punteggio del giocatore nella singola partita
	private String nome;//nickName del giocatore
	public String colore;//Colore associato al giocatore
	private int deltaCoin;//Valuta di gioco
	private MappaDefinitiva map;
	private int basi_prese;
	private PropertyChangeSupport changes;
	public static final String GIOCATORE_PROP="numero_basi";
	private  boolean life;

	public Giocatore(String nome) {
		this.nome=nome;
		punteggio=0;
		deltaCoin=0;
		life=true;
		basi_prese=1;
		this.changes = new PropertyChangeSupport(this);
	}

	public Giocatore() {
		super();
		this.nome=null;
		punteggio=0;
		life=true;
		deltaCoin=0;
		basi_prese=1;
		this.changes = new PropertyChangeSupport(this);
	}

	public Giocatore(Giocatore user) {
		this.nome=user.getNome();
		this.punteggio=user.getPunteggio();
		basi_prese=1;
		this.changes = new PropertyChangeSupport(this);
	}

	public int getPunteggio() {
		return punteggio;
	}

	public void setPunteggio(int punteggio) {
		this.punteggio =punteggio;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void aggiornaPunteggio(int punteggio) {
		this.punteggio+=punteggio;
	}

	public void setColore(String colore) {
		this.colore=colore;
	}

	public String getColore() {
		return colore;
	}

	public int getValuta() {
		return deltaCoin;
	}

	public void setValuta(int deltaCoin) {
		this.deltaCoin = deltaCoin;
	}

	public synchronized int getBasi_prese() {
		return basi_prese;
	}

	public synchronized void setBasi_prese(int basi_prese) {
		Giocatore old= new Giocatore(this) {	
		};
		this.basi_prese = basi_prese;
		changes.firePropertyChange(GIOCATORE_PROP, old.getBasi_prese(), this.getBasi_prese());
				
	}

	public void aggiornaValuta(int valuta) {
		this.deltaCoin += valuta;
	}

	public void setMap(MappaDefinitiva map) {
		this.map = map;
	}

	public MappaDefinitiva getMap() {
		return map;
	}

	@Override
	public int compareTo(Giocatore o) {
		return -this.getPunteggio()+o.getPunteggio();
	}

	@Override
	public int hashCode() {
		return Objects.hash(nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		Giocatore other = (Giocatore) obj;
		return Objects.equals(nome, other.nome)&& punteggio == other.punteggio;
	}

	@Override
	public String toString() {
		return "Giocatore [punteggio=" + punteggio + ", nome=" + nome + ", colore=" + colore + ", deltaCoin="
				+ deltaCoin + "]";
	}
	public PropertyChangeSupport getChanges() {
		return changes;
	}
	
	public synchronized boolean getLife() {
		return life;
	}
	
	public synchronized void setLife(boolean life) {
			 this.life = life;
	}
	
	
	
	
}
