package torre;

import java.awt.Color;

import entorno.Entorno;
import entorno.Herramientas;
import java.awt.Image;

public class Viga {
	private double x;
	private double y;
	private int ancho;
	private int alto;
	private Image imagen;

	public Viga(double x, double y, int ancho, int alto, Image imagen) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.imagen = imagen;
	}

	public void dibujar(Entorno e) {
		e.dibujarRectangulo(x, y, ancho, alto, 0, Color.WHITE);
		e.dibujarImagen(imagen, x, y, 0, 1.0);
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public int getAncho() {
		return this.ancho;
	}

	public double getAlto() {
		return this.alto;
	}
}