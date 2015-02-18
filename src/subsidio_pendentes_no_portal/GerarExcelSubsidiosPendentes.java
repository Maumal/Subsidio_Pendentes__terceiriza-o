package subsidio_pendentes_no_portal;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;
//import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
//import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;

public class GerarExcelSubsidiosPendentes {

    public void Importar(String arqRetorno) throws FileNotFoundException, IOException {
      //  System.out.println(arqRetorno);
    
///dentro
         Locale locale = new Locale("pt","BR"); 
GregorianCalendar calendar = new GregorianCalendar(); 
SimpleDateFormat formatador = new SimpleDateFormat("dd' de 'MMMMM' de 'yyyy' _ 'HH' 'mm' h'",locale); 

System.out.println(formatador.format(calendar.getTime())); 
      
        String dataHoje = formatador.format(calendar.getTime());
        System.out.println(dataHoje); 
        
        
        String Aba = "SUBSIDIOS_PENDENTES";
        String ArqSaida = "C:/Temp/SUBSIDIOS_PENDENTES "+dataHoje+".xls";

        String s = "";
        String nome = "";
        String cpf = "";
        String mensagem = "";
        String copiarValor = "";
        String NPJ = "";
        int ORIGEM ;
        int DESTINO ;
        String NCONTROLE = "";
        String DATALIMITE = "";
        String TIPO = "";
        String ITENSDOSUBSIDIO = "";
        String ESTADO = "";
        
        

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
        for (int i = 0; i < 8; i++) {
            switch (i) {
                case 0:
                    celula = linha.createCell(i);
                    celula.setCellValue("NPJ");
                    celula.setCellStyle(cabecalho);                    
                    break;
                case 1:
                    celula = linha.createCell(i);
                    celula.setCellValue("ORIGEM");
                    celula.setCellStyle(cabecalho);
                    break;
                case 2:
                    celula = linha.createCell(i);
                    celula.setCellValue("DESTINO");
                    celula.setCellStyle(cabecalho);
                    break;
                     case 3:
                    celula = linha.createCell(i);
                    celula.setCellValue("NºCONTROLE");
                    celula.setCellStyle(cabecalho);
                    
                    break;
                          case 4:
                    celula = linha.createCell(i);
                    celula.setCellValue("DATA LIMITE");
                    celula.setCellStyle(cabecalho);
                    break;
                               case 5:
                    celula = linha.createCell(i);
                    celula.setCellValue("TIPO");
                    celula.setCellStyle(cabecalho);
                    break;
                                    case 6:
                    celula = linha.createCell(i);
                    celula.setCellValue("ITENS DO SUBSIDIO");
                    celula.setCellStyle(cabecalho);
                    break;
                                         case 7:
                    celula = linha.createCell(i);
                    celula.setCellValue("ESTADO");
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
                     //NOME
                    //nome = l[0];
                    //CPF
                    //cpf = l[1];
                    // RETIRA OS PONTOS E TRAÇO DO CPF
                    //cpf = cpf.replace(".", "");
                    //cpf = cpf.replace("-", "");
                    // COLOCA PONTOS E TRAÇO NO CPF
                    //StringBuilder cpfSB = new StringBuilder(cpf);
                    //cpfSB.insert(3, ".").insert(7,".").insert(11,"-");
                    //cpf = cpfSB.toString();
                    String l[] = s.split(";"); //Retorcatar o txt para preencher a tabela
                    System.out.println(l[0]);
                    System.out.println(l[1]);
                    System.out.println(l[2]);
                    System.out.println(l[3]);
                    System.out.println(l[4]);
                    System.out.println(l[5]);
                    System.out.println(l[6]);
                    System.out.println(l[7]);
                    // VALOR                    
                    NPJ = l[0].trim();
                    System.out.println(NPJ);                 
                   // ORIGEM = l[1].trim();
                    ORIGEM = Integer.parseInt(l[1].trim());
                    System.out.println(ORIGEM);                   
                    DESTINO = Integer.parseInt(l[2].trim());  
                    System.out.println(DESTINO);                   
                  //  DESTINO = l[2].trim();
                    NCONTROLE = l[3].trim();
                    System.out.println(NCONTROLE);                   
                    DATALIMITE = l[4].trim();
                    System.out.println(DATALIMITE);                   
                    TIPO = l[5].trim();
                    System.out.println(TIPO);                   
                    ITENSDOSUBSIDIO = l[6].trim();
                    System.out.println(ITENSDOSUBSIDIO);                   
                    ESTADO = l[7].trim();                     
                    System.out.println(ESTADO);                   
                    
                     System.out.println(" npj: " +NPJ + "  ORIGEM: " + ORIGEM + "  DESTINO: " +DESTINO + "  CONTROLE: " +NCONTROLE + "  DATA: " +DATALIMITE + "  TIPO: " +TIPO + "  ITENS: " +ITENSDOSUBSIDIO + "  ESTADO: " +ESTADO);
                    
                    
                    
                    
                   // consig = Double.parseDouble(copiarValor);
                   // consig = consig / 100;
                   // totalConsig = totalConsig + consig;

                    // MENSAGEM
                    //mensagem = s.substring(108, 148).trim();

                    // PREENCHE CÉLULAS XLS
                    row++;
                    linha = plan1.createRow(row);
                    
                    
                    for (int i = 0; i < 8; i++) {
                        switch (i) {                            
                            case 0: // NPJ
                                celula = linha.createCell(i);
                               // plan1.autoSizeColumn(i); //CONFIGURA LARGURA CONFORME TEXTO
                                celula.setCellValue(NPJ);
                                celula.setCellStyle(esquerda);
                                //celula.setCellStyle(maior);
                                break;
                            case 1: // ORIGEM
                                celula = linha.createCell(i);
                              //  plan1.autoSizeColumn(i); //CONFIGURA LARGURA CONFORME TEXTO
                                celula.setCellValue(ORIGEM);
                                celula.setCellStyle(centro);
                              //  celula.setCellStyle(maior);
                                break;
                            case 2: // DESTINO
                                celula = linha.createCell(i);
                             //   plan1.autoSizeColumn(i); //CONFIGURA LARGURA CONFORME TEXTO
                                celula.setCellValue(DESTINO);
                            //    celula.setCellStyle(maior);
                               // celula.(moeda);
                                break;
                                case 3: // NCONTROLE
                                celula = linha.createCell(i);
                              //  plan1.autoSizeColumn(i);
                                celula.setCellValue(NCONTROLE);
                                celula.setCellStyle(centro);
                          //      celula.setCellStyle(maior);
                                break;
                                    case 4: // ESTADO
                                      //  System.out.println(l[7]);
                                      //  System.out.println(ESTADO);
                                       
                                        if ( "Solicitado".equals(ESTADO)) { //Quando ESTADO = SOLICITADO = data em vermelho
                                            celula = linha.createCell(i);
                                      //      plan1.setColumnWidth((short) 2, (short) (400));  
                                            celula.setCellValue(DATALIMITE);
                                            celula.setCellStyle(centro);
                                            celula.setCellStyle(vermelho);                                            
                                        }else{
                                            celula = linha.createCell(i);
                                        //    plan1.autoSizeColumn(i);
                                            celula.setCellValue(DATALIMITE);
                                            celula.setCellStyle(centro);                                            
                                        }
         
                                        
                                
                                
                           //     celula.setCellStyle(maior);
                                break;
                                        case 5: // TIPO
                                celula = linha.createCell(i);
                               // plan1.autoSizeColumn(i); //CONFIGURA LARGURA CONFORME TEXTO
                                celula.setCellValue(TIPO);
                                celula.setCellStyle(centro);
                          //      celula.setCellStyle(maior);
                                break;
                                            case 6: // ITENSDOSUBSIDIO
                                celula = linha.createCell(i);
                             //   plan1.autoSizeColumn(i); //CONFIGURA LARGURA CONFORME TEXTO
                                celula.setCellValue(ITENSDOSUBSIDIO);
                                celula.setCellStyle(centro);
                          //      celula.setCellStyle(maior);
                                break;
                                                case 7: // ESTADO
                                celula = linha.createCell(i);
                               // plan1.autoSizeColumn(i); //CONFIGURA LARGURA CONFORME TEXTO
                                celula.setCellValue(ESTADO);
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
