package so;

import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import so.model.giocatore.Bot;
import so.model.giocatore.comportamento.Potenziamento;
import so.model.giocatore.comportamento.Produzione;
import so.model.main.MainDefinitivo;
import so.model.nodo.Nodo;
import so.observers.AttaccoObs;
import so.observers.FinePartitaObserver;
import so.observers.NodoObserver;
import so.observers.PotenziamentoObs;
import so.observers.ProduzioneObs;
import so.util.RedirectConsoleOutput;
import so.view.partita.Partita;
import so.view.partita.PartitaStage;

public class Main extends Application {
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
	@Override
	public void start(Stage arg0) throws Exception {
		RedirectConsoleOutput ps=ridirect();
		
		MainDefinitivo model=new MainDefinitivo();
		model.avvioPartita(15, 10, "Monkey D. Rufy", 0);
		
		Partita p=new Partita(model,ps);
		
		FinePartitaObserver fs=new FinePartitaObserver(model, p);
		p.setFineObserver(fs);
		
		initNodoObs(model.getTabellone().getMap(), p);
		
		PropertyChangeListener attaccoObs=new AttaccoObs(model, p);
		PropertyChangeListener produzioneObs=new ProduzioneObs(p);
		PropertyChangeListener potenziamentoObs=new PotenziamentoObs(p);
		
		initComportamentoObs(model, attaccoObs, produzioneObs, potenziamentoObs);
		
		p.show();

	}
		
	
	private RedirectConsoleOutput ridirect() {
		File f=new File("./resources/log/log.log");
		if(!f.exists()) {
			if(f.getParent()!=null){
				f.getParentFile().mkdirs();
			}
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		RedirectConsoleOutput ps=null;
		try {
			ps = new RedirectConsoleOutput(f.getPath());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.setOut(ps);
		return ps;
	}
	
	private void initNodoObs(Nodo[][] a, PartitaStage  partitaStage) {
		for (int i=0;i<a.length;i++) {
			for(int j=0;j<a[0].length;j++) {
				a[i][j].getChanges().addPropertyChangeListener(Nodo.NOME_POSS_PROP,new NodoObserver(partitaStage)) ;
			}
		}
	}
	
	private void initComportamentoObs(MainDefinitivo facade,PropertyChangeListener attaccoObs,PropertyChangeListener produzioneObs,PropertyChangeListener potenziamentoObs) {
		for(int i=1;i<facade.getGiocatori().length;i++) {
			facade.getGiocatori()[i].getChanges().addPropertyChangeListener(Bot.BOT_PROP,attaccoObs);
			Bot b=(Bot)facade.getGiocatori()[i];
			b.getProduzione().getChanges().addPropertyChangeListener(Produzione.PROP_PROD,produzioneObs);
			b.getPotenziamento().getChanges().addPropertyChangeListener(Potenziamento.PROP_POTE,potenziamentoObs);

		}
	}
	
}
