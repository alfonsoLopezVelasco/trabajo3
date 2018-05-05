package etsisi.ems.trabajo3.banco;

import java.time.LocalDate;

public class Debito extends Tarjeta{

	public Debito(String numero, String titular, LocalDate fechaCaducidad) {
		super(titular, fechaCaducidad, numero);
	}

	public void retirar(double importe) throws IllegalArgumentException {
		this.mCuentaAsociada.retirar("Retirada en cajero automático", importe);
	}

	public void ingresar(double importe) throws IllegalArgumentException {
		this.mCuentaAsociada.ingresar("Ingreso en cajero automático", importe);
	}

	public void pagoEnEstablecimiento(String datos, double importe) throws IllegalArgumentException {
		this.mCuentaAsociada.retirar("Compra en :" + datos, importe);
	}

	public double getSaldo() {
		return mCuentaAsociada.getSaldo();
	}
}