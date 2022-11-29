package torre;

import java.awt.Color;

import entorno.Entorno;
import entorno.InterfaceJuego;
import entorno.Herramientas;
import java.awt.Image;
import java.io.File;

public class Juego extends InterfaceJuego {

	private static final int TIEMPO_DE_COSO_POWER_UP = 350;
	private static final int ALTO_ESCENARIO = 500;
	private static final int ANCHO_ESCENARIO = 800;
	private static final int ALTO_DEL_SECTOR_DE_PUNTUACION = 100;
	private Entorno entorno;
	private Viga[] vigas;
	private Heroe jugador;
	private Enemigo[] enemigos;
	private GrupoDeBalas balas;
	private Image fondo;
	private Image fondoGameOver;
	private Image fondoWin;
	private BarraPoderEspecial poderEspecial;
	private int cantidadDeTics;
	private GrupoDePowerUps powerUps; // Literalmente son todos los power UPS
										// que aparecen durante el juego
	private Score puntuacion;
	private int cantidadDeEnemigos;
	private int scoreFinal = 0;

	public Juego() {
		int unAncho = 300;
		int unAlto = 20;
		int tiempoCongelado = 200; 
				
		fondo = Herramientas.cargarImagen("fondo2.gif");
		fondoGameOver = Herramientas.cargarImagen("GAMEOVER.png");
		fondoWin = Herramientas.cargarImagen("GANASTE.png");
		entorno = new Entorno(
				this,
				"Juego Peronista del General Peron y Evita, y de Nestor y Cristina!",
				ANCHO_ESCENARIO, ALTO_DEL_SECTOR_DE_PUNTUACION + ALTO_ESCENARIO);
		Image imagenDeVigas = Herramientas.cargarImagen("bloque.png");
		Viga[] vigas = {
				new Viga(entorno.ancho() / 2, ALTO_ESCENARIO / 5, unAncho,
						unAlto, imagenDeVigas),
				new Viga(0 + unAncho / 2, (ALTO_ESCENARIO / 5) * 2, unAncho,
						unAlto, imagenDeVigas),
				new Viga(entorno.ancho() - unAncho / 2,
						(ALTO_ESCENARIO / 5) * 2, unAncho, unAlto,
						imagenDeVigas),
				new Viga(entorno.ancho() / 2, (ALTO_ESCENARIO / 5) * 3,
						unAncho, unAlto, imagenDeVigas),
				new Viga(0 + unAncho / 2, ALTO_ESCENARIO - unAlto * 1 / 2,
						unAncho, unAlto, imagenDeVigas),
				new Viga(entorno.ancho() - unAncho / 2, ALTO_ESCENARIO - unAlto
						* 1 / 2, unAncho, unAlto, imagenDeVigas),
				new Viga(entorno.ancho() / 2, (ALTO_ESCENARIO / 5) * 4,
						unAncho * 5 / 4, unAlto,
						Herramientas.cargarImagen("bloque1.png")),
				new Viga(0 + unAncho / 2, 0 + unAlto * 1 / 2, unAncho, unAlto,
						imagenDeVigas),
				new Viga(entorno.ancho() - unAncho / 2, 0 + unAlto * 1 / 2,
						unAncho, unAlto, imagenDeVigas) };
		this.vigas = vigas;
		jugador = new Heroe(entorno.ancho() / 2, entorno.alto() / 10, 20, 30,
				3, true, tiempoCongelado);
		Enemigo[] enemigos = {
				new Enemigo(entorno.ancho() - entorno.ancho() / 4,
						2 * (entorno.alto() / 3), 40, 40, 4, true,
						tiempoCongelado),
				new Enemigo(entorno.ancho() / 4, 2 * (entorno.alto() / 3), 40,
						40, 3, false, tiempoCongelado),
				new Enemigo(entorno.ancho() / 2, 2 * (entorno.alto() / 4), 40,
						40, 2, true, tiempoCongelado),
				new Enemigo(entorno.ancho() / 2, 2 * (entorno.alto() / 5), 40,
						40, 2, false, tiempoCongelado), };

		this.enemigos = enemigos;
		cantidadDeEnemigos = enemigos.length;
		balas = new GrupoDeBalas();
		poderEspecial = new BarraPoderEspecial(60, 20, 0);
		cantidadDeTics = 0;
		powerUps = new GrupoDePowerUps();
		puntuacion = new Score();
		entorno.cambiarFont("Arial", 18, Color.WHITE);
		entorno.iniciar();

	}

	public void tick() {

		if (cantidadDeEnemigos > 0) {
			entorno.dibujarImagen(fondo, entorno.ancho() / 2, entorno.alto()
					/ 2 - ALTO_DEL_SECTOR_DE_PUNTUACION, 0, 0.57);
			for (Viga v : vigas) {
				v.dibujar(entorno);
			}
			jugador.dibujar(entorno);
			for (int aux = 0; aux < enemigos.length; aux++) {
				enemigos[aux].dibujar(entorno);
			}
			poderEspecial.dibujar(entorno);
			puntuacion.dibujar(entorno);
			balas.dibujar(entorno);
			// Power up
			cantidadDeTics = cantidadDeTics + 1;
			if (cantidadDeTics == TIEMPO_DE_COSO_POWER_UP) {
				cantidadDeTics = 0;
				powerUps.agregarPowerUpRandom(entorno);
			}

			for (int x = 0; x < powerUps.getLimiteDePowerUps(); x++) {
				if (!powerUps.isPowerUp(x)) {
					powerUps.dibujarPowerUps(entorno, x);
					if (!powerUps.estaPowerUpSobreViga(x, vigas))
						powerUps.caer(x);
					if (jugador.agarroPowerUp(powerUps.getPowerUp(x))
							&& !poderEspecial.puedoDisparar()) {
						// SOLO PUEDO AGARRAR UN POWER UP, SI Y SOLO SI LO TOCO
						// Y NO TENGO LA BARRA LLENA
						puntuacion.sumarPuntos(powerUps.getPuntosDePowerUp(x));
						poderEspecial.sumarPuntos(powerUps
								.getPuntosDePowerUp(x));
						powerUps.desaparecerPowerUp(x);
					}
				}
			}

			if (jugador.chocoAlgunEnemigoNoCongelado(enemigos)) {
				jugador.morir(entorno);
			}
			if (jugador.estaVivo()) {
				if (!jugador.estaSobreAlgunaViga(vigas)) {
					jugador.caer();
				}
				if (entorno.estaPresionada('a')) {
					jugador.mirarIzquierda();
				}
				if (entorno.estaPresionada('d')) {
					jugador.mirarDerecha();
				}
				if (jugador.getDerecha() && !jugador.estaCayendo()) {
					jugador.moverDerecha(entorno);
				}
				if (!jugador.getDerecha() && !jugador.estaCayendo()) {
					jugador.moverIzquierda(entorno);
				}
				if (jugador.chocoIzquierda(entorno) && !jugador.estaCayendo()) {
					jugador.moverDerecha(entorno);
					jugador.mirarDerecha();
				}
				if (jugador.chocoDerecha(entorno) && !jugador.estaCayendo()) {
					jugador.moverIzquierda(entorno);
					jugador.mirarIzquierda();
				}
				// Poder especial
				if (entorno.estaPresionada(entorno.TECLA_ESPACIO)
						&& poderEspecial.puedoDisparar()) {
					balas.habilitarBala(jugador.disparar());
					balas.habilitarBala(jugador.dispararEspecial());
					balas.habilitarBala(jugador.dispararEspecialHaciaAbajo());
					jugador.cambiarDireccion();
					balas.habilitarBala(jugador.disparar());
					balas.habilitarBala(jugador.dispararEspecial());
					balas.habilitarBala(jugador.dispararEspecialHaciaAbajo());
					balas.habilitarBala(jugador.dispararArriba());
					balas.habilitarBala(jugador.dispararAbajo());
					poderEspecial.yaDisparo();
					jugador.cambiarDireccion();
				}
			} else {
				// Esta muerto
				if (entorno.estaPresionada('r') && jugador.tengoVida()) {
					jugador.revivir(entorno);
				}
			}
			if (jugador.llegoFondo(entorno)) {
				jugador.reSpawn();
			}
			if (jugador.aterrizaSobreViga(jugador.estaSobreAlgunaViga(vigas))) {
				if (entorno.estaPresionada('a')) {
					jugador.mirarIzquierda();
				}
				if (entorno.estaPresionada('d')) {
					jugador.mirarDerecha();
				}
				balas.habilitarBala(jugador.disparar());
			}
			// Enemigos
			for (int aux = 0; aux < enemigos.length; aux++) {
				if (enemigos[aux].estaVivo()) {
					// Todo esto se hara si el enemigo esta vivo
					if (enemigos[aux]
							.fueChocadoPorUnEnemigoCongeladoYPateado(enemigos)) {
						puntuacion
								.sumarPuntos(enemigos[aux]
										.getPuntosPorSerGolpeadoPorCompaneroCongelado());
						enemigos[aux].morir();
						cantidadDeEnemigos--;
						enemigos[aux].dejarPowerUp(powerUps);
						enemigos[enemigos[aux]
								.IndiceDeCompaneroCongeladoYPateadoQueToco(enemigos)]
								.estandoCongeladochocoCompanero();
					}
					if (enemigos[aux].chocoDerecha(entorno)
							|| enemigos[aux].chocoIzquierda(entorno)) {
						enemigos[aux].cambiarDireccion();
					}
					if (enemigos[aux].chocoBala(balas.getArregloDeBalas())) {
						enemigos[aux].congelar();
						// poderEspecial.sumarPuntos();
						balas.desaparecerBala(enemigos[aux]
								.indiceDeBalaQueChoco(balas.getArregloDeBalas()));
					}
					if (!enemigos[aux].estaSobreAlgunaViga(vigas)) {
						enemigos[aux].caer();
					}
					if (!enemigos[aux].estaCongelado()
							&& enemigos[aux].estaSobreAlgunaViga(vigas)
							&& enemigos[aux].estaSobreAlgunaViga(vigas)) {
						if (enemigos[aux].getDerecha()) {
							enemigos[aux].moverDerecha();
						} else {
							enemigos[aux].moverIzquierda();
						}
					}
					// Este enemigo esta congelado
					if (enemigos[aux].estaCongelado()
							&& enemigos[aux].chocoAlHeroe(jugador)
							&& !enemigos[aux].estaPateado()
							&& enemigos[aux].estaSobreAlgunaViga(vigas)) {
						puntuacion.sumarPuntos(enemigos[aux]
								.getPuntosNormales());
						enemigos[aux].fuePateado();
						if (jugador.getDerecha() != enemigos[aux].getDerecha()) {
							enemigos[aux].cambiarDireccion();
						}
					}
					if (enemigos[aux].estaPateado()
							&& enemigos[aux].estaSobreAlgunaViga(vigas)) {
						if (enemigos[aux].getDerecha()) {
							enemigos[aux].moverDerechaSiendoPateado();
						} else {
							enemigos[aux].moverIzquierdaSiendoPateado();
						}
					}
					if (enemigos[aux].llegoFondo(entorno))
						if (enemigos[aux].estaPateado()) {
							enemigos[aux].morir();
							cantidadDeEnemigos--;
						} else {
							enemigos[aux].reSpawn();
						}
				} // Fin del if(Si esta vivo)
			} // Fin del ciclo for
		}// Fin enemigos
		if (cantidadDeEnemigos == 0) {
			scoreFinal = puntuacion.getScore();
			entorno.dibujarImagen(fondoWin, entorno.ancho() / 2, entorno.alto()
					/ 2 - ALTO_DEL_SECTOR_DE_PUNTUACION, 0, 0.57);
			entorno.iniciar();
			entorno.cambiarFont("Arial", 18, Color.GREEN);
			entorno.escribirTexto(
					"Matastes a todos los enemigos, felicidades tu puntuacion final es de: "
							+ scoreFinal, entorno.ancho() / 4,
					entorno.alto() * 14 / 15);

		}
		if (!jugador.tengoVida()) {
			scoreFinal = puntuacion.getScore();
			entorno.dibujarImagen(fondoGameOver, entorno.ancho() / 2, entorno.alto()
					/ 2 - ALTO_DEL_SECTOR_DE_PUNTUACION, 0, 0.58);
			entorno.iniciar();
			entorno.cambiarFont("Arial", 18, Color.RED);
			entorno.escribirTexto(
					"PERDISTE jajaja, tu puntuacion final es de: " + scoreFinal,
					entorno.ancho() / 4, entorno.alto() * 14 / 15);

		}
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}

}
