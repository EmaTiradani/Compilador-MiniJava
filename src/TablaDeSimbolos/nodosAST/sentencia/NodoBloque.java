package TablaDeSimbolos.nodosAST.sentencia;

import TablaDeSimbolos.TablaDeSimbolos;
import exceptions.SemanticException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NodoBloque extends NodoSentencia{

    ArrayList<NodoSentencia> sentencias;
    Map<String, NodoVarLocal> variablesLocales;

    public NodoBloque(){
        sentencias = new ArrayList<NodoSentencia>();
        variablesLocales = new HashMap<>();
    }

    public void insertarSentencia(NodoSentencia sentencia){
        sentencias.add(sentencia);
    }

    public ArrayList<NodoSentencia> getSentencias() {
        return sentencias;
    }

    public void setSentencias(ArrayList<NodoSentencia> sentencias) {
        this.sentencias = sentencias;
    }

    public Map<String, NodoVarLocal> getVariablesLocales() {
        return variablesLocales;
    }

    public NodoVarLocal getVarLocal(String idVar){
        return variablesLocales.get(idVar);
    }

    //DONE realizar el chequeo a ver si ya tengo una
    public void insertarVariableLocal(NodoVarLocal variableLocal) throws SemanticException {
        // Chequeo que no haya otra var con ese id
        if(TablaDeSimbolos.metodoActual.getArgumento(variableLocal.getNombre().getLexema()) != null){
            throw new SemanticException("Ya habia una variable local declarada con este identificador en el scope", variableLocal.getNombre());
        }
        // Chequeo que no haya otra var local con ese id
        if(TablaDeSimbolos.getVarLocalClaseActual(variableLocal.getNombre().getLexema())!= null){
            throw new SemanticException("Ya habia una variable local con este identificador", variableLocal.getNombre());
        }
        this.variablesLocales.put(variableLocal.getNombre().getLexema(), variableLocal);
    }

    @Override
    public void chequear() throws SemanticException {
        TablaDeSimbolos.apilarBloque(this);
        for(NodoSentencia sentencia : sentencias){
            sentencia.chequear();
        }

        TablaDeSimbolos.desapilarBloqueActual();
    }
}
