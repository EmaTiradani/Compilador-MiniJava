package TablaDeSimbolos;

import exceptions.SemanticException;
import lexycal.Token;
import lexycal.TokenId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static lexycal.TokenId.*;

public final class TablaDeSimbolos {

    private static HashMap<String, Clase> clases;

    public static Clase claseActual;
    public static Metodo metodoActual;
    public static Atributo atributoActual;

    public TablaDeSimbolos() throws SemanticException {
        clases = new HashMap<String, Clase>();

    }


    public static void insertClass(String name, Clase clase) throws SemanticException {
        if(clases.containsKey(name)){
            throw new SemanticException("ya estaba declarada", clase.getToken());
        }else{
            clases.put(name, clase);
        }
    }

    public static Clase getClase(String name){
        return clases.get(name);
    }

    public static void print(){ //Metodo para ver si se crean bien las cosas de la TS
        for(Map.Entry<String, Clase> clase : clases.entrySet()){
            System.out.println("-Nombre de clase: " + clase.getKey());
            clase.getValue().print();
            System.out.println("|");
        }
    }

    private void crearClaseObject() throws SemanticException {
        Clase object = new Clase(new Token(idClase ,"Object", 0));
        ArrayList<Argumento> argumentosObject = new ArrayList<Argumento>();
        argumentosObject.add(new Argumento(new Token(idMetVar, "i", 0), new Tipo("int")));
        Metodo debugPrint = new Metodo(new Token(idMetVar, "debugPrint", 0), new Tipo("void"), true, argumentosObject);
        clases.put("Object", object);
    }

    private void checkDec(){
        //Chequear si tiene herencia expliocita de una clase declarada
        //Chequear que no tenga herencia circular
        //Que no tenga 2 metodos o variables de instancia con el mismo nombre
        //Que todos sus mets, vars, de injstancia y su constructor esten correctamente declarados
    }

    private void consolidar() throws SemanticException {
        crearClaseObject();
        crearClaseSystem();
        crearClaseString();
        for (Map.Entry<String, Clase> clase : clases.entrySet()){
            if(clase.getValue().getNombreClase() != "Object"){
                insertarMetodosYAtributosDeAncestros(clase.getValue());
            }
        }
    }

    private void insertarMetodosYAtributosDeAncestros(Clase clase) throws SemanticException {
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
        Clase string = new Clase(new Token(idClase, "String", 0));
        clases.put("String", string);
    }

    private void crearClaseSystem() throws SemanticException {
        Clase system = new Clase(new Token(idClase, "System", 0));
        crearMetodosSystem(system);
        clases.put("System", system);
    }

    private void crearMetodosSystem(Clase system) throws SemanticException {
        Metodo read = new Metodo(new Token(idMetVar,"read", 0),new Tipo("int"), true, null);// Read
        system.insertarMetodo(read);
        ArrayList<Argumento> argumentosPrintB = new ArrayList<Argumento>();// PrintB
        argumentosPrintB.add(new Argumento(new Token(idMetVar, "b", 0), new Tipo("boolean")));
        Metodo print = new Metodo(new Token(idMetVar,"printB", 0),new Tipo("void"), true, argumentosPrintB);
        system.insertarMetodo(print);
        ArrayList<Argumento> argumentosPrintC = new ArrayList<Argumento>();// PrintC
        argumentosPrintC.add(new Argumento(new Token(idMetVar, "c", 0), new Tipo("char")));
        Metodo printC = new Metodo(new Token(idMetVar,"printC", 0),new Tipo("void"), true, argumentosPrintC);
        system.insertarMetodo(printC);
        ArrayList<Argumento> argumentosPrintI = new ArrayList<Argumento>();// PrintI
        argumentosPrintI.add(new Argumento(new Token(idMetVar, "i", 0), new Tipo("int")));
        Metodo printI = new Metodo(new Token(idMetVar,"printI", 0),new Tipo("void"), true, argumentosPrintI);
        system.insertarMetodo(printI);
        ArrayList<Argumento> argumentosPrintS = new ArrayList<Argumento>();// PrintS
        argumentosPrintS.add(new Argumento(new Token(idMetVar, "s", 0), new Tipo("String")));
        Metodo printS = new Metodo(new Token(idMetVar,"printS", 0),new Tipo("void"), true, argumentosPrintS);
        system.insertarMetodo(printS);
        Metodo println = new Metodo(new Token(idMetVar,"println", 0),new Tipo("void"), true, null);// Println
        system.insertarMetodo(println);
        ArrayList<Argumento> argumentosPrintBln = new ArrayList<Argumento>();// PrintBln
        argumentosPrintBln.add(new Argumento(new Token(idMetVar, "b", 0), new Tipo("boolean")));
        Metodo printBln = new Metodo(new Token(idMetVar,"printBln", 0),new Tipo("void"), true, argumentosPrintBln);
        system.insertarMetodo(printBln);
        ArrayList<Argumento> argumentosPrintCln = new ArrayList<Argumento>();// PrintCln
        argumentosPrintCln.add(new Argumento(new Token(idMetVar, "c", 0), new Tipo("char")));
        Metodo printCln = new Metodo(new Token(idMetVar,"printCln", 0),new Tipo("void"), true, argumentosPrintCln);
        system.insertarMetodo(printCln);
        ArrayList<Argumento> argumentosPrintIln = new ArrayList<Argumento>();// PrintIln
        argumentosPrintIln.add(new Argumento(new Token(idMetVar, "i", 0), new Tipo("int")));
        Metodo printIln = new Metodo(new Token(idMetVar,"printIln", 0),new Tipo("void"), true, argumentosPrintIln);
        system.insertarMetodo(printIln);
        ArrayList<Argumento> argumentosPrintSln = new ArrayList<Argumento>();// PrintSln
        argumentosPrintS.add(new Argumento(new Token(idMetVar, "s", 0), new Tipo("String")));
        Metodo printSln = new Metodo(new Token(idMetVar,"printSln", 0),new Tipo("void"), true, argumentosPrintSln);
        system.insertarMetodo(printSln);

    }


}
