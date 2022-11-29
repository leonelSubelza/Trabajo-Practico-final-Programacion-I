package torre;

import java.awt.Color;
import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;
import torre.Viga;
import torre.Bala;
import entorno.Herramientas;
import torre.PowerUp;

public class Heroe {
	private static final double TICS_NECESARIOS_PARA_DETERMINAR_SI_CAMBIAR_AUTOMATICO = 12;

	private double x;
	private double y;
	private int ancho;
	private int alto;

	private double factorDesplazamiento;
	// private double impulso;
	// private double limiteDeSalto;
	private boolean derecha;
	private int gravedad = 5;
	private Image imagenDerecha = Herramientas.cargarImagen("MagoDeHielo.gif");
	private Image imagenIzquierda = Herramientas
			.cargarImagen("MagoDeHieloIzquierda.gif");
	private Image imagenMuerto = Herramientas
			.cargarImagen("MagoDeHieloEstadoSuspencion.jpg");
	private Image imagenDeCorazon = Herramientas.cargarImagen("heart.gif");
	private int vidas;
	private boolean vivo;
	private boolean cayendo = false;

	public Heroe(double x, double y, int ancho, int alto, double f,
			boolean der, int tiempoCongelado) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.factorDesplazamiento = f;
		this.derecha = der;
		this.vidas = 5;
		vivo = true;
		// this.impulso = 0;
		// this.limiteDeSalto = 16;
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

	public void dibujar(Entorno e) {
		if (vivo)
			e.dibujarImagen(derecha ? imagenDerecha : imagenIzquierda, x, y, 0,
					0.3);

		else {
			if (vidas > 0) {
				e.dibujarImagen(imagenMuerto, x, y, 0, 1.0);
				e.cambiarFont("Arial", 18, Color.GREEN);
				e.escribirTexto("Presiona r para revivir ... ", e.ancho() / 2,
						e.alto() * 11 / 12);
			}
		}
		for (int x = 0; x < vidas; x++) {
			e.dibujarImagen(imagenDeCorazon, e.ancho() * 1 / 4 + x * 16,
					e.alto() * 9 / 10, 0, 0.25);
		}
	}

	public void moverIzquierda(Entorno e) {
		x -= factorDesplazamiento;
	}

	public void moverDerecha(Entorno e) {
		x += factorDesplazamiento;
	}

	public void caer() {
		// y += factorDesplazamiento+impulso*3/2;
		if (vivo) {
			y += gravedad;
			cayendo = true;
		}
	}

	public boolean estaCayendo() {
		return cayendo;
	}

	public boolean aterrizaSobreViga(boolean sobre) {
		if (sobre && cayendo) {
			cayendo = false;
			return true;
		}
		return false;
	}

	public boolean llegoFondo(Entorno e) {
		return y > e.alto();
	}

	public void reSpawn() {
		y = 0;
	}

	public boolean chocoIzquierda(Entorno e) {
		return x - ancho / 2 <= 0;
	}

	public boolean chocoDerecha(Entorno e) {
		return x + ancho / 2 >= e.ancho();
	}

	public boolean getDerecha() {
		return derecha;
	}

	public void cambiarDireccion() {
		derecha = !derecha;
	}

	public void mirarIzquierda() {
		this.derecha = false;
	}

	public void mirarDerecha() {
		this.derecha = true;
	}

	public Bala dispararEspecial() {
		Bala aux = new Bala(x, y, derecha);
		aux.balaEspecial();
		return aux;
	}

	public Bala dispararEspecialHaciaAbajo() {
		Bala aux = new Bala(x, y, derecha);
		aux.balaEspecialAbajo();
		return aux;
	}

	public Bala dispararArriba() {
		Bala aux = new Bala(x, y, derecha);
		aux.balaArriba();
		return aux;
	}

	public Bala dispararAbajo() {
		Bala aux = new Bala(x, y, derecha);
		aux.balaAbajo();
		return aux;
	}

	public Bala disparar() {
		return new Bala(this.x, this.y, this.derecha);
	}

	public boolean agarroPowerUp(PowerUp p) {
		return (p.getX() <= this.x + this.ancho / 2 && p.getX() >= this.x
				- this.ancho / 2)
				&& (p.getY() >= y - alto / 2 && p.getY() <= y + alto / 2);
	}

	public boolean estaSobreAlgunaViga(Viga[] vigas) {
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

	public boolean chocoAlgunEnemigoNoCongelado(Enemigo[] enemigos) {
		for (Enemigo e : enemigos) {
			if ((x + ancho / 3 >= e.getX() - e.getAncho() / 2)
					&& (x - ancho / 3 <= e.getX() + e.getAncho() / 2)
					&& (y + alto / 3 <= e.getY() + e.getAlto() / 2)
					&& (y + alto / 3 >= e.getY() - e.getAlto() / 2)
					&& !e.estaCongelado() && e.estaVivo()) {
				return true;
			}
		}
		return false;
	}

	public boolean tengoVida() {
		return (vidas > 0);
	}

	public boolean estaVivo() {
		return vivo;
	}

	public void morir(Entorno e) {
		if (vivo) {
			vidas -= 1;
			x = e.ancho() / 2;
			y = 5 * (e.alto() / 6);
			vivo = false;
		}
	}

	public void revivir(Entorno e) {
		x = e.ancho() / 2;
		y = 5 * (e.alto() / 6);
		vivo = true;
	}
}
