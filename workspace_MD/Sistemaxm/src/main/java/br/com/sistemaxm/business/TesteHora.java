package br.com.sistemaxm.business;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;

import org.jfree.data.time.Day;
import org.jfree.data.time.Hour;

public class TesteHora {

	public static void main(String[] args) {
	
		/*Calendar calendar = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		calendar.set(calendar.HOUR,calendar.MINUTE);
		calendar2.set(calendar2.HOUR,calendar2.MINUTE);
		LocalTime horaInicio = LocalTime.of(6,0);
		LocalTime horaFim = LocalTime.of(18,0);
		LocalTime jornada = LocalTime.of(7,20);
		LocalTime horaIntervalo = LocalTime.of(4,0);
		LocalTime horaAux = LocalTime.of(horaFim.getHour()-horaInicio.getHour(), horaFim.getMinute()-horaInicio.getMinute());
		LocalTime calculaIntervalo = LocalTime.of(horaAux.getHour()-horaIntervalo.getHour(),horaAux.getMinute()-horaIntervalo.getMinute());
		
		
		if (calculaIntervalo.getHour() > jornada.getHour()) {
			
			Calendar horaExtra = Calendar.getInstance();
			horaExtra.set(Calendar.HOUR,calculaIntervalo.getHour()-jornada.getHour());
			horaExtra.set(Calendar.MINUTE, calculaIntervalo.getMinute()-jornada.getMinute());		
			System.out.println(horaExtra.get(Calendar.HOUR));
			System.out.println(horaExtra.get(Calendar.MINUTE));
		}
			
			
*/		
		Calendar calendar = Calendar.getInstance();
		calendar.getTime().getHours();
		System.out.println(calendar);
	}
		
		
	}


