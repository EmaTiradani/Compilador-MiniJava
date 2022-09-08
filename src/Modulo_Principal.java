import lexycal.AnalizadorLexico;
import Exceptions.ExcepcionLexica;
import lexycal.Token;
import FileManager.FileManager;

import java.io.IOException;

public class Modulo_Principal {

    public static void main(String[] args) throws IOException {

        boolean sinErrores = true;

        FileManager fileManager = new FileManager(args[0]);
        AnalizadorLexico analizadorLexico = new AnalizadorLexico(fileManager);

        do{
            Token token = new Token(null, "", 0);
            try {
                token = analizadorLexico.getToken();
                System.out.print("(" + token.getTokenId().toString() + "," + token.getLexema() + "," + token.getLinea() + ") \n");
            } catch (ExcepcionLexica e){
                sinErrores = false;
                System.out.print(e.getMessage());
                analizadorLexico.actualizarCaracterActual();
            };
        }while(!fileManager.reachedEndOfFile());

        if(sinErrores){
            System.out.print("\n[SinErrores]");
        }
    }
}
