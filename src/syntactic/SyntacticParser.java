package syntactic;

import Exceptions.SyntacticException;
import lexycal.AnalizadorLexico;
import Exceptions.ExcepcionLexica;
import lexycal.Token;
import lexycal.TokenId;

import java.io.IOException;

import static lexycal.TokenId.EOF;


public class SyntacticParser {

    AnalizadorLexico analizadorLexico;
    boolean sinErrores = true;
    Token tokenActual;
    Firsts firsts;
    public SyntacticParser(AnalizadorLexico analizadorLexico) throws ExcepcionLexica, IOException {
        this.analizadorLexico = analizadorLexico;
        firsts = new Firsts();
        tokenActual = analizadorLexico.getToken();
        //inicial();
    }

    public void match(String expectedToken) throws ExcepcionLexica, IOException, SyntacticException {
        if(expectedToken.equals(tokenActual.getTokenId()))
            tokenActual = analizadorLexico.getToken();
        else
            throw new SyntacticException(expectedToken, tokenActual);
    }

    public void startAnalysis(){
        inicial();
    }


    /*public void startAnalysis() throws IOException {
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
    }*/

    private void inicial() throws ExcepcionLexica, SyntacticException, IOException {
        listaClases();
        match("EOF");
    }

    private void listaClases(){

    }
}
