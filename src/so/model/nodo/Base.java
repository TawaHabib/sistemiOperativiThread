package so.model.nodo;

import so.model.giocatore.Giocatore;
import so.model.risorse.Cpu;
import so.model.risorse.Energia;
import so.model.risorse.Firewall;
import so.model.risorse.Ram;
import so.model.risorse.Risorsa;
import so.model.software.Antivirus;
import so.model.software.Rootcrash;
import so.model.software.Software;
import so.model.software.Virus;


public class Base extends Nodo{
	private Software[] stats_software_creati;
	private final int TIPI_SOFTWARE=3;
	private Risorsa[] risorse;
	private final int TIPI_RISORSE=4;

	public Base() {
		super.setTipologia("base");
		super.setDist_base(0);
		super.setPossessore(null);
		super.setSoftware_disponibile(0);
		stats_software_creati= new Software[TIPI_SOFTWARE];
		this.inizializza_software();
		risorse=new Risorsa[TIPI_RISORSE];
		this.inizializza_risorse();
	}

	public Base(Giocatore possessore) {
		super.setTipologia("base");
		super.setDist_base(0);
		super.setPossessore(possessore);
		super.setSoftware_disponibile(0);
		stats_software_creati= new Software[TIPI_SOFTWARE];
		this.inizializza_software();
		risorse=new Risorsa[TIPI_RISORSE];
		this.inizializza_risorse();
	}

	public void inizializza_software() {
		
		stats_software_creati[0]=new Antivirus(0,0);
		stats_software_creati[1]=new Virus(0,0);
		stats_software_creati[2]=new Rootcrash(0,0);
	}

	public void inizializza_risorse() {		
		risorse[0]=new Cpu(1);
		super.setLvl_cpu(risorse[0].getLivello_risorsa());
		risorse[1]=new Ram(1);
		super.setLvl_ram(risorse[1].getLivello_risorsa());
		risorse[2]=new Energia(1);
		super.setE_disponibile(risorse[2].getLivelloAntivirus());
		risorse[3]=new Firewall(0);
		super.setLvl_firewall(risorse[3].getLivello_risorsa());
	}

	public synchronized boolean compra_risorsa(String nome) {
	
		boolean check=false;
		int i;
		for(i=0; i<TIPI_RISORSE;i++) {
			if(risorse[i].getNome().equalsIgnoreCase(nome)) {
				check= risorse[i].potenziamento(); 
			}
		}
		
		return check;
	}

	public synchronized void potenzia_risorsa(String nome) {
		
		boolean check=false;
		int en_usata, i;
		for(i=0; i<TIPI_RISORSE;i++) {
			if(risorse[i].getNome().equalsIgnoreCase(nome)) {
				if(risorse[i].getE_richiesta()<= risorse[2].getLivelloAntivirus()) {
						en_usata= risorse[2].getLivelloAntivirus()-risorse[i].getE_richiesta();
						risorse[2].setStat1(en_usata);
						check= risorse[i].potenziamento(); 
				}
			}
		}
		if(check) {
			System.out.println(getPossessore().getNome()+": potenziamento eseguito");
		} else System.out.println(getPossessore().getNome()+": potenziamento fallito");
	}

	public synchronized int getTempoRisorsa(String nome) {
		int tempo=-1;
		for(int i=0; i<TIPI_RISORSE;i++) {
			if(risorse[i].getNome().equalsIgnoreCase(nome))
				tempo=risorse[i].getTempo_richiesto();
		}
		return tempo;
	}

	public synchronized int getTempoSoftware(String nome) {
		int tempo=-1;
		for(int i=0;i<TIPI_SOFTWARE;i++) {
			if(stats_software_creati[i].getNome().equalsIgnoreCase(nome))
				tempo=stats_software_creati[i].getTemp_richiesto();
		}
		return tempo;
	}

	public synchronized boolean compra_software(String nome, int quantita) {

		boolean check=false;
		int n_soft;
		int i;
		
		if(quantita>risorse[1].getLivelloAntivirus()) {
			quantita=this.getSpazio_Ram();
		}
		for(i=0;i<TIPI_SOFTWARE;i++) {
			if(stats_software_creati[i].getNome().equalsIgnoreCase(nome)) {
				n_soft=super.getSoftware_disponibile()+quantita;
				quantita= stats_software_creati[i].getQuantita()+quantita;
				switch(i) {
				case 0: 
					stats_software_creati[0]= new Antivirus(risorse[0].getLivelloAntivirus(), quantita);
					break;
				case 1: 
					stats_software_creati[1]= new Virus(risorse[0].getLivelloAntivirus(), quantita);
					break;
				case 2:
					stats_software_creati[2]= new Rootcrash(risorse[0].getLivelloAntivirus(), quantita);
					break;
				}
				super.setSoftware_disponibile(n_soft);
				check= true;
			}
		}
		
		return check;
	}

	public synchronized  void crea_software(String nome, int quantita) {
	
		boolean check=false;
		int n_soft;
		int i;
		
		n_soft=super.getSoftware_disponibile() + quantita;
		if(n_soft<=risorse[1].getLivelloAntivirus()) {
			for(i=0;i<TIPI_SOFTWARE;i++) {
				if(stats_software_creati[i].getNome().equalsIgnoreCase(nome)) {
					quantita= stats_software_creati[i].getQuantita()+quantita;
					switch(i) {
					case 0: 
						if(risorse[0].getLivelloAntivirus()!=0) {
							if(risorse[1].getLivelloAntivirus()-quantita>=0) {
								stats_software_creati[0]= new Antivirus(risorse[0].getLivelloAntivirus(), quantita);
							}
						}
						break;
					case 1: 
						if(risorse[0].getStat2()!=0) {
							if(risorse[1].getLivelloAntivirus()-quantita>=0) {
								stats_software_creati[1]= new Virus(risorse[0].getStat2(), quantita);
							}
						}
						break;
					case 2:
						if(risorse[0].getStat2()!=0) {
							if(risorse[1].getLivelloAntivirus()-quantita>=0) {
								stats_software_creati[2]= new Rootcrash(risorse[0].getStat3(), quantita);
							}
						}
						break;
					}
					super.setSoftware_disponibile(n_soft);
					check= true;
				}
			}
		}else System.out.println(getPossessore().getNome()+": spazio non disponibile in ram");
		if(check){
			System.out.println(getPossessore().getNome()+": software creati con successo");
		} else System.out.println(getPossessore().getNome()+": creazione non eseguita");	
	}

	@Override
	public synchronized int getSoftware_max() {
		return risorse[1].getLivelloAntivirus();
	}

	public synchronized int getBonus_def() {
		return risorse[3].getLivelloAntivirus();
	}

	public synchronized void setBonus_def(int bonus_def) {
		risorse[3].setStat1(bonus_def);;
	}

	public synchronized  int getE_disponibile() {
		return risorse[2].getLivelloAntivirus();
	}

	@Override
	public synchronized int getE_lvl() {
		return risorse[2].getLivello_risorsa();
	}

	public synchronized int getLvl_cpu() {
		return risorse[0].getLivello_risorsa();
	}

	public synchronized int getLvl_ram() {
		return risorse[1].getLivello_risorsa();
	}

	public synchronized int getLvl_firewall() {
		return risorse[3].getLivello_risorsa();
	}

	public synchronized Software[] getStats_software_creati() {
		return stats_software_creati;
	}

	public synchronized  Risorsa[] getRisorse() {
		return risorse;
	}

	public synchronized int getLvl_max_cpu() {
		return risorse[0].getMAX_LVL();
	}

	public synchronized int getLvl_max_ram() {
		return risorse[1].getMAX_LVL();
	}

	public synchronized int getSpazio_Ram() {
		return risorse[1].getLivelloAntivirus()-super.getSoftware_disponibile();
	}

	public synchronized int getLvl_max_firewall() {
		return risorse[3].getMAX_LVL();
	}

	public synchronized int getLvl_virus() {
		return risorse[0].getStat2();
	}

	public synchronized int getQnt_virus() {
		return stats_software_creati[1].getQuantita();
	}

	public synchronized int getLvl_antivirus() {
		return risorse[0].getLivelloAntivirus();
	}

	public synchronized int getQnt_antivirus() {
		return stats_software_creati[0].getQuantita();
	}

	public synchronized int getLvl_rootcrash() {
		return risorse[0].getStat3();
	}

	public synchronized int getQnt_rootcrash() {
		return stats_software_creati[2].getQuantita();
	}
	@Override
	public synchronized int getLivelloRisorsa(String nome) {
		if(nome.equalsIgnoreCase("firewall")) {
			return risorse[3].getLivello_risorsa();
		}
		if(nome.equalsIgnoreCase("cpu")) {
			return risorse[0].getLivello_risorsa();
		}
		if(nome.equalsIgnoreCase("ram")) {
			return risorse[1].getLivello_risorsa();
		}
		if(nome.equalsIgnoreCase("energia")) {
			return risorse[2].getLivello_risorsa();
		}
		return 0;
	}
	
	public synchronized int getMaxRisorsa(String nome) {
		if(nome.equalsIgnoreCase("firewall")) {
			return risorse[3].getMAX_LVL();
		}
		if(nome.equalsIgnoreCase("cpu")) {
			return risorse[0].getMAX_LVL();
		}
		if(nome.equalsIgnoreCase("ram")) {
			return risorse[1].getMAX_LVL();
		}
		if(nome.equalsIgnoreCase("energia")) {
			return risorse[2].getMAX_LVL();
		}
		return 0;
	}
	
	
	
}
