package TablaDeSimbolos;

import exceptions.SemanticException;

import java.util.HashMap;
import java.util.Map;

public final class TablaDeSimbolos {

    private static HashMap<String, Clase> clases;

    public static Clase claseActual;
    public static Metodo metodoActual;
    public static Atributo atributoActual;

    public TablaDeSimbolos(){
        clases = new HashMap<String, Clase>();
    }


    public static void insertClass(String name, Clase clase) throws SemanticException {
        if(clases.containsKey(name)){
            throw new SemanticException("Error", "Error");
        }else{
            clases.put(name, clase);
        }
    }

    public static Clase getClass(String name){
        return clases.get(name);
    }

    public static void print(){ //Metodo para ver si se crean bien las cosas de la TS
        /*String tablaAString;
        clases.forEach((s,c) -> {System.out.println("Nombre de clase: " + s + "\n | "); c.print();});
        //System.out.printf(clases.toString());
        return clases.toString();*/
        for(Map.Entry<String, Clase> clase : clases.entrySet()){
            System.out.println("-Nombre de clase: " + clase.getKey());
            clase.getValue().print();
            System.out.println("|");
        }
    }


}
