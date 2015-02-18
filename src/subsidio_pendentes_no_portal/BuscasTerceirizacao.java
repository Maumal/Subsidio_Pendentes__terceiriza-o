/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package subsidio_pendentes_no_portal;

//import Formularios.frmSubsifiosPendentesNoPortal;
import DAO.Conexao;
import java.text.ParseException;
import java.util.ArrayList;
import com.thoughtworks.selenium.Selenium;
import java.io.IOException;
import javax.swing.JOptionPane;
import org.openqa.selenium.By;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import java.util.List;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.joda.time.LocalDate;
//import java.util.GregorianCalendar;
//import java.util.Locale;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import sun.misc.Cleaner;

/**
 *
 * @author f7057419
 */
public class BuscasTerceirizacao{

    /**
     * @param args the command line arguments
     */
    private final String usuario;
    private final String senha;
    int analisado = 0;//Variavel para ir para segunda lista
    int paginas;
    int Linhas;
    int LinhasU;//Linhas da última página
    int segundos;
    int minutos;
    int horas;
    int dia;
    int mes;
    int ano;
    Calendar data;

    //Variáveis de contagem dos  Dias úteis
    Integer d0 = 0;
    Integer d1 = 0;
    Integer d2 = 0;
    Integer d3 = 0;
    Integer d4 = 0;
    Integer d5 = 0;
    Integer d6 = 0;
    Integer d7 = 0;
    Integer dM7 = 0;//maior que 7 dias úteis

    public BuscasTerceirizacao(String usuario, String senha) {
        this.usuario = usuario;
        this.senha = senha;
    }

    public void capturaInfos() throws IOException, InterruptedException, ParseException, SQLException {

        //CONFIGURAR PROXY
        Proxy proxy = new Proxy();

        proxy.setProxyAutoconfigUrl("http://intranet.bb.com.br/firefox_proxy.pac");
        //  proxy.setProxyAutoconfigUrl("cache.bb.com.br");
        proxy.setSocksUsername(usuario);
        proxy.setSocksPassword(senha);

        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setCapability(CapabilityType.PROXY, proxy);

        //Abre o firefox
        WebDriver driver = new FirefoxDriver(capabilities);
        //  Arquivo será gravado na temp
        //String entrada = "C:\\TEMP\\teste1.TXT";
        //String saida = "C:\\Temp\\teste.TXT";
        //BufferedWriter out = new BufferedWriter(new FileWriter(saida)));
        //String phrase = "";

        //FileWriter fw = new FileWriter("c:/teste.txt");       
        //FileWriter fw = new FileWriter("C:/Temp/teste.TXT");
        //BufferedWriter bw = new BufferedWriter(fw);             
        //bw.write("outra coisa");  
        //bw.flush();  
        //bw.close();      
        //CARREGAR LISTA COM AS SITUAÇOES DO BANCO
        //situacoes = new SituacaoDAO().consultaTodas();
        //WebDriver driver = new FirefoxDriver();
        //String url = "https://www.consignacoes.prefeitura.sp.gov.br";
        //String url = "http://intranet.bb.com.br";
        String url = "http://juridico.intranet.bb.com.br/paj";

        driver.get(url);
        Selenium selenium = new WebDriverBackedSelenium(driver, url);
        selenium.open(url);

        //PROCURAR OS ELEMENTOS PARA ENTRAR NO PORTAL
        //WebElement userName = driver.findElement(By.name("username"));
        //WebElement passWord = driver.findElement(By.name("senha"));
        //WebElement codigo = driver.findElement(By.id("captcha"));
        //PROCURAR OS ELEMENTOS PARA ENTRAR NA INTRANET
        WebElement userName = driver.findElement(By.name("chave"));
        WebElement passWord = driver.findElement(By.name("senha"));
        //WebElement codigo = driver.findElement(By.id("entrar"));
        WebElement entrar = driver.findElement(By.id("entrar"));

        userName.sendKeys(usuario);
        passWord.sendKeys(senha);

        //String cod = JOptionPane.showInputDialog(null, "Digite o codigo captcha para entrar");
        //codigo.sendKeys(cod);
        entrar.click();
        
            String Aguarde = "";
        while ("".equals(Aguarde)) {
            try {
                Aguarde = driver.findElement(By.id("Processos")).getText();
            } catch (Exception e) {
            }
        }
        
             
        
        
        
        driver.findElement(By.id("Processos")).click();
        driver.findElement(By.id("Incompletos")).click();
        //driver.findElement(By.id("pesquisarProcessoIncompletoForm:ujResponsavelDecorate:ujResponsavelInput")).click();
        //driver.findElement(By.id("pesquisarProcessoIncompletoForm:tipoVariacaoDecorate:tipoVariacaoRadio:1")).clear();
        //driver.findElement(By.id("pesquisarProcessoIncompletoForm:ujResponsavelDecorate:ujResponsavelInput")).sendKeys("8661 - AJURE TERCEIRIZACAO");
        //new Select(driver.findElement(By.id("pesquisarProcessoIncompletoForm:faseCadastroDecorate:faseCadastroListMenu"))).selectByVisibleText("Inicial");

        new Select(driver.findElement(By.id("pesquisarProcessoIncompletoForm:posicaoBBDecorate:posicaoBBListBox"))).selectByVisibleText("Réu");
        driver.findElement(By.id("pesquisarProcessoIncompletoForm:selectJECDecorate:selectJECRadio:0")).click();        
        driver.findElement(By.id("pesquisarProcessoIncompletoForm:tipoVariacaoDecorate:tipoVariacaoRadio:1")).click();     

        driver.findElement(By.id("pesquisarProcessoIncompletoForm:btPesquisar")).click();
        
          String NenhumRegistro;
        try {
           NenhumRegistro = driver.findElement(By.id("pesquisarSolicitacaoForm:j_id316")).getText();  
        } catch (Exception e) {
            NenhumRegistro="achou"; 
        }
       
       
       
    //    driver.findElement(By.id("pesquisarSolicitacaoForm:dataTabletableSolicitacaoComplemento:0:j_id408")).getText(); 
     //  System.out.println(NenhumRegistro);
       
        if ("Nenhum registro encontrado.".equals(NenhumRegistro)) {
           driver.findElement(By.id("pesquisarProcessoIncompletoForm:selectJECDecorate:selectJECRadio:1")).click();   
            driver.findElement(By.id("pesquisarProcessoIncompletoForm:btPesquisar")).click();
            analisado = 2;
            Thread.sleep(5000);            
        }
        //clica nos 100         
       driver.findElement(By.id("pesquisarProcessoIncompletoForm:j_id555")).click();             
       Thread.sleep(5000);
       //Dados da tabela
       //NÚMERO DE PÁGINAS
       //String numeroPaginas = driver.findElement(By.id("pesquisarProcessoIncompletoForm:dataTabletableProcessosIncompletos:j_id826")).getText();--23/04/2014
       String numeroPaginas = driver.findElement(By.id("pesquisarProcessoIncompletoForm:dataTabletableProcessosIncompletos:j_id890")).getText();
       //System.out.println(numeroPaginas);
       numeroPaginas = numeroPaginas.replaceAll("paginas", "");
       numeroPaginas = numeroPaginas.replaceAll("«", "").trim();
       numeroPaginas = numeroPaginas.replaceAll("»", "").trim();
       //System.out.println(numeroPaginas);
       //SEPARA XX/XX  TOTAL DE PÁGINAS      
       String s[] = numeroPaginas.split("/");
       //System.out.println(s[0]);
       //System.out.println(s[1]);
       numeroPaginas = (s[1]).trim();

       //String totaldePaginas = (s[1]).trim();
       paginas = Integer.parseInt(numeroPaginas);
       //--System.out.println("páginas " + paginas);
       //NÚMERO DE linhas
       //String linhas = driver.findElement(By.id("pesquisarConferenciaLiberacaoForm:divDataTabletableContratosPrimeiroNivel")).getText();
       String linha = driver.findElement(By.className("dataTableNumeroRegistros")).getText();

       //System.out.println(linha);
       linha = linha.replaceAll("Registros encontrados:", "").trim();
       String L[] = linha.split("de");
       //System.out.println(L[0]);
       //System.out.println(L[1]);
       linha = (L[1]).trim();

       int Linhainicio = 1;
       Linhas = Integer.parseInt(linha);
       //--System.out.println("Linhas " + Linhas);     

       Linhas = Linhas + 1;

       List<Terceirizacao> lista = new ArrayList<>();
       //List<Honorario> lista = new ArrayList<Honorario>();

       FileOutputStream arquivo;
       arquivo = new FileOutputStream("c://temp/terceirizacao.txt");

       PrintStream printStream = new PrintStream(arquivo);

        //Pega o total de páginas e pega os dados da tabela n vezes
        for (int i = 0; i < paginas; i++) {
            //--      System.out.println("Página :" + i);
            for (int e = 0; e < 100; e++) {//--
               /* String destino = driver.findElement(By.id("pesquisarSolicitacaoForm:dataTabletableSolicitacaoComplemento:" + e + ":colDependenciaDestinoDecorate:j_id457")).getText();
                 if (!"1981".equals(destino)) {//------------------verificar se sai quado não acha nada
                 i = paginas;
                 break;
                 }*/
                Terceirizacao terceirizacao = new Terceirizacao();
                //--  System.out.println("Linha: " + Linhainicio + " de : " + Linhas);                
                //Campo EVENTO da tabela                 
                //   String dataCad = "";
                
              //-23/04/2014  String dataCad = driver.findElement(By.id("pesquisarProcessoIncompletoForm:dataTabletableProcessosIncompletos:" + e + ":horasDecorate:horasOutput")).getText();
                String dataCad = driver.findElement(By.id("pesquisarProcessoIncompletoForm:dataTabletableProcessosIncompletos:" + e + ":horasDecorate:horasOutput")).getText();
                
                dataCad = dataCad.substring(0, 10).trim();
               // System.out.println(dataCad);

                terceirizacao.setDataCad(dataCad);

                //Campo DATA da tabela
                //  String NPJ = "";
               //-23/04/2014  String NPJ = driver.findElement(By.id("pesquisarProcessoIncompletoForm:dataTabletableProcessosIncompletos:" + e + ":colBBJURDecorate:colBBJUROutput")).getText().trim();;
                String NPJ = driver.findElement(By.id("pesquisarProcessoIncompletoForm:dataTabletableProcessosIncompletos:" + e + ":colBBJURDecorate:colBBJUROutput")).getText().trim();
                terceirizacao.setNPJ(NPJ);
                //  honorario.setDataHonorario(formato2.format(formato1.parse(dataHonorarios)));
                //Campo NPJ da tabela

                //  String posiçãoDoBB = "";
              //-23/04/2014   String posiçãoDoBB = driver.findElement(By.id("pesquisarProcessoIncompletoForm:dataTabletableProcessosIncompletos:" + e + ":j_id587")).getText().trim();
                String posiçãoDoBB = driver.findElement(By.id("pesquisarProcessoIncompletoForm:dataTabletableProcessosIncompletos:" + e + ":colPosicaoDecorate:colPosicaoOutput")).getText().trim();
                //--        System.out.println("O npj da linha " + Linhainicio + " é: " + npjHonorarios);
                terceirizacao.setPosiçãoDoBB(posiçãoDoBB);
                //Campo CONTRATADO da tabela 

                //  String adversoPrincipal = "";
              //-23/04/2014   String adversoPrincipal = driver.findElement(By.id("pesquisarProcessoIncompletoForm:dataTabletableProcessosIncompletos:" + e + ":j_id608")).getText().trim();
                String adversoPrincipal = driver.findElement(By.id("pesquisarProcessoIncompletoForm:dataTabletableProcessosIncompletos:" + e + ":colAdversoPrincipalDecorate:colAdversoPrincipalOutput")).getText().trim();
                //--       System.out.println("O contratado da linha " + Linhainicio + " é: " + contratadoHonorarios);
                terceirizacao.setAdversoPrincipal(adversoPrincipal);
                //Campo MODO_de_REMUNERAÇÂO da tabela

                // String depCad = "";
              //-23/04/2014   String depCad = driver.findElement(By.id("pesquisarProcessoIncompletoForm:dataTabletableProcessosIncompletos:" + e + ":j_id629")).getText().trim();
                String depCad = driver.findElement(By.id("pesquisarProcessoIncompletoForm:dataTabletableProcessosIncompletos:" + e + ":colUnidadeCadastranteDecorate:j_id704")).getText().trim();
                //System.out.println(modoDeRemuneracaoHonorarios);
                terceirizacao.setDepCad(depCad);

                //Campo VALOR da tabela
                // String acao = "";
                String acao = driver.findElement(By.id("pesquisarProcessoIncompletoForm:dataTabletableProcessosIncompletos:" + e + ":j_id672")).getText().trim();
              //  String acao=driver.findElement(By.id("pesquisarProcessoIncompletoForm:dataTabletableProcessosIncompletos:" + e + ":colAcaoDecorate:colAcaoOutput")).getText().trim();
                      
                
                //--       System.out.println("O valor da linha " + Linhainicio + " é: " + valorHonorarios);
                terceirizacao.setAcao(acao);
                
                

                // String docAnexos = "";
              //-23/04/2014   String docAnexos = driver.findElement(By.id("pesquisarProcessoIncompletoForm:dataTabletableProcessosIncompletos:" + e + ":j_id721")).getText().trim();
                String docAnexos = driver.findElement(By.id("pesquisarProcessoIncompletoForm:dataTabletableProcessosIncompletos:" + e + ":j_id785")).getText().trim();
                //--      System.out.println("A  Situação da linha " + Linhainicio + " é: " + situacaoHonorarios);
                terceirizacao.setDocAnexos(docAnexos);

                //String natureza = "";
              //-23/04/2014   String natureza = driver.findElement(By.id("pesquisarProcessoIncompletoForm:dataTabletableProcessosIncompletos:" + e + ":j_id742")).getText().trim();
                String natureza = driver.findElement(By.id("pesquisarProcessoIncompletoForm:dataTabletableProcessosIncompletos:" + e + ":j_id806")).getText().trim();
                //--      System.out.println("A  Situação da linha " + Linhainicio + " é: " + situacaoHonorarios);
                terceirizacao.setNatureza(natureza);

                //String pendencia = "";
              //-23/04/2014   String pendencia = driver.findElement(By.id("pesquisarProcessoIncompletoForm:dataTabletableProcessosIncompletos:" + e + ":j_id763")).getText().trim();
                String pendencia = driver.findElement(By.id("pesquisarProcessoIncompletoForm:dataTabletableProcessosIncompletos:" + e + ":j_id827")).getText().trim();
                //--      System.out.println("A  Situação da linha " + Linhainicio + " é: " + situacaoHonorarios);
                terceirizacao.setPendencia(pendencia);

                //String chave = "";
              //-23/04/2014   String chave = driver.findElement(By.id("pesquisarProcessoIncompletoForm:dataTabletableProcessosIncompletos:" + e + ":j_id784")).getText().trim();
                String chave = driver.findElement(By.id("pesquisarProcessoIncompletoForm:dataTabletableProcessosIncompletos:" + e + ":j_id848")).getText().trim();
                //--      System.out.println("A  Situação da linha " + Linhainicio + " é: " + situacaoHonorarios);
                terceirizacao.setChave(chave);

              /*  //GRAVAR NO BANCO                
                Double NPJBb = Double.parseDouble(NPJ.replace("/", "").replace("-", "").trim());
                String dia = dataCad.substring(0, 2);
                String mes = dataCad.substring(3, 5);
                String ano = dataCad.substring(6, 10);
                String dataCadBd = "'" + ano + "-" + mes + "-" + dia + "'";
                String adversoPrincipalBd = "'" + adversoPrincipal.replaceAll("'", "")+ "'";*/

                /*Boolean HaNPJ = confereNpj(NPJBb);
                
                confereNpj(NPJBb);
                
                
                //System.out.println(HaNPJ);
                

                if (HaNPJ == false) {
                    try (Connection cn = (Connection) new Conexao().conectar()) {
                        String sqlGrava = "INSERT INTO TERCEIRIZACAO VALUES (" + NPJBb + "," + dataCadBd + ","+depCad+" ,"+ adversoPrincipalBd+ ");";
                        
//
                        Statement stm = null;
                        try {
                            stm = (Statement) cn.createStatement();
                        } catch (SQLException ex) {
                        }
                        
                        int rs = 0;
                        try {
                            rs = stm.executeUpdate(sqlGrava);
                        } catch (SQLException ex) {
                        }
                    }

                }*/

                // DateFormat formatter = new SimpleDateFormat("YYYY-MM-dd");
                // Date b = (Date) formatter.parse(dataCad);
                String diaD = dataCad.substring(0, 2).trim();
                String mesD = dataCad.substring(3, 5).trim();
                String anoD = dataCad.substring(6, 10).trim();
               // System.out.println(diaD + "    " + mesD + "   " + anoD);
                String DataPesquisa = "" + anoD + "-" + mesD + "-" + diaD + "";

                LocalDate c = new LocalDate(DataPesquisa);
              //  System.out.println(c);

                int dias;
                dias = DiferencaDatasUteis(c);//sem contar dia úteis

                String D;

                switch (dias) {
                    case 0:
                        // comandos caso a opção 1 tenha sido escolhida;
                        D = "D0";
                        break;
                    case 1:
                        //comandos caso a opção 2 tenha sido escolhida
                        D = "D1";
                        break;

                    case 2:
                        //comandos caso a opção 3 tenha sido escolhida
                        D = "D2";
                        break;
                    case 3:
                        //comandos caso a opção 3 tenha sido escolhida
                        D = "D3";
                        break;
                    case 4:
                        //comandos caso a opção 3 tenha sido escolhida
                        D = "D4";
                        break;
                    case 5:
                        //comandos caso a opção 3 tenha sido escolhida
                        D = "D5";
                        break;
                    case 6:
                        //comandos caso a opção 3 tenha sido escolhida
                        D = "D6";
                        break;
                    case 7:
                        D = "D7";
                        //comandos caso a opção 3 tenha sido escolhida
                        break;

                    default:
                        D = "> D7";
                    //comandos caso nenhuma das opções anteriores tenha sido escolhida
                }

                terceirizacao.setD(D);
                        
                Linhainicio = Linhainicio + 1;
                
                lista.add(terceirizacao);
                Thread.sleep(300);
                
              //  for(Terceirizacao elemento: lista){  
              System.out.println(NPJ);             
 
//               }                
                //printStream.println(Terceirizacao.getNpj() + " ; " + Subsidio.getOrigem() + " ; " + Subsidio.getDestino() + " ; " + Subsidio.getControle() + " ; " + Subsidio.getDataAtendimento() + " ; " + Subsidio.getTipo() + " ; " + Subsidio.getItensdoSubsidio() + " ; " + Subsidio.getEstado());
                printStream.println(terceirizacao.getDataCad() + " ; " + terceirizacao.getNPJ() + " ; " + terceirizacao.getPosiçãoDoBB() + " ; " + terceirizacao.getAdversoPrincipal() + " ; " + terceirizacao.getDepCad() + " ; " + terceirizacao.getAcao() + " ; " + terceirizacao.getDocAnexos() + " ; " + terceirizacao.getNatureza() + " ; " + terceirizacao.getPendencia() + " ; " + terceirizacao.getChave() + " ; " + terceirizacao.getD());

                while (Linhainicio >= Linhas) {               
                    
                      if (analisado < 1) { //Carrega a segunda tabela  
                         driver.findElement(By.id("pesquisarProcessoIncompletoForm:selectJECDecorate:selectJECRadio:1")).click();   
                         driver.findElement(By.id("pesquisarProcessoIncompletoForm:btPesquisar")).click(); 
                         
                        Thread.sleep(5000);
                        //driver.findElement(By.id("pesquisarSolicitacaoForm:j_id352")).click();
                        //Thread.sleep(3000);
                        //NÚMERO DE PÁGINAS
                        String numeroPaginasAnalisado = driver.findElement(By.id("pesquisarProcessoIncompletoForm:dataTabletableProcessosIncompletos:j_id890")).getText();
                        //System.out.println(numeroPaginas);
                        numeroPaginasAnalisado = numeroPaginasAnalisado.replaceAll("paginas", "").trim();
                        numeroPaginasAnalisado = numeroPaginasAnalisado.replaceAll("«", "").trim();
                        numeroPaginasAnalisado = numeroPaginasAnalisado.replaceAll("»", "").trim();
                        //System.out.println("jj " + numeroPaginasAnalisado + " jj");
                        //SEPARA XX/XX  TOTAL DE PÁGINAS      
                        String pP[] = numeroPaginasAnalisado.split("/");
                        //System.out.println(pP[0]);
                        //System.out.println(pP[1]);
                        numeroPaginasAnalisado = (pP[1]).trim();
                        //System.out.println("ss "+numeroPaginasAnalisado+" ss");

                        //String totaldePaginas = (s[1]).trim();
                        //int paginasA;
                        paginas = Integer.parseInt(numeroPaginasAnalisado);
                        //--System.out.println("páginas " + paginas);
                        //NÚMERO DE linhas
                        //String linhas = driver.findElement(By.id("pesquisarConferenciaLiberacaoForm:divDataTabletableContratosPrimeiroNivel")).getText();
                        String linhaAnalisado = driver.findElement(By.className("dataTableNumeroRegistros")).getText();

                        //System.out.println(linhaAnalisado);
                        linha = linhaAnalisado.replaceAll("Registros encontrados:", "").trim();
                        String LP[] = linha.split("de");
                        //System.out.println(LP[0]);
                        //System.out.println(LP[1]);
                        linha = (LP[1]).trim();
                        //int Linhas;      
                        Linhas = Integer.parseInt(linha);
                    //    Linhas = Linhas + 1;
                        Linhainicio = 0;
                        i = 0;
                        e = -1;
                        analisado = 2;
                    } else {//Sai do programa
                        //printStream.println("O total de processos é: " + lista.size());
                        e = 100;//Para sair do for -acabou
                        i = paginas;
                        break;
                    }                 
                }
            }
            //tratamento para não clicar em próxima página na última página
            if (i < paginas - 1) {
                //CLICAR PARA ADIANTAR PARA AS PRÓXIMAS TELAS DA TABELA                 
             //23/04/2014   driver.findElement(By.id("pesquisarProcessoIncompletoForm:dataTabletableProcessosIncompletos:j_id850")).click();
                driver.findElement(By.id("pesquisarProcessoIncompletoForm:dataTabletableProcessosIncompletos:j_id890")).getText();
                Thread.sleep(5000);
                
                numeroPaginas = driver.findElement(By.id("pesquisarProcessoIncompletoForm:dataTabletableProcessosIncompletos:j_id890")).getText();

                    //CLICAR PARA ADIANTAR PARA AS PRÓXIMAS TELAS DA TABELA               
                    driver.findElement(By.id("pesquisarProcessoIncompletoForm:dataTabletableProcessosIncompletos:j_id914")).click();

                    //Aguarda o carregamento da página
                    String AguardesnumeroPaginas = numeroPaginas;
                    while (numeroPaginas.equals(AguardesnumeroPaginas)) {
                        try {
                            AguardesnumeroPaginas = driver.findElement(By.id("pesquisarProcessoIncompletoForm:dataTabletableProcessosIncompletos:j_id890")).getText();
                        } catch (Exception e) {
                        }
                    }
             

                if (i == Linhas - 2) {//Número de linhas de linhas não bate na última página 
                    //NÚMERO DE linhas
                    //String linhas = driver.findElement(By.id("pesquisarConferenciaLiberacaoForm:divDataTabletableContratosPrimeiroNivel")).getText();
                    String linhaAnalisadoUltimapagina = driver.findElement(By.className("dataTableNumeroRegistros")).getText();

                    //    System.out.println(linhaAnalisado);
                    linhaAnalisadoUltimapagina = linhaAnalisadoUltimapagina.replaceAll("Registros", " ").trim();
                    System.out.println(linhaAnalisadoUltimapagina);
                    linhaAnalisadoUltimapagina = linhaAnalisadoUltimapagina.replaceAll("do total de", " ").trim();
                    System.out.println(linhaAnalisadoUltimapagina);
                    linhaAnalisadoUltimapagina = linhaAnalisadoUltimapagina.replaceAll("-", " ").trim();
                    System.out.println(linhaAnalisadoUltimapagina);

                    String LS[] = linhaAnalisadoUltimapagina.split(" ");
                    //   System.out.println(LS[0]);
                    //   System.out.println(LS[1]);
                    //    System.out.println(LS[2]);
                    linhaAnalisadoUltimapagina = (LS[1]).trim();
                    Linhas = Integer.parseInt(linhaAnalisadoUltimapagina);
                    //  System.out.println("++++"+Linhas);
                    //   System.out.println(LinhasU);
                    if (Linhas == 0) {
                        i = paginas + 1;
                        break;
                    } else {
                        Linhas = Linhas + 1;
                        System.out.println("+++++" + Linhas);
                    }
                }
            }

        }
        
        
        
        
        
        
        // System.out.println(lista.size());
        Integer meiaUm = 0;//Variável contas os 8661
        Integer meiaCinco = 0; //Variável contas os 8665
        Integer nujur = 0; //Variável contas os NUJUR
        //Corre a lista
        for (Terceirizacao h : lista) {
            if ("8661".equals(h.getDepCad())) {
                meiaUm = meiaUm + 1;
            }
            if ("8665".equals(h.getDepCad())) {
                meiaCinco = meiaCinco + 1;
            }
            
            if ("9019".equals(h.getDepCad())) {
                nujur = nujur + 1;
            }
            
            if ("9020".equals(h.getDepCad())) {
                nujur = nujur + 1;
            }
            if ("9021".equals(h.getDepCad())) {
                nujur = nujur + 1;
            }
            if ("9022".equals(h.getDepCad())) {
                nujur = nujur + 1;
            }
            if ("9054".equals(h.getDepCad())) {
                nujur = nujur + 1;
            }
            if ("9056".equals(h.getDepCad())) {
                nujur = nujur + 1;
            }
                     
            Date a = new Date();

            DateFormat formatter = new SimpleDateFormat("MM/dd/yy");
            Date b = (Date) formatter.parse(h.getDataCad());
            //System.out.println(h.getAcao());
        }

        printStream.println("0 ;" + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; ");
        printStream.println("0 ;" + "8661 = " + meiaUm + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; ");
        printStream.println("0 ;" + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; ");
        printStream.println("0 ;" + "8665 = " + meiaCinco + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; ");
        printStream.println("0 ;" + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; ");
        printStream.println("0 ;" + "NUJUR = " + nujur + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; ");

//Contar processos por datas
        for (Terceirizacao h : lista) {
            //  DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
            // Date b = (Date) formatter.parse(h.getDataCad());

            System.out.println(h.getDataCad());
            String diat = h.getDataCad().substring(0, 2).trim();
            String mest = h.getDataCad().substring(3, 5).trim();
            String anot = h.getDataCad().substring(6, 10).trim();
            //   System.out.println(diat + "    " + mest + "   " + anot);
            String DataPesquisat = "" + anot + "-" + mest + "-" + diat + "";
            System.out.println(DataPesquisat);

            LocalDate b = new LocalDate(DataPesquisat);
            int dias;
            // dias = DiferencaDatass(b);
            dias = DiferencaDatasUteis(b);

            System.out.println(dias);

            if ("8665".equals(h.getDepCad()) || ("8661".equals(h.getDepCad()))|| ("9019".equals(h.getDepCad()))|| ("9020".equals(h.getDepCad()))|| ("9021".equals(h.getDepCad()))|| ("9022".equals(h.getDepCad()))|| ("9054".equals(h.getDepCad()))|| ("9056".equals(h.getDepCad()))) {

            } else {

                switch (dias) {
                    case 0:
                        d0 = d0 + 1;
                        break;

                    case 1: 
                        d1 = d1 + 1;
                        break;

                    case 2:
                        d2 = d2 + 1;
                        break;
                    case 3:
                        d3 = d3 + 1;
                        break;
                    case 4:
                        d4 = d4 + 1;
                        break;
                    case 5:
                        d5 = d5 + 1;
                        break;
                    case 6:
                        d6 = d6 + 1;
                        break;
                    case 7:
                        d7 = d7 + 1;
                        break;
                    default:
                        dM7 = dM7 + 1;
                }

            }
        }
        //D0

        System.out.println(d0 + "--" + d1 + "--" + d2 + "--" + d3 + "--" + d4 + "--" + d5 + "--" + d6 + "--" + d7 + "--" + dM7);

        if (d0 != 0) {
            printStream.println("0 ;" + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; ");
            printStream.println("0 ;" + "D0 = " + d0 + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; ");
            printStream.println("0 ;" + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; ");
        }
        //D1
        if (d1 != 0) {
            printStream.println("0 ;" + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; ");
            printStream.println("0 ;" + "D1 = " + d1 + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; ");
            printStream.println("0 ;" + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; ");
        }
        //D2
        if (d2 != 0) {
            printStream.println("0 ;" + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; ");
            printStream.println("0 ;" + "D2 = " + d2 + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; ");
            printStream.println("0 ;" + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; ");
        }
        //D3
        if (d3 != 0) {
            printStream.println("0 ;" + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; ");
            printStream.println("0 ;" + "D3 = " + d3 + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; ");
            printStream.println("0 ;" + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; ");
        }
        //D4
        if (d4 != 0) {
            printStream.println("0 ;" + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; ");
            printStream.println("0 ;" + "D4 = " + d4 + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; ");
            printStream.println("0 ;" + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; ");
        }
        //D5
        if (d5 != 0) {
            printStream.println("0 ;" + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; ");
            printStream.println("0 ;" + "D5 = " + d5 + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; ");
            printStream.println("0 ;" + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; ");
        }
        //D6
        if (d6 != 0) {
            printStream.println("0 ;" + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; ");
            printStream.println("0 ;" + "D6 = " + d6 + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; ");
            printStream.println("0 ;" + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; ");
        }
        //D7
        if (d7 != 0) {
            printStream.println("0 ;" + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; ");
            printStream.println("0 ;" + "D7 = " + d7 + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; ");
            printStream.println("0 ;" + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; ");
        }
        //D8
        if (dM7 != 0) {
            printStream.println("0 ;" + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; ");
            printStream.println("0 ;" + "MAIOR QUE D7 = " + dM7 + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; ");
            printStream.println("0 ;" + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; ");
        }

        int total = lista.size();       
      
        printStream.println("0 ;" + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; ");
        printStream.println("0 ;" + "TOTAL = " + total + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; ");
        printStream.println("0 ;" + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; " + " ; ");

        new GerarExcelTerceirizacao().Importar("C:/TEMP/terceirizacao.txt");
        driver.close();
        JOptionPane.showMessageDialog(null, "ARQUIVO COM OS DADOS GRAVADOS EM:   C:/Temp/TERCEIRIZACAO.xls ");

        //  System.exit(0);
    }
    
    

    public int DiferencaDatass(Date data1) {

        int count = 0;

        try {

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            //     Date data1 = sdf.parse("07/06/2012");
            Date data2 = new Date();

            long diff = data2.getTime() - data1.getTime();

            //      System.out.println("Dif. Segundos: "+diff/1000);
            //      System.out.println("Dif. Minutos: "+diff/(1000*60));
            //      System.out.println("Dif. Horas: "+diff/(1000*60*60));
            //      System.out.println("Dif. Dias: "+diff/(1000*60*60*24));
            count = (int) (diff / (1000 * 60 * 60 * 24));
        } catch (Exception x) {
        }

        return count;
    }

    public int DiferencaDatasUteis(LocalDate data1) {

        int count = 0;

        try {
            //  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            //     Date data1 = sdf.parse("07/06/2012");
            //   Date data2 = new Date();

            LocalDate hoje = new LocalDate();
          //  LocalDate DataCadastro = new LocalDate(data1);

            // Calculando a diferença em dias e em Meses:
            //       System.out.printf("Dias decorridos = %d dias\n",
            //                   Days.daysBetween(data1, hoje).getDays());
            double hojed;//Double pois LocalDate-> não estava dando certo o while
            hojed = Double.parseDouble(hoje.toString().replace("-", ""));
            double data1d;
            data1d = Double.parseDouble(data1.toString().replace("-", ""));

            if (hojed == data1d) {//Se hoje d0
                count = 0;
            } else {
                while (data1d != hojed) {
                    boolean feriado = VerificaFeriado(data1);
                    //          System.out.println(data1 + "   " + data1.getDayOfWeek() + "   " + feriado);
                    if (data1.getDayOfWeek() == 6 || data1.getDayOfWeek() == 7 || feriado == true) {
                        count--;
                    }
                    data1 = data1.plusDays(1);
                    data1d = Double.parseDouble(data1.toString().replace("-", ""));
                    count++;
                    if (data1d == hojed) {
                        break;
                    }
                }
            }

            /* 
             long diff = data2.getTime() - data1.getTime();
             //      System.out.println("Dif. Segundos: "+diff/1000);
             //      System.out.println("Dif. Minutos: "+diff/(1000*60));
             //      System.out.println("Dif. Horas: "+diff/(1000*60*60));
             //      System.out.println("Dif. Dias: "+diff/(1000*60*60*24));
             count = (int) (diff / (1000 * 60 * 60 * 24));
             */
        } catch (NumberFormatException x) {
        }

        return count;
    }

    public boolean VerificaFeriado(LocalDate dataVerificar) {
        boolean Feriado = false;
        //Feriados 2014
        String dataVerificarS = dataVerificar.toString();

        switch (dataVerificarS) {
            //Feriados de 2014
            case "2014-04-18":
                Feriado = true;
                //System.out.println("Acho o feriado");
                break;
            case "2014-04-21":
                Feriado = true;
                //         System.out.println("Acho o feriado");
                break;
            case "2014-05-01":
                Feriado = true;
                //          System.out.println("Acho o feriado");
                break;
            case "2014-06-19":
                Feriado = true;
                //           System.out.println("Acho o feriado");
                break;
            case "2014-12-25":
                Feriado = true;
                //           System.out.println("Acho o feriado");
                break;
            //Feriados de 2015
            case "2015-01-01":
                Feriado = true;
                //         System.out.println("Acho o feriado");
                break;
            case "2015-02-16":
                Feriado = true;
                //          System.out.println("Acho o feriado");
                break;
            case "2015-02-17":
                Feriado = true;
                //          System.out.println("Acho o feriado");
                break;
            case "2015-04-03":
                Feriado = true;
                //          System.out.println("Acho o feriado");
                break;
            case "2015-04-21":
                Feriado = true;
                //        System.out.println("Acho o feriado");
                break;
            case "2015-05-01":
                Feriado = true;
                //       System.out.println("Acho o feriado");
                break;
            case "2015-06-04":
                Feriado = true;
                //    System.out.println("Acho o feriado");
                break;
            case "2015-10-12":
                Feriado = true;
                //    System.out.println("Acho o feriado");
                break;
            case "2015-11-02":
                Feriado = true;
                //   System.out.println("Acho o feriado");
                break;
            case "2015-12-25":
                Feriado = true;
                //   System.out.println("Acho o feriado");
                break;
        }

        return Feriado;
    }

   /* private Boolean confereNpj(Double Npj) throws SQLException {//Confere se ha linha dá tabela já foi inserida
        Boolean haNPJ = true;
        // Connection cnc = (Connection) new Conexao().conectar();              
        Connection cn = (Connection) new Conexao().conectar();
        String sqlBuscaBuscaNPJ = "Select TERCEIRIZACAO.DATACAD from TERCEIRIZACAO where TERCEIRIZACAO.NPJ= " + Npj + "";
        //  String sqlVai = "select *  from bdjudicial.Ajure where pleco = '1' and DT_GRAV = " + "'" + dataHoje + "'";
        java.sql.Statement stm = cn.createStatement();
        ResultSet rs = stm.executeQuery(sqlBuscaBuscaNPJ);
        //  txtResultado.setText(rs.toString());
        //  int sqlresultado = rs.toString().length();
        // txtResultado.setText("teste");
        //   txtResultado.setText(Integer.toString(sqlresultado));
        // System.out.println(sqlresultado);
        // System.out.println(rs.toString().length());

        int linhas = 0;//Contador registros no banco para a data pesquisada

        while (rs.next()) {
            linhas++;
        }

        if (linhas == 0) {
            haNPJ = false;
        } else {
        }
        return haNPJ;
    }*/

}
