package torre;

import java.awt.Color;

import entorno.Entorno;
import entorno.Herramientas;
import torre.Viga;
import java.awt.Image;

public class Bala {
	private double x;
	private double y;
	private double velocidad;
	private boolean derecha;
	private double angulo;
	private Image imagenbala = Herramientas.cargarImagen("iceball.gif");

	public Bala(double x, double y, boolean derecha) {
		this.x = x;
		this.y = y;
		this.derecha = derecha;
		this.velocidad = 8;
		this.angulo = 0;

	}

	public void balaEspecial() {
		this.angulo = 5;
	}

	public void balaEspecialAbajo() {
		this.angulo = -7;
	}

	public void balaArriba() {
		this.velocidad = 0;
		this.angulo = 7;
	}

	public void balaAbajo() {
		this.velocidad = 0;
		this.angulo = -7;
	}

	public void avanzar() {
		x += (derecha ? velocidad : -velocidad);
		y -= angulo;
	}

	public void dibujar(Entorno entorno) {
		entorno.dibujarImagen(imagenbala, x, y, 0, 0.25);
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public boolean chocoIzquierda(Entorno e) {
		return x <= 0;
	}

	public boolean chocoDerecha(Entorno e) {
		return x >= e.ancho();
	}
}
