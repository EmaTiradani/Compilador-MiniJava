package TablaDeSimbolos;

import exceptions.SemanticException;
import lexycal.Token;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Clase {

    private Token nombreClase;
    private String nombreClasePadre;
    public ArrayList<String> implemented;

    private HashMap<String, Atributo> atributos;
    private HashMap<String, ArrayList<Metodo>> metodos;

    boolean consolidado;
    boolean notHerenciaCircular;


    public abstract Token getToken();

    public abstract String getNombreClase();

    public abstract void insertarAtributo(Atributo atributo)throws SemanticException;

    public abstract void insertarMetodo(Metodo metodo) throws SemanticException;

    public abstract boolean estaBienDeclarada() throws SemanticException;


}
