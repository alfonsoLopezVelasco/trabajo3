package etsisi.ems.trabajo3.banco;

import java.util.Iterator;
import java.util.ArrayList;

public class Cuenta {
	private String mNumero;
	private String mTitular;
	private ArrayList<Movimiento> mMovimientos;
	
	public Cuenta(String numero, String titular) {
		mNumero = numero;
		mTitular = titular;
		mMovimientos = new ArrayList<Movimiento>();
	}

	public String getMNumero() {
		return mNumero;
	}
	
	public void setMNumero(String mNumero) {
		this.mNumero = mNumero;
	}
	
	public String getMTitular() {
		return mTitular;
	}
	
	public void setMTitular(String mTitular) {
		this.mTitular = mTitular;
	}
	
	public ArrayList<Movimiento> getMMovimientos(){
		return this.mMovimientos;
	}
	
	public void setMMovimientos(ArrayList<Movimiento> mMovimientos) {
		this.mMovimientos = mMovimientos;
	}

	public void ingresar(double importe) throws IllegalArgumentException {
		if (importe <= 0) {
			throw new IllegalArgumentException("No se puede ingresar una cantidad negativa");
		}
		Movimiento movimiento = new Movimiento("Ingreso en efectivo", importe);
		this.mMovimientos.add(movimiento);
	}

	public void retirar(double importe) throws IllegalArgumentException {
		if (importe <= 0) {
			throw new IllegalArgumentException("No se puede retirar una cantidad negativa");
		}
		if (getSaldo() < importe) {
			throw new IllegalArgumentException("Saldo insuficiente");
		}

		Movimiento movimiento = new Movimiento("Retirada de efectivo", -importe);
		this.mMovimientos.add(movimiento);
	}

	public void ingresar(String concepto, double importe) throws IllegalArgumentException {
		if (importe <= 0) {
			throw new IllegalArgumentException("No se puede ingresar una cantidad negativa");
		}
		Movimiento movimiento = new Movimiento(concepto, importe);
		this.mMovimientos.add(movimiento);
	}

	public void retirar(String concepto, double importe) throws IllegalArgumentException {
		if (importe <= 0) {
			throw new IllegalArgumentException("No se puede retirar una cantidad negativa");
		}
		if (getSaldo() < importe) {
			throw new IllegalArgumentException("Saldo insuficiente");
		}

		Movimiento movimiento = new Movimiento(concepto, -importe);
		this.mMovimientos.add(movimiento);
	}

	public double getSaldo() {
		double saldo = 0.0;
		
		Iterator<Movimiento> iterator = mMovimientos.iterator();
		while(iterator.hasNext()) {
			saldo += iterator.next().getImporte();
		}
		
		return saldo;
	}

	public void addMovimiento(Movimiento movimiento) {
		mMovimientos.add(movimiento);
	}

	public ArrayList<Movimiento> buscarMovimientos(int mes, int anyo) {
		return GestorBusqueda.buscarMovimientos(mMovimientos, mes, anyo);
	}
}