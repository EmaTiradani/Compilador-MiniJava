package TablaDeSimbolos;

import exceptions.SemanticException;
import lexycal.Token;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Clase {

    public Token nombreClase;
    public String nombreClasePadre;
    public ArrayList<String> implemented;

    public HashMap<String, Atributo> atributos;
    public HashMap<String, ArrayList<Metodo>> metodos;

    boolean consolidado;
    boolean notHerenciaCircular;


    public abstract Token getToken();

    public abstract String getNombreClase();

    public abstract void insertarAtributo(Atributo atributo)throws SemanticException;

    public abstract void insertarMetodo(Metodo metodo) throws SemanticException;

    public abstract boolean estaBienDeclarada() throws SemanticException;

    public abstract void consolidar() throws SemanticException;

    public abstract boolean herenciaCircular();


}
