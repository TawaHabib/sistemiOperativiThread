package so.view.partita;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Pair;
import so.model.main.MainDefinitivo;
import so.model.nodo.Base;
import so.model.nodo.Nodo;
import so.observers.FinePartitaObserver;
import so.util.RedirectConsoleOutput;
import so.view.partita.board.PlayTable;
import so.view.partita.board.bean.mappa.BasicMap;
import so.view.partita.board.bean.mappa.MapData;
import so.view.partita.board.bean.nodes.HexData;
import so.view.partita.board.bean.nodes.Hexagon;
import so.view.partita.board.bean.util.Point;
import so.view.partita.pane.BaseStatsPane;
import so.view.partita.pane.ProgressBarConteiner;
import so.view.partita.pane.util.ProgressStyle;
import so.view.partita.pane.util.menu.SDMenuBar;

public abstract class PartitaStage extends Stage{
	
	/**
	 * Pannello che contiene i progressBar 
	 * degli attacchi/difese del giocatore
	 */
	private ProgressBarConteiner battleBox;
	
	/**
	 *ridirezione della console in uno stage 
	 */
	private RedirectConsoleOutput redirectConsoleOutput;
	/**
	 * Pannello che contiene i progressBar 
	 * degli sviluppi sW/hw che sta facendo il giocatore
	 */
	private ProgressBarConteiner poweUpBox;
	
	/**
	 * Tavolo da gioco contenete gli esagoni
	 */
	private PlayTable playTable;
	
	
	/**
	 * Coordinate del punto selezionato (secondo la convenzione matriciale)
	 */
	private Point selectedPoint; 
	
	/**
	 * Esegono selezionato
	 */
	private Hexagon selectedHexagon; 
	
	/**
	 * Nodo selezionato
	 */
	private Nodo selectedNode;
	
	/**
	 * dimensioni del tavolo da gioco
	 */
	private Pair<Integer, Integer> dimensioni;
	
	/**
	 * Disegnatrice di mappe
	 */
	private BasicMap basicMap;
	
	/**
	 * 
	 */
	private SDMenuBar menuBar;
	
	private BaseStatsPane baseStatsPane;
	
	public PartitaStage(Nodo[][] nodi, Integer nodiAltezzaMappa,Integer nodiLarghezzaMappa,RedirectConsoleOutput redirectConsoleOutput) {
		super();
		selectedNode=nodi[0][0];
		this.redirectConsoleOutput=redirectConsoleOutput;
		super.setMinHeight(700);
		super.setMinWidth(800);
		super.setAlwaysOnTop(false);
		this.menuBarCreator();
		this.battleBox=new ProgressBarConteiner();
		this.battleBox.setTitle("BATTAGLIE IN CORSO");
		this.poweUpBox=new ProgressBarConteiner();
		this.poweUpBox.setTitle("PRODUZIONI IN CORSO");
		this.dimensioni=new Pair<Integer, Integer>(nodiLarghezzaMappa,nodiAltezzaMappa );
		baseStatsPane=new BaseStatsPane(selectedNode);
		this.playTableMaker(nodi);
		this.initPlayTableListener();
		this.disponiPannelli();
		
		
	}

	public PartitaStage(MainDefinitivo main,RedirectConsoleOutput redirectConsoleOutput) {
		this(main.getTabellone().getMap(),main.getTabellone().getY_max(),main.getTabellone().getX_max(),redirectConsoleOutput);
	}
	
	
	/**
	 * metodo che deve contenere le istuzoni per far finire il gioco
	 */
	public abstract void fineGioco();
	
	public void disponiPannelli(){
		Background black=new Background(new BackgroundFill(Color.web("#000000"), new CornerRadii(10), null));
		
		playTable.getScrollPane().setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		playTable.getScrollPane().setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		
		
		ScrollPane battleBoxScrollpane=new ScrollPane(
				this.putPaneInAscrollableGridPane(this.battleBox,
						black,new Background(new BackgroundFill(Color.web("#000000"), null, null))));
		battleBoxScrollpane.setFitToWidth(true);
		ScrollPane powrUpScrollPane=new ScrollPane(this.putPaneInAscrollableGridPane(this.poweUpBox,black,new Background(new BackgroundFill(Color.web("#000000"), null, null))));
		powrUpScrollPane.setFitToWidth(true);
		HBox hProgressBox=new HBox();
		HBox.setHgrow(powrUpScrollPane, Priority.ALWAYS);
		HBox.setHgrow(battleBoxScrollpane, Priority.ALWAYS);

		hProgressBox.getChildren().addAll(battleBoxScrollpane,powrUpScrollPane,redirectConsoleOutput.getTextBox().getSp());
		hProgressBox.setMinHeight(190);
		hProgressBox.setSpacing(10);
		hProgressBox.setMaxHeight(190);
		
		
		//borderPane

		BorderPane borderPane = new BorderPane();;
		borderPane.setCenter(playTable.getScrollPane());
		BorderPane.setMargin(playTable.getScrollPane(), new Insets(10));
		
		borderPane.setRight(baseStatsPane);
		
		borderPane.setBottom(hProgressBox);
		BorderPane.setMargin(hProgressBox, new Insets(10));

		VBox vPartitaBox=new VBox();
		vPartitaBox.setSpacing(10);
		vPartitaBox.getChildren().addAll(menuBar.getMenuBar(),borderPane);
		Scene scena = new Scene(vPartitaBox,800,700);
	
		super.setScene(scena);
		this.drowMappa();
	}

	public void menuBarCreator() {
		menuBar=new SDMenuBar();
		MenuItem finePartita=new MenuItem("Ferma bot");
		finePartita.setOnAction(actionEvent ->{
			fineGioco();
		});
		Menu m=new Menu("Menu");
		/*		
		MenuItem riprendi=new MenuItem("Sveglia bot");
		riprendi.setOnAction(actionEvent ->{
			this.riprendiBot();
		});*/
		menuBar.addItems(m,finePartita);
		
	}
	/**
	 * Cosctuisce il tavolo da gioco
	 * @param nodi
	 * Nodi di cui è costituita la mappa
	 */
	public void playTableMaker(Nodo[][] nodi) {
		MapData mapData = new MapData(nodi);
		playTable= new PlayTable(dimensioni);
		playTable.getPlayTable(dimensioni, mapData, mapData.getRay());
		
		this.basicMapMaker(playTable.getMapData(), playTable.getBasicCanvas().getGraphicsContext2D());
		basicMap.setSelected(true);
		basicMap.drawMap();
	}
	
	/**
	 * 
	 */
	public void initPlayTableListener() {
		playTable.getScrollPane().setOnMouseClicked(event -> {
			double xMouse=event.getX();
			double yMouse=event.getY();
			double xBase=playTable.getBasicCanvas().getWidth()-(playTable.getScrollPane().getViewportBounds().getMaxX()-playTable.getScrollPane().getViewportBounds().getMinX());
			double yBase=playTable.getBasicCanvas().getHeight()-(playTable.getScrollPane().getViewportBounds().getMaxY()-playTable.getScrollPane().getViewportBounds().getMinY());
			double xOffset=(playTable.getScrollPane().getHvalue()/playTable.getScrollPane().getHmax())*xBase;
			double yOffset=(playTable.getScrollPane().getVvalue()/playTable.getScrollPane().getVmax())*yBase;
			Hexagon est = playTable.getMapData().pixelToHex(new Point(xMouse+xOffset,yMouse+yOffset));
			HexData data = playTable.getMapData().getHexData(est);
			
			if (data != null) { 			// controllo se il click avviene fuori dagli esagoni
				this.selectedNode=data.nodo;
				this.selectedHexagon=est;
				Double d= Math.floor(est.getX()+(est.getY()/2));
				this.selectedPoint=new Point(d.intValue() , est.getY());
				this.basicMap.drawMap(est);
				if (Base.class.isAssignableFrom( selectedNode.getClass()) ){
					baseStatsPane.setNodeBase(selectedNode);
				}
			}
		});
	}
	
	/**
	 * 
	 */
	public void drowMappa() {
		basicMap.drawMap();
	}

	/**
	 * Aggiungere un attacco nel pannello delle battaglie
	 * @param titolo
	 * Titolo della battaglia
	 * @param durata
	 * durata della Battaglia
	 */
	public void addAttacco(String titolo,int durata) {
		battleBox.addElement(titolo, durata, ProgressStyle.GREEN_STYLE);
	}
	
	/**
	 * Aggiungere difesa pannello delle battaglie
	 * @param titolo
	 * Titolo della battaglia
	 * @param durata
	 * Durata della Battaglia
	 */
	public void addDifesa(String titolo,int durata) {
		battleBox.addElement(titolo, durata, ProgressStyle.RED_STYLE);
	}
	
	/**
	 * Aggiungere potenziamento risorsa in corso
	 * nel pannello dedicato ai potenziamento
	 * @param titolo
	 * Titolo della battaglia
	 * @param durata
	 * Durata del potenziamento
	 */
	public void addPtenziamentoRisorsa(String titolo,int durata) {
		poweUpBox.addElement(titolo, durata, ProgressStyle.ORANGE_STYLE);
	}
	
	/**
	 *Aggiungere sviluppo Software in corso
	 * nel pannello dedicato agli sviluppi
	 * @param titolo
	 * Titolo della battaglia
	 * @param durata
	 * durata della produzione 
	 */
	public void addProduzioneSoftware(String titolo,int durata) {
		poweUpBox.addElement(titolo, durata, ProgressStyle.YELLO_STYLE);
	}

	public Hexagon getSelectedHexagon() {
		return selectedHexagon;
	}

	public void setSelectedHexagon(Hexagon selectedHexagon) {
		this.selectedHexagon = selectedHexagon;
	}

	public Nodo getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(Nodo selectedNode) {
		this.selectedNode = selectedNode;
	}

	public Pair<Integer, Integer> getDimensioni() {
		return dimensioni;
	}

	public void setDimensioni(Pair<Integer, Integer> dimensioni) {
		this.dimensioni = dimensioni;
	}

	public BasicMap getBasicMap() {
		return basicMap;
	}

	public void setBasicMap(BasicMap basicMap) {
		this.basicMap = basicMap;
	}
	
	public void setFineObserver(FinePartitaObserver fineObserver) {};

	public Point getSelectedPoint() {
		return selectedPoint;
	}

	public void setSelectedPoint(Point selectedPoint) {
		this.selectedPoint = selectedPoint;
	}

	public ProgressBarConteiner getBattleBox() {
		return battleBox;
	}

	public void setBattleBox(ProgressBarConteiner batteBox) {
		this.battleBox = batteBox;
	}

	public ProgressBarConteiner getPoweUpBox() {
		return poweUpBox;
	}

	public void setPoweUpBox(ProgressBarConteiner poweUpBox) {
		this.poweUpBox = poweUpBox;
	}

	public PlayTable getPlayTable() {
		return playTable;
	}

	public void setPlayTable(PlayTable playTable) {
		this.playTable = playTable;
	}

	public SDMenuBar getMenuBar() {
		return menuBar;
	}

	public void setMenuBar(SDMenuBar menuBar) {
		this.menuBar = menuBar;
	}


	private void basicMapMaker(MapData mappa,GraphicsContext contestoGrafico) {
		 basicMap = new BasicMap(mappa, contestoGrafico);
	}

	private GridPane putPaneInAscrollableGridPane(Pane node,Background b,Background bPane) {
		GridPane gp=new GridPane();
		gp.setMinHeight(190);
		gp.setBackground(b);
		gp.setAlignment(Pos.TOP_CENTER);
		gp.add(node, 1, 1);
		node.setBackground(bPane);
		return gp;
	}

	public RedirectConsoleOutput getRedirectConsoleOutput() {
		return redirectConsoleOutput;
	}

	public void setRedirectConsoleOutput(RedirectConsoleOutput redirectConsoleOutput) {
		this.redirectConsoleOutput = redirectConsoleOutput;
	}

	public BaseStatsPane getBaseStatsPane() {
		return baseStatsPane;
	}

	public void setBaseStatsPane(BaseStatsPane baseStatsPane) {
		this.baseStatsPane = baseStatsPane;
	}
	
	public abstract void riprendiBot();
	
}
