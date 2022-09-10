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
        if(expectedToken.equals(tokenActual.getLexema()))
            nextToken();
        else
            throw new SyntacticException(expectedToken, tokenActual);
    }

    public void matchFirsts(String head) throws SyntacticException, ExcepcionLexica, IOException {
        if(firsts.isFirst(head, tokenActual))
            nextToken();
        else
            throw new SyntacticException(head, tokenActual);
    }

    private void nextToken() throws ExcepcionLexica, IOException {
        tokenActual = analizadorLexico.getToken();
    }

    public void startAnalysis() throws ExcepcionLexica, SyntacticException, IOException {
        inicial();
    }

    private void inicial() throws ExcepcionLexica, SyntacticException, IOException {
        listaClases();
        match("EOF");
    }

    private void listaClases() throws ExcepcionLexica, SyntacticException, IOException {
        //if(firsts.isFirst("ListaClases", tokenActual))
        clase();
        listaClases();
    }

    private void clase() throws ExcepcionLexica, SyntacticException, IOException {
        //Try?
        claseConcreta();
        interface_();
    }
    private void claseConcreta() throws ExcepcionLexica, SyntacticException, IOException {
        /*matchFirsts("ClaseConcreta");
        matchFirsts("Clase");*/
        //firsts.isFirst("ClaseConcreta", tokenActual);
        //nextToken();
        matchFirsts("ClaseConcreta");
        matchFirsts("IdClase");
        heredaDe();
        implementaA();
        match("{"); //Deberia funcionar con un solo coso, arreglarlo
        listaEncabezados();
        match("}"); //rt
    }
    private void interface_() throws ExcepcionLexica, SyntacticException, IOException {
        //matchFirsts("Interface");
        firsts.isFirst("Interface", tokenActual);
        match("IdClase");
        extiendeA();
        match("{");
        listaEncabezados();
        match("}");
    }
    private void heredaDe() throws ExcepcionLexica, SyntacticException, IOException {
        if(firsts.isFirst("HeredaDe", tokenActual)){
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
            //metodo();
        }
    }
    private void atributo(){
        //visibilidad();

    }
}
