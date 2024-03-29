package so.model.risorse;

public abstract class Risorsa {
	private int livello_risorsa;
	private String nome;
	protected final int MAX_LVL=10;
	private int e_richiesta;
	private int tempo_richiesto;
	private int stat1, stat2, stat3;

	public Risorsa(int livello_risorsa) {
		if(livello_risorsa<=MAX_LVL) {
			this.livello_risorsa=livello_risorsa;
		}
		e_richiesta=0;
		tempo_richiesto=0;
	}
	
	public Risorsa() {

	}

	public boolean potenziamento() {
		
		boolean powerup=false;
		int i;
		i=livello_risorsa+1;
		if(i<=MAX_LVL) {
			livello_risorsa= livello_risorsa+1;
			this.effetto();
			powerup=true;
		}
		return powerup;
	}
	public abstract void effetto();


	public int getLivello_risorsa() {
		return livello_risorsa;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setLivello_risorsa(int livello_risorsa) {
		this.livello_risorsa = livello_risorsa;
	}

	public int getE_richiesta() {
		return e_richiesta;
	}

	public void setE_richiesta(int e_richiesta) {
		this.e_richiesta = e_richiesta;
	}

	public int getTempo_richiesto() {
		return tempo_richiesto;
	}

	public int getMAX_LVL() {
		return MAX_LVL;
	}

	public void setTempo_richiesto(int tempo_richiesto) {
		this.tempo_richiesto = tempo_richiesto;
	}

	public int getLivelloAntivirus() {
		return stat1;
	}

	public void setStat1(int stat1) {
		this.stat1 = stat1;
	}

	public int getStat2() {
		return stat2;
	}

	public void setStat2(int stat2) {
		this.stat2 = stat2;
	}

	public int getStat3() {
		return stat3;
	}

	public void setStat3(int stat3) {
		this.stat3 = stat3;
	}
	
	
}
