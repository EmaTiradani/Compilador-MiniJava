package syntactic;


import TablaDeSimbolos.*;
import exceptions.SemanticException;
import exceptions.SyntacticException;
import lexycal.AnalizadorLexico;
import exceptions.LexicalException;
import lexycal.Token;
import lexycal.TokenId;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    public void startAnalysis() throws LexicalException, SyntacticException, IOException, SemanticException {
        inicial();
    }

    private void inicial() throws LexicalException, SyntacticException, IOException, SemanticException {
        listaClases();
        TablaDeSimbolos.print();
        match(EOF);
    }

    private void listaClases() throws LexicalException, SyntacticException, IOException, SemanticException {
        if(firsts.isFirst("ClaseConcreta", tokenActual) || firsts.isFirst("Interface", tokenActual)){
            clase();
            listaClases();
        }else{
            //No hago nada, epsilon
        }
    }

    private void clase() throws LexicalException, SyntacticException, IOException, SemanticException {
        if(firsts.isFirst("ClaseConcreta", tokenActual)){
            claseConcreta();
        }else{
            interface_();
        }
    }

    private void claseConcreta() throws LexicalException, SyntacticException, IOException, SemanticException {
        matchFirsts("ClaseConcreta");
        Token idC = tokenActual; // Me guardo el ID de la clase
        match(idClase);
        Token idPadre = heredaDe();
        ClaseConcreta clase;
        clase = new ClaseConcreta(idC);
        if(idPadre != null)
            clase.insertarPadre(idPadre.getLexema());

        TablaDeSimbolos.claseActual = clase;
        //HashMap<String,ArrayList<Metodo>> metodosClasePadre = TablaDeSimbolos.getClase(clase.getNombreClasePadre()).getMetodos();
        //insertarMetodos(TablaDeSimbolos.claseActual, metodosClasePadre);
        implementaA();
        TablaDeSimbolos.insertClass(idC.getLexema(), clase);
        match(punt_llaveIzq);
        listaMiembros();
        match(punt_llaveDer);
    }

    private void insertarMetodos(ClaseConcreta clase, HashMap<String,ArrayList<Metodo>> metodos) throws SemanticException {
        for(Map.Entry<String, ArrayList<Metodo>> listaMetodos : metodos.entrySet()) {
            for(Metodo metodo : listaMetodos.getValue()){
                clase.insertarMetodo(metodo);
            }
        }
    }

    private void interface_() throws LexicalException, SyntacticException, IOException, SemanticException {
        if(firsts.isFirst("Interface", tokenActual)) {
            match(kw_interface);
            Token nombreInterfaz = tokenActual;
            match(idClase);
            Interfaz interfaz = new Interfaz(nombreInterfaz);
            TablaDeSimbolos.claseActual = interfaz;
            extiendeA();
            TablaDeSimbolos.insertInterface(interfaz.getNombreClase(), interfaz);
            match(punt_llaveIzq);
            listaEncabezados();
            match(punt_llaveDer);
        }else{
            throw new SyntacticException("interface", tokenActual);
        }
    }
    private Token heredaDe() throws LexicalException, SyntacticException, IOException {
        Token idClasePadre = null;
        if(firsts.isFirst("HeredaDe", tokenActual)){
            match(kw_extends);
            idClasePadre = tokenActual; // Me guardo el ID de la clase padre
            match(idClase);
        } else{
          // ε
        }
        return idClasePadre;
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
        Token nombreExtiende = tokenActual;
        TablaDeSimbolos.claseActual.implemented.add(nombreExtiende.getLexema());
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

    private void listaMiembros() throws LexicalException, SyntacticException, IOException, SemanticException {
        if(firsts.isFirst("Miembro", tokenActual)){
            miembro();
            listaMiembros();
        }else{
            //No hago nada por ahora porque va a ε
        }
    }
    private void listaEncabezados() throws LexicalException, IOException, SyntacticException, SemanticException {
        if(firsts.isFirst("EncabezadoMetodo",tokenActual)){
            encabezadoMetodo();
            match(punt_puntoYComa);
            listaEncabezados();
        }else{
            //No hago nada por ahora porque va a ε
        }
    }
    private void miembro() throws LexicalException, SyntacticException, IOException, SemanticException {
        if(firsts.isFirst("Atributo", tokenActual)){
            atributo();
        }else if(firsts.isFirst("Metodo", tokenActual)){
            metodo();
        }else{
            throw new SyntacticException("Atributo o Metodo", tokenActual);
        }
    }

    private void atributo() throws LexicalException, SyntacticException, IOException, SemanticException {
        TokenId visibilidad = visibilidad();
        Tipo tipo = tipo();
        listaDecAtrs(visibilidad, tipo);
        match(punt_puntoYComa);
    }

    private Tipo tipo() throws LexicalException, SyntacticException, IOException {
        Tipo tipo = null;
        if(firsts.isFirst("TipoPrimitivo", tokenActual) || tokenActual.getTokenId() == idClase) {
            if (firsts.isFirst("TipoPrimitivo", tokenActual)) {
                tipo = tipoPrimitivo();
            } else {
                tipo = new Tipo(tokenActual.getLexema());
                match(idClase);
            }
        }else{
            throw new SyntacticException("Tipo", tokenActual);
        }
        return tipo;
    }

    private Tipo tipoPrimitivo() throws LexicalException, SyntacticException, IOException {
        Tipo tipo = new Tipo(tokenActual.getLexema());
        matchFirsts("TipoPrimitivo");
        return tipo;
    }

    private TokenId visibilidad() throws LexicalException, SyntacticException, IOException {
        if(tokenActual.getTokenId() == kw_private || tokenActual.getTokenId() == kw_public) {
            if (tokenActual.getTokenId() == kw_private) {
                match(kw_private);
                return kw_private;
            } else {
                match(kw_public);
                return kw_public;
            }
        }else{
            throw new SyntacticException("Visibilidad",tokenActual);
        }
    }

    private void listaDecAtrs(TokenId visibilidad, Tipo tipo) throws LexicalException, SyntacticException, IOException, SemanticException {
        Atributo atributo = new Atributo(tokenActual, tipo, visibilidad);
        TablaDeSimbolos.claseActual.insertarAtributo(atributo);
        match(idMetVar);
        TablaDeSimbolos.atributoActual = atributo;
        listaDecAtrsFact(visibilidad, tipo);
    }

    private void listaDecAtrsFact(TokenId visibilidad, Tipo tipo) throws LexicalException, SyntacticException, IOException, SemanticException {
        if(tokenActual.getTokenId() == punt_coma){
            match(punt_coma);
            listaDecAtrs(visibilidad, tipo);
        }else {
            // Epsilon
        }
    }

    private void metodo() throws LexicalException, SyntacticException, IOException, SemanticException {
        encabezadoMetodo();
        bloque();
    }

    private void encabezadoMetodo() throws LexicalException, SyntacticException, IOException, SemanticException {
        boolean estatico;
        estatico = estaticoOpt();
        TipoMetodo tipoMetodo = tipoMetodo();
        Token idMetodo = tokenActual;
        match(idMetVar);
        TablaDeSimbolos.metodoActual = new Metodo(idMetodo, tipoMetodo, estatico, null);
        argsFormales();
        TablaDeSimbolos.claseActual.insertarMetodo((TablaDeSimbolos.metodoActual));
    }

    private boolean estaticoOpt() throws LexicalException, SyntacticException, IOException {
        if(tokenActual.getTokenId() == kw_static){
            match(kw_static);
            return true;
        }else{
            // Epsilon
        }
        return false;
    }

    private TipoMetodo tipoMetodo() throws LexicalException, SyntacticException, IOException {
        if(firsts.isFirst("Tipo", tokenActual) || tokenActual.getTokenId() == kw_void) {
            if (firsts.isFirst("Tipo", tokenActual)) {
                return new TipoMetodo(tipo().getType());
            } else {
                match(kw_void);
                return new TipoMetodo("void");
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



    private void argFormal() throws LexicalException, SyntacticException, IOException {
        Tipo tipoArgumento = tipo();
        Argumento argumento = new Argumento(tokenActual, tipoArgumento);
        match(idMetVar);
        TablaDeSimbolos.metodoActual.addArgumento(argumento);
    }



}
