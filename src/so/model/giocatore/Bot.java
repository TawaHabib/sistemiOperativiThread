package so.model.giocatore;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

import so.model.giocatore.comportamento.Attacco;
import so.model.giocatore.comportamento.Potenziamento;
import so.model.giocatore.comportamento.Produzione;
import so.model.main.Battaglia;
import so.model.mappa.Coordinate;
import so.model.mappa.MappaDefinitiva;

public class Bot extends Giocatore{
	
	private MappaDefinitiva map;
	private Coordinate base;
	private Battaglia battle;
	private final int T_UNITARIO=5;
	private int t_timer;
	private int cont;
	//private Coordinate[] confini;
	private ArrayList<Coordinate> attaccabili;
	private int incremento; 
	
	private Attacco attacco;
	private Potenziamento potenziamento;
	private Produzione produzione;
	
	
	private PropertyChangeSupport changes;
	public static final String BOT_PROP="bot";
	public static final String PUNTEGGIO_BOT="fineBattagliaBot";

	
	public Bot(String nome) {
		super(nome);
		attaccabili=new ArrayList<Coordinate>();
		super.setPunteggio(0);
		super.setLife(true);
		incremento=1;
		cont=0;
		this.changes= new PropertyChangeSupport(this);
		attacco=new Attacco(this);
		potenziamento=new Potenziamento(this);
		produzione=new Produzione(this);
	}
	
	@Override
	public void run() {
		this.setMap(map);
		this.datiBase();
		//this.cambiaTarget();
		attacco.start();
		potenziamento.start();
		produzione.start();
	}


	public void cambiaTarget() {
		attaccabili.clear();
		int altezza=incremento+1;
		for(int i=0; i<altezza;i++) {
			attaccabili.add(new Coordinate(map.checkx(base.getX()+i),map.checky( base.getY()+incremento) ));
			attaccabili.add(new Coordinate(map.checkx(base.getX()+i),map.checky( base.getY()-incremento) ));
		}
		for(int i=1; i<altezza;i++) {
			attaccabili.add(new Coordinate(map.checkx(base.getX()-i),map.checky( base.getY()+incremento) ));
			attaccabili.add(new Coordinate(map.checkx(base.getX()-i),map.checky( base.getY()-incremento) ));
		}
		altezza=incremento;
		for(int i=0; i<altezza;i++) {
			attaccabili.add(new Coordinate(map.checkx(base.getX()+incremento),map.checky( base.getY()+i) ));
			attaccabili.add(new Coordinate(map.checkx(base.getX()-incremento),map.checky( base.getY()+i) ));
		}
		for(int i=1; i<altezza;i++) {
			attaccabili.add(new Coordinate(map.checkx(base.getX()+incremento),map.checky( base.getY()-i) ));
			attaccabili.add(new Coordinate(map.checkx(base.getX()-incremento),map.checky( base.getY()-i) ));
		}
	}
	
	
	
	public void datiBase() {
		int i;
		int x=0;
		int y=0;
		
		for(i=0;i<map.getBasi().length; i++) {
			if(map.getBasi()[i].getNome()==this.getNome() ) {
				x=map.getBasi()[i].getX();
				y=map.getBasi()[i].getY();
			} 
		}
		base= new Coordinate(x, y, this.getNome());
	}

	@Override
	public void setMap(MappaDefinitiva map) {
		this.map = map;
	}

	public int getT_timer() {
		return t_timer;
	}

	public PropertyChangeSupport getChanges() {
		return changes;
	}

	public Coordinate getBase() {
		return base;
	}

	public void setBase(Coordinate base) {
		this.base = base;

	}

	public Battaglia getBattle() {
		return battle;
	}

	public void setBattle(Battaglia battle) {
		this.battle = battle;
	}

	public int getCont() {
		return cont;
	}

	public void setCont(int cont) {
		this.cont = cont;
	}
	
	public int getIncremento() {
		return incremento;
	}

	public void setIncremento(int incremento) {
		this.incremento = incremento;
	}

	public MappaDefinitiva getMap() {
		return map;
	}

	public int getT_UNITARIO() {
		return T_UNITARIO;
	}

	public void setT_timer(int t_timer) {
		this.t_timer = t_timer;
	}

	public void setChanges(PropertyChangeSupport changes) {
		this.changes = changes;
	}

	public ArrayList<Coordinate> getAttaccabili() {
		return attaccabili;
	}

	public void setAttaccabili(ArrayList<Coordinate> attaccabili) {
		this.attaccabili = attaccabili;
	}

	public Attacco getAttacco() {
		return attacco;
	}

	public void setAttacco(Attacco attacco) {
		this.attacco = attacco;
	}

	public Potenziamento getPotenziamento() {
		return potenziamento;
	}

	public void setPotenziamento(Potenziamento potenziamento) {
		this.potenziamento = potenziamento;
	}

	public Produzione getProduzione() {
		return produzione;
	}

	public void setProduzione(Produzione produzione) {
		this.produzione = produzione;
	}
	@Override
	public void interrupt() {
		attacco.interrupt();
		potenziamento.interrupt();
		produzione.interrupt();
		super.interrupt();

	}
}

