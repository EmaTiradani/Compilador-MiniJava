package syntactic;


import TablaDeSimbolos.*;
import TablaDeSimbolos.nodosAST.expresion.NodoExpresion;
import TablaDeSimbolos.nodosAST.expresion.NodoExpresionBinaria;
import TablaDeSimbolos.nodosAST.expresion.NodoExpresionUnaria;
import TablaDeSimbolos.nodosAST.expresion.NodoOperando;
import TablaDeSimbolos.nodosAST.expresion.binarias.*;
import TablaDeSimbolos.nodosAST.expresion.operandos.*;
import TablaDeSimbolos.nodosAST.sentencia.*;
import exceptions.SemanticException;
import exceptions.SyntacticException;
import lexycal.AnalizadorLexico;
import exceptions.LexicalException;
import lexycal.Token;
import lexycal.TokenId;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        TablaDeSimbolos.claseActual.listaInterfaces.add(nombreExtiende.getLexema());
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
        NodoBloque nodoBloque = bloque();
        TablaDeSimbolos.metodoActual.insertarBloque(nodoBloque);
    }

    private void encabezadoMetodo() throws LexicalException, SyntacticException, IOException, SemanticException {
        boolean estatico = estaticoOpt();
        TipoMetodo tipoMetodo = tipoMetodo();
        Token idMetodo = tokenActual;
        match(idMetVar);
        Metodo metodoActual = new Metodo(idMetodo, tipoMetodo, estatico, null);

        TablaDeSimbolos.metodoActual = metodoActual;
        argsFormales();
        metodoActual.insertClaseContenedora(TablaDeSimbolos.claseActual.getNombreClase());
        TablaDeSimbolos.claseActual.insertarMetodo(metodoActual);

        //NodoBloque bloque = bloque();
        //metodoActual.insertarBloque(bloque);
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

    private NodoBloque bloque() throws LexicalException, SyntacticException, IOException {
        match(punt_llaveIzq);
        NodoBloque nodoBloque = new NodoBloque();
        listaSentencias(nodoBloque);
        match(punt_llaveDer);

        return nodoBloque;
    }

    private void listaSentencias(NodoBloque nodoBloque) throws LexicalException, SyntacticException, IOException {
        if(firsts.isFirst("Sentencia", tokenActual)){
            NodoSentencia nodoSentencia = sentencia();
            nodoBloque.insertarSentencia(nodoSentencia);
            listaSentencias(nodoBloque);
        }else{
            // Epsilon
        }
    }

    private NodoSentencia sentencia() throws LexicalException, SyntacticException, IOException {
        if(tokenActual.getTokenId() == punt_puntoYComa){
            match(punt_puntoYComa);
            return new NodoSentenciaVacia();
        }else if(tokenActual.getTokenId() == kw_var){
            NodoSentencia nodoSentencia = varLocal();
            return nodoSentencia;
        }else if(tokenActual.getTokenId() == kw_return){
            NodoSentencia nodoSentencia = return_();
            return nodoSentencia;
        }else if(tokenActual.getTokenId() == kw_if){
            NodoIf nodoIf = if_();
            return nodoIf;
        }else if(tokenActual.getTokenId() == kw_while){
            NodoWhile nodoWhile = while_();
            return nodoWhile;
        }else if(tokenActual.getTokenId() == punt_llaveIzq){
            NodoBloque nodoBloque = bloque(); // TODO esto que onda?
            return nodoBloque;
        }else if(firsts.isFirst("Acceso", tokenActual)){
            NodoAcceso nodoAcceso = acceso();
            return asignacionOLlamada(nodoAcceso);
        }else throw new SyntacticException("; o Sentencia", tokenActual);
    }

    private NodoSentencia asignacionOLlamada(NodoAcceso nodoAcceso) throws LexicalException, SyntacticException, IOException {
        if(firsts.isFirst("TipoDeAsignacion",tokenActual)){
            NodoAsignacion nodoAsignacion = tipoDeAsignacion(nodoAcceso);
            nodoAsignacion.setNodoExpresion(expresion());
            match(punt_puntoYComa);
            return nodoAsignacion;
        }else if(tokenActual.getTokenId() == punt_puntoYComa){
            Token puntoYComa = tokenActual;
            match(punt_puntoYComa);
            return new NodoLlamada(puntoYComa, nodoAcceso);
            // Epsilon
        }else{
            throw new SyntacticException("Tipo asignacion", tokenActual);
        }
    }

    private NodoExpresion expresion() throws LexicalException, SyntacticException, IOException {
        NodoExpresion nodoExpresionIzq = expresionUnaria();
        return expresionRec(nodoExpresionIzq);
    }

    private NodoExpresion expresionRec(NodoExpresion nodoExpresionIzq) throws LexicalException, SyntacticException, IOException {
        if(firsts.isFirst("OperadorBinario", tokenActual)){
            NodoExpresionBinaria nodoExpresionBinaria = operadorBinario();
            NodoExpresion nodoExpresionDer = expresionUnaria();
            nodoExpresionBinaria.setLadoDerecho(nodoExpresionDer);
            nodoExpresionBinaria.setLadoIzquierdo(nodoExpresionIzq);
            NodoExpresion nodoExpresion = expresionRec(nodoExpresionBinaria);
            return nodoExpresion;
        }else {
            return nodoExpresionIzq;
            // Epsilon
        }
    }

    private NodoExpresionBinaria operadorBinario() throws LexicalException, SyntacticException, IOException {
        Token tokenOperacion = tokenActual;
        if(tokenActual.getTokenId() == op_or){
            matchFirsts("OperadorBinario");
            return new NodoExpresionBinariaOr(tokenOperacion);
        }else if(tokenActual.getTokenId() == op_and){
            matchFirsts("OperadorBinario");
            return new NodoExpresionBinariaAnd(tokenOperacion);
        }else if(tokenActual.getTokenId() == op_igual){
            matchFirsts("OperadorBinario");
            return new NodoExpresionBinariaIgual(tokenOperacion);
        }else if(tokenActual.getTokenId() == op_distinto){
            matchFirsts("OperadorBinario");
            return new NodoExpresionBinariaDistinto(tokenOperacion);
        }else if(tokenActual.getTokenId() == op_menor){
            matchFirsts("OperadorBinario");
            return new NodoExpresionBinariaMenor(tokenOperacion);
        }else if(tokenActual.getTokenId() == op_mayor){
            matchFirsts("OperadorBinario");
            return new NodoExpresionBinariaMayor(tokenOperacion);
        }else if(tokenActual.getTokenId() == op_menorIgual){
            matchFirsts("OperadorBinario");
            return new NodoExpresionBinariaMenorIgual(tokenOperacion);
        }else if(tokenActual.getTokenId() == op_mayorIgual){
            matchFirsts("OperadorBinario");
            return new NodoExpresionBinariaMayorIgual(tokenOperacion);
        }else if(tokenActual.getTokenId() == op_suma){
            matchFirsts("OperadorBinario");
            return new NodoExpresionBinariaSuma(tokenOperacion);
        }else if(tokenActual.getTokenId() == op_resta){
            matchFirsts("OperadorBinario");
            return new NodoExpresionBinariaResta(tokenOperacion);
        }else if(tokenActual.getTokenId() == op_multiplicacion){
            matchFirsts("OperadorBinario");
            return new NodoExpresionBinariaMultiplicacion(tokenOperacion);
        }else if(tokenActual.getTokenId() == op_division){
            matchFirsts("OperadorBinario");
            return new NodoExpresionBinariaDivision(tokenOperacion);
        }else if(tokenActual.getTokenId() == op_modulo){
            matchFirsts("OperadorBinario");
            return new NodoExpresionBinariaModulo(tokenOperacion);
        }else{
            throw new SyntacticException(" Operador Binario", tokenOperacion);
        }
        //matchFirsts("OperadorBinario");
    }

    private NodoExpresion expresionUnaria() throws LexicalException, SyntacticException, IOException {
        if(firsts.isFirst("OperadorUnario", tokenActual)){
            Token tokenOperador = operadorUnario();
            NodoOperando nodoOperando = operando();
            return new NodoExpresionUnaria(tokenOperador, nodoOperando);
        }else{
            return operando();
        }

    }

    private NodoOperando operando() throws LexicalException, SyntacticException, IOException {
        if(firsts.isFirst("Literal",tokenActual)){
            return literal();
        }else if(firsts.isFirst("Acceso", tokenActual)){
            return acceso();
        }else
            throw new SyntacticException("Operando", tokenActual);
    }

    private NodoOperando literal() throws LexicalException, SyntacticException, IOException {
        if(tokenActual.getTokenId() == kw_null){
            Token tokenNull = tokenActual;
            matchFirsts("Literal");
            return new NodoNull(tokenNull);
        }else if(tokenActual.getTokenId() == stringLiteral){
            Token tokenString = tokenActual;
            matchFirsts("Literal");
            return new NodoString(tokenString);
        }else if(tokenActual.getTokenId() == intLiteral){
            Token tokenInt = tokenActual;
            matchFirsts("Literal");
            return new NodoNum(tokenInt);
        }else if(tokenActual.getTokenId() == charLiteral){
            Token tokenChar = tokenActual;
            matchFirsts("Literal");
            return new NodoChar(tokenChar);
        }else if(tokenActual.getTokenId() == kw_true){
            Token tokenTrue = tokenActual;
            matchFirsts("Literal");
            return new NodoTrue(tokenTrue);
        }else if(tokenActual.getTokenId() == kw_false){
            Token tokenFalse = tokenActual;
            matchFirsts("Literal");
            return new NodoFalse(tokenFalse);
        }else{
            throw new SyntacticException(" literal", tokenActual);
        }
        //matchFirsts("Literal"); // Sacar este final, tiene que haber 1 por caso
    }

    private Token operadorUnario() throws LexicalException, SyntacticException, IOException {
        //matchFirsts("OperadorUnario"); Esto estaba solo
        if(tokenActual.getTokenId() == op_suma){
            Token suma = tokenActual;
            matchFirsts("OperadorUnario");
            return suma;
        }else if(tokenActual.getTokenId() == op_resta){
            Token resta = tokenActual;
            matchFirsts("OperadorUnario");
            return resta;
        }else if(tokenActual.getTokenId() == op_negacion){
            Token negacion = tokenActual;
            matchFirsts("OperadorUnario");
            return negacion;
        }else
            throw new SyntacticException(" Operador Unario", tokenActual);
    }

    private NodoAsignacion tipoDeAsignacion(NodoAcceso nodoAcceso) throws LexicalException, SyntacticException, IOException {
        //matchFirsts("TipoDeAsignacion");
        Token tokenAsignacion = tokenActual;
        if(tokenActual.getTokenId() == asignacion){
            matchFirsts("TipoDeAsignacion");
            //NodoExpresion nodoExpresion = expresion();
            return new NodoAsignacionExp(tokenAsignacion, nodoAcceso);
        }else if(tokenActual.getTokenId() == incremento){
            matchFirsts("TipoDeAsignacion");
            return new NodoAsignacionIncremento(tokenAsignacion, nodoAcceso);
        }else if(tokenActual.getTokenId() == decremento){
            matchFirsts("TipoDeAsignacion");
            return new NodoAsignacionDecremento(tokenAsignacion, nodoAcceso);
        }else{
            throw new SyntacticException("Tipo de asignacion", tokenAsignacion);
        }
    }

    private NodoAcceso acceso() throws LexicalException, SyntacticException, IOException {
        NodoAcceso nodoAcceso = primario();
        nodoAcceso.setNodoEncadenado(encadenadoOpt());
        return nodoAcceso;
    }

    private NodoAcceso primario() throws LexicalException, SyntacticException, IOException {
        if(tokenActual.getTokenId() == kw_this){ // Acceso this
            Token tokenThis = tokenActual;
            match(kw_this);
            return new NodoAccesoThis(tokenThis);
        }else if(tokenActual.getTokenId() == idMetVar){
            Token tokenIdMetVar = tokenActual;
            match(idMetVar);
            return accesoVarOMetodo(tokenIdMetVar); // TODO
        }else if(tokenActual.getTokenId() == kw_new){ //Acceso constructor
            match(kw_new);
            Token tokenConstructor = tokenActual;
            match(idClase);
            match(punt_parentIzq);
            match(punt_parentDer);
            return new NodoAccesoConstructor(tokenConstructor);
        }else if(tokenActual.getTokenId() == idClase) {
            return accesoMetodoEstatico();
        }else if(tokenActual.getTokenId() == punt_parentIzq){
            return expresionParentizada();
        }else{
            throw new SyntacticException("Token Primario", tokenActual);
        }
    }

    private NodoAccesoExpresionParentizada expresionParentizada() throws LexicalException, SyntacticException, IOException {
        match(punt_parentIzq);
        NodoExpresion expresion = expresion();
        match(punt_parentDer);
        return new NodoAccesoExpresionParentizada(expresion);
    }

    private NodoAcceso accesoMetodoEstatico() throws LexicalException, SyntacticException, IOException {
        Token tokenClaseEstatica = tokenActual;
        match(idClase);
        match(punt_punto);
        Token tokenMetodoEstatico = tokenActual;
        match(idMetVar);
        List<NodoExpresion> parametros = argsActuales();
        NodoAccesoMetodoEstatico nodoAccesoMetodoEstatico = new NodoAccesoMetodoEstatico(tokenClaseEstatica, tokenMetodoEstatico, parametros);
        return nodoAccesoMetodoEstatico;
    }

    private NodoAcceso accesoVarOMetodo(Token idMetVar) throws LexicalException, SyntacticException, IOException {
        if(tokenActual.getTokenId() == punt_parentIzq) {
            List<NodoExpresion> listaParametrosActuales = argsActuales();
            return new NodoAccesoMetodo(idMetVar, listaParametrosActuales);
        }
        else{
            // Epsilon
            return new NodoAccesoVar(idMetVar);
        }

    }

    private NodoEncadenado encadenadoOpt() throws LexicalException, SyntacticException, IOException {
        if(tokenActual.getTokenId() == punt_punto){
            match(punt_punto);
            Token idMV = tokenActual;
            match(idMetVar);
            NodoEncadenado nodoEncadenado = varOMetEncadenado(idMV);
            NodoEncadenado proximoNodoEncadenado = encadenadoOpt();// Esto estaba en varOMetEncadenado()
            nodoEncadenado.setNodoEncadenado(proximoNodoEncadenado); //TODO  se va a agregar un nodoEncadenado null, no hay drama?
            return nodoEncadenado;
        }else{
            // Epsilon
            return null;
        }
    }

    private NodoEncadenado varOMetEncadenado(Token idMV) throws LexicalException, SyntacticException, IOException {
        if(tokenActual.getTokenId() == punt_parentIzq){
            List<NodoExpresion> parametros = argsActuales();
            NodoMetodoEncadenado nodoMetodoEncadenado = new NodoMetodoEncadenado(idMV);
            nodoMetodoEncadenado.setParametros(parametros);
            return nodoMetodoEncadenado;
            // Aca estaba encadenadoOpt()
        }else{
            return new NodoVarEncadenada(idMV);
            // Epsilon
            // Aca estaba encadenadoOpt()
        }
    }

    private List<NodoExpresion> argsActuales() throws LexicalException, SyntacticException, IOException {
        match(punt_parentIzq);
        List<NodoExpresion> listaParametros = listaExpsOpt();
        match(punt_parentDer);
        return listaParametros;
    }

    private List<NodoExpresion> listaExpsOpt() throws LexicalException, SyntacticException, IOException {
        if(firsts.isFirst("Expresion", tokenActual)){
            return listaExps();
        }else{
            // Epsilon
            return new ArrayList<NodoExpresion>();
        }
    }

    private List<NodoExpresion> listaExps() throws LexicalException, SyntacticException, IOException {
        NodoExpresion nodoExpresion = expresion();
        List<NodoExpresion> listaParametros = listaExpsFact();
        listaParametros.add(nodoExpresion);
        return listaParametros;
    }

    private List<NodoExpresion> listaExpsFact() throws LexicalException, SyntacticException, IOException {
        if(tokenActual.getTokenId() == punt_coma){
            match(punt_coma);
            return listaExps();
        }else{
            // Epsilon
            return new ArrayList<NodoExpresion>();
        }
    }


    private NodoWhile while_() throws LexicalException, SyntacticException, IOException {
        Token tokenWhile = tokenActual;
        match(kw_while);
        match(punt_parentIzq);
        NodoExpresion condicion = expresion();
        match(punt_parentDer);
        NodoSentencia sentenciaWhile = sentencia();
        NodoWhile nodoWhile = new NodoWhile(tokenWhile, condicion, sentenciaWhile);
        return nodoWhile;
    }

    private NodoIf if_() throws LexicalException, SyntacticException, IOException {
        Token tokenIf = tokenActual;
        match(kw_if);
        match(punt_parentIzq);
        NodoExpresion condicion = expresion();
        match(punt_parentDer);
        NodoSentencia nodoSentenciaIf = sentencia();
        NodoSentencia nodoSentenciaElse = else_();
        NodoIf nodoIf = new NodoIf(tokenIf, condicion, nodoSentenciaIf, nodoSentenciaElse);
        return nodoIf;
    }

    private NodoSentencia else_() throws LexicalException, SyntacticException, IOException {
        if(tokenActual.getTokenId() == kw_else){
            match(kw_else);
            return sentencia();
        }else{
            return null;
            // Epsilon
        }
    }

    private NodoSentencia return_() throws LexicalException, SyntacticException, IOException {
        Token tokenReturn = tokenActual;
        match(kw_return);
        NodoReturn nodoReturn = new NodoReturn(tokenReturn);
        NodoExpresion nodoExpresion = expresionOpt();
        nodoReturn.insertarExpresion(nodoExpresion);
        match(punt_puntoYComa);
        return nodoReturn;
    }

    private NodoExpresion expresionOpt() throws LexicalException, SyntacticException, IOException {
        if(firsts.isFirst("ExpresionUnaria", tokenActual)){
            return expresion();
        }else{
            // Epsilon
            return null;
        }
    }

    private NodoVarLocal varLocal() throws LexicalException, SyntacticException, IOException {
        match(kw_var);
        String idVar = tokenActual.getLexema();
        NodoVarLocal nodoVarLocal = new NodoVarLocal(tokenActual);
        match(idMetVar);
        match(asignacion);
        nodoVarLocal.setExpresion(expresion());
        match(punt_puntoYComa); //TODO chequear esto, no cambio nada
        return nodoVarLocal;
    }

    private void argFormal() throws LexicalException, SyntacticException, IOException {
        Tipo tipoArgumento = tipo();
        Argumento argumento = new Argumento(tokenActual, tipoArgumento);
        match(idMetVar);
        TablaDeSimbolos.metodoActual.addArgumento(argumento);
    }



}
