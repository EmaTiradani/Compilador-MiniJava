package TablaDeSimbolos;

import TablaDeSimbolos.nodosAST.expresion.NodoExpresion;
import exceptions.SemanticException;
import lexycal.Token;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Clase {

    public Token nombreClase;
    public String nombreClasePadre;
    public ArrayList<String> listaInterfaces;

    public HashMap<String, Atributo> atributos;
    public HashMap<String, ArrayList<Metodo>> metodos;

    protected int offsetInicialConflictos;


    boolean consolidado;
    boolean notHerenciaCircular;

    public int offsetActualVT;

    public Clase(){
        atributos = new HashMap<>();
        metodos = new HashMap<>();
        listaInterfaces = new ArrayList<>();
        offsetInicialConflictos = 0;
    }

    public abstract Token getToken();

    public abstract String getNombreClase();

    public abstract HashMap<String,ArrayList<Metodo>> getMetodos();

    public abstract void insertarAtributo(Atributo atributo)throws SemanticException;

    public abstract void insertarMetodo(Metodo metodo) throws SemanticException;

    public abstract boolean estaBienDeclarada() throws SemanticException;

    public abstract void consolidar() throws SemanticException;

    public abstract boolean herenciaCircular();

    public HashMap<String, Atributo> getAtributos(){
        return atributos;
    }

    public Atributo getAtributo(String idVar){
        return atributos.get(idVar);
    }

    public abstract ArrayList<String> getAncestros();

    public Metodo getMetodoQueConformaParametros(Token idMet, List<NodoExpresion> parametros) throws SemanticException {
        List<Tipo> tiposDeLosParametros = new ArrayList<>();
        for(NodoExpresion parametro : parametros){
            tiposDeLosParametros.add(parametro.chequear());
        }

        ArrayList<Metodo> metodosPosibles = metodos.get(idMet.getLexema());

        if(metodosPosibles != null){
            for(Metodo metodo : metodosPosibles){
                if(metodo.conformanParametros(tiposDeLosParametros)){
                    return metodo;
                }
            }
        }
        return null;
    }

    public abstract int getCantMetodosSinConflictos();

    public abstract int getOffsetActualVT();

    public abstract Map<Integer, Metodo> getMetodosDinamicos();

    public abstract void propagarConflicto(Metodo metodo, Clase clase);

    public void conflictoSolucionado(){
        offsetInicialConflictos++;
    }
}
