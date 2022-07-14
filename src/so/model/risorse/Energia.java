package so.model.risorse;

public class Energia extends Risorsa{
	private final int INCREMENTATORE_EN= 75;
	private int e_disponibile;

	public Energia(int livello_risorsa) {
		super(livello_risorsa);
		if (livello_risorsa>= super.MAX_LVL) {
			super.setLivello_risorsa(10);
		}
		super.setNome("Energia");
		e_disponibile= INCREMENTATORE_EN *super.getLivello_risorsa();
		super.setE_richiesta(0);
		super.setTempo_richiesto(3);
	}

	public void effetto() {
		
		e_disponibile= e_disponibile +INCREMENTATORE_EN;

	}

	public int getLivelloAntivirus() {
		return e_disponibile;
	}

	public void setStat1(int e_disponibile) {
		this.e_disponibile = e_disponibile;
	}

}
