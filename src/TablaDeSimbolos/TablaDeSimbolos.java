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
        crearMetodosObject(object);
        clases.put("Object", object);
    }

    private void checkDec(){

    }

    private void consolidar() throws SemanticException {
        crearClaseObject();
    }

    private void crearMetodosObject(Clase object) throws SemanticException {
        Metodo clone = new Metodo(new Token(idMetVar,"clone", 0),new Tipo("protected"), false, null);
        object.insertarMetodo(clone);
        ArrayList<Argumento> argumentosEquals = new ArrayList<Argumento>();
        argumentosEquals.add(new Argumento(new Token(idMetVar, "obj", 0), new Tipo("Object")));
        Metodo equals = new Metodo(new Token(idMetVar,"equals", 0),new Tipo("boolean"), false, argumentosEquals);
        object.insertarMetodo(equals);
        Metodo finalize = new Metodo(new Token(idMetVar, "finalize", 0),new Tipo("void"), false, null);
        object.insertarMetodo(finalize);
        Metodo getClass = new Metodo(new Token(idMetVar, "getClass", 0), new Tipo("Class"), false, null);
        object.insertarMetodo(getClass);
        Metodo hashCode = new Metodo(new Token(idMetVar, "hashCode", 0), new Tipo("int"), false, null);
        object.insertarMetodo(hashCode);
        Metodo notify = new Metodo(new Token(idMetVar, "notify", 0), new Tipo("void"), false, null);
        object.insertarMetodo(notify);
        Metodo notifyAll = new Metodo(new Token(idMetVar, "notifyAll", 0), new Tipo("void"), false, null);
        object.insertarMetodo(notifyAll);
        Metodo toString = new Metodo(new Token(idMetVar, "toString", 0), new Tipo("String"), false, null);
        object.insertarMetodo(toString);
        Metodo wait = new Metodo(new Token(idMetVar, "wait", 0), new Tipo("void"), false, null);
        object.insertarMetodo(wait);
        ArrayList<Argumento> argumentosWaitConUnArg = new ArrayList<Argumento>();
        argumentosWaitConUnArg.add(new Argumento(new Token(idMetVar, "timeout", 0), new Tipo("long")));
        Metodo waitConUnArg = new Metodo(new Token(idMetVar, "wait", 0), new Tipo("void"), false, argumentosWaitConUnArg);
        //object.insertarMetodo(waitConUnArg);
        ArrayList<Argumento> argumentosWaitConDosArgs = new ArrayList<Argumento>();
        argumentosWaitConDosArgs.add(new Argumento(new Token(idMetVar, "timeout", 0), new Tipo("long")));
        argumentosWaitConDosArgs.add(new Argumento(new Token(idMetVar, "nanos", 0), new Tipo("int")));
        Metodo waitConDosArgs = new Metodo(new Token(idMetVar, "wait", 0), new Tipo("void"), false, argumentosWaitConDosArgs);
        //object.insertarMetodo(waitConDosArgs);
    }


}
