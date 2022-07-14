package so.model.software;

public class Virus extends Software{
	private int val_def;
	private int val_atk;
	
	public Virus (int livello, int quantita) {
		super(livello);
		super.setMax_lvl(5);
		super.setNome("Virus");
		super.setTemp_richiesto(2);
		val_def=0;
		val_atk= super.getLivello();
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
		this.setVal_atk(livello);
	}
	
}
