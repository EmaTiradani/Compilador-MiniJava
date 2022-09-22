package syntactic;

import exceptions.SyntacticException;
import lexycal.AnalizadorLexico;
import exceptions.LexicalException;
import lexycal.Token;
import lexycal.TokenId;

import java.io.IOException;

import static lexycal.TokenId.*;

public class SyntacticParser {
    AnalizadorLexico analizadorLexico;
    Token tokenActual;
    Firsts firsts;
    public SyntacticParser(AnalizadorLexico analizadorLexico) throws LexicalException, IOException {
        this.analizadorLexico = analizadorLexico;
        firsts = new Firsts();
        nextToken();
    }

    public void match(TokenId expectedToken) throws LexicalException, IOException, SyntacticException {
        if(expectedToken.equals(tokenActual.getTokenId()))
            nextToken();
        else
            throw new SyntacticException(expectedToken.toString(), tokenActual);
    }

    public void matchFirsts(String head) throws SyntacticException, LexicalException, IOException {
        if(firsts.isFirst(head, tokenActual))
            nextToken();
        else
            throw new SyntacticException(head, tokenActual);
    }

    private void nextToken() throws LexicalException, IOException {
        tokenActual = analizadorLexico.getToken();
    }

    public void startAnalysis() throws LexicalException, SyntacticException, IOException {
        inicial();
    }

    private void inicial() throws LexicalException, SyntacticException, IOException {
        listaClases();
        match(EOF);
    }

    private void listaClases() throws LexicalException, SyntacticException, IOException {
        if(firsts.isFirst("ClaseConcreta", tokenActual) || firsts.isFirst("Interface", tokenActual)){
            clase();
            listaClases();
        }else{
            //No hago nada, epsilon
        }
    }

    private void clase() throws LexicalException, SyntacticException, IOException {
        if(firsts.isFirst("ClaseConcreta", tokenActual)){
            claseConcreta();
        }else{
            interface_();
        }
    }
    private void claseConcreta() throws LexicalException, SyntacticException, IOException {
        matchFirsts("ClaseConcreta");
        Token iDC = tokenActual; // Semantic
        match(idClase);
        heredaDe();
        implementaA();
        match(punt_llaveIzq);
        listaMiembros();
        match(punt_llaveDer);
    }
    private void interface_() throws LexicalException, SyntacticException, IOException {
        if(firsts.isFirst("Interface", tokenActual)) {
            match(kw_interface);
            match(idClase);
            extiendeA();
            match(punt_llaveIzq);
            listaEncabezados();
            match(punt_llaveDer);
        }else{
            throw new SyntacticException("interface", tokenActual);
        }
    }
    private void heredaDe() throws LexicalException, SyntacticException, IOException {
        if(firsts.isFirst("HeredaDe", tokenActual)){
            match(kw_extends);
            match(idClase);
        } else{
          // ε
        }
    }
    private void implementaA() throws LexicalException, SyntacticException, IOException {
        if(firsts.isFirst("ImplementaA", tokenActual)){
            match(kw_implements);
            listaTipoReferencia();
        }else{
            //No hago nada por ahora porque va a ε
        }
    }
    private void extiendeA() throws LexicalException, SyntacticException, IOException {
        if(tokenActual.getTokenId() == kw_extends){
            match(kw_extends);
            listaTipoReferencia();
        }else{
            //No hago nada por ahora porque va a ε
        }
    }
    private void listaTipoReferencia() throws LexicalException, SyntacticException, IOException {
        match(idClase);
        listaTipoReferenciaFact();
    }
    private void listaTipoReferenciaFact() throws LexicalException, SyntacticException, IOException {
        if(tokenActual.getLexema().equals(",")){
            match(punt_coma);
            listaTipoReferencia();
        }else{
            //No hago nada por ahora porque va a ε
        }
    }

    private void listaMiembros() throws LexicalException, SyntacticException, IOException {
        if(firsts.isFirst("Miembro", tokenActual)){
            miembro();
            listaMiembros();
        }else{
            //No hago nada por ahora porque va a ε
        }
    }
    private void listaEncabezados() throws LexicalException, IOException, SyntacticException {
        if(firsts.isFirst("EncabezadoMetodo",tokenActual)){
            encabezadoMetodo();
            match(punt_puntoYComa);
            listaEncabezados();
        }else{
            //No hago nada por ahora porque va a ε
        }
    }
    private void miembro() throws LexicalException, SyntacticException, IOException {
        if(firsts.isFirst("Atributo", tokenActual)){
            atributo();
        }else if(firsts.isFirst("Metodo", tokenActual)){
            metodo();
        }else{
            throw new SyntacticException("Atributo o Metodo", tokenActual);
        }
    }

    private void atributo() throws LexicalException, SyntacticException, IOException {
        visibilidad();
        tipo();
        listaDecAtrs();
        match(punt_puntoYComa);
    }

    private void metodo() throws LexicalException, SyntacticException, IOException {
        encabezadoMetodo();
        bloque();
    }

    private void bloque() throws LexicalException, SyntacticException, IOException {
        match(punt_llaveIzq);
        listaSentencias();
        match(punt_llaveDer);
    }

    private void listaSentencias() throws LexicalException, SyntacticException, IOException {
        if(firsts.isFirst("Sentencia", tokenActual)){
            sentencia();
            listaSentencias();
        }else{
            // Epsilon
        }
    }

    private void sentencia() throws LexicalException, SyntacticException, IOException {
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
        }else throw new SyntacticException("; o Sentencia", tokenActual);
    }

    private void asignacionOLlamada() throws LexicalException, SyntacticException, IOException {
        if(firsts.isFirst("TipoDeAsignacion",tokenActual)){
            tipoDeAsignacion();
            expresion();
        }else{
            // Epsilon
        }
    }

    private void expresion() throws LexicalException, SyntacticException, IOException {
        expresionUnaria();
        expresionRec();
    }

    private void expresionRec() throws LexicalException, SyntacticException, IOException {
        if(firsts.isFirst("OperadorBinario", tokenActual)){
            operadorBinario();
            expresionUnaria();
            expresionRec();
        }else{
            // Epsilon
        }

    }

    private void operadorBinario() throws LexicalException, SyntacticException, IOException {
        matchFirsts("OperadorBinario");
    }

    private void expresionUnaria() throws LexicalException, SyntacticException, IOException {
        if(firsts.isFirst("OperadorUnario", tokenActual)){
            operadorUnario();
            operando();
        }else{
            operando();
        }

    }

    private void operando() throws LexicalException, SyntacticException, IOException {
        if(firsts.isFirst("Literal",tokenActual)){
            literal();
        }else if(firsts.isFirst("Acceso", tokenActual)){
            acceso();
        }else
            throw new SyntacticException("Operando", tokenActual);
    }

    private void literal() throws LexicalException, SyntacticException, IOException {
        matchFirsts("Literal");
    }

    private void operadorUnario() throws LexicalException, SyntacticException, IOException {
        matchFirsts("OperadorUnario");
    }

    private void tipoDeAsignacion() throws LexicalException, SyntacticException, IOException {
        matchFirsts("TipoDeAsignacion");
    }

    private void acceso() throws LexicalException, SyntacticException, IOException {
        primario();
        encadenadoOpt();
    }

    private void primario() throws LexicalException, SyntacticException, IOException {
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
            throw new SyntacticException("Token Primario", tokenActual);
        }
    }

    private void expresionParentizada() throws LexicalException, SyntacticException, IOException {
        match(punt_parentIzq);
        expresion();
        match(punt_parentDer);
    }

    private void accesoMetodoEstatico() throws LexicalException, SyntacticException, IOException {
        match(idClase);
        match(punt_punto);
        match(idMetVar);
        argsActuales();
    }

    private void accesoVarOMetodo() throws LexicalException, SyntacticException, IOException {
        if(tokenActual.getTokenId() == punt_parentIzq)
            argsActuales();
        else{
            // Epsilon
        }

    }

    private void encadenadoOpt() throws LexicalException, SyntacticException, IOException {
        if(tokenActual.getTokenId() == punt_punto){
            match(punt_punto);
            match(idMetVar);
            varOMetEncadenado();
        }else{
            // Epsilon
        }
    }

    private void varOMetEncadenado() throws LexicalException, SyntacticException, IOException {
        if(tokenActual.getTokenId() == punt_parentIzq){
            argsActuales();
            encadenadoOpt();
        }else{
            encadenadoOpt();
        }
    }

    private void argsActuales() throws LexicalException, SyntacticException, IOException {
        match(punt_parentIzq);
        listaExpsOpt();
        match(punt_parentDer);
    }

    private void listaExpsOpt() throws LexicalException, SyntacticException, IOException {
        if(firsts.isFirst("Operando", tokenActual)){
            listaExps();
        }else{
            // Epsilon
        }
    }

    private void listaExps() throws LexicalException, SyntacticException, IOException {
        expresion();
        listaExpsFact();
    }

    private void listaExpsFact() throws LexicalException, SyntacticException, IOException {
        if(tokenActual.getTokenId() == punt_coma){
            match(punt_coma);
            listaExps();
        }else{
            // Epsilon
        }
    }


    private void while_() throws LexicalException, SyntacticException, IOException {
        match(kw_while);
        match(punt_parentIzq);
        expresion();
        match(punt_parentDer);
        sentencia();
    }

    private void if_() throws LexicalException, SyntacticException, IOException {
        match(kw_if);
        match(punt_parentIzq);
        expresion();
        match(punt_parentDer);
        sentencia();
        else_();
    }

    private void else_() throws LexicalException, SyntacticException, IOException {
        if(tokenActual.getTokenId() == kw_else){
            match(kw_else);
            sentencia();
        }else{
            // Epsilon
        }
    }

    private void return_() throws LexicalException, SyntacticException, IOException {
        match(kw_return);
        expresionOpt();
        match(punt_puntoYComa);
    }

    private void expresionOpt() throws LexicalException, SyntacticException, IOException {
        if(firsts.isFirst("ExpresionUnaria", tokenActual)){
            expresion();
        }else{
            // Epsilon
        }
    }

    private void varLocal() throws LexicalException, SyntacticException, IOException {
        match(kw_var);
        match(idMetVar);
        match(asignacion);
        expresion();
        match(punt_puntoYComa); //TODO chequear esto, no cambio nada
    }

    private void encabezadoMetodo() throws LexicalException, SyntacticException, IOException {
        estaticoOpt();
        tipoMetodo();
        match(idMetVar);
        argsFormales();
    }

    private void estaticoOpt() throws LexicalException, SyntacticException, IOException {
        if(tokenActual.getTokenId() == kw_static){
            match(kw_static);
        }else{
            // Epsilon
        }
    }

    private void tipoMetodo() throws LexicalException, SyntacticException, IOException {
        if(firsts.isFirst("Tipo", tokenActual) || tokenActual.getTokenId() == kw_void) {
            if (firsts.isFirst("Tipo", tokenActual)) {
                tipo();
            } else {
                match(kw_void);
            }
        }else{
            throw new SyntacticException("TipoMetodo", tokenActual);
        }
    }

    private void argsFormales() throws LexicalException, SyntacticException, IOException {
        match(punt_parentIzq);
        listaArgsFormalesOpt();
        match(punt_parentDer);
    }

    private void listaArgsFormalesOpt() throws LexicalException, SyntacticException, IOException {
        if(firsts.isFirst("ListaArgsFormales",tokenActual)){
            listaArgsFormales();
        }
    }

    private void listaArgsFormales() throws LexicalException, SyntacticException, IOException {
        argFormal();
        listaArgsFormalesFact();
    }

    private void listaArgsFormalesFact() throws LexicalException, SyntacticException, IOException {
        if(tokenActual.getTokenId() == punt_coma){
            match(punt_coma);
            listaArgsFormales();
        }else{
            // Epsilon
        }
    }

    private void argFormal() throws LexicalException, SyntacticException, IOException {
        tipo();
        match(idMetVar);
    }

    private void listaDecAtrs() throws LexicalException, SyntacticException, IOException {
        match(idMetVar);
        listaDecAtrsFact();
    }

    private void listaDecAtrsFact() throws LexicalException, SyntacticException, IOException {
        if(tokenActual.getTokenId() == punt_coma){
            match(punt_coma);
            listaDecAtrs();
        }else {
            // Epsilon
        }
    }

    private void tipo() throws LexicalException, SyntacticException, IOException {
        if(firsts.isFirst("TipoPrimitivo", tokenActual) || tokenActual.getTokenId() == idClase) {
            if (firsts.isFirst("TipoPrimitivo", tokenActual)) {
                tipoPrimitivo();
            } else {
                match(idClase);
            }
        }else{
            throw new SyntacticException("Tipo", tokenActual);
        }
    }

    private void tipoPrimitivo() throws LexicalException, SyntacticException, IOException {
        matchFirsts("TipoPrimitivo");
    }

    private void visibilidad() throws LexicalException, SyntacticException, IOException {
        if(tokenActual.getTokenId() == kw_private || tokenActual.getTokenId() == kw_public) {
            if (tokenActual.getTokenId() == kw_private) {
                match(kw_private);
            } else {
                match(kw_public);
            }
        }else{
            throw new SyntacticException("Visibilidad",tokenActual);
        }
    }
}
