package so.observers;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import so.model.main.MainDefinitivo;
import so.view.partita.PartitaStage;
import so.view.partita.pane.IDrawable;

public class FinePartitaObserver implements PropertyChangeListener, IDrawable {


	private MainDefinitivo mainDefinitivoModello;

	private PartitaStage partitaStage;
	
	
	public FinePartitaObserver(MainDefinitivo mainDefinitivoModello, PartitaStage partitaStage) {
		super();
		this.mainDefinitivoModello = mainDefinitivoModello;
		this.partitaStage = partitaStage;
		this.partitaStage.setOnCloseRequest(e->{
			this.finePartita();
		});
	
	}


	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		boolean finePartita=false;
		try {
			if((Integer)evt.getNewValue()==0)
				finePartita=true;
			System.out.println(finePartita);
		
		} catch (Exception e) {
			try {
				if((boolean)evt.getNewValue()==false)
					finePartita=true;
			} catch (Exception e2) {
				e2.printStackTrace();
				this.finePartita();
			}
		}

		if(finePartita) {
			this.finePartita();			
		}
		
	}

	public void finePartita() {
			this.mainDefinitivoModello.stopBot();
	}
	


	
	@Override
	public void drow() {
		finePartita();
		
	}
		
}
