package torre;

import java.awt.Color;
import entorno.Entorno;

public class Score {
	private int puntos;

	public Score() {
		puntos = 0;
	}

	public void sumarPuntos(int pun) {
		puntos += pun;
	}

	public void dibujar(Entorno e) {
		e.cambiarFont("Arial", 18, Color.WHITE);
		e.escribirTexto("Puntos: 0" + puntos, e.ancho() * 3 / 4,
				e.alto() * 9 / 10);
	}

	public int getScore() {
		return puntos;
	}
}
