package torre;

import torre.Bala;
import torre.Viga;
import entorno.Entorno;

public class GrupoDeBalas {
	private static final int CANTIDAD_DE_BALAS = 20;
	private Bala[] balas;
	private int numeroDeBala;

	public GrupoDeBalas() {
		balas = new Bala[CANTIDAD_DE_BALAS];
		numeroDeBala = 0;
	}

	public void habilitarBala(Bala b) {
		balas[numeroDeBala] = b;
		numeroDeBala++;
		if (numeroDeBala == CANTIDAD_DE_BALAS) {
			numeroDeBala = 0;
		}
	}

	public void desaparecerBala(int indice) {
		balas[indice] = null;
	}

	public Bala[] getArregloDeBalas() {
		return balas;
	}

	public void dibujar(Entorno e) {
		for (int i = 0; i < balas.length; i++) {
			if (balas[i] != null) {
				balas[i].avanzar();
				balas[i].dibujar(e);
				if (balas[i].chocoDerecha(e) || balas[i].chocoIzquierda(e)) {
					balas[i] = null;
				}
			}

		}
	}

}
