package br.com.sistemaxm.entidades;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TesteSimpleDateFormat {

	public static void main(String[] args) throws ParseException {
		// inicioJornadaString = "22:37"
		
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		String data = "22:37";
		Date dataD = new Date(sdf.parse(data).getTime());
		System.out.println(dataD);
	}

}
