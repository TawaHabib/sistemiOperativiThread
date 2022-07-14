package so.model.main;

import java.beans.PropertyChangeSupport;
import java.util.Collections;

import so.model.giocatore.Bot;
import so.model.giocatore.Giocatore;
import so.model.giocatore.Sistema;
import so.model.mappa.MappaDefinitiva;
import so.model.nodo.Colore;


public class MainDefinitivo extends Thread {
	private MappaDefinitiva tabellone;
	private int n_basi;
	private Giocatore[] giocatori;
	private int t_unitario, t_timer;
	private Colore colore;
	private boolean bot_vivi;
	private PropertyChangeSupport support;
	private Battaglia[] fight;
	private int maxbattle=7;
	private int count;
	public static String SUPPORT_BOT_VIVI="bot_vivi";

	public MainDefinitivo() {
		bot_vivi=true;
		support=new PropertyChangeSupport(this);
	}

	public static void main(String[] args) {
		MainDefinitivo m=new MainDefinitivo();
		m.avvioPartita(15, 10, "ubi", 0);
	}
	
	public void avvioPartita(int x_max, int y_max, String nomeUtente, int valuta)  {
		t_unitario=5;
		colore= new Colore();
		this.creazioneGiocatori(nomeUtente, x_max, valuta);
		tabellone = new MappaDefinitiva(x_max, y_max, giocatori);
		fight= new Battaglia[maxbattle];
		maxbattle=6;
		this.avvioBot();
		
	}


	public void creazioneGiocatori(String utente, int x_max, int valuta) {  
		Collections.shuffle(colore.getColori());
				
		switch(x_max) {
		case 15:
			n_basi=3;
			bot_vivi=true;
			giocatori= new Giocatore[n_basi+1];
			giocatori[0]= new Sistema(); 
			giocatori[0].setColore(colore.getGrigio()); 
			giocatori[1]= new Bot(utente); 
			giocatori[2]= new Bot("Eustass kidd");
			giocatori[3]= new Bot("Kozuki Oden");
			break;
		case 20:
			n_basi=5;
			bot_vivi=true;
			giocatori= new Giocatore[n_basi+1];
			giocatori[0]= new Sistema();
			giocatori[0].setColore(colore.getGrigio()); 
			giocatori[1]= new Bot(utente);
			giocatori[2]= new Bot("bob");
			giocatori[3]= new Bot("sandra");
			giocatori[4]= new Bot("roger");
			giocatori[5]= new Bot("max");
			break;
		case 30:
			n_basi=10;
			bot_vivi=true;
			giocatori= new Giocatore[n_basi+1];
			giocatori[0]= new Sistema(); 
			giocatori[0].setColore(colore.getGrigio()); 
			giocatori[1]= new Bot(utente);
			giocatori[2]= new Bot("bob");
			giocatori[3]= new Bot("sandra");
			giocatori[4]= new Bot("roger");
			giocatori[5]= new Bot("max");
			giocatori[6]= new Bot("jupiter");
			giocatori[7]= new Bot("alex");
			giocatori[8]= new Bot("lonfo");
			giocatori[9]= new Bot("mara");
			giocatori[10]=new Bot("alice");
			break;
		}
		
		for (int i = 1; i < giocatori.length; i++) {
			giocatori[i].setColore(colore.getColori().get(i-1)); 
		}
		
	}


	public void avvioBot() {
		int i;
		for(i=1; i<=n_basi; i++) {
			giocatori[i].setMap(tabellone);
			giocatori[i].start();
		}
	}

	public void stopBot() {
		int i;
		for(i=1; i<=n_basi; i++) {
			giocatori[i].setLife(false);
			giocatori[i].interrupt();
		}
	}

	public boolean powerupCheck(int x, int y){
	
		boolean checkp= false;
		if(tabellone.getNodo(x, y).getPossessore().getNome().equalsIgnoreCase(giocatori[1].getNome())) {
			checkp=true;
		}
		return checkp;
	}

	public int getTempoRisorsa(int x, int y, String risorsa) {
		int tempo=-1;
		tempo=tabellone.getNodo(x, y).getTempoRisorsa(risorsa);
		return tempo;
	}

	public boolean softcheck(int x, int y) {
		
		boolean checks=false;
		if(tabellone.getNodo(x, y).getPossessore().equals(giocatori[1])) {
			checks=true;
		}
		return checks;
	}



	public int getTempoSoftware(int x, int y, String software) {
		int tempo=-1;
		tempo=tabellone.getNodo(x, y).getTempoSoftware(software);
		return tempo;
	}

	public boolean marketcheck(int x, int y) {
		
		boolean check=false;
		if(tabellone.getNodo(x, y).getTipologia().equalsIgnoreCase("base")) {
			if(tabellone.getNodo(x, y).getPossessore().equals(giocatori[1])) {
				check=true;
			}
		}
		return check;
	}

	public boolean nodecheck(Giocatore attaccante, int x, int y) {
	
		boolean checkf=false;
		if(maxbattle>0) {
			if(tabellone.attaccabile(x,y, attaccante)) {
				checkf=true;
			}
		}
		
		return checkf;
	}
		
	public int battlecheck(Giocatore attaccante, int x, int y,int quantitaV, int quantitaR) {
		
		ObserverFineBattaglia finebattagliaobs;
		t_timer=0;
		
		if(this.nodecheck(attaccante, x, y)){

			t_timer= t_unitario;
			fight[maxbattle]=new Battaglia(tabellone.trovaBase(attaccante), tabellone.getNodo(x,y), t_timer);
			finebattagliaobs= new ObserverFineBattaglia(attaccante, x, y, this);
			fight[maxbattle].getChanges().addPropertyChangeListener(Battaglia.BATTLE_PROP, finebattagliaobs);

			fight[maxbattle].selezione(quantitaV, quantitaR);
			count=maxbattle;
			
			maxbattle--;
			
		}
		return t_timer; 
	}

	public void avvioBattaglia() {
		
		fight[count].start();
				
	}
	
	public void fineBattaglia(Giocatore attaccante, int x, int y) {
		
		if(fight[count].getEsito()) {
			if(tabellone.getNodo(x, y).getTipologia().equals("base")) {

			}
		}
		maxbattle++;
		System.out.println(attaccante.getNome() + fight[count].getReport() );
		this.checkbot();
		System.out.println("bot vivi? " + bot_vivi);
		support.firePropertyChange(SUPPORT_BOT_VIVI, true, bot_vivi);
	}


	public void checkbot () {
		int i;
		int cont=0;
		for(i=2;i<giocatori.length;i++) {
			if (!giocatori[i].getLife()) {
				cont++;
			} else {
				bot_vivi=true;
				break;
			}
			
		}
			
		if(cont== n_basi) {
			bot_vivi=false;
		}
		
		System.out.println("bot morti= "+ cont);
	}


	public int getX_max() {
		return tabellone.getX_max();
	}

	public int getY_max() {
		return tabellone.getY_max();
	}

	public MappaDefinitiva getTabellone() {
		return tabellone;
	}
	
	public Giocatore[] getGiocatori() {
		return giocatori;
	}

	public int getT_unitario() {
		return t_unitario;
	}

	public void setT_unitario(int t_unitario) {
		this.t_unitario = t_unitario;
	}
	
	public PropertyChangeSupport getSupport() {
		return support;
	}
	
	public void setSupport(PropertyChangeSupport support) {
		this.support = support;
	}

	public int getN_basi() {
		return n_basi;
	}

	public void setN_basi(int n_basi) {
		this.n_basi = n_basi;
	}

	public int getT_timer() {
		return t_timer;
	}

	public void setT_timer(int t_timer) {
		this.t_timer = t_timer;
	}

	public Colore getColore() {
		return colore;
	}

	public void setColore(Colore colore) {
		this.colore = colore;
	}

	public boolean isBot_vivi() {
		return bot_vivi;
	}

	public void setBot_vivi(boolean bot_vivi) {
		this.bot_vivi = bot_vivi;
	}

	public Battaglia[] getFight() {
		return fight;
	}

	public void setFight(Battaglia[] fight) {
		this.fight = fight;
	}

	public int getMaxbattle() {
		return maxbattle;
	}

	public void setMaxbattle(int maxbattle) {
		this.maxbattle = maxbattle;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setTabellone(MappaDefinitiva tabellone) {
		this.tabellone = tabellone;
	}

	public void setGiocatori(Giocatore[] giocatori) {
		this.giocatori = giocatori;
	}
	
	
}
