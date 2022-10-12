package TablaDeSimbolos.nodosAST.sentencia;

import java.util.ArrayList;
import java.util.Map;

public class NodoBloque {

    ArrayList<NodoSentencia> sentencias;
    Map<String, NodoVarLocal> variablesLocales;

    public NodoBloque(ArrayList<NodoSentencia> sentencias, Map<String, NodoVarLocal> variablesLocales) {
        this.sentencias = sentencias;
        this.variablesLocales = variablesLocales;
    }

    public NodoBloque(){

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
}
