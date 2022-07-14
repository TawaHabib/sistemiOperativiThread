package so.view.partita.board.bean.nodes;

import so.model.nodo.Base;
import so.model.nodo.Nodo;

public class HexData  { // 
			
	public Nodo nodo;

	public HexData(Nodo nodo) {
		super();
		this.nodo = nodo;
	}

	/**
	 * definisce le caratteristiche dell'esagono
	 * @return
	 */
	public boolean isBase() {
    	if(Base.class.isAssignableFrom(nodo.getClass())) {
    		return true;
    	}
    	return false;
	}


}
