package so.model.risorse;


public class Cpu extends Risorsa{
	private int antivirus, virus, rootcrash;

	public Cpu (int livello_risorsa) {
		super(livello_risorsa);
		if (livello_risorsa>= super.MAX_LVL) {
			super.setLivello_risorsa(10);
		}
		super.setNome("Cpu");
		this.effetto();
	}

	public void effetto() {
		switch ( super.getLivello_risorsa() ) {
		case 0: antivirus=0;
				virus=0;
				rootcrash=0;
				super.setE_richiesta(20);
				super.setTempo_richiesto(5);
				break;
		case 1: antivirus=1;
				virus=1;
				super.setTempo_richiesto(10);
				super.setE_richiesta(20);
				break;
		case 2:	antivirus=2;
				virus=1;
				super.setTempo_richiesto(10);
				super.setE_richiesta(20);
				break;
		case 3:	virus=2;
				antivirus=2;
				super.setTempo_richiesto(10);
				super.setE_richiesta(20);
		case 4: antivirus=3;
				virus=3;
				super.setTempo_richiesto(15);
				super.setE_richiesta(20);
				break;
		case 5: antivirus=4;
				virus=3;
				rootcrash=1;
				super.setE_richiesta(30);
				super.setTempo_richiesto(15);
				break;
		case 6: virus=4;
				antivirus=4;
				rootcrash=1;
				super.setE_richiesta(30);
				super.setTempo_richiesto(15);
				break;
		case 7: antivirus=5;
				virus=4;
				rootcrash=1;
				super.setTempo_richiesto(20);
				super.setE_richiesta(30);
				break;
		case 8: rootcrash=2;
				virus=4;
				antivirus=5;
				super.setTempo_richiesto(20);
				super.setE_richiesta(30);
				break;
		case 9: virus=5;
				antivirus=5;
				rootcrash=2;
				super.setE_richiesta(40);
				super.setTempo_richiesto(30);
				break;
		case 10:rootcrash=3;
				virus=5;
				antivirus=5;
				break;
		}
	}

	public int getStat1() {
		return antivirus;
	}

	public int getStat2() {
		return virus;
	}

	public int getStat3() {
		return rootcrash;
	}
	
}