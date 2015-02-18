/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.Months;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.joda.time.format.DateTimeParser;

/**
 *
 * @author f7057419
 */
public class JodaTimeLocalDateExamples {

    public static void main(String[] args) {

        List<Integer> numeros = new ArrayList<>();

 //Algortimo números primos           
        for (int i = 0; i <= 71; i++) {
            numeros.add(i);
            System.out.println(i);
        }

        int contador;
        int numero = 0;
        for (Integer i : numeros) {
            contador = 0;
            System.out.println(i);
            System.out.println(numero);

            for (int u = 1; u <= i; u++) {
                System.out.println(i);
                System.out.println(u);
                if (i % u == 0) {
                    contador++;
                    numero = u;
                    System.out.println(contador);
                    System.out.println(u);
                }
            }
            if (contador == 2) {
                System.out.println("O Número: " + numero + " é primo e seus primos são: 1 e " + numero);
            }
        }

//---------------
        System.out.printf("Joda-Time - Classe LocalDate - Exemplos\n\n");
		// Classe LocalDate - Representa uma data 
        // sem informação de hora e nem TimeZone.
        // Perfeito para guardar data de nascimento ou casamento.
        // o construtor sem parametros retorna a data de hoje:
        LocalDate hoje = new LocalDate();
        LocalDate seg = new LocalDate(2004, 02, 17);
        LocalDate ter = new LocalDate(2004, 02, 18);
        LocalDate qua = new LocalDate(2004, 02, 19);
        LocalDate qui = new LocalDate(2004, 02, 20);
        LocalDate sext = new LocalDate(2004, 02, 21);
        LocalDate sad = new LocalDate(2004, 02, 22);
        LocalDate dom = new LocalDate(2004, 02, 23);
        System.out.printf("Hoje = %s\n", seg.getDayOfWeek());
        System.out.printf("Hoje = %s\n", ter.getDayOfWeek());
        System.out.printf("Hoje = %s\n", qua.getDayOfWeek());
        System.out.printf("Hoje = %s\n", qui.getDayOfWeek());
        System.out.printf("Hoje = %s\n", sext.getDayOfWeek());
        System.out.printf("Hoje = %s\n", sad.getDayOfWeek());
        System.out.printf("Hoje = %s\n", dom.getDayOfWeek());

        hoje.dayOfWeek();
        System.out.printf("Hoje = %s\n", hoje.getDayOfWeek());
        System.out.printf("Hoje = %s\n", hoje.getCenturyOfEra());
        System.out.printf("Hoje = %s\n", hoje.getChronology());
        System.out.printf("Hoje = %s\n", hoje.getEra());
        //  System.out.printf("Hoje = %s\n", hoje.getValue(numero));
        System.out.printf("Hoje = %s\n", hoje.getYearOfCentury());
        System.out.printf("Hoje = %s\n", hoje.getYearOfEra());

        System.out.printf("Hoje = %s\n", hoje);
        System.out.printf("Hoje = %s\n", hoje);

        // Uma data pode ser criada diretamente passando o ano, mes e dia:
        LocalDate dataNascimento = new LocalDate(1976, 01, 12);
        // Mostrando a data em diversos formatos:
        System.out.printf("Nascimento = %s (formato dia/mes/ano)\n",
                dataNascimento.toString("dd/MM/yyyy"));
        System.out.printf("Nascimento = %s (formato Inglês)\n",
                dataNascimento.toString("MMMMM, d/y", Locale.ENGLISH));
        System.out.printf("Nascimento = %s (formato Francês)\n",
                dataNascimento.toString("d/MMMMM/y", Locale.FRENCH));

        Locale pt_BR = new Locale("pt", "BR");
        System.out.printf("Nascimento = %s (formato Português Brasileiro)\n",
                dataNascimento.toString("d/MMMMM/y", pt_BR));

        // Calculando a diferença em anos:
        System.out.printf("Idade = %d anos\n",
                Years.yearsBetween(dataNascimento, hoje).getYears());

        // Calculando a diferença em dias e em Meses:
        System.out.printf("Dias decorridos = %d dias\n",
                Days.daysBetween(dataNascimento, hoje).getDays());
        Days.daysBetween(dataNascimento, hoje);
        System.out.printf("Meses decorridos = %d meses\n",
                Months.monthsBetween(dataNascimento, hoje).getMonths());

        // Para saber o último dia de um mês
        System.out.printf("Último dia deste mês = %d\n",
                hoje.dayOfMonth().getMaximumValue());
        System.out.printf("Último dia deste mês no tipo LocalDate = %s\n",
                hoje.dayOfMonth().withMaximumValue());

        // Convertendo um Date do Java em LocalDate do Joda-Time:
        Date dataJava = new Date();
        LocalDate dataJodaTime = new LocalDate(dataJava);

        System.out.printf("Data em Java = %s\n", dataJava);
        System.out.printf("Data do Joda-Time = %s\n", dataJodaTime);
        // Convertendo uma data em string para LocalDate
        String dataString = "23/01/2013";
        DateTimeFormatter dateFormatter = new DateTimeFormatterBuilder()
                .appendPattern("dd/MM/yyyy").toFormatter();
        LocalDate dataFromStr = LocalDate.parse(dataString, dateFormatter);
        System.out.printf("dataFromStr = %s\n", dataFromStr);
        /* Convertendo de uma String para o LocalDate do Joda-Time:
         * O exemplo abaixo permite tanto que o dia, mes e ano sejam
         * separados por '-' ou '/': 
         */
        DateTimeParser[] parsers = {
            DateTimeFormat.forPattern("dd-MM-yyyy").getParser(),
            DateTimeFormat.forPattern("dd/MM/yyyy").getParser()};
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .append(null, parsers).toFormatter();

        LocalDate data1 = formatter.parseLocalDate("24-02-2013");
        LocalDate data2 = formatter.parseLocalDate("25/03/2013");

        System.out.printf("Data1 = %s\n", data1);
        System.out.printf("Data2 = %s\n", data2);

        LocalDate ent = new LocalDate(2004, 02, 17);
        LocalDate sai = new LocalDate(2004, 02, 20);
        int cont = 0;
        double said;
        said = Double.parseDouble(sai.toString().replace("-", ""));
        double entd;
        entd = Double.parseDouble(ent.toString().replace("-", ""));
        while (entd != said) {
            cont++;
            System.out.println(ent + "  " + sai + "   " + cont);
            System.out.println(entd + "  " + said + "   " + cont);
            ent = ent.plusDays(1);
            entd = Double.parseDouble(ent.toString().replace("-", ""));
        }
    }
}
