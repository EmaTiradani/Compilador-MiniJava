package TablaDeSimbolos;

import exceptions.SemanticException;

import java.util.HashMap;

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




}
