package syntactic;

import lexycal.AnalizadorLexico;
import lexycal.ExcepcionLexica;
import lexycal.Token;

import java.io.IOException;

public class SyntacticParser {

    AnalizadorLexico analizadorLexico;
    boolean sinErrores = true;

    public SyntacticParser(AnalizadorLexico analizadorLexico) {
        this.analizadorLexico = analizadorLexico;
    }


    public void startAnalysis() throws IOException {
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
        }while(!fileManager.reachedEndOfFile()); //Cuando llega al EOF el lexico tiene que tirar EOF constante, ahi cortas
    }


}
