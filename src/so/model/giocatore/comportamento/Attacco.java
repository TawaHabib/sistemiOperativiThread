package so.model.giocatore.comportamento;

import java.beans.PropertyChangeEvent;
import java.util.concurrent.TimeUnit;

import so.model.giocatore.Bot;
import so.model.main.Battaglia;
import so.model.mappa.Coordinate;

public class Attacco extends Thread {
	private Bot bot;
	private int t_timer;
	private Battaglia battle;

	public Attacco(Bot bot) {
		super();
		this.bot = bot;
	}

	@Override
	public void run() {
		//*
		int n_virus;
		boolean end_battle=false;
		bot.cambiaTarget();
		while(bot.getLife()) {
			if (bot.getCont()>=bot.getAttaccabili().size()) {
				bot.setIncremento(bot.getIncremento()+1);
				bot.setCont(0);						
				bot.cambiaTarget();
			}
			
			try {
					n_virus=(int)Math.floor( Math.random()*((bot.getMap().trovaBase(bot).getSoftware_disponibile()))+2);
					if(bot.getMap().trovaBase(bot).getStats_software_creati()[1].getQuantita()<n_virus||bot.getMap().trovaBase(bot).getStats_software_creati()[2].getQuantita()<1) {
						System.out.println(bot.getNome()+" virus o rootcrash non disponibili, attacco tra 5 secondi");
						Thread.sleep(5000);
						continue;
					}
					t_timer= bot.getT_UNITARIO();
					battle= new Battaglia(bot.getMap().trovaBase(bot), bot.getMap().getNodo(bot.getAttaccabili().get(bot.getCont()).getX(),bot.getAttaccabili().get(bot.getCont()).getY()), t_timer) ;
					
					System.out.println(bot.getNome()+ " crea battaglia vs " + bot.getMap().getNodo(bot.getAttaccabili().get(bot.getCont()).getX(),bot.getAttaccabili().get(bot.getCont()).getY()).getPossessore().getNome());
					
					battle.selezione( n_virus, 1);
					System.out.println(bot.getNome() + "ha selezionato virus: " + n_virus );
				
					bot.getChanges().firePropertyChange(new PropertyChangeEvent(this, Bot.BOT_PROP, new Coordinate(bot.getAttaccabili().get(bot.getCont()).getX(),bot.getAttaccabili().get(bot.getCont()).getY()), bot.getBase()));	
					TimeUnit.SECONDS.sleep(t_timer);
					
					end_battle= battle.calcola_vincitore();
					
					System.out.println(bot.getNome() + battle.stampa_report(end_battle)); 
					
					if(end_battle||true) {
						battle.aggiornastati();
						bot.getChanges().firePropertyChange(Bot.PUNTEGGIO_BOT,0,1);
					}
					bot.setCont(bot.getCont()+1);

					
				}
				
			 catch (InterruptedException e) {
				//System.out.println( "Attacco di "+bot.getNome()+" Interrotto");
				continue;
			}
			
		}
		//*/
		System.out.println(bot.getNome()+" ha smesso di attaccare per sempre");
	}
	
	
}

