package subsidio_pendentes_no_portal;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
//import javax.swing.JOptionPane;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
//import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;

public class GerarExcelTerceirizacao {

    public void Importar(String arqRetorno) throws FileNotFoundException, IOException {
        //  System.out.println(arqRetorno);

///dentro
Locale locale = new Locale("pt","BR"); 
GregorianCalendar calendar = new GregorianCalendar(); 
SimpleDateFormat formatador = new SimpleDateFormat("dd' de 'MMMMM' de 'yyyy' _ 'HH' 'mm' h'",locale); 

System.out.println(formatador.format(calendar.getTime())); 
      
        String dataHoje = formatador.format(calendar.getTime());
        
        
        
        System.out.println(dataHoje); 
        String Aba = "TERCEIRIZAÇÃO";
        String ArqSaida = "C:/Temp/TERCEIRIZAÇÃO "+dataHoje+".xls";

        String s = "";
        String nome = "";
        String cpf = "";
        String mensagem = "";
        String copiarValor = "";


        String dataCad;
        String NPJ;
        String posiçãoDoBB;
        String adversoPrincipal;
        String depCad;
        String acao;
        String docAnexos;
        String natureza;
        String pendencia;
        String chave;
        String D;



        int row = 0; // controle das linhas da planilha XLS
        // int qtdFunci = 0;

        Double totalConsig = 0.0;
        // Double consig = 0.0;
        // Double valorAnterior = 0.0;
        Double x = 0.0;

        // PLANILHA
        Workbook wb = new HSSFWorkbook();
        Sheet plan1 = wb.createSheet(Aba);
        Row linha;
        Cell celula;
        //  plan1.setColumnWidth((short) 0, (short) (1000));
        //  plan1.autoSizeColumn(row);
    /*    plan1.setColumnWidth((short) 1, (short) (1000)); 
        plan1.setColumnWidth((short) 2, (short) (1000)); 
        plan1.setColumnWidth((short) 3, (short) (1000)); 
        plan1.setColumnWidth((short) 4, (short) (1000)); 
        plan1.setColumnWidth((short) 5, (short) (1000)); 
        plan1.setColumnWidth((short) 6, (short) (1000)); 
        plan1.setColumnWidth((short) 7, (short) (1000)); */

        // FONTE CABECALHO
        Font fontCab = wb.createFont();
        fontCab.setFontHeightInPoints((short) 10);
        fontCab.setFontName("Arial");
        fontCab.setBoldweight((short) 1000);
        fontCab.setColor(IndexedColors.BLACK.getIndex());



        // CABECALHO
        CellStyle cabecalho = wb.createCellStyle();
        cabecalho.setFont(fontCab);
        cabecalho.setFillForegroundColor(IndexedColors.LAVENDER.getIndex());
        cabecalho.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cabecalho.setAlignment(CellStyle.ALIGN_CENTER);
        // ALINHA ESQUERDA
        CellStyle esquerda = wb.createCellStyle();
        esquerda.setAlignment(CellStyle.ALIGN_LEFT);
        // CENTRALIZA
        CellStyle centro = wb.createCellStyle();
        centro.setAlignment(CellStyle.ALIGN_CENTER);
        // ALINHA DIREITA
        CellStyle direita = wb.createCellStyle();
        direita.setAlignment(CellStyle.ALIGN_LEFT);

        // ALINHA DIREITA
        CellStyle maior = wb.createCellStyle();
        maior.setAlignment(CellStyle.ALIGN_JUSTIFY);






        // VErmelho
        Font fontCabV = wb.createFont();
        fontCabV.setColor(IndexedColors.RED.getIndex());
        CellStyle vermelho = wb.createCellStyle();
        vermelho.setFont(fontCabV);
        //vermelho.setFillBackgroundColor(IndexedColors.WHITE.getIndex());
        vermelho.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        vermelho.setFillPattern(CellStyle.SOLID_FOREGROUND);
        vermelho.setAlignment(CellStyle.ALIGN_CENTER);


        /*/ FORMATA VALOR
        CellStyle moeda = wb.createCellStyle();
        DataFormat df = wb.createDataFormat();
        moeda.setDataFormat(df.getFormat("#,##0.00"));*/


        // CABEÇALHO
        linha = plan1.createRow(row);
        for (int i = 0; i < 11; i++) {
            switch (i) {
                case 0:
                    celula = linha.createCell(i);
                    celula.setCellValue("DATA DO CADASTRO");
                    celula.setCellStyle(cabecalho);
                    break;
                case 1:
                    celula = linha.createCell(i);
                    celula.setCellValue("NPJ");
                    celula.setCellStyle(cabecalho);
                    break;
                case 2:
                    celula = linha.createCell(i);
                    celula.setCellValue("POSIÇÃO DO BB");
                    celula.setCellStyle(cabecalho);
                    break;
                case 3:
                    celula = linha.createCell(i);
                    celula.setCellValue("ADVERSO PRINCIPAL");
                    celula.setCellStyle(cabecalho);
                    break;
                case 4:
                    celula = linha.createCell(i);
                    celula.setCellValue("DEPARTAMENTO CADASTRO");
                    celula.setCellStyle(cabecalho);
                    break;
                case 5:
                    celula = linha.createCell(i);
                    celula.setCellValue("AÇÃO");
                    celula.setCellStyle(cabecalho);
                    break;
                case 6:
                    celula = linha.createCell(i);
                    celula.setCellValue("DOC ANEXOS");
                    celula.setCellStyle(cabecalho);
                    break;
                case 7:
                    celula = linha.createCell(i);
                    celula.setCellValue("NATUREZA");
                    celula.setCellStyle(cabecalho);
                    break;
                case 8:
                    celula = linha.createCell(i);
                    celula.setCellValue("PENDÊNCIA");
                    celula.setCellStyle(cabecalho);
                    break;
                case 9:
                    celula = linha.createCell(i);
                    celula.setCellValue("CHAVE");
                    celula.setCellStyle(cabecalho);
                    break;
                    case 10:
                    celula = linha.createCell(i);
                    celula.setCellValue("Dias D");
                    celula.setCellStyle(cabecalho);
                    break;
            } // FIM SWITCH
        } // FIM FOR - CABECALHO



        // ABRE-FECHA ARQUIVOS ==> TRY-WITH-RESOURCES
        try (BufferedReader in = new BufferedReader(new FileReader(arqRetorno))) {

            // LE ARQUIVO TXT
            while ((s = in.readLine()) != null) {

                // VALIDAÇÃO DA LINHA
                try {
                    x = Double.parseDouble(s.substring(0, 1).trim());
                } catch (Exception e) {
                    x = -1.0;
                }


                /*  System.out.println(l[0]);
                System.out.println(l[1]);
                System.out.println(l[2]);
                System.out.println(l[3]);
                System.out.println(l[4]);
                System.out.println(l[5]);     
                System.out.println(l[6]); 
                System.out.println(l[7]); */


                // PROCESSA SOMENTE LINHA VÁLIDA
                if (x >= 0) {

                    // NOME
                    //   nome = l[0];

                    // CPF
                    //   cpf = l[1];
                    // RETIRA OS PONTOS E TRAÇO DO CPF
                    //cpf = cpf.replace(".", "");
                    //cpf = cpf.replace("-", "");
                    // COLOCA PONTOS E TRAÇO NO CPF
                    //StringBuilder cpfSB = new StringBuilder(cpf);
                    //cpfSB.insert(3, ".").insert(7,".").insert(11,"-");
                    //cpf = cpfSB.toString();
                    String l[] = s.split(";"); //Retorcatar o txt para preencher a tabela                   

                     dataCad= l[0].trim();
                     NPJ= l[1].trim();
                     posiçãoDoBB= l[2].trim();
                     adversoPrincipal= l[3].trim();
                     depCad= l[4].trim();
                     acao= l[5].trim();
                     docAnexos= l[6].trim();
                     natureza= l[7].trim();
                     pendencia= l[8].trim();
                     chave= l[9].trim();
                     D=l[10].trim();



                    // PREENCHE CÉLULAS XLS
                    row++;
                    linha = plan1.createRow(row);


                    for (int i = 0; i < 11; i++) {
                        switch (i) {
                            case 0: 
                                celula = linha.createCell(i);
                                // plan1.autoSizeColumn(i); //CONFIGURA LARGURA CONFORME TEXTO
                                celula.setCellValue(dataCad);
                                celula.setCellStyle(esquerda);
                                //celula.setCellStyle(maior);
                                break;
                            case 1: 
                                celula = linha.createCell(i);
                                //  plan1.autoSizeColumn(i); //CONFIGURA LARGURA CONFORME TEXTO
                                celula.setCellValue(NPJ);
                                celula.setCellStyle(centro);
                                //  celula.setCellStyle(maior);
                                break;
                            case 2: 
                                celula = linha.createCell(i);
                                //   plan1.autoSizeColumn(i); //CONFIGURA LARGURA CONFORME TEXTO
                                celula.setCellValue(posiçãoDoBB);
                                //    celula.setCellStyle(maior);
                                // celula.(moeda);
                                break;
                            case 3: 
                                celula = linha.createCell(i);
                                //  plan1.autoSizeColumn(i);
                                celula.setCellValue(adversoPrincipal);
                                celula.setCellStyle(centro);
                                //      celula.setCellStyle(maior);
                                break;
                            case 4:                               
                                    celula = linha.createCell(i);
                                    //    plan1.autoSizeColumn(i);
                                    celula.setCellValue(depCad);
                                    celula.setCellStyle(centro);                                
                                //     celula.setCellStyle(maior);
                                break;
                            case 5: 
                                celula = linha.createCell(i);
                                // plan1.autoSizeColumn(i); //CONFIGURA LARGURA CONFORME TEXTO
                                celula.setCellValue(acao);
                                celula.setCellStyle(centro);
                                //      celula.setCellStyle(maior);
                                break;
                            case 6: 
                                celula = linha.createCell(i);
                                //   plan1.autoSizeColumn(i); //CONFIGURA LARGURA CONFORME TEXTO
                                celula.setCellValue(docAnexos);
                                celula.setCellStyle(centro);
                                //      celula.setCellStyle(maior);
                                break;
                            case 7: 
                                celula = linha.createCell(i);
                                // plan1.autoSizeColumn(i); //CONFIGURA LARGURA CONFORME TEXTO
                                celula.setCellValue(natureza);
                                celula.setCellStyle(centro);
                                //      celula.setCellStyle(maior);
                                break;
                                case 8: 
                                celula = linha.createCell(i);
                                // plan1.autoSizeColumn(i); //CONFIGURA LARGURA CONFORME TEXTO
                                celula.setCellValue(pendencia);
                                celula.setCellStyle(centro);
                                //      celula.setCellStyle(maior);
                                break;
                                    case 9: 
                                celula = linha.createCell(i);
                                // plan1.autoSizeColumn(i); //CONFIGURA LARGURA CONFORME TEXTO
                                celula.setCellValue(chave);
                                celula.setCellStyle(centro);
                                //      celula.setCellStyle(maior);
                                break;
                                         case 10: 
                                celula = linha.createCell(i);
                                // plan1.autoSizeColumn(i); //CONFIGURA LARGURA CONFORME TEXTO
                                celula.setCellValue(D);
                                celula.setCellStyle(centro);
                                //      celula.setCellStyle(maior);
                                break;

                        } // FIM SWITCH
                    } // FIM FOR - PREENCHE CELULAS

                    // CONTADOR REGISTROS
                    //        qtdFunci++;

                } // FIM - IF - LINHA VÁLIDA

            } // FIM - WHILE - ARQUIVO RETORNO

        } // FIM TRY-WITH-RESOURCES

        // TOTAL
   /*     row++;
        /*      linha = plan1.createRow(row);
        celula = linha.createCell(2);
        celula.setCellValue(totalConsig);
        //  celula.setCellStyle(moeda);*/

        // FORMATA TAMANHO DAS CÉLULAS
        plan1.autoSizeColumn(0);
        plan1.autoSizeColumn(1);
        plan1.autoSizeColumn(2);

        // ABRE
        FileOutputStream out = new FileOutputStream(ArqSaida);
        // GRAVA
        wb.write(out);
        // FECHA
        out.close();


        /*   JOptionPane.showMessageDialog(null,
        "ARQUIVO CONVERTIDO!" + "\r\n"
        + qtdFunci + " Registros" + "\r\n"
        + "em " + ArqSaida + "\r\n");*/

    }
}
