import Analizador_Lexico.AnalizadorLexico;
import Analizador_Lexico.ExcepcionLexica;
import Analizador_Lexico.Token;
import FileManager.FileManager;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Modulo_Principal {
    public static void main(String[] args) throws IOException {

        //FileManager fileManager = new FileManager(args[0]);
        FileManager fileManager = new FileManager("C:\\Users\\ema_c\\Desktop\\Compiladores\\Etapa 1\\MiniJavaSourceCode.txt");
        
        AnalizadorLexico analizadorLexico = new AnalizadorLexico(fileManager);

        do{
            //fileManager.getNextLine();
            //System.out.println(fileManager.reachedEndOfFile());
            Token token = new Token(null, "", 0);
            try {
                System.out.print("hola\n");
                token = analizadorLexico.getToken();
                System.out.print("hola\n");
                //System.out.print("" + token.getTokenId().toString() + "," + token.getLexema() + "," + token.getLinea() + "\n");
                System.out.print(token.getTokenId().toString() + "\n");
                System.out.print(token.getLexema() + "\n");
                System.out.print(token.getLinea() + "\n");
            } catch (ExcepcionLexica e){
                System.out.print(e.getMessage());
                //analizadorLexico.actualizarCaracterActual();
            };
        }while(!fileManager.reachedEndOfFile());


    }
}
