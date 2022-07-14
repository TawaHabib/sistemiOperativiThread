package so.observers;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import so.model.main.MainDefinitivo;
import so.model.mappa.Coordinate;
import so.view.partita.PartitaStage;

public class AttaccoObs implements PropertyChangeListener {
	private MainDefinitivo mainDefinitivo;
	private PartitaStage partitaStage;
	
	public AttaccoObs(MainDefinitivo mainDefinitivo, PartitaStage partitaStage) {
		super();
		this.mainDefinitivo = mainDefinitivo;
		this.partitaStage = partitaStage;
	}


	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(Coordinate.class.isAssignableFrom(evt.getNewValue().getClass())&& Coordinate.class.isAssignableFrom(evt.getOldValue().getClass())) {
			Coordinate attaccante=(Coordinate)evt.getNewValue();
			Coordinate difensore=(Coordinate)evt.getOldValue();
			int tempoAttaco=mainDefinitivo.getT_unitario()*1000;
			partitaStage.addDifesa(mainDefinitivo.getTabellone().getNodo(attaccante.getX(), attaccante.getY()).getPossessore().getNome()+" sta attaccando il nodo"
						+"("+(difensore.getX()-difensore.getY()/2)+","+ difensore.getY()+")"
						, tempoAttaco);
			};
				
				
		}

	

}

