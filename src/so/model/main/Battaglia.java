package so.model.main;

import java.beans.PropertyChangeSupport;
import java.util.concurrent.TimeUnit;

import so.model.nodo.Cloud;
import so.model.nodo.Nodo;
import so.model.risorse.Firewall;
import so.model.software.Antivirus;
import so.model.software.Rootcrash;
import so.model.software.Software;
import so.model.software.Virus;


public class Battaglia extends Thread{
	private Nodo attaccante;
	private Nodo difensore;
	private Software[] sel_attaccanti;
	private Software[] sel_difensori;
	
	private boolean esito;
	private int t_timer;
	private String report;
	@SuppressWarnings("unused")
	private Nodo partenza;
	private boolean life=false;
	private PropertyChangeSupport changes;
	public static final String BATTLE_PROP="battaglia";

	public Battaglia(Nodo attaccante, Nodo difensore, int t_timer) {
		this.attaccante=attaccante;
		this.difensore=difensore;
		this.t_timer= t_timer;
		
		sel_attaccanti= new Software[3];
		sel_difensori= new Software[3];
		life=true;
		changes= new PropertyChangeSupport(this);
		
	}

	public synchronized void selezione(int quantita_v, int quantita_r) {
				
		sel_attaccanti[1]= new Virus(attaccante.getStats_software_creati()[1].getLivello(), quantita_v);
		sel_attaccanti[2]= new Rootcrash(attaccante.getStats_software_creati()[2].getLivello(), quantita_r);
		
		sel_difensori[0]= new Antivirus(difensore.getStats_software_creati()[0].getLivello(), difensore.getStats_software_creati()[0].getQuantita());
		
		if (attaccante.getStats_software_creati()[2].getQuantita()>1 && quantita_r==1) {
			sel_attaccanti[2].setQuantita(quantita_r);
			
			attaccante.getStats_software_creati()[2].setQuantita(attaccante.getStats_software_creati()[2].getQuantita()-1);
			
		}
		if(attaccante.getStats_software_creati()[1].getQuantita()>=quantita_v) {
			sel_attaccanti[1].setQuantita(quantita_v);
			
			attaccante.getStats_software_creati()[1].setQuantita(attaccante.getStats_software_creati()[1].getQuantita()-quantita_v);
		}
		
		
		
		attaccante.setSoftware_disponibile(attaccante.getSoftware_disponibile()-(quantita_v+ quantita_r) );
	}

	public int aggiorna_firewall() {
		
		Firewall temp;		
		temp= new Firewall(difensore.getLvl_firewall()-sel_attaccanti[2].getVal_atk());
		return temp.getLivelloAntivirus();
	}	

	public boolean calcola_vincitore() {
		
		int attacco, difesa, difesa1, difesa2;
		int temp;
		
		boolean successo=false;
		attacco= sel_attaccanti[1].getQuantita() * sel_attaccanti[1].getVal_atk();
		difesa1=aggiorna_firewall();
		difesa2=(sel_difensori[0].getVal_def()*sel_difensori[0].getQuantita());
		
		difesa= difesa1+difesa2;

		if(attacco>difesa) {
			successo=true;
			sel_difensori[0].setQuantita(0);
			
		} 
		else {
			if(attacco<difesa) {
				if(sel_difensori[0].getLivello()!=0) {
					temp= (difesa2/sel_difensori[0].getVal_def()) - attacco;


					difensore.getStats_software_creati()[0].setQuantita(temp);
				}
			} else if(attacco==difesa) {
				difensore.getStats_software_creati()[0].setQuantita(0); 
			}
		}
		
		return successo;
	}

	public String stampa_report(boolean esito) {
		
		String report;
		if(esito) {
			report=" Ha conquistato il nodo di "+difensore.getPossessore().getNome();
		}else report=" Non ha conquistato il nodo di "+difensore.getPossessore().getNome();
		
		return report;
	}

	public void aggiornastati() {

		if (Cloud.class.isAssignableFrom(difensore.getClass())) {
			
			attaccante.compra_risorsa("Energia");
			
			if(difensore.getLvl_cpu()>0) {
				attaccante.compra_risorsa("Cpu");
			}
			if(difensore.getLvl_ram()>0) {
				attaccante.compra_risorsa("Ram");
			}
			
		}
		else {

			difensore.getPossessore().setBasi_prese(difensore.getPossessore().getBasi_prese()-1);
			attaccante.getPossessore().setBasi_prese(attaccante.getPossessore().getBasi_prese()+1);
			if(difensore.getPossessore().getBasi_prese()==0) {
				difensore.getPossessore().setLife(false);
				difensore.getPossessore().interrupt();
			}
			
		}
		difensore.setPossessore(attaccante.getPossessore());
		attaccante.getPossessore().aggiornaPunteggio(10);
	}
	

	public void run() {
		while(life) {
			try {
				TimeUnit.SECONDS.sleep(t_timer);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			esito= this.calcola_vincitore();
			report=stampa_report(esito);
			if(esito) {
				this.aggiornastati();
				
			}
			changes.firePropertyChange(BATTLE_PROP, 0, 1);
			life=false;
		}
		
	}

	public boolean getEsito() {
		return esito;
	}

	public String getReport() {
		return report;
	}

	public void setPartenza(Nodo partenza) {
		this.partenza = partenza;
	}

	public Nodo getAttaccante() {
		return attaccante;
	}

	public Nodo getDifensore() {
		return difensore;
	}

	public PropertyChangeSupport getChanges() {
		return changes;
	}
	
	
}