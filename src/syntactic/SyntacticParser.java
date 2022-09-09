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
        //tokenActual = analizadorLexico.getToken();
        nextToken();
        //inicial();
    }

    public void match(String expectedToken) throws ExcepcionLexica, IOException, SyntacticException {
        if(expectedToken.equals(tokenActual.getTokenId()))
            nextToken();
        else
            throw new SyntacticException(expectedToken, tokenActual);
    }

    private void nextToken() throws ExcepcionLexica, IOException {
        tokenActual = analizadorLexico.getToken();
    }


    public void startAnalysis() throws ExcepcionLexica, SyntacticException, IOException {
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
        //if(firsts.isFirst("ListaClases", tokenActual))
        clase();
        listaClases();
    }

    private void clase(){
        //Try?
        claseConcreta();
        interface();
    }
    private void claseConcreta() throws ExcepcionLexica, SyntacticException, IOException {
        matchFirsts("ClaseConcreta");
        matchFirsts("Clase");
        heredaDe();
        implementaA();
        match("{"); //Deberia funcionar con un solo coso, arreglarlo
        listaEncabezados();
        match("}"); //rt
    }
    private void interface_() throws ExcepcionLexica, SyntacticException, IOException {
        matchFirsts("Interface");
        match("IdClase");
        extiendeA();
        match("{");
        listaEncabezados();
        match("}");
    }
    private void heredaDe() throws ExcepcionLexica, SyntacticException, IOException {
        if(firsts.isFirst(tokenActual)){
             match("idClase");
        } else{
          //No hago nada por ahora porque va a ε
        }
    }
    private void implementaA() throws ExcepcionLexica, SyntacticException, IOException {
        if(firsts.isFirst("ImplementaA", tokenActual)){
            listaTipoReferencia();
        }else{
            //No hago nada por ahora porque va a ε
        }
    }
    private void extiendeA() throws ExcepcionLexica, SyntacticException, IOException {
        if(firsts.isFirst("ExtiendeA",tokenActual)){
            listaTipoReferencia();
        }else{
            //No hago nada por ahora porque va a ε
        }
    }
    private void listaTipoReferencia() throws ExcepcionLexica, SyntacticException, IOException {
        match("idClase");
        listaTipoReferenciaFact();
    }
    private void listaTipoReferenciaFact() throws ExcepcionLexica, SyntacticException, IOException {
        if(firsts.isFirst("ListaTipoReferenciaFact", tokenActual)){
            //match(","); ya lo chequeo en el if
            listaTipoReferencia();
        }else{
            //No hago nada por ahora porque va a ε
        }
    }

    private void listaMiembros(){
        if(firsts.isFirst("Miembro", tokenActual)){
            miembro();
            listaMiembros();
        }else{
            //No hago nada por ahora porque va a ε
        }
    }
    private void listaEncabezados() throws ExcepcionLexica, IOException, SyntacticException {
        if(firsts.isFirst("EncabezadoMetodo",tokenActual)){
            nextToken();
            match(";");
            listaEncabezados();
        }else{
            //No hago nada por ahora porque va a ε
        }
    }
    private void miembro(){
        if(firsts.isFirst("Atributo", tokenActual)){
            //nextToken();//No se si sera necesario, depende de el body de atributo()
            atributo();
        }else if(firsts.isFirst("Metodo", tokenActual)){
            //nextToken();
            metodo();
        }
    }
    private void atributo(){
        visibilidad();

    }
}
