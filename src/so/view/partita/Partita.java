package so.view.partita;

import so.model.main.MainDefinitivo;
import so.observers.FinePartitaObserver;
import so.util.RedirectConsoleOutput;
import so.view.partita.pane.IDrawable;

public class Partita extends PartitaStage {
	private IDrawable drawable;
	private FinePartitaObserver fineObserver;
	private MainDefinitivo mainDefinitivo;
	
	public Partita(MainDefinitivo main, FinePartitaObserver fineObserver,RedirectConsoleOutput redirectConsoleOutput) {
		super(main,redirectConsoleOutput);
		this.drawable=fineObserver;
		this.fineObserver=fineObserver;
		this.mainDefinitivo=main;

	}
	
	public Partita(MainDefinitivo main,RedirectConsoleOutput redirectConsoleOutput) {
		super(main,redirectConsoleOutput);
		this.mainDefinitivo=main;
		
	}


	@Override
	public void fineGioco() {
		fineObserver.finePartita();

	}
	

	public IDrawable getDrawable() {
		return drawable;
	}

	public void setDrawable(IDrawable drawable) {
		this.drawable = drawable;
	}

	public FinePartitaObserver getFineObserver() {
		return fineObserver;
	}

	public void setFineObserver(FinePartitaObserver fineObserver) {
		this.fineObserver = fineObserver;
		this.drawable=fineObserver;
	}

	public MainDefinitivo getMainDefinitivo() {
		return mainDefinitivo;
	}

	public void setMainDefinitivo(MainDefinitivo mainDefinitivo) {
		this.mainDefinitivo = mainDefinitivo;
	}

	@Override
	public void riprendiBot() {
		mainDefinitivo.avvioBot();
		
	}
	
}
