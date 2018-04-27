package etsisi.ems.trabajo3.banco;

import java.time.LocalDate;

public class Debito extends Tarjeta{

	public Debito(String numero, String titular, LocalDate fechaCaducidad) {
		super(titular, fechaCaducidad, numero);
	}

	public void retirar(double x) throws Exception {
		this.mCuentaAsociada.retirar("Retirada en cajero automático", x);
	}

	public void ingresar(double x) throws Exception {
		this.mCuentaAsociada.ingresar("Ingreso en cajero automático", x);
	}

	public void pagoEnEstablecimiento(String datos, double x) throws Exception {
		this.mCuentaAsociada.retirar("Compra en :" + datos, x);
	}

	public double getSaldo() {
		return mCuentaAsociada.getSaldo();
	}
}