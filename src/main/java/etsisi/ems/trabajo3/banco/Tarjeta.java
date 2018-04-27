package etsisi.ems.trabajo3.banco;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public abstract class Tarjeta {
	private Cuenta mCuentaAsociada;
	private String mNumero;
	private String mTitular;
	private LocalDate mFechaDeCaducidad;
	
	public void Tarjeta(String titular, String mFechaDeCaducidad) {
		this.mTitular = titular;
		this.mFechaDeCaducidad = mFechaDeCaducidad;
	}
	
	public void setCuenta(Cuenta c) {
		this.mCuentaAsociada = c;
	}
	
	public abstract void retirar(double importe);
	
	public abstract void ingresar(double importe);
	
	public abstract void pagoEnEstablecimiento(String datos, double importe);
	
	public abstract double getSaldo();		
	
}