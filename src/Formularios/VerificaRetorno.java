
package Formularios;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class VerificaRetorno {


    public boolean Verifica (String arquivo) throws FileNotFoundException{

        // Verifica se o arquivo retorno é do convênio em questão
        FileInputStream in = new FileInputStream(arquivo);
        Scanner scan = new Scanner(in);

        while (scan.hasNextLine()){
            if (scan.nextLine().contains("")){
                return true;
            }
        }
        return false;

    }

}
