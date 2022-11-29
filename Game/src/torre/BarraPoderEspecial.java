package torre;

import java.awt.Color;

import entorno.Entorno;
import entorno.Herramientas;
import java.awt.Image;

public class BarraPoderEspecial {

	private double x;
	private double y;
	private int puntos;

	public BarraPoderEspecial(double x, double y, int puntos) {
		this.x = x;
		this.y = y;
		this.puntos = puntos;
	}

	public void dibujar(Entorno e) {
		e.dibujarRectangulo(x, y, 100, 20, 0, Color.BLACK);
		if (puntos == 100) {
			e.dibujarRectangulo(x, y, puntos, 20, 0, Color.GREEN);
		} else {
			e.dibujarRectangulo(x, y, puntos, 20, 0, Color.RED);
		}
	}

	public int getPuntos() {
		return this.puntos;
	}

	public boolean puedoDisparar() {
		return puntos == 100;
	}

	public void yaDisparo() {
		puntos = 0;
	}

	public void sumarPuntos(int puntos) {
		this.puntos = this.puntos + puntos;
		if (this.puntos > 100) {
			this.puntos = 100;
		}
	}

}