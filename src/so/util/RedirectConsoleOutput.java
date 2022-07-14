package so.util;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import so.view.partita.pane.TextBox;

public class RedirectConsoleOutput  extends PrintStream{
	private TextBox textBox;
	
	public RedirectConsoleOutput(String fileName) throws FileNotFoundException {
		super(fileName);
		textBox=new TextBox();
	}
	
	
	@Override
	public void println(String x) {
		super.println(x);
		textBox.insertText(x);
	}
	
	public TextBox getTextBox() {
		return textBox;
	}

	public void setTextBox(TextBox textBox) {
		this.textBox = textBox;
	}

	

}
