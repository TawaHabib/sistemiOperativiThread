package so.model.mappa;


import so.model.giocatore.Giocatore;
import so.model.nodo.Base;
import so.model.nodo.Cloud;
import so.model.nodo.Nodo;


public class MappaDefinitiva{
	private Nodo[][] map;
	private Coordinate[] basi;
	private int x_max, y_max, n_basi;
	private String[] vicini;
	private Coordinate[] confini; 
	private int xsup, xinf, ysup, yinf;


	public MappaDefinitiva() {
		super();
	}

	public MappaDefinitiva(int x_max, int y_max, Giocatore[] giocatori) {
		this.x_max= x_max;
		this.y_max=y_max;
		int i,j;
		
		map= new Nodo[x_max][y_max];
		
		switch(x_max) {
		case(15):
			n_basi=3;
			break;
		case(20):
			n_basi=5;
			break;
		case(30):
			n_basi=10;
			break;
		}
		
		this.assegnamento(n_basi, giocatori);
		
		for(i=0; i<x_max; i++) {
			for(j=0; j<y_max; j++ ) {
				if(map[i][j]==null) {
					map[i][j]= new Cloud(giocatori[0]);
				}
			} 
		}
		
		vicini= new String[6];
		confini= new Coordinate[6];
		
	}

	public void assegnamento(int n_basi, Giocatore[] giocatori) {
			basi= new Coordinate[n_basi];
			
			switch(n_basi) {
			case(3):
				map[3][1]= new Base(giocatori[1]);
				map[11][1]= new Base(giocatori[2]);
				map[7][8]= new Base(giocatori[3]);
				
				basi[0]= new Coordinate(3,1,giocatori[1].getNome());
				basi[1]= new Coordinate(11,1, giocatori[2].getNome());
				basi[2]= new Coordinate(7,8,giocatori[3].getNome());
				break;
			case(5):
				map[4][2]=new Base(giocatori[1]);
				map[12][2]= new Base(giocatori[2]);
				map[1][7]= new Base(giocatori[3]);
				map[16][7]= new Base(giocatori[4]);
				map[8][12]= new Base(giocatori[5]);
				
				basi[0]= new Coordinate(4,2,giocatori[1].getNome());
				basi[1]= new Coordinate(12,2, giocatori[2].getNome());
				basi[2]= new Coordinate(1,7,giocatori[3].getNome());
				basi[3]=new Coordinate(16,7, giocatori[4].getNome());
				basi[4]=new Coordinate(8,12, giocatori[5].getNome());
				break;
			case(10):
				map[6][2]= new Base(giocatori[1]);
				map[15][3]= new Base(giocatori[2]);
				map[21][2]= new Base(giocatori[3]);
				map[4][11]= new Base(giocatori[4]);
				map[10][10]= new Base(giocatori[5]);
				map[19][12]= new Base(giocatori[6]);
				map[27][11]= new Base(giocatori[7]);
				map[7][18]= new Base(giocatori[8]);
				map[16][16]= new Base(giocatori[9]);
				map[23][17]= new Base(giocatori[10]);
				
				basi[0]= new Coordinate(6,2,giocatori[1].getNome());
				basi[1]= new Coordinate(15,3, giocatori[2].getNome());
				basi[2]= new Coordinate(21,2,giocatori[3].getNome());
				basi[3]= new Coordinate(4,11, giocatori[4].getNome());
				basi[4]= new Coordinate(10,10, giocatori[5].getNome());
				basi[5]= new Coordinate(19,12,giocatori[6].getNome());
				basi[6]= new Coordinate(27,11, giocatori[7].getNome());
				basi[7]= new Coordinate(7,18,giocatori[8].getNome());
				basi[8]= new Coordinate(16,16, giocatori[9].getNome());
				basi[9]= new Coordinate(23,17, giocatori[10].getNome());
				break;
			}
		}
	
	public Nodo trovaBase( Giocatore player) {
		
			int i;
			int x,y;
			x=3;
			y=1;

				for(i=0;i<n_basi; i++) {
					if(basi[i].getNome().equals(player.getNome()) ) {
						x=basi[i].getX();
						y=basi[i].getY();	
					} 
				}
			
			return map[x][y];
		}

	public synchronized boolean attaccabile(int x, int y, Giocatore player) {
		boolean prox=true;

		/*
		int i;
		
		
		x= x - y/2;
		
		if(map[checkx(x+(checky(checky(y))/2))][checky(y)].getPossessore().getNome().equalsIgnoreCase(player.getNome()))
			return false;
		
		
		xsup= x+1;
		xinf=x-1;
		ysup=y+1;
		yinf= y-1;
		
		if(xsup>= x_max)
			xsup= 0;
			
		if (xinf<0) 
			xinf=x_max-1;
		if(ysup>= y_max) 
			ysup=0;
		if (yinf<0)
			yinf=y_max-1;

		vicini[0]=map[checkx(xinf+(checky(y)/2))][checky(y)].getPossessore().getNome();	
			
		vicini[1]=map[checkx(xsup+(checky(y)/2))][checky(y)].getPossessore().getNome();
	
		vicini[2]=map[checkx(x+(checky(ysup)/2))][checky(ysup)].getPossessore().getNome();
		
		vicini[3]=map[checkx(x+(checky(yinf)/2))][checky(yinf)].getPossessore().getNome();
		
		vicini[4]=map[checkx(xsup+(checky(yinf)/2))][checky(yinf)].getPossessore().getNome();
		
		vicini[5]=map[checkx(xinf+(checky(ysup)/2))][checky(ysup)].getPossessore().getNome();
		
		for(i=0;i<6;i++) {
			if(vicini[i].equalsIgnoreCase(player.getNome())) {
				prox=true;
				break;
			}
		}
		*/
		return prox;
	}
		
	public  int checkx(int i) {

		if(i>= x_max)
			i=i%x_max;
			
		if (i<0) {
			i=Math.abs(i);
			i=checkx(i);
		}
		
		return i;
	}
	
	public int checky(int i) {

		if(i>= y_max)
			i=i%y_max;
			
		if (i<0){
			i=Math.abs(i);
			i=checky(i);
		}
		
		return i;
	}


		public Nodo getNodo(int x, int y) {
		
			return map[x][y];
		}

		public int getX_max() {
			return x_max;
		}

		public int getY_max() {
			return y_max;
		}

		public Nodo[][] getMap() {
			return map;
		}

		public Coordinate[] getBasi() {
			return basi;
		}

		public void setMap(Nodo[][] map) {
			this.map = map;
		}

		public int getN_basi() {
			return n_basi;
		}

		public void setN_basi(int n_basi) {
			this.n_basi = n_basi;
		}

		public String[] getVicini() {
			return vicini;
		}

		public void setVicini(String[] vicini) {
			this.vicini = vicini;
		}

		public Coordinate[] getConfini() {
			return confini;
		}

		public void setConfini(Coordinate[] confini) {
			this.confini = confini;
		}

		public int getXsup() {
			return xsup;
		}

		public void setXsup(int xsup) {
			this.xsup = xsup;
		}

		public int getXinf() {
			return xinf;
		}

		public void setXinf(int xinf) {
			this.xinf = xinf;
		}

		public int getYsup() {
			return ysup;
		}

		public void setYsup(int ysup) {
			this.ysup = ysup;
		}

		public int getYinf() {
			return yinf;
		}

		public void setYinf(int yinf) {
			this.yinf = yinf;
		}

		public void setBasi(Coordinate[] basi) {
			this.basi = basi;
		}

		public void setX_max(int x_max) {
			this.x_max = x_max;
		}

		public void setY_max(int y_max) {
			this.y_max = y_max;
		}

		
		
}