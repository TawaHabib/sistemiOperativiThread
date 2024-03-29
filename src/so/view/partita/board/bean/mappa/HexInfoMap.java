package so.view.partita.board.bean.mappa;

import java.util.HashMap;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import so.view.partita.board.bean.nodes.HexData;
import so.view.partita.board.bean.nodes.Hexagon;
import so.view.partita.board.bean.util.Point;

public class HexInfoMap extends Map {
	public HexInfoMap(MapData mapData, GraphicsContext gc) {
		super(mapData, gc);
	}

	/**
	 * genera la mappa, il tavolo di gioco con tutti gli esagoni
	 */
	public void drawMap() {
		for (HashMap.Entry<Hexagon, HexData> entry : getMapData().getData().entrySet()) {
			drawHex(entry.getKey(), getMapData().hex_to_pixel(entry.getKey()));
		}
	}

	/**
	 * Metodo per la struttura degli esagoni
	 * @param a
	 * @param center
	 */
	public void drawHex(Hexagon a, Point center) {
		getGc().setFill(Color.BLACK);
		getGc().fillText("" + a.getX(), center.getX() - 14, center.getY() - 6);
		getGc().fillText("" + a.getY(), center.getX() + 6, center.getY() + 5);
		getGc().fillText("" + a.getZ(), center.getX() - 14, center.getY() + 14);
	}
}