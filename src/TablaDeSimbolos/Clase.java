package TablaDeSimbolos;

import TablaDeSimbolos.nodosAST.expresion.NodoExpresion;
import exceptions.SemanticException;
import lexycal.Token;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Clase {

    public Token nombreClase;
    public String nombreClasePadre;
    public ArrayList<String> listaInterfaces;

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

    public abstract ArrayList<String> getAncestros();

    public Metodo getMetodoQueConformaParametros(Token idMet, List<NodoExpresion> parametros) throws SemanticException {
        List<Tipo> tiposDeLosParametros = new ArrayList<>();
        for(NodoExpresion parametro : parametros){
            tiposDeLosParametros.add(parametro.chequear());
        }


        return null; // TODO sacar esto cuando termine de hacerlo bien
    }

}
