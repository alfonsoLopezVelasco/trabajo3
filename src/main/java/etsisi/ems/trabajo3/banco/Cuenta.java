package etsisi.ems.trabajo3.banco;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Vector;

public class Cuenta {
	protected String mNumero;
	protected String mTitular;
	protected Vector<Movimiento> mMovimientos;

	public Cuenta(String numero, String titular) {
		mNumero = numero;
		mTitular = titular;
		mMovimientos = new Vector<Movimiento>();
	}

	public void ingresar(double importe) throws IllegalArgumentException {
		if (importe <= 0)
			throw new IllegalArgumentException("No se puede ingresar una cantidad negativa");
		Movimiento movimiento = new Movimiento("Ingreso en efectivo", importe);
		this.mMovimientos.addElement(movimiento);
	}

	public void retirar(double importe) throws IllegalArgumentException {
		if (importe <= 0)
			throw new IllegalArgumentException("No se puede retirar una cantidad negativa");
		if (getSaldo() < importe)
			throw new IllegalArgumentException("Saldo insuficiente");

		Movimiento movimiento = new Movimiento("Retirada de efectivo", -importe);
		this.mMovimientos.addElement(movimiento);

	}

	public void ingresar(String concepto, double importe) throws IllegalArgumentException {
		if (importe <= 0)
			throw new IllegalArgumentException("No se puede ingresar una cantidad negativa");
		Movimiento movimiento = new Movimiento(concepto, importe);
		this.mMovimientos.addElement(movimiento);
	}

	public void retirar(String concepto, double importe) throws IllegalArgumentException {
		if (importe <= 0)
			throw new IllegalArgumentException("No se puede retirar una cantidad negativa");
		if (getSaldo() < importe)
			throw new IllegalArgumentException("Saldo insuficiente");

		Movimiento movimiento = new Movimiento(concepto, -importe);
		this.mMovimientos.addElement(movimiento);
	}

	public double getSaldo() {
		double saldo = 0.0;
		for (int i = 0; i < this.mMovimientos.size(); i++) {
			Movimiento movimiento = (Movimiento) mMovimientos.elementAt(i);
			saldo += movimiento.getImporte();
		}
		return saldo;
	}

	public void addMovimiento(Movimiento movimiento) {
		mMovimientos.addElement(movimiento);
	}

	public Vector<Movimiento> buscarMovimientos(int mes, int anyo) {
		return GestorBusqueda.buscarMovimientos(mMovimientos, mes, anyo);
	}
}