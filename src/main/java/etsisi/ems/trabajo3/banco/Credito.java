package etsisi.ems.trabajo3.banco;

import java.util.Vector;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Credito extends Tarjeta{
	protected double mCredito;
	protected Vector<Movimiento> mMovimientos;
	public String mNombreEntidad;
	public int mCCV;
	public int mMarcaInternacional; // mastercard, maestro, visa ...
	public int mTipo; // oro platino clásica
    public final int comisionMinima = 3;        
    private static final Map<MarcaInternacional,Double> COMISIONES = new HashMap<MarcaInternacional,Double>();
    static{
    	COMISIONES.put(MarcaInternacional.MasterCard,0.05);
    	COMISIONES.put(MarcaInternacional.Maestro,0.05);
    	COMISIONES.put(MarcaInternacional.VisaClasica,0.03);
    	COMISIONES.put(MarcaInternacional.VisaElectron,0.02);
    }
    private static final Map<TipoTarjeta,Integer> LIMITECREDITO = new HashMap<TipoTarjeta,Integer>();
    static {
    	LIMITECREDITO.put(TipoTarjeta.oro, 1000);
    	LIMITECREDITO.put(TipoTarjeta.platino, 800);
    	LIMITECREDITO.put(TipoTarjeta.clasica, 600);
    }

	public Credito(String numero, String titular, LocalDate fechacaducidad, double credito, int marcainternacional,
			String nombreentidad, int ccv) {
		super(titular, fechacaducidad, numero);
		mCredito = credito;
		mMovimientos = new Vector<Movimiento>();
		mMarcaInternacional = marcainternacional;
		mNombreEntidad = nombreentidad;
		mCCV = ccv;
	}

	
	//Revisar que constructor es mas adecuado
	public Credito(String numero, String titular, LocalDate fechacaducidad, int tipo, int marcainternacional,
			String nombreentidad, int ccv) {
		super(titular, fechacaducidad, numero);
		mTipo = tipo;
		mCredito = calcularCredito(mTipo);
		mMovimientos = new Vector<Movimiento>();
		mMarcaInternacional = marcainternacional;
		mNombreEntidad = nombreentidad;
		mCCV = ccv;
	}

	public double calcularCredito(int tipo) {
		Integer credito;
		
		credito = LIMITECREDITO.get(mTipo);
		if(credito == null) {
			credito = 600;
		}
		return credito.doubleValue();
	}

	public void retirar(double importe) throws IllegalArgumentException {
		Double comisiontarifa;
                
                comisiontarifa = COMISIONES.get(mMarcaInternacional);
                if(comisiontarifa == null){
                    comisiontarifa = 0.05;
                }
                
		// Añadimos una comisión de un 5% o 3% o 2%, mínimo de 3 euros.
		double comision = (importe * comisiontarifa < comisionMinima ? comisionMinima : importe * comisiontarifa);
		if (importe > getCreditoDisponible())
			throw new IllegalArgumentException("Crédito insuficiente");
		Movimiento movimiento = new Movimiento("Retirada en cuenta asociada (cajero automático)", (importe + comision));
		mMovimientos.addElement(movimiento);
	}

	// traspaso tarjeta a cuenta
	public void ingresar(double importe) throws IllegalArgumentException {
		double comision = (importe * 0.05 < comisionMinima ? comisionMinima : importe * 0.05); // Añadimos una comisión de un 5%, mínimo de 3 euros.
		if (importe > getCreditoDisponible())
			throw new IllegalArgumentException("Crédito insuficiente");
		Movimiento movimiento = new Movimiento("Traspaso desde tarjeta a cuenta", importe);
		mMovimientos.addElement(movimiento);

		mCuentaAsociada.ingresar("Traspaso desde tarjeta a cuenta", importe);
		mCuentaAsociada.retirar("Comision Traspaso desde tarjeta a cuenta", comision);
	}

	public void pagoEnEstablecimiento(String datos, double importe) throws Exception {
		Movimiento movimiento = new Movimiento(("Compra a credito en : " + datos) , importe);
		mMovimientos.addElement(movimiento);
	}

	public double getSaldo() {
		double saldo = 0.0;
		for (int i = 0; i < this.mMovimientos.size(); i++) {
			Movimiento movimiento = (Movimiento) mMovimientos.elementAt(i);
			saldo += movimiento.getImporte();
		}
		return saldo;
	}

	public double getCreditoDisponible() {
		return mCredito - getSaldo();
	}

	private double obtenerImporteALiquidar(int mes, int anyo) {
		double resultado = 0.0;
		Iterator <Movimiento> iterador = mMovimientos.iterator();
		while(iterador.hasNext()) {
			Movimiento movimiento = iterador.next();
			if (movimiento.getFecha().getMonthValue() == mes && movimiento.getFecha().getYear() == anyo
					&& !movimiento.isLiquidado())
				resultado += movimiento.getImporte();
			movimiento.setLiquidado(true);
		}
		return resultado;

	}

	public void liquidar(int mes, int anyo) throws IllegalArgumentException {
		double importe = obtenerImporteALiquidar(mes, anyo);
		if (importe != 0) {
			String concepto = "Liquidación de operaciones tarj. crédito, " + mes + " de " + anyo;
			Movimiento liquidacion = new Movimiento(concepto, -importe);
			mCuentaAsociada.addMovimiento(liquidacion);
		}
	}

	// liquidación parcial sobre el total de los gastos realizados con esa tarjeta
	// durante el mes/año de liquidación que consiste en lo siguiente:
	// los gastos totales, incluida una comisión de 12%, se dividen en 3 cuotas a
	// pagar en los 3 meses siguientes
	public void liquidarPlazos(int mes, int anyo) throws IllegalArgumentException {
		double importe = obtenerImporteALiquidar(mes, anyo) * 1.12;
		if (importe != 0) {
			for (int i = 0; i < 3; i++) {
				if(mes==12) {
					mes=1;
					anyo++;
				}else 
					mes++;
				String concepto = "Liquidación de operaciones tarj. crédito, " + mes + " de " + anyo;
				Movimiento liquidacion = new Movimiento(concepto, -importe);
				mCuentaAsociada.addMovimiento(liquidacion);
			}
		}
	}
}