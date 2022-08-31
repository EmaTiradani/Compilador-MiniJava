import Analizador_Lexico.AnalizadorLexico;
import Analizador_Lexico.ExcepcionLexica;
import Analizador_Lexico.Token;
import FileManager.FileManager;

import java.io.IOException;

public class Modulo_Principal {

    public static void main(String[] args) throws IOException {

        boolean sinErrores = true;

        //FileManager fileManager = new FileManager(args[0]);
        FileManager fileManager = new FileManager("C:\\Users\\ema_c\\Desktop\\Compiladores\\Etapa 1\\Compilador\\MiniJavaSourceCode.txt"); //Cable
        //FileManager fileManager = new FileManager("C:\\Users\\default.LAPTOP-9ASHTB0Q\\Desktop\\Lenguajes\\Proyecto\\proyecto-ldp\\Compilador-MiniJava\\MiniJavaSourceCode.txt");
        //C:\Users\ema_c\Desktop\Compiladores\Etapa 1\MiniJavaSourceCode.txt
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
