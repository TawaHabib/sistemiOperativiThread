package so.model.giocatore.comportamento;

import java.beans.PropertyChangeSupport;
import java.util.concurrent.TimeUnit;

import so.model.giocatore.Bot;

public class Produzione extends Thread {
		private Bot bot;
		private PropertyChangeSupport changes;
		public static String PROP_PROD="produzione";
		
		public Produzione(Bot bot) {
			super();
			this.bot = bot;
			this.changes=new PropertyChangeSupport(this);
		}

		@Override
		public void run() {

			while(bot.getLife()) {
				
				if(bot.getMap().trovaBase(bot).getSpazio_Ram() == 0) {	
					continue;
				}
				
				try {
					if(bot.getMap().trovaBase(bot).getSpazio_Ram()>=5) {
						System.out.println(bot.getNome()+" sta creando Virus");
						changes.firePropertyChange(PROP_PROD, bot.getNome()+" produzione in corso di n° 5 virus", bot.getMap().trovaBase(bot).getTempoSoftware("Virus")*5*1000);
						TimeUnit.SECONDS.sleep(bot.getMap().trovaBase(bot).getTempoSoftware("Virus")*5);
						bot.getMap().trovaBase(bot).crea_software("Virus", 5);
					}

					if(bot.getMap().trovaBase(bot).getStats_software_creati()[2].getQuantita()<2
							&&bot.getMap().trovaBase(bot).getSpazio_Ram()>=2) {
						System.out.println(bot.getNome()+" sta creando Rootcrash");
						changes.firePropertyChange(PROP_PROD, bot.getNome()+" produzione in corso di n° 2 rootcrash", bot.getMap().trovaBase(bot).getTempoSoftware("Rootcrash")*2*1000);
						TimeUnit.SECONDS.sleep(bot.getMap().trovaBase(bot).getTempoSoftware("Rootcrash")*2);
						bot.getMap().trovaBase(bot).crea_software("Rootcrash", 2);
					}
					
					if(bot.getMap().trovaBase(bot).getStats_software_creati()[0].getQuantita()<1
							&&bot.getMap().trovaBase(bot).getSpazio_Ram()>=1) {
						System.out.println(bot.getNome()+" sta creando Antivirus");
						changes.firePropertyChange(PROP_PROD, bot.getNome()+" produzione in corso di n° 1 antivirus", bot.getMap().trovaBase(bot).getTempoSoftware("Antivirus")*1*1000);
						TimeUnit.SECONDS.sleep(bot.getMap().trovaBase(bot).getTempoSoftware("Antivirus")*1);
						bot.getMap().trovaBase(bot).crea_software("Antivirus", 1);
					}

					
				} catch (InterruptedException e) {
					//System.out.println("Produzione di "+bot.getNome()+" Interrotto");
					continue;
				}
				
			}
			
			System.out.println(bot.getNome()+" ha smesso di produrre per sempre");
		}

		public Bot getBot() {
			return bot;
		}

		public void setBot(Bot bot) {
			this.bot = bot;
		}

		public PropertyChangeSupport getChanges() {
			return changes;
		}

		public void setChanges(PropertyChangeSupport changes) {
			this.changes = changes;
		}
		
		
		
}
