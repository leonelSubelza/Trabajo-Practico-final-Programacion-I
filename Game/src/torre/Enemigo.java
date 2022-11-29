package torre;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class Enemigo {
	private double x;
	private double y;
	private int ancho;
	private int alto;
	private Image imagenMuerte;
	private Image imagenVivo;
	private Image imagenCongelado;
	private Image imagenCongeladoAlAplastarAUnCompanero;
	private Image imagenCongeladoAlAplastarADosCompanero;
	private Image imagenCongeladoAlAplastarATresCompanero;

	private double factorDesplazamiento;
	private boolean derecha;
	private boolean congelado;
	private boolean pateado;
	private boolean vivo = true;
	private int tiempoCongelado;
	private int tiempoDeCongeladoActual;
	private int tiempoDibujoDeMuerte = 0;
	private int gravedad = 5;
	private int cantidadDeCompanerosQueChoco = 0;
	private static final double VELOCIDAD_DE_MOVIMIENTO_AL_SER_PATEADO = 10;
	private static final int CANTIDAD_DE_TICKS_QUE_DURA_LA_MUERTE = 30;
	private static final int CANTIDAD_DE_IMAGENES_DE_CHOQUES = 3;
	private int puntos;

	public Enemigo(double x, double y, int ancho, int alto, double f,
			boolean der, int tiempoCongelado) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.factorDesplazamiento = f;
		// this.impulso = 0;
		this.derecha = der;
		this.congelado = false;
		this.tiempoCongelado = tiempoCongelado;
		this.tiempoDeCongeladoActual = 0;

		pateado = false;
		this.cantidadDeCompanerosQueChoco = 0;

		imagenMuerte = Herramientas.cargarImagen("explosion.gif");
		imagenVivo = Herramientas.cargarImagen("malo.gif");
		imagenCongelado = Herramientas.cargarImagen("maloCongelado.gif");
		imagenCongeladoAlAplastarAUnCompanero = Herramientas
				.cargarImagen("choco1.jpg");
		imagenCongeladoAlAplastarADosCompanero = Herramientas
				.cargarImagen("choco2.jpg");
		;
		imagenCongeladoAlAplastarATresCompanero = Herramientas
				.cargarImagen("choco3.jpg");
		;

		this.puntos = 100;
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
		// PRIMERO DIBUJO SU MUERTE
		if (!vivo) {
			if (tiempoDibujoDeMuerte <= CANTIDAD_DE_TICKS_QUE_DURA_LA_MUERTE) {
				e.dibujarImagen(imagenMuerte, x, y, 0, 1);
				tiempoDibujoDeMuerte++;
			}
			return;
		}
		// SEGUNDO, SI NO ESTA MUERTO, PREGUNTO SI NO ESTA CONGELADO
		if (!congelado) {
			e.dibujarImagen(imagenVivo, x, y, 0, 0.1);
			return;
		}

		// TERCERO, SI LLEGO HASTA ACA, ES PORQUE ESTA VIVO PERO CONGELADO,
		// PREGUNTO SI FUE PATEADO
		if (!pateado) {
			e.dibujarImagen(imagenCongelado, x, y, x, 0.1);
			if (tiempoDeCongeladoActual == tiempoCongelado && !pateado) {
				congelado = false;
				pateado = false;
				tiempoDeCongeladoActual = 0;
			} else {
				tiempoDeCongeladoActual = tiempoDeCongeladoActual + 1;
			}
			return;
		}

		// FUE PATEADO
		if (cantidadDeCompanerosQueChoco == 0) {
			e.dibujarImagen(imagenCongelado, x, y, x, 0.1);
			return;
		}
		if (cantidadDeCompanerosQueChoco == 1) {
			e.dibujarImagen(imagenCongeladoAlAplastarAUnCompanero, x, y, x, 1.0);
			return;
		}

		if (cantidadDeCompanerosQueChoco == 2) {
			e.dibujarImagen(imagenCongeladoAlAplastarADosCompanero, x, y, x,
					1.0);
			return;
		}

		e.dibujarImagen(imagenCongeladoAlAplastarATresCompanero, x, y, x, 1.0);
	}

	public void moverIzquierda() {
		x -= factorDesplazamiento;
	}

	public void moverDerecha() {
		x += factorDesplazamiento;
	}

	public void moverDerechaSiendoPateado() {
		x += VELOCIDAD_DE_MOVIMIENTO_AL_SER_PATEADO;
	}

	public void moverIzquierdaSiendoPateado() {
		x -= VELOCIDAD_DE_MOVIMIENTO_AL_SER_PATEADO;
	}

	public void abajo() {
		y += factorDesplazamiento;
	}

	public void caer() {
		y += (pateado ? VELOCIDAD_DE_MOVIMIENTO_AL_SER_PATEADO : gravedad);
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

	public boolean estaVivo() {
		return vivo;
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

	public boolean chocoBala(Bala[] balas) {
		for (int x = 0; x < balas.length; x++) {
			if (balas[x] != null
					&& balas[x].getX() <= this.x + this.ancho / 2
					&& balas[x].getX() >= this.x - this.ancho / 2
					&& (balas[x].getY() >= y - alto / 2 && balas[x].getY() <= y
							+ alto / 2)) {
				return true;
			}
		}
		return false;
	}

	public int indiceDeBalaQueChoco(Bala[] balas) {
		for (int x = 0; x < balas.length; x++) {
			if (balas[x] != null
					&& balas[x].getX() <= this.x + this.ancho / 2
					&& balas[x].getX() >= this.x - this.ancho / 2
					&& (balas[x].getY() >= y - alto / 2 && balas[x].getY() <= y
							+ alto / 2)) {
				return x;
			}
		}
		return 0;
	}

	public boolean estaCongelado() {
		return congelado;
	}

	public void congelar() {
		congelado = true;
		tiempoDeCongeladoActual = 1;
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

	public boolean estaPateado() {
		return pateado;
	}

	public void fuePateado() {
		pateado = congelado;
	}

	public void noPateado() {
		pateado = false;
	}

	public boolean chocoAlHeroe(Heroe h) {
		if ((x >= h.getX() - h.getAncho() / 2)
				&& (x <= h.getX() + h.getAncho() / 2)
				&& (y <= h.getY() + h.getAlto() / 2)
				&& (y >= h.getY() - h.getAlto() / 2)) {
			return true;
		}
		return false;
	}

	public int getPuntosNormales() {
		return puntos;
	}

	public int getPuntosPorSerGolpeadoPorCompaneroCongelado() {
		return puntos * 3 / 2;
	}

	public void morir() {
		vivo = false;
	}

	public boolean fueChocadoPorUnEnemigoCongeladoYPateado(Enemigo[] enemigos) {
		for (Enemigo h : enemigos) {
			if (this.estaVivo() && h.estaVivo() && h.estaPateado()
					&& (x >= h.getX() - h.getAncho() / 2)
					&& (x <= h.getX() + h.getAncho() / 2)
					&& (y <= h.getY() + h.getAlto() / 2)
					&& (y >= h.getY() - h.getAlto() / 2) && !this.estaPateado()) {
				return true;
			}
		}
		return false;
	}

	public int IndiceDeCompaneroCongeladoYPateadoQueToco(Enemigo[] enemigos) {
		int aux = 0;
		for (Enemigo h : enemigos) {
			if (h.estaPateado() && (x >= h.getX() - h.getAncho() / 2)
					&& (x <= h.getX() + h.getAncho() / 2)
					&& (y <= h.getY() + h.getAlto() / 2)
					&& (y >= h.getY() - h.getAlto() / 2) && !this.estaPateado())
				// x != h.getX() && y != h.getY()
				return aux;
			aux++;
		}
		return 0;
	}

	public void dejarPowerUp(GrupoDePowerUps g) {
		g.agregarPowerUpEnPosicion(this.x, this.y);
	}

	public void estandoCongeladochocoCompanero() {
		if (cantidadDeCompanerosQueChoco < CANTIDAD_DE_IMAGENES_DE_CHOQUES) {
			cantidadDeCompanerosQueChoco++;
		}
	}
}
