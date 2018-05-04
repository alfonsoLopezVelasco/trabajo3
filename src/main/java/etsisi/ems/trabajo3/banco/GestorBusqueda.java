package etsisi.ems.trabajo3.banco;

import java.util.Iterator;
import java.util.Vector;

public class GestorBusqueda {

	
	public static Vector<Movimiento> buscarMovimientos(Vector<Movimiento> movimientos, int mes, int anyo) {
		Vector<Movimiento> movimientosFiltrados = new Vector<Movimiento>();
		Iterator <Movimiento> iterador = movimientos.iterator();
		while(iterador.hasNext()) {
			Movimiento movimiento = iterador.next();
			if (movimiento.getFecha().getMonthValue() == mes && movimiento.getFecha().getYear() == anyo
					&& !movimiento.isLiquidado())
				movimientosFiltrados.add(movimiento);
		}
		return movimientosFiltrados;
		
	}
}
