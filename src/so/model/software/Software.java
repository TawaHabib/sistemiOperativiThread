package so.model.software;

public abstract class Software {
	private int livello;
	private int max_lvl;
	private String nome;
	private int quantita;
	private int temp_richiesto;

	public Software(int livello) { 
		this.livello = livello;
	}

	public abstract int getVal_def();
	public abstract int getVal_atk();

	public int getLivello() {
		return livello;
	}

	public void setLivello(int livello) {
		this.livello = livello;
	}

	public int getMax_lvl() {
		return max_lvl;
	}

	public void setMax_lvl(int max_lvl) {
		this.max_lvl = max_lvl;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getQuantita() {
		return quantita;
	}

	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}

	public int getTemp_richiesto() {
		return temp_richiesto;
	}

	public void setTemp_richiesto(int temp_richiesto) {
		this.temp_richiesto = temp_richiesto;
	}
	
	

}
