package TablaDeSimbolos;

import TablaDeSimbolos.nodosAST.sentencia.NodoBloque;
import TablaDeSimbolos.nodosAST.sentencia.nodosBloqueMetsDefault.NodoBloqueDebugPrint;
import TablaDeSimbolos.nodosAST.sentencia.NodoVarLocal;
import TablaDeSimbolos.nodosAST.sentencia.nodosBloqueMetsDefault.*;
import exceptions.SemanticException;
import lexycal.Token;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static lexycal.TokenId.*;

public final class TablaDeSimbolos {

    private static HashMap<String, ClaseConcreta> clases;
    private static HashMap<String, Interfaz> interfaces;

    public static Clase claseActual;
    public static Metodo metodoActual;
    public static Atributo atributoActual;
    public static Metodo metodoMain;

    public static List<NodoBloque> pilaDeBloques;

    public static ArrayList<String> listaInstrucciones;
    public static String stringInstrucciones;

    public TablaDeSimbolos() throws SemanticException {
        clases = new HashMap<String, ClaseConcreta>();
        interfaces = new HashMap<String, Interfaz>();
        pilaDeBloques = new ArrayList<>();
        crearClaseObject();
        crearClaseSystem();
        crearClaseString();

        listaInstrucciones = new ArrayList<>();
    }

    public static void insertClass(String name, ClaseConcreta clase) throws SemanticException {
        if(clases.containsKey(name) || interfaces.containsKey(name)){
            throw new SemanticException("ya estaba declarada", clase.getToken());
        }else{
            clases.put(name, clase);
        }
    }

    public static void insertInterface(String name, Interfaz interfaz) throws SemanticException {
        if(clases.containsKey(name) || interfaces.containsKey(name)){
            throw new SemanticException("ya estaba declarada", interfaz.getToken());
        }else{
            interfaces.put(name, interfaz);
        }
    }

    public static NodoBloque getBloqueActual(){
        if(pilaDeBloques.size() == 0)
            return null;
        else
            return pilaDeBloques.get(0); //Retorno el ultimo bloque agregado, asi funciona como una pila la lista
    }

    public static ClaseConcreta getClase(String name){
        return clases.get(name);
    }

    public static Interfaz getInterfaz(String name){
        return interfaces.get(name);
    }

    public static boolean existeClase(String nombreClase){
        return clases.containsKey(nombreClase);
    }

    public static boolean existeInterfaz(String nombreClase){
        return interfaces.containsKey(nombreClase);
    }



    public static void checkDec() throws SemanticException {
        boolean hayMain = false;
        for (Map.Entry<String, ClaseConcreta> clase : clases.entrySet()){
            boolean tieneMain = clase.getValue().estaBienDeclarada();
            if(tieneMain){
                hayMain = true;
            }
        }
        if(!hayMain){
            throw new SemanticException("No hay ninguna clase con un metodo main", new Token(idMetVar, "main", 0));
        }
        for (Map.Entry<String, Interfaz> interfaz : interfaces.entrySet()){
            interfaz.getValue().estaBienDeclarada();
        }
    }


    public static void consolidar() throws SemanticException {
        for (Map.Entry<String, ClaseConcreta> clase : clases.entrySet()){
            if(clase.getValue().getNombreClase() != "Object"){
                clase.getValue().consolidar();
            }
        }
        for (Map.Entry<String, Interfaz> interfaz : interfaces.entrySet()){
            interfaz.getValue().consolidar();
        }

        /*// Esto lo hago para que luego de consolidar las clases e interfaces, se le puedan asignar correctamente los offsets correctamente
        for (Map.Entry<String, ClaseConcreta> clase : clases.entrySet()){
            if(clase.getValue().getNombreClase() != "Object"){
                clase.getValue().consolidar();
            }
        }*/
    }

    public static void checkSentencias() throws SemanticException {
        for (Map.Entry<String, ClaseConcreta> clase : clases.entrySet()){
            if(clase.getValue().getNombreClase() != "Object"){
                clase.getValue().checkSentencias();
            }
        }
    }


    private void insertarMetodosYAtributosDeAncestros(ClaseConcreta clase) throws SemanticException {
        if(clase.getNombreClasePadre().equals("Object")){
            HashMap<String,ArrayList<Metodo>> metodosClasePadre = TablaDeSimbolos.getClase("Object").getMetodos();
            for(Map.Entry<String, ArrayList<Metodo>> listaMetodos : metodosClasePadre.entrySet()){
                for(Metodo metodo : listaMetodos.getValue()){
                    clase.insertarMetodo(metodo);
                }
            }
        }else{
            insertarMetodosYAtributosDeAncestros(TablaDeSimbolos.clases.get(clase.getNombreClasePadre()));
            // Inserto los metodos del padre
            HashMap<String,ArrayList<Metodo>> metodosClasePadre = TablaDeSimbolos.getClase(clase.getNombreClasePadre()).getMetodos();
            for(Map.Entry<String, ArrayList<Metodo>> listaMetodos : metodosClasePadre.entrySet()){
                for(Metodo metodo : listaMetodos.getValue()){
                    clase.insertarMetodo(metodo);
                }
            }
            // Inserto los atributos del padre
            HashMap<String,Atributo> atributosClasePadre = TablaDeSimbolos.getClase(clase.getNombreClasePadre()).getAtributos();
            for(Map.Entry<String, Atributo> atributo : atributosClasePadre.entrySet()){
                clase.insertarAtributo(atributo.getValue());
            }
        }
    }

    private void crearClaseString(){
        ClaseConcreta string = new ClaseConcreta(new Token(idClase, "String", 0));
        clases.put("String", string);
    }

    private void crearClaseSystem() throws SemanticException {
        ClaseConcreta system = new ClaseConcreta(new Token(idClase, "System", 0));
        crearMetodosSystem(system);
        system.generado = true;
        clases.put("System", system);
    }

    private void crearClaseObject() throws SemanticException {
        ClaseConcreta object = new ClaseConcreta(new Token(idClase ,"Object", 0));
        ArrayList<Argumento> argumentosObject = new ArrayList<Argumento>();
        argumentosObject.add(new Argumento(new Token(idMetVar, "i", 0), new Tipo("int")));
        Metodo debugPrint = new Metodo(new Token(idMetVar, "debugPrint", 0), new TipoMetodo("void"), true, argumentosObject);
        object.insertarMetodo(debugPrint);
        debugPrint.insertarBloque(new NodoBloqueDebugPrint());
        debugPrint.insertClaseContenedora("Object");
        object.noTieneHerenciaCircular();
        object.consolidado = true;
        object.generado = true;
        clases.put("Object", object);
    }

    private void crearMetodosSystem(ClaseConcreta system) throws SemanticException {
        Metodo read = new Metodo(new Token(idMetVar,"read", 0),new TipoMetodo("int"), true, null);// Read
        read.insertarBloque(new NodoBloqueRead());
        read.insertClaseContenedora("System");
        system.insertarMetodo(read);
        ArrayList<Argumento> argumentosPrintB = new ArrayList<Argumento>();// PrintB
        argumentosPrintB.add(new Argumento(new Token(idMetVar, "b", 0), new Tipo("boolean")));
        Metodo print = new Metodo(new Token(idMetVar,"printB", 0),new TipoMetodo("void"), true, argumentosPrintB);
        print.insertarBloque(new NodoBloquePrintB());
        print.insertClaseContenedora("System");
        system.insertarMetodo(print);
        ArrayList<Argumento> argumentosPrintC = new ArrayList<Argumento>();// PrintC
        argumentosPrintC.add(new Argumento(new Token(idMetVar, "c", 0), new Tipo("char")));
        Metodo printC = new Metodo(new Token(idMetVar,"printC", 0),new TipoMetodo("void"), true, argumentosPrintC);
        printC.insertarBloque(new NodoBloquePrintC());
        printC.insertClaseContenedora("System");
        system.insertarMetodo(printC);
        ArrayList<Argumento> argumentosPrintI = new ArrayList<Argumento>();// PrintI
        argumentosPrintI.add(new Argumento(new Token(idMetVar, "i", 0), new Tipo("int")));
        Metodo printI = new Metodo(new Token(idMetVar,"printI", 0),new TipoMetodo("void"), true, argumentosPrintI);
        printI.insertarBloque(new NodoBloquePrintI());
        printI.insertClaseContenedora("System");
        system.insertarMetodo(printI);
        ArrayList<Argumento> argumentosPrintS = new ArrayList<Argumento>();// PrintS
        argumentosPrintS.add(new Argumento(new Token(idMetVar, "s", 0), new Tipo("String")));
        Metodo printS = new Metodo(new Token(idMetVar,"printS", 0),new TipoMetodo("void"), true, argumentosPrintS);
        printS.insertarBloque(new NodoBloquePrintS());
        printS.insertClaseContenedora("System");
        system.insertarMetodo(printS);
        Metodo println = new Metodo(new Token(idMetVar,"println", 0),new TipoMetodo("void"), true, null);// Println
        println.insertarBloque(new NodoBloquePrintln());
        println.insertClaseContenedora("System");
        system.insertarMetodo(println);
        ArrayList<Argumento> argumentosPrintBln = new ArrayList<Argumento>();// PrintBln
        argumentosPrintBln.add(new Argumento(new Token(idMetVar, "b", 0), new Tipo("boolean")));
        Metodo printBln = new Metodo(new Token(idMetVar,"printBln", 0),new TipoMetodo("void"), true, argumentosPrintBln);
        printBln.insertarBloque(new NodoBloquePrintBln());
        printBln.insertClaseContenedora("System");
        system.insertarMetodo(printBln);
        ArrayList<Argumento> argumentosPrintCln = new ArrayList<Argumento>();// PrintCln
        argumentosPrintCln.add(new Argumento(new Token(idMetVar, "c", 0), new Tipo("char")));
        Metodo printCln = new Metodo(new Token(idMetVar,"printCln", 0),new TipoMetodo("void"), true, argumentosPrintCln);
        printCln.insertarBloque(new NodoBloquePrintCln());
        printCln.insertClaseContenedora("System");
        system.insertarMetodo(printCln);
        ArrayList<Argumento> argumentosPrintIln = new ArrayList<Argumento>();// PrintIln
        argumentosPrintIln.add(new Argumento(new Token(idMetVar, "i", 0), new Tipo("int")));
        Metodo printIln = new Metodo(new Token(idMetVar,"printIln", 0),new TipoMetodo("void"), true, argumentosPrintIln);
        printIln.insertarBloque(new NodoBloquePrintIln());
        printIln.insertClaseContenedora("System");
        system.insertarMetodo(printIln);
        ArrayList<Argumento> argumentosPrintSln = new ArrayList<Argumento>();// PrintSln
        argumentosPrintSln.add(new Argumento(new Token(idMetVar, "s", 0), new Tipo("String")));
        Metodo printSln = new Metodo(new Token(idMetVar,"printSln", 0),new TipoMetodo("void"), true, argumentosPrintSln);
        printSln.insertarBloque(new NodoBloquePrintSln());
        printSln.insertClaseContenedora("System");
        system.insertarMetodo(printSln);

    }

    public static void print(){ //Metodo para ver si se crean bien las cosas de la TS
        for(Map.Entry<String, ClaseConcreta> clase : clases.entrySet()){
            System.out.println("-Nombre de clase: " + clase.getKey());
            clase.getValue().print();
            System.out.println("|--------------");
        }
        for(Map.Entry<String, Interfaz> interfaz : interfaces.entrySet()){
            System.out.println("-Nombre de clase: " + interfaz.getKey());
            interfaz.getValue().print();
            System.out.println("|--------------");
        }
    }

    public static void apilarBloque(NodoBloque bloque){
        pilaDeBloques.add(0, bloque); // Como un push
    }

    public static void desapilarBloqueActual(){
        pilaDeBloques.remove(0); // Como un pop
    }

    public static NodoVarLocal getVarLocalClaseActual(String idVar){
        for(NodoBloque bloque : pilaDeBloques){
            NodoVarLocal varLocal = bloque.getVarLocal(idVar);
            if(varLocal != null){
                return varLocal;
            }
        }
        return null;
    }

    public static void generar(){
        generarInicial();
        for(ClaseConcreta clase : clases.values()){
            clase.generar();
        }
    }

    public static void generarInicial(){
        // Code
        listaInstrucciones.add(".CODE");
        listaInstrucciones.add("PUSH main");
        listaInstrucciones.add("CALL");
        listaInstrucciones.add("HALT");

        // Simple malloc
        listaInstrucciones.add("simple_malloc:");
        listaInstrucciones.add("LOADFP	; Inicialización unidad");
        listaInstrucciones.add("LOADSP");
        listaInstrucciones.add("STOREFP ; Finaliza inicialización del RA");
        listaInstrucciones.add("LOADHL	; hl");
        listaInstrucciones.add("DUP	; hl");
        listaInstrucciones.add("PUSH 1	; 1");
        listaInstrucciones.add("ADD	; hl+1");
        listaInstrucciones.add("STORE 4 ; Guarda el resultado (un puntero a la primer celda de la región de memoria)");
        listaInstrucciones.add("LOAD 3	; Carga la cantidad de celdas a alojar (parámetro que debe ser positivo)");
        listaInstrucciones.add("ADD");
        listaInstrucciones.add("STOREHL ; Mueve el heap limit (hl). Expande el heap");
        listaInstrucciones.add("STOREFP");
        listaInstrucciones.add("RET 1	; Retorna eliminando el parámetro)");
        listaInstrucciones.add("");
        // System

        // Object
        listaInstrucciones.add(".DATA");
        listaInstrucciones.add("VT_Object: NOP");
        listaInstrucciones.add("");
        listaInstrucciones.add(".CODE");
        listaInstrucciones.add("debugPrintObject:");
        listaInstrucciones.add("LOADFP");
        listaInstrucciones.add("LOADSP");
        listaInstrucciones.add("STOREFP");
        listaInstrucciones.add("LOAD 3 ; Carga el primer parametro");
        listaInstrucciones.add("IPRINT");
        listaInstrucciones.add("PRNLN");
        listaInstrucciones.add("STOREFP");
        listaInstrucciones.add("RET 1 ; Retorno de debug print");
        listaInstrucciones.add("");
        listaInstrucciones.add("");

    }

    public static void gen(String instruction){
        listaInstrucciones.add(instruction);
    }


}
