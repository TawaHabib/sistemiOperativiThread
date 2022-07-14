package so.model.mappa;

import java.util.Objects;

public class Coordinate {
	private int x;
	private int y;
	private String nome;


	public Coordinate(int x, int y) {
		this.x=x;
		this.y=y;
	}

	public Coordinate(int x, int y, String nome) {
		this.x=x;
		this.y=y;
		this.nome= nome;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coordinate other = (Coordinate) obj;
		return x == other.x && y == other.y;
	}
	
	
}
