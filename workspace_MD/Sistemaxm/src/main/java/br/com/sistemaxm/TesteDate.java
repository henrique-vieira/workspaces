package br.com.sistemaxm;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.parsing.ParseState;

public class TesteDate {
	
	public static void main(String[] args) throws ParseException {
		Calendar c = Calendar.getInstance();
		c.set(c.get(Calendar.YEAR), 2, 2);
		Date dataNascimento;
		DateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy");		
		String d = dataFormatada.format(c.getTime().toString());
		dataNascimento = dataFormatada.parse(d);
		System.out.println(dataNascimento);
		
		
		
		/*String testeSplit = "OCP-1092";
		int i = (testeSplit.charAt(testeSplit.length()-1));
		System.out.println(i);*/
		//int i = this.carro.getPlaca().charAt(this.carro.getPlaca().length()-1);
	}

}
