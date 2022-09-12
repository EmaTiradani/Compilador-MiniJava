package syntactic;

import Exceptions.SyntacticException;
import lexycal.AnalizadorLexico;
import Exceptions.ExcepcionLexica;
import lexycal.Token;
import lexycal.TokenId;

import java.io.IOException;

import static lexycal.TokenId.*;

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

    public void match(TokenId expectedToken) throws ExcepcionLexica, IOException, SyntacticException {
        if(expectedToken.equals(tokenActual.getTokenId()))
            nextToken();
        else
            throw new SyntacticException(expectedToken.toString(), tokenActual);
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
        match(EOF);
    }

    private void listaClases() throws ExcepcionLexica, SyntacticException, IOException {
        //if(firsts.isFirst("ListaClases", tokenActual))
        if(firsts.isFirst("ClaseConcreta", tokenActual) || firsts.isFirst("Interface", tokenActual)){
            clase();
            listaClases();
        }else{
            //No hago nada, epsilon
        }

    }

    private void clase() throws ExcepcionLexica, SyntacticException, IOException {
        if(firsts.isFirst("ClaseConcreta", tokenActual)){
            claseConcreta();
        }else{
            interface_();
        }
    }
    private void claseConcreta() throws ExcepcionLexica, SyntacticException, IOException {
        /*matchFirsts("ClaseConcreta");
        matchFirsts("Clase");*/
        //firsts.isFirst("ClaseConcreta", tokenActual);
        //nextToken();
        matchFirsts("ClaseConcreta");
        match(idClase);
        heredaDe();
        implementaA();
        match(punt_llaveIzq); //Deberia funcionar con un solo coso, arreglarlo
        listaEncabezados();
        match(punt_llaveDer); //rt
    }
    private void interface_() throws ExcepcionLexica, SyntacticException, IOException {
        //matchFirsts("Interface");
        firsts.isFirst("Interface", tokenActual);
        match(idClase);
        extiendeA();
        match(punt_llaveIzq);
        listaEncabezados();
        match(punt_llaveDer);
    }
    private void heredaDe() throws ExcepcionLexica, SyntacticException, IOException {
        if(firsts.isFirst("HeredaDe", tokenActual)){
             match(idClase);
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
        match(idClase);
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
            match(punt_puntoYComa);
            listaEncabezados();
        }else{
            //No hago nada por ahora porque va a ε
        }
    }
    private void miembro() throws ExcepcionLexica, SyntacticException, IOException {
        if(firsts.isFirst("Atributo", tokenActual)){
            //nextToken();//No se si sera necesario, depende de el body de atributo()
            atributo();
        }else if(firsts.isFirst("Metodo", tokenActual)){
            metodo();
        }
    }

    private void atributo() throws ExcepcionLexica, SyntacticException, IOException {
        visibilidad();
        tipo();
        listaDecAtrs();
        match(punt_puntoYComa);
    }

    private void metodo() throws ExcepcionLexica, SyntacticException, IOException {
        encabezadoMetodo();
        bloque();
    }

    private void bloque() throws ExcepcionLexica, SyntacticException, IOException {
        match(punt_llaveIzq);
        listaSentencias();
        match(punt_llaveDer);
    }

    private void listaSentencias() {
        if(firsts.isFirst("Sentencia", tokenActual)){
            sentencia();
            listaSentencias();
        }else{
            // Epsilon
        }
    }

    private void sentencia() throws ExcepcionLexica, SyntacticException, IOException {
        if(tokenActual.getTokenId() == punt_puntoYComa){
            match(punt_puntoYComa);
        }else if(tokenActual.getTokenId() == kw_var){
            varLocal();
        }else if(tokenActual.getTokenId() == kw_return){
            return_();
        }else if(tokenActual.getTokenId() == kw_if){
            if_();
        }else if(tokenActual.getTokenId() == kw_while){
            while_();
        }else if(tokenActual.getTokenId() == punt_llaveIzq){
            bloque();
        }else if(firsts.isFirst("Acceso", tokenActual)){
            acceso();
            asignacionOLlamada();
        }
    }

    private void asignacionOLlamada() {
    }

    private void acceso() {
    }

    private void while_() {
    }

    private void if_() {
    }

    private void return_() {
    }

    private void varLocal() {
    }

    private void encabezadoMetodo() {
    }

    private void listaDecAtrs() {
    }

    private void tipo() {
    }

    private void visibilidad() {
    }
}
