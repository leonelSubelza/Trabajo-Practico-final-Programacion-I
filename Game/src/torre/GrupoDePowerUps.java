package torre;

import torre.PowerUp;
import entorno.Entorno;
import torre.Viga;
import torre.Heroe;

public class GrupoDePowerUps {

	private static final int CANTIDAD_DE_POWER_UPS_QUE_PUEDE_HABER = 20;

	private PowerUp[] powerups;
	private int indiceDePowerUp;

	public GrupoDePowerUps() {
		powerups = new PowerUp[CANTIDAD_DE_POWER_UPS_QUE_PUEDE_HABER];
		indiceDePowerUp = 0;
	}

	public void agregarPowerUpRandom(Entorno e) {
		powerups[indiceDePowerUp] = new PowerUp(e);
		powerups[indiceDePowerUp].establecerImagen();
		indiceDePowerUp++;
		if (indiceDePowerUp == CANTIDAD_DE_POWER_UPS_QUE_PUEDE_HABER)
			indiceDePowerUp = 0;
	}

	public void agregarPowerUpEnPosicion(double x, double y) {
		powerups[indiceDePowerUp] = new PowerUp(null);
		powerups[indiceDePowerUp].establecerImagenPequeno();
		powerups[indiceDePowerUp].setY(y);
		powerups[indiceDePowerUp].setX(x);
		indiceDePowerUp++;
		if (indiceDePowerUp == CANTIDAD_DE_POWER_UPS_QUE_PUEDE_HABER)
			indiceDePowerUp = 0;
	}

	public int getLimiteDePowerUps() {
		return CANTIDAD_DE_POWER_UPS_QUE_PUEDE_HABER;
	}

	public int getPuntosDePowerUp(int i) {
		return powerups[i].getPuntos();
	}

	public PowerUp getPowerUp(int i) {
		return powerups[i];
	}

	public void desaparecerPowerUp(int i) {
		powerups[i] = null;
	}

	public void dibujarPowerUps(Entorno e, int i) {
		if (powerups[i] != null) {
			powerups[i].dibujar(e);
			powerups[i].actualizarTiempo();
		}
	}

	public boolean isPowerUp(int i) {
		return powerups[i] == null;
	}

	public boolean estaPowerUpSobreViga(int i, Viga[] vigas) {
		return powerups[i].estaSobreViga(vigas);
	}

	public void caer(int i) {
		powerups[i].caer();
	}

	public boolean esTiempoDeDesaparecer(int i) {
		return powerups[i].esTiempoDeDesaparecer();
	}
}
