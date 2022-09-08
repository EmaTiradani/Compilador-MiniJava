package syntactic;

import Exceptions.SyntacticException;
import lexycal.AnalizadorLexico;
import Exceptions.ExcepcionLexica;
import lexycal.Token;

import java.io.IOException;

import static lexycal.TokenId.EOF;

public class SyntacticParser {

    AnalizadorLexico analizadorLexico;
    boolean sinErrores = true;
    Token tokenActual;
    public SyntacticParser(AnalizadorLexico analizadorLexico) {
        this.analizadorLexico = analizadorLexico;
    }

    public void match(String nombreToken) throws ExcepcionLexica, IOException, SyntacticException {
        if(nombreToken.equals(tokenActual.getTokenId()))
            tokenActual = analizadorLexico.getToken();
        else
            throw new SyntacticException();
    }


    public void startAnalysis() throws IOException {
        do{
            tokenActual = new Token(null, "", 0);
            try {
                tokenActual = analizadorLexico.getToken();
                //System.out.print("(" + token.getTokenId().toString() + "," + token.getLexema() + "," + token.getLinea() + ") \n");
            } catch (ExcepcionLexica e){
                sinErrores = false;
                System.out.print(e.getMessage());
                analizadorLexico.actualizarCaracterActual();
            };
        }while(!(tokenActual.getTokenId() == EOF));
        //while(!fileManager.reachedEndOfFile()); //Cuando llega al EOF el lexico tiene que tirar EOF constante, ahi corta



    }


}
