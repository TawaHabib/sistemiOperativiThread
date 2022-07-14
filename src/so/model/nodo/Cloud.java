package so.model.nodo;

import so.model.giocatore.Giocatore;
import so.model.risorse.Cpu;
import so.model.risorse.Energia;
import so.model.risorse.Firewall;
import so.model.risorse.Ram;
import so.model.risorse.Risorsa;
import so.model.software.Antivirus;
import so.model.software.Software;


public class Cloud extends Nodo {
	private Software[] stats_software_creati;
	private Risorsa[] risorse;
	private final int TIPI_RISORSE=4;

	public Cloud (Giocatore possessore) {
		super.setTipologia("cloud");
		super.setDist_base(0);
		super.setPossessore(possessore);
		super.setSoftware_disponibile(0);
		super.setSoftware_max(20);
		risorse= new Risorsa[TIPI_RISORSE];
		this.inizializza_risorse();
		stats_software_creati= new Software[1];
		stats_software_creati[0]= new Antivirus(0, 0);
	}

	public Cloud () {
		super.setTipologia("cloud");
		super.setDist_base(0);
		super.setSoftware_disponibile(0);
		super.setSoftware_max(20);
		risorse= new Risorsa[TIPI_RISORSE];
		this.inizializza_risorse();
		stats_software_creati= new Software[1];
		stats_software_creati[0]= new Antivirus(0, 0);
	}

	public void inizializza_risorse() {

		risorse[0]=new Cpu((int)(Math.random()*2));
		super.setLvl_cpu(risorse[0].getLivello_risorsa());
		risorse[1]=new Ram((int)(Math.random()*2));
		super.setLvl_ram(risorse[1].getLivello_risorsa());
		risorse[2]=new Energia(1);
		super.setE_disponibile(risorse[2].getLivelloAntivirus());
		risorse[3]=new Firewall(0);
		super.setLvl_firewall(risorse[3].getLivello_risorsa());
	}

	public void potenzia_risorsa(String nome) {
				
		boolean check=false;
		if(!nome.equalsIgnoreCase("firewall")) {
			System.out.println("risorsa non potenziabile");
		}else {
			check=risorse[3].potenziamento();
		}
		if(check) {
			System.out.println("potenziamento eseguito");
		} else System.out.println("potenziamento fallito");
		
	}


	public void crea_software(String nome, int quantita) {
			
		boolean check=false;
		int n_soft;
		if(!nome.equalsIgnoreCase("Antivirus")) {
			System.out.println("software non disponibile in nodo cloud");
		}else {
			n_soft=quantita+super.getSoftware_disponibile();
			if(n_soft<=super.getSoftware_max()) {
				stats_software_creati[0]= new Antivirus(1,n_soft);
				check=true;
				super.setSoftware_disponibile(n_soft);
			}else System.out.println("spazio non sufficiente per la creazione");
		}
		if(check){
			System.out.println("successo");
		} else System.out.println("azione non eseguita");
		
	}

	public int getTempoRisorsa(String nome) {
		int tempo=-1;
		if(!nome.equalsIgnoreCase("firewall")) {
			return tempo;
		} else {
			tempo=risorse[3].getTempo_richiesto();
		}
		return tempo;
	}
	
	public int getTempoSoftware(String nome) {
		int tempo=-1;
		if(!nome.equalsIgnoreCase("Antivirus")) {
			return tempo;
		} else {
			tempo=stats_software_creati[0].getTemp_richiesto();
		}
		return tempo;
	}
	
	public int getLvl_cpu() {
		return risorse[0].getLivello_risorsa();
	}

	public Software getAntivirus() {
		return stats_software_creati[0];
	}

	public int getLvl_ram() {
		return risorse[1].getLivello_risorsa();
	}

	public int getE_disponibile() {
		return risorse[2].getLivelloAntivirus();
	}

	public int getLvl_firewall() {
		return risorse[3].getLivello_risorsa();
	}

	public int getBonus_def() {
		return risorse[3].getLivelloAntivirus();
	}

	public void setBonus_def(int bonus_def) {
		risorse[3].setStat1(bonus_def);
	}

	public Software[] getStats_software_creati() {
		return stats_software_creati;
	}

	public Risorsa[] getRisorse() {
		return risorse;
	}

	public int getQnt_antivirus() {
		return super.getSoftware_disponibile();
	}

	@Override
	public int getLivelloRisorsa(String nome) {
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

	public int getMaxRisorsa(String nome) {
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
