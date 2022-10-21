package TablaDeSimbolos.nodosAST.sentencia;

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

    public void setVariablesLocales(Map<String, NodoVarLocal> variablesLocales) {
        this.variablesLocales = variablesLocales;
    }

    @Override
    public void chequear() throws SemanticException {
        for(NodoSentencia sentencia : sentencias){
            sentencia.chequear();
        }
    }
}
