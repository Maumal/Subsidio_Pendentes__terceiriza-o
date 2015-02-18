/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package subsidio_pendentes_no_portal;

//import Formularios.frmSubsifiosPendentesNoPortal;
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
//import java.text.SimpleDateFormat;
import java.util.Calendar;
//import java.util.GregorianCalendar;
//import java.util.Locale;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import sun.misc.Cleaner;

/**
 *
 * @author f7057419
 */
public class BuscasSubsidiosPendentes {

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

    public BuscasSubsidiosPendentes(String usuario, String senha) {
        this.usuario = usuario;
        this.senha = senha;
    }

    public void capturaInfos() throws IOException, InterruptedException, ParseException {

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
        //    String phrase = "";

        //   FileWriter fw = new FileWriter("c:/teste.txt");       
        // FileWriter fw = new FileWriter("C:/Temp/teste.TXT");
        // BufferedWriter bw = new BufferedWriter(fw);             
        // bw.write("outra coisa");  
        // bw.flush();  
        // bw.close();      
        //CARREGAR LISTA COM AS SITUAÇOES DO BANCO
        //   situacoes = new SituacaoDAO().consultaTodas();
        //WebDriver driver = new FirefoxDriver();
        //String url = "https://www.consignacoes.prefeitura.sp.gov.br";
        //String url = "http://intranet.bb.com.br";
        String url = "http://juridico.intranet.bb.com.br/paj";

        driver.get(url);
        Selenium selenium = new WebDriverBackedSelenium(driver, url);
        selenium.open(url);

        //PROCURAR OS ELEMENTOS PARA ENTRAR NO PORTAL
        // WebElement userName = driver.findElement(By.name("username"));
        // WebElement passWord = driver.findElement(By.name("senha"));
        // WebElement codigo = driver.findElement(By.id("captcha"));
        //PROCURAR OS ELEMENTOS PARA ENTRAR NA INTRANET
        WebElement userName = driver.findElement(By.name("chave"));
        WebElement passWord = driver.findElement(By.name("senha"));
        //  WebElement codigo = driver.findElement(By.id("entrar"));
        WebElement entrar = driver.findElement(By.id("entrar"));

        userName.sendKeys(usuario);
        passWord.sendKeys(senha);

        //     String cod = JOptionPane.showInputDialog(null, "Digite o codigo captcha para entrar");
        //  codigo.sendKeys(cod);
        entrar.click();
        
        String Aguarde = "";
        while ("".equals(Aguarde)) {
            try {
                Aguarde = driver.findElement(By.id("Processos")).getText();
            } catch (Exception e) {
            }
        }
 
        
        
        
        
        driver.findElement(By.id("Processos")).click();
        driver.findElement(By.id("Pedido de Subsídios")).click();
        driver.findElement(By.id("pesquisarSolicitacaoForm:pesquisarDependenciaDestinoDecorate:pesquisarDependenciaDestinoDecorate:pesquisarDependenciaDestinoInput")).click();
        driver.findElement(By.id("pesquisarSolicitacaoForm:pesquisarDependenciaDestinoDecorate:pesquisarDependenciaDestinoDecorate:pesquisarDependenciaDestinoInput")).clear();
        driver.findElement(By.id("pesquisarSolicitacaoForm:pesquisarDependenciaDestinoDecorate:pesquisarDependenciaDestinoDecorate:pesquisarDependenciaDestinoInput")).sendKeys("1981");
        new Select(driver.findElement(By.id("pesquisarSolicitacaoForm:comboSituacoesComplementoDecorate:comboSituacoesComplementoListBox"))).selectByVisibleText("Solicitado");
        driver.findElement(By.id("pesquisarSolicitacaoForm:btPesquisarSolicitacao")).click();
        
       //Verifica se há dados na tabela e caso hão haver vai para segunda parte 
        
        
        
       String NenhumRegistro;
        try {
           NenhumRegistro = driver.findElement(By.id("pesquisarSolicitacaoForm:j_id316")).getText();  
        } catch (Exception e) {
            NenhumRegistro="achou"; 
        }
       
       
       
    //    driver.findElement(By.id("pesquisarSolicitacaoForm:dataTabletableSolicitacaoComplemento:0:j_id408")).getText(); 
       System.out.println(NenhumRegistro);
       
        if ("Nenhum registro encontrado.".equals(NenhumRegistro)) {
            new Select(driver.findElement(By.id("pesquisarSolicitacaoForm:comboSituacoesComplementoDecorate:comboSituacoesComplementoListBox"))).selectByVisibleText("Analisado");
            driver.findElement(By.id("pesquisarSolicitacaoForm:btPesquisarSolicitacao")).click();
            analisado = 2;
            Thread.sleep(5000);            
        }
        
        
        
        //clica nos 100         
        //driver.findElement(By.id("pesquisarSolicitacaoForm:j_id352")).click();
        //Thread.sleep(18000);
        //Dados da tabela
        //NÚMERO DE PÁGINAS
        String numeroPaginas = driver.findElement(By.id("pesquisarSolicitacaoForm:dataTabletableSolicitacaoComplemento:j_id597")).getText();
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
        //-- System.out.println("páginas " + paginas);
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

        List<ListaTerceirizacao> lista = new ArrayList<>();
        //List<Honorario> lista = new ArrayList<Honorario>();

        FileOutputStream arquivo;
        arquivo = new FileOutputStream("c://temp/Subsidios.txt");

        PrintStream printStream = new PrintStream(arquivo);

//Pega o total de páginas e pega os dados da tabela n vezes
        for (int i = 0; i < paginas; i++) {
            //--System.out.println("Página :" + i);

            for (int e = 0; e < 100; e++) {//--
               /* String destino = driver.findElement(By.id("pesquisarSolicitacaoForm:dataTabletableSolicitacaoComplemento:" + e + ":colDependenciaDestinoDecorate:j_id457")).getText();
                 if (!"1981".equals(destino)) {//------------------verificar se sai quado não acha nada
                 i = paginas;
                 break;
                 }*/
                ListaTerceirizacao Subsidio = new ListaTerceirizacao();
                //--System.out.println("Linha: " + Linhainicio + " de : " + Linhas);                
                //Campo EVENTO da tabela 
                String npj;

                npj = driver.findElement(By.id("pesquisarSolicitacaoForm:dataTabletableSolicitacaoComplemento:" + e + ":j_id408")).getText().trim();
                //--System.out.println("O evento da linha " + Linhainicio + " é: " + eventoHonorarios);                
                Subsidio.setNpj(npj);
                //Campo DATA da tabela
                String origem;
                origem = driver.findElement(By.id("pesquisarSolicitacaoForm:dataTabletableSolicitacaoComplemento:" + e + ":colDependenciaSolicitanteDecorate:j_id424")).getText().trim();
                Subsidio.setOrigem(origem);
                //honorario.setDataHonorario(formato2.format(formato1.parse(dataHonorarios)));
                //Campo NPJ da tabela
                String destino;
                destino = driver.findElement(By.id("pesquisarSolicitacaoForm:dataTabletableSolicitacaoComplemento:" + e + ":colDependenciaDestinoDecorate:j_id457")).getText().trim();
                //--System.out.println("O npj da linha " + Linhainicio + " é: " + npjHonorarios);
                Subsidio.setDestino(destino);
                //Campo CONTRATADO da tabela                
                String controle;
                controle = driver.findElement(By.id("pesquisarSolicitacaoForm:dataTabletableSolicitacaoComplemento:" + e + ":j_id479")).getText().trim();
                //--System.out.println("O contratado da linha " + Linhainicio + " é: " + contratadoHonorarios);
                Subsidio.setControle(controle);
                //Campo MODO_de_REMUNERAÇÂO da tabela 
                String dataAtendimento;
                dataAtendimento = driver.findElement(By.id("pesquisarSolicitacaoForm:dataTabletableSolicitacaoComplemento:" + e + ":j_id500")).getText().trim();
                //System.out.println(modoDeRemuneracaoHonorarios);
                Subsidio.setDataAtendimento(dataAtendimento);
                //Campo VALOR da tabela
                String tipo;
                tipo = driver.findElement(By.id("pesquisarSolicitacaoForm:dataTabletableSolicitacaoComplemento:" + e + ":colTipoDecorate:colTipoOutput")).getText().trim();
                //--System.out.println("O valor da linha " + Linhainicio + " é: " + valorHonorarios);
                Subsidio.setTipo(tipo);
                String itensdoSubsidio;
                itensdoSubsidio = driver.findElement(By.id("pesquisarSolicitacaoForm:dataTabletableSolicitacaoComplemento:" + e + ":j_id542")).getText();
                itensdoSubsidio=itensdoSubsidio.replaceAll("","wwwwwwwwwwwwwwwwwwwwwwwwww");
                itensdoSubsidio = itensdoSubsidio.substring(0, 9).trim().replaceAll("ã", "ão");
                itensdoSubsidio=itensdoSubsidio.replaceAll("w", "");
                //--System.out.println("A  Situação da linha " + Linhainicio + " é: " + situacaoHonorarios);
                Subsidio.setItensdoSubsidio(itensdoSubsidio);
                String estado;
                estado = driver.findElement(By.id("pesquisarSolicitacaoForm:dataTabletableSolicitacaoComplemento:" + e + ":j_id564")).getText().trim();
                //--System.out.println("A  Situação da linha " + Linhainicio + " é: " + situacaoHonorarios);
                Subsidio.setEstado(estado);
                //System.out.println("\r");
                System.out.println(Linhainicio);
                System.out.println(npj);

                /*
                 //Pegar data MODO 1
                 Locale locale = new Locale("pt","BR"); 
                 GregorianCalendar calendar = new GregorianCalendar(); 
                 SimpleDateFormat formatador = new SimpleDateFormat("dd' de 'MMMMM' de 'yyyy' - 'HH':'mm'h'",locale); 
                 System.out.println((calendar.getTime()));                
                 System.out.println(formatador.format(calendar.getTime())); 
                
                 //Pegar data MODO 2
                 data = Calendar.getInstance();  
                 horas = data.get(Calendar.HOUR_OF_DAY);  
                 minutos = data.get(Calendar.MINUTE);  
                 segundos = data.get(Calendar.SECOND); 
                 dia= data.get(Calendar.DATE);
                 mes= data.get(Calendar.MONTH);
                 ano= data.get(Calendar.YEAR);
                 System.out.println(horas + ":" + minutos + ":" + segundos); 
                 System.out.println(dia + "/" + mes + "/" + ano);             
                 */
                //Thread.sleep(50);
                //--System.out.println("");                
                Linhainicio = Linhainicio + 1;

                lista.add(Subsidio);
                printStream.println(Subsidio.getNpj() + " ; " + Subsidio.getOrigem() + " ; " + Subsidio.getDestino() + " ; " + Subsidio.getControle() + " ; " + Subsidio.getDataAtendimento() + " ; " + Subsidio.getTipo() + " ; " + Subsidio.getItensdoSubsidio() + " ; " + Subsidio.getEstado());

                while (Linhainicio >= 100) {
                                    
                    if (analisado < 1) { //Carrega a segunda tabela  
                        new Select(driver.findElement(By.id("pesquisarSolicitacaoForm:comboSituacoesComplementoDecorate:comboSituacoesComplementoListBox"))).selectByVisibleText("Analisado");
                        driver.findElement(By.id("pesquisarSolicitacaoForm:btPesquisarSolicitacao")).click();
                        Thread.sleep(5000);
                        //driver.findElement(By.id("pesquisarSolicitacaoForm:j_id352")).click();
                        //Thread.sleep(3000);
                        //NÚMERO DE PÁGINAS
                        String numeroPaginasAnalisado = driver.findElement(By.id("pesquisarSolicitacaoForm:dataTabletableSolicitacaoComplemento:j_id599")).getText();
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
                        Linhainicio = 1;
                        i = 0;
                        e = -1;
                        analisado = 2;
                    } else {//Sai do programa
                        //printStream.println("O total de processos é: " + lista.size());
                        e = Linhas;//Para sair do for -acabou
                        i = paginas;
                        break;
                    }  
                }
            }//--
            //tratamento para não clicar em próxima página na última página
            if (i < paginas - 1) {
                //CLICAR PARA ADIANTAR PARA AS PRÓXIMAS TELAS DA TABELA                 
                driver.findElement(By.id("pesquisarSolicitacaoForm:dataTabletableSolicitacaoComplemento:j_id623")).click();
                Thread.sleep(5000);

                if (i == paginas - 2) {//Número de linhas de linhas não bate na última página 
                    //NÚMERO DE linhas
                    //String linhas = driver.findElement(By.id("pesquisarConferenciaLiberacaoForm:divDataTabletableContratosPrimeiroNivel")).getText();
                    String linhaAnalisadoUltimapagina = driver.findElement(By.className("dataTableNumeroRegistros")).getText();

                    //System.out.println(linhaAnalisado);
                    linhaAnalisadoUltimapagina = linhaAnalisadoUltimapagina.replaceAll("Registros", " ").trim();
                    System.out.println(linhaAnalisadoUltimapagina);
                    linhaAnalisadoUltimapagina = linhaAnalisadoUltimapagina.replaceAll("do total de", " ").trim();
                    System.out.println(linhaAnalisadoUltimapagina);
                    linhaAnalisadoUltimapagina = linhaAnalisadoUltimapagina.replaceAll("-", " ").trim();
                    System.out.println(linhaAnalisadoUltimapagina);

                    String LS[] = linhaAnalisadoUltimapagina.split(" ");
                    //System.out.println(LS[0]);
                    //System.out.println(LS[1]);
                    //System.out.println(LS[2]);
                    linhaAnalisadoUltimapagina = (LS[1]).trim();
                    Linhas = Integer.parseInt(linhaAnalisadoUltimapagina);
                    //System.out.println("++++"+Linhas);
                    //System.out.println(LinhasU);
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
        
        
        
        
        new GerarExcelSubsidiosPendentes().Importar("C:/TEMP/Subsidios.txt");
        driver.close();
        JOptionPane.showMessageDialog(null, "ARQUIVO COM OS DADOS GRAVADOS EM:   C:/Temp/SUBSIDIOS_PENDENTES.xls ");
        //    System.exit(0);
    }
}
