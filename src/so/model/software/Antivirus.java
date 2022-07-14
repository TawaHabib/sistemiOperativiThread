package so.model.software;

public class Antivirus extends Software{
	private int val_atk;
	private int val_def;

	public Antivirus(int livello, int quantita) {
		super(livello);
		val_atk= 0;
		val_def= super.getLivello();
		super.setMax_lvl(5);
		super.setNome("Antivirus");
		super.setTemp_richiesto(2);
		super.setQuantita(quantita);
	}

	public int getVal_def() {
		return val_def;
	}

	public void setVal_def(int val_def) {
		this.val_def = val_def;
	}

	public int getVal_atk() {
		return val_atk;
	}

	public void setVal_atk(int val_atk) {
		this.val_atk = val_atk;
	}

	public void setLivello(int livello) {
		super.setLivello(livello);
		this.setVal_def(livello);
	}
		
}
