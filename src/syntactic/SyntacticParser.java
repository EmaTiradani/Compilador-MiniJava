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
        listaMiembros();
        match(punt_llaveDer); //rt
    }
    private void interface_() throws ExcepcionLexica, SyntacticException, IOException {
        if(firsts.isFirst("Interface", tokenActual)) {
            match(kw_interface);
            match(idClase);
            extiendeA();
            match(punt_llaveIzq);
            listaEncabezados();
            match(punt_llaveDer);
        }else{
            throw new SyntacticException("kw_interface", tokenActual); //Poner bien la excepcion
        }
    }
    private void heredaDe() throws ExcepcionLexica, SyntacticException, IOException {
        if(firsts.isFirst("HeredaDe", tokenActual)){
            match(kw_extends);
            match(idClase);
        } else{
          //No hago nada por ahora porque va a ε
        }
    }
    private void implementaA() throws ExcepcionLexica, SyntacticException, IOException {
        if(firsts.isFirst("ImplementaA", tokenActual)){
            match(kw_implements);
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
        if(tokenActual.getLexema().equals(",")){
            match(punt_coma);
            listaTipoReferencia();
        }else{
            //No hago nada por ahora porque va a ε
        }
    }

    private void listaMiembros() throws ExcepcionLexica, SyntacticException, IOException {
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
        }else{
            throw new SyntacticException("hola", tokenActual);
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

    private void listaSentencias() throws ExcepcionLexica, SyntacticException, IOException {
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
        }else throw new SyntacticException("; o sentencia", tokenActual);
    }

    private void asignacionOLlamada() throws ExcepcionLexica, SyntacticException, IOException {
        if(firsts.isFirst("AsignacionOLlamada",tokenActual)){
            tipoDeAsignacion();
            expresion();
        }
    }

    private void expresion() throws ExcepcionLexica, SyntacticException, IOException {
        expresionUnaria();
        expresionRec();
    }

    private void expresionRec() throws ExcepcionLexica, SyntacticException, IOException {
        if(firsts.isFirst("OperadorBinario", tokenActual)){
            operadorBinario();
            expresionUnaria();
            expresionRec();
        }else{
            // Epsilon
        }

    }

    private void operadorBinario() throws ExcepcionLexica, SyntacticException, IOException {
        matchFirsts("OperadorBinario");
    }

    private void expresionUnaria() throws ExcepcionLexica, SyntacticException, IOException {
        operadorUnario();
        operando();
    }

    private void operando() {
    }

    private void operadorUnario() throws ExcepcionLexica, SyntacticException, IOException {
        matchFirsts("OperadorUnario");
    }

    private void tipoDeAsignacion() throws ExcepcionLexica, SyntacticException, IOException {
        matchFirsts("TipoDeAsignacion");
    }

    private void acceso() {
    }

    private void while_() throws ExcepcionLexica, SyntacticException, IOException {
        match(kw_while);
        match(punt_parentIzq);
        expresion();
        match(punt_parentDer);
        sentencia();
    }

    private void if_() throws ExcepcionLexica, SyntacticException, IOException {
        match(kw_if);
        match(punt_parentIzq);
        expresion();
        match(punt_parentDer);
        sentencia();
        else_();
    }

    private void else_() throws ExcepcionLexica, SyntacticException, IOException {
        if(tokenActual.getTokenId() == kw_else){
            match(kw_else);
            sentencia();
        }else{
            // Epsilon
        }
    }

    private void return_() throws ExcepcionLexica, SyntacticException, IOException {
        match(kw_return);
        expresionOpt();
    }

    private void expresionOpt() throws ExcepcionLexica, SyntacticException, IOException {
        if(firsts.isFirst("Expresion", tokenActual)){
            expresion();
        }else{
            // Epsilon
        }
    }

    private void varLocal() throws ExcepcionLexica, SyntacticException, IOException {
        match(kw_var);
        match(idMetVar);
        match(asignacion);
        expresion();
    }

    private void encabezadoMetodo() throws ExcepcionLexica, SyntacticException, IOException {
        estaticoOpt();
        tipoMetodo();
        match(idMetVar);
        argsFormales();
    }

    private void estaticoOpt() throws ExcepcionLexica, SyntacticException, IOException {
        if(tokenActual.getTokenId() == kw_static){
            match(kw_static);
        }else{
            // Epsilon
        }
    }

    private void tipoMetodo() throws ExcepcionLexica, SyntacticException, IOException {
        if(firsts.isFirst("Tipo", tokenActual) || tokenActual.getTokenId() == kw_void) {
            if (firsts.isFirst("Tipo", tokenActual)) {
                tipo();
            } else {
                match(kw_void);
            }
        }else{
            throw new SyntacticException("tipoMetodo", tokenActual);
        }
    }

    private void argsFormales() throws ExcepcionLexica, SyntacticException, IOException {
        match(punt_parentIzq);
        listaArgsFormalesOpt();
        match(punt_parentDer);
    }

    private void listaArgsFormalesOpt() throws ExcepcionLexica, SyntacticException, IOException {
        if(firsts.isFirst("ListaArgsFormales",tokenActual)){
            listaArgsFormales();
        }
    }

    private void listaArgsFormales() throws ExcepcionLexica, SyntacticException, IOException {
        argFormal();
        listaArgsFormalesFact();
    }

    private void listaArgsFormalesFact() throws ExcepcionLexica, SyntacticException, IOException {
        if(tokenActual.getTokenId() == punt_coma){
            match(punt_coma);
            listaArgsFormales();
        }else{
            // Epsilon
        }
    }

    private void argFormal() throws ExcepcionLexica, SyntacticException, IOException {
        tipo();
        match(idMetVar);
    }

    private void listaDecAtrs() throws ExcepcionLexica, SyntacticException, IOException {
        match(idMetVar);
        listaDecAtrsFact();
    }

    private void listaDecAtrsFact() throws ExcepcionLexica, SyntacticException, IOException {
        if(tokenActual.getTokenId() == punt_coma){
            match(punt_coma);
            listaDecAtrs();
        }else {
            // Epsilon
        }
    }

    private void tipo() throws ExcepcionLexica, SyntacticException, IOException {
        if(firsts.isFirst("TipoPrimitivo", tokenActual) || tokenActual.getTokenId() == idClase) {
            if (firsts.isFirst("TipoPrimitivo", tokenActual)) {
                tipoPrimitivo();
            } else {
                match(idClase);
            }
        }else{
            throw new SyntacticException("tipo", tokenActual);
        }
    }

    private void tipoPrimitivo() throws ExcepcionLexica, SyntacticException, IOException {
        matchFirsts("TipoPrimitivo");
    }

    private void visibilidad() throws ExcepcionLexica, SyntacticException, IOException {
        if(tokenActual.getTokenId() == kw_private || tokenActual.getTokenId() == kw_public) {
            if (tokenActual.getTokenId() == kw_private) {
                match(kw_private);
            } else {
                match(kw_public);
            }
        }else{
            throw new SyntacticException("a",tokenActual);
        }
    }
}
