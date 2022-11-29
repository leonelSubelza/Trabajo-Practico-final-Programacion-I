package torre;

import java.awt.Image;
import java.util.Random;
import entorno.Entorno;
import entorno.Herramientas;

public class PowerUp {
	private double x;
	private double y;
	private int ancho;
	private int alto;
	private int tiempoTotal; // El tiempo que dura durante el juego
	private int tiempoDeVidaActual; // El tiempo que dura durante el juego
	private int gravedad;
	private int puntos;
	private double tamanoEscala;
	private Image imagen;

	public PowerUp(Entorno e) {
		if (e != null) {
			Random r = new Random();
			this.x = 1 + r.nextInt(e.ancho() - 1);
			this.y = 10 + r.nextInt(e.alto() * 3 / 4 - 100);
		}
		this.ancho = 40;
		this.alto = 40;
		this.tiempoTotal = 700;
		this.tiempoDeVidaActual = 0;
		this.gravedad = 5;
		this.puntos = 0;
		this.tamanoEscala = 0;
		this.imagen = null;
	}

	public void establecerImagen() {
		Random r = new Random();
		int numero = r.nextInt(100);
		if (numero >= 0 && numero < 50) {
			this.imagen = Herramientas.cargarImagen("diamante.gif");
			this.puntos = 10;
			tamanoEscala = 0.16;
			return;
		}
		if (numero >= 50 && numero < 80) {
			this.imagen = Herramientas.cargarImagen("durazno.gif");
			this.puntos = 20;
			tamanoEscala = 0.07;
			return;
		}
		if (numero >= 80 && numero < 90) {
			this.imagen = Herramientas.cargarImagen("bananaDance.gif");
			this.puntos = 30;
			tamanoEscala = 0.16;
			return;
		}
		if (numero >= 90 && numero < 97) {
			this.imagen = Herramientas.cargarImagen("moneda.gif");
			this.puntos = 40;
			tamanoEscala = 0.125; // Calculo matematico = 100/40
			return;
		}
		if (numero >= 97 && numero <= 99) {
			this.imagen = Herramientas.cargarImagen("potion.gif");
			this.puntos = 50;
			tamanoEscala = 0.030; // Calculo matematico empleado: 40/1296
			return;
		}
		this.imagen = Herramientas.cargarImagen("hongo.gif");
		this.puntos = 100;
		tamanoEscala = 0.08; // Calculo matematico empleado = 40/500
	}

	public void dibujar(Entorno e) {
		e.dibujarImagen(imagen, x, y, 0, tamanoEscala);
	}

	public void actualizarTiempo() {
		tiempoDeVidaActual += 1;
	}

	public boolean esTiempoDeDesaparecer() {
		return tiempoDeVidaActual == tiempoTotal;
	}

	public void caer() {
		y += gravedad;
	}

	public boolean estaSobreViga(Viga[] vigas) {
		for (int z = 0; z < vigas.length; z++) {
			if ((x + ancho / 2 >= vigas[z].getX() - vigas[z].getAncho() / 2)
					&& (x - +ancho / 2 <= vigas[z].getX() + vigas[z].getAncho()
							/ 2)
					&& (y + alto / 2 <= vigas[z].getY() + vigas[z].getAlto()
							/ 2)
					&& (y + alto / 2 >= vigas[z].getY() - vigas[z].getAlto()
							/ 2)) {
				return true;
			}
		}
		return false;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setX(double x) {
		this.x = x;
	}

	public int getPuntos() {
		return puntos;
	}

	public void establecerImagenPequeno() {
		Random r = new Random();
		int numero = r.nextInt(5);
		if (numero <= 3) {
			this.imagen = Herramientas.cargarImagen("bananaDance.gif");
			this.puntos = 5;
			tamanoEscala = 0.08;
		} else {
			this.imagen = Herramientas.cargarImagen("durazno.gif");
			this.puntos = 10;
			tamanoEscala = 0.06;
		}
		this.ancho = 20;
		this.alto = 20;
	}
}
