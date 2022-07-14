package so.observers;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import so.view.partita.PartitaStage;

public class ProduzioneObs implements PropertyChangeListener {
	private PartitaStage partitaStage;
	
	public ProduzioneObs(PartitaStage partitaStage) {
		super();
		this.partitaStage = partitaStage;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		partitaStage.addProduzioneSoftware(evt.getOldValue().toString(),(Integer) evt.getNewValue());
	}

	public PartitaStage getPartitaStage() {
		return partitaStage;
	}

	public void setPartitaStage(PartitaStage partitaStage) {
		this.partitaStage = partitaStage;
	}

}
