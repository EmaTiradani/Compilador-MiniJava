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
        nextToken();
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
        matchFirsts("ClaseConcreta");
        match(idClase);
        heredaDe();
        implementaA();
        match(punt_llaveIzq);
        listaMiembros();
        match(punt_llaveDer);
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
        if(tokenActual.getTokenId() == kw_extends){
            match(kw_extends);
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
            //nextToken();
            encabezadoMetodo();
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
            match(punt_puntoYComa);
        }else throw new SyntacticException("; o sentencia", tokenActual);
    }

    private void asignacionOLlamada() throws ExcepcionLexica, SyntacticException, IOException {
        if(firsts.isFirst("TipoDeAsignacion",tokenActual)){
            tipoDeAsignacion();
            expresion();
        }else{
            // Epsilon
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
        if(firsts.isFirst("OperadorUnario", tokenActual)){
            operadorUnario();
            operando();
        }else{
            operando();
        }

    }

    private void operando() throws ExcepcionLexica, SyntacticException, IOException {
        //matchFirsts("Operando");
        if(firsts.isFirst("Literal",tokenActual)){
            literal();
        }else if(firsts.isFirst("Acceso", tokenActual)){
            acceso();
        }else
            throw new SyntacticException("Operando", tokenActual);
    }

    private void literal() throws ExcepcionLexica, SyntacticException, IOException {
        matchFirsts("Literal");
    }

    private void operadorUnario() throws ExcepcionLexica, SyntacticException, IOException {
        matchFirsts("OperadorUnario");
    }

    private void tipoDeAsignacion() throws ExcepcionLexica, SyntacticException, IOException {
        matchFirsts("TipoDeAsignacion");
    }

    private void acceso() throws ExcepcionLexica, SyntacticException, IOException {
        primario();
        encadenadoOpt();
    }

    private void primario() throws ExcepcionLexica, SyntacticException, IOException {
        if(tokenActual.getTokenId() == kw_this){ // Acceso this
            match(kw_this);
        }else if(tokenActual.getTokenId() == idMetVar){
            match(idMetVar);
            accesoVarOMetodo();
        }else if(tokenActual.getTokenId() == kw_new){ //Acceso constructor
            match(kw_new);
            match(idClase);
            match(punt_parentIzq);
            match(punt_parentDer);
        }else if(tokenActual.getTokenId() == idClase) {
            accesoMetodoEstatico();
        }else if(tokenActual.getTokenId() == punt_parentIzq){
            expresionParentizada();
        }else{
            throw new SyntacticException("Token de primario", tokenActual);
        }
    }

    private void expresionParentizada() throws ExcepcionLexica, SyntacticException, IOException {
        match(punt_parentIzq);
        expresion();
        match(punt_parentDer);
    }

    private void accesoMetodoEstatico() throws ExcepcionLexica, SyntacticException, IOException {
        match(idClase);
        match(punt_punto);
        match(idMetVar);
        argsActuales();
    }

    private void accesoVarOMetodo() throws ExcepcionLexica, SyntacticException, IOException {
        if(tokenActual.getTokenId() == punt_parentIzq)
            argsActuales();
        else{
            // Epsilon
        }

    }

    private void encadenadoOpt() throws ExcepcionLexica, SyntacticException, IOException {
        if(tokenActual.getTokenId() == punt_punto){
            match(punt_punto);
            match(idMetVar);
            varOMetEncadenado();
        }else{
            // Epsilon
        }
    }

    private void varOMetEncadenado() throws ExcepcionLexica, SyntacticException, IOException {
        if(tokenActual.getTokenId() == punt_parentIzq){
            argsActuales();
            encadenadoOpt();
        }else{
            encadenadoOpt();
        }
    }

    private void argsActuales() throws ExcepcionLexica, SyntacticException, IOException {
        match(punt_parentIzq);
        listaExpsOpt();
        match(punt_parentDer);
    }

    private void listaExpsOpt() throws ExcepcionLexica, SyntacticException, IOException {
        if(firsts.isFirst("Operando", tokenActual)){
            listaExps();
        }else{
            // Epsilon
        }
    }

    private void listaExps() throws ExcepcionLexica, SyntacticException, IOException {
        expresion();
        listaExpsFact();
    }

    private void listaExpsFact() throws ExcepcionLexica, SyntacticException, IOException {
        if(tokenActual.getTokenId() == punt_coma){
            match(punt_coma);
            listaExps();
        }else{
            // Epsilon
        }
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
        if(firsts.isFirst("ExpresionUnaria", tokenActual)){
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
