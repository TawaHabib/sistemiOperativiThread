package so.view.partita.pane;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class TextBox {
	
	private ScrollPane sp = new ScrollPane();
	private VBox text = new VBox();
	private ArrayList<Log> logs = new ArrayList<Log>();
	
	public TextBox() {
		sp.setContent(text);
				
	}

	public synchronized void insertText(String text) {
		Platform.runLater(() -> {
		logs.add(0, new Log(text));  //il nuovo testo è inserito nella posizione 0, quindi è sempre il primo in alto
		this.text.getChildren().clear();  //pulizia di sicurezza, non è necessaria
		for (Log l : logs) {
			Label inserted = new Label();
			inserted.setAlignment(Pos.CENTER);
			inserted.setTextAlignment(TextAlignment.CENTER);
			inserted.setPadding(new Insets(10,10,10,10));
			inserted.setText(l.displayText());
			this.text.getChildren().add(inserted);
			this.text.getChildren().add(new Separator());
		}
		});
	}
	
	public void generateFile() {
		@SuppressWarnings("unused")
		String desktop = System.getProperty("user.home");
		File f = new File("SDlog.txt");
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(f));
			for (Log l : logs) {
				writer.write(l.displayText() + "\n");
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private class Log {
		public String text;
		public Date logDate = new Date();
		
		public Log(String text) {
			this.text = text;
			
		}
		
		public String displayText() {
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			return format.format(logDate) + " " + text;
		}
	}

	public ScrollPane getSp() {
		return sp;
	}

	public void setSp(ScrollPane sp) {
		this.sp = sp;
	}
	

}
