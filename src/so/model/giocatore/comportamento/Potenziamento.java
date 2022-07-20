package so.model.giocatore.comportamento;

import java.beans.PropertyChangeSupport;

import so.model.giocatore.Bot;

public class Potenziamento extends Thread {
	private Bot bot;
	private PropertyChangeSupport changes;
	public static String PROP_POTE="POTENZIAMENTO"; 
	
	public Potenziamento(Bot bot) {
		super();
		this.bot = bot;
		changes=new PropertyChangeSupport(this);
	}
	
	@Override
	public void run() {
		int min=1;
		int max=3;
		int scelta;
		String risorsa="Energia";
		while(bot.getLife()) {

			
			try {
			if (	bot.getMap().trovaBase(bot).getLvl_cpu()==10
					&&bot.getMap().trovaBase(bot).getLvl_ram()==10 
					&&bot.getMap().trovaBase(bot).getLvl_firewall()==9
					&&bot.getMap().trovaBase(bot).getE_lvl()==10) 
			{
				continue;
			}
				scelta=(int) Math.floor((Math.random()*(max-min))+min);
				risorsa=getScelta(scelta);
				if(bot.getMap().trovaBase(bot).getRisorse()[2].getLivelloAntivirus()<20) {
					System.out.println(bot.getNome()+ " potenzia Energia per potenziamento ");
					Integer i=bot.getMap().trovaBase(bot).getTempoRisorsa(risorsa)*1000;
					changes.firePropertyChange(PROP_POTE, bot.getNome()+"potenzia energia", i);
					Thread.sleep(bot.getMap().trovaBase(bot).getTempoRisorsa(risorsa)*1000);
					bot.getMap().trovaBase(bot).potenzia_risorsa("Energia");
				}else 
					if(bot.getMap().trovaBase(bot).getLivelloRisorsa(risorsa)<=bot.getMap().trovaBase(bot).getMaxRisorsa(risorsa)){
					System.out.println(bot.getNome()+ " sta potenziando " + risorsa);
					Integer i=bot.getMap().trovaBase(bot).getTempoRisorsa(risorsa)*1000;
					changes.firePropertyChange(PROP_POTE, bot.getNome()+"potenzia "+risorsa, i);
					
					Thread.sleep(bot.getMap().trovaBase(bot).getTempoRisorsa(risorsa)*1000);
					bot.getMap().trovaBase(bot).potenzia_risorsa(risorsa);
				}else {
					continue;
				}
			} catch (InterruptedException e) {
				//System.out.println("Potenziamento di "+bot.getNome()+" Interrotto");
				continue;
			}
			
		}
		
		System.out.println(bot.getNome()+" ha smesso di potenziare per sempre");
	}
	
	private String getScelta(int i) {
		String risorsa;
		switch(i) {
		case 0: 
			risorsa="Cpu";
			break;
		case 1: 
			risorsa= "Ram";
			break;
		case 2:
			risorsa= "Energia";
			break;
		case 3:
			risorsa= "Firewall";
			break;
		default:
			risorsa= "Ram";
			break;
		}
		return risorsa;
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
