package so.model.software;

public class Rootcrash extends Software {
	private int val_def;
	private int val_atk;

	public Rootcrash (int livello, int quantita) {
		super(livello);
		super.setMax_lvl(3);
		super.setNome("Rootcrash");
		if(super.getLivello()> super.getMax_lvl() ) {
			super.setLivello(3);
		}
		val_atk= 3*super.getLivello();
		val_def= 0;
		super.setTemp_richiesto(4);
		super.setQuantita(quantita);
	}

	public int getVal_def() {
		return val_def;
	}

	public int getVal_atk() {
		return val_atk;
	}

	public synchronized void setVal_atk(int val_atk) {
		this.val_atk = val_atk;
	}

	public synchronized void setLivello(int livello) {
		super.setLivello(livello);
		this.setVal_atk(livello);
	}
	
	
}
