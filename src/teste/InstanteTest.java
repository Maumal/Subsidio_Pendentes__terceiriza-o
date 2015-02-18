/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 *
 * @author f7057419
 */
public class InstanteTest {

    public static void main(String... args) {
// Configurando a data referência TimeZone timeZone = 
        //  TimeZone.getTimeZone("GMT"); 
        Calendar calendar = new GregorianCalendar(1970, 00, 01, 00, 00, 00);
        calendar.setTimeZone(TimeZone.getTimeZone("GMT"));
       // Deve ser 0 
        System.out.println("Diferenca em milisegundos = " + calendar.getTimeInMillis());
      // Configurando uma hora de diferença 
        calendar.set(Calendar.HOUR_OF_DAY, 1);
     // Deve ser de 1 hora = 3600000 milisegundos 
        System.out.println("Diferenca em milisegundos = " + calendar.getTimeInMillis());
        System.out.println("Diferenca em horas = " + calendar.getTimeInMillis() / 1000 / 60 / 60);
    }
     //Leia mais em: Trabalhando com Joda-Time http://www.devmedia.com.br/trabalhando-com-joda-time/26524#ixzz2vqdJ0zD8
}
