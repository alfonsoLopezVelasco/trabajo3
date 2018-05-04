package etsisi.ems.trabajo3.banco;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public abstract class Tarjeta {
	protected Cuenta mCuentaAsociada;
	private String mNumero;
	private String mTitular;
	private LocalDate mFechaDeCaducidad;
	
	public Tarjeta(String titular, LocalDate mFechaDeCaducidad, String mNumero) {
		this.mTitular = titular;
		this.mFechaDeCaducidad = mFechaDeCaducidad;
		this.mNumero = mNumero;
	}
	
	public void setCuenta(Cuenta c) {
		this.mCuentaAsociada = c;
	}
	
	public abstract void retirar(double importe) throws Exception;
	
	public abstract void ingresar(double importe) throws Exception;
	
	public abstract void pagoEnEstablecimiento(String datos, double importe) throws Exception;
	
	public abstract double getSaldo();		
	
}