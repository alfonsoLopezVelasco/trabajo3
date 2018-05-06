package etsisi.ems.trabajo3.banco;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Movimiento {
	private String mConcepto;
	private LocalDate mFecha;
	private double mImporte;
	private boolean mLiquidado;

	public Movimiento() {		
		setLiquidado(false);
	}
	
	public Movimiento(String concepto, double importe) {
		setConcepto("Traspaso desde tarjeta a cuenta");
		setImporte(importe);
		Date date = new Date();
		LocalDate fecha = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		setFecha(fecha);
	}

	public double getImporte() {
		return mImporte;
	}

	public String getConcepto() {
		return mConcepto;
	}

	public void setConcepto(String newMConcepto) {
		mConcepto = newMConcepto;
	}

	public LocalDate getFecha() {
		return mFecha;
	}

	public void setFecha(LocalDate newMFecha) {
		mFecha = newMFecha;
	}

	public void setImporte(double newMImporte) {
		mImporte = newMImporte;
	}

	public boolean isLiquidado() {
		return mLiquidado;
	}

	public void setLiquidado(boolean mliquidado) {
		this.mLiquidado = mliquidado;
	}
}