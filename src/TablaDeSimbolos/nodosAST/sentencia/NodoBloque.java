package TablaDeSimbolos.nodosAST.sentencia;

import TablaDeSimbolos.TablaDeSimbolos;
import exceptions.SemanticException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NodoBloque extends NodoSentencia{

    ArrayList<NodoSentencia> sentencias;
    Map<String, NodoVarLocal> variablesLocales;
    boolean chequeado;
    boolean generado;
    int offsetVarsLocales;

    public NodoBloque(){
        sentencias = new ArrayList<NodoSentencia>();
        variablesLocales = new HashMap<>();
        chequeado = false;
        generado = false;
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

    public void setOffsetVarsLocales(int offsetVarsLocales){
        this.offsetVarsLocales = offsetVarsLocales;
    }

    public Map<String, NodoVarLocal> getVariablesLocales() {
        return variablesLocales;
    }

    public NodoVarLocal getVarLocal(String idVar){
        return variablesLocales.get(idVar);
    }

    public void insertarVariableLocal(NodoVarLocal variableLocal) throws SemanticException {
        // Chequeo que no haya otra var con ese id
        if(TablaDeSimbolos.metodoActual.getArgumento(variableLocal.getNombre().getLexema()) != null){
            throw new SemanticException(" Ya habia una variable local declarada con este identificador en el scope", variableLocal.getNombre());
        }
        // Chequeo que no haya otra var local con ese id
        if(TablaDeSimbolos.getVarLocalClaseActual(variableLocal.getNombre().getLexema())!= null){
            throw new SemanticException(" Ya habia una variable local con este identificador", variableLocal.getNombre());
        }

        variableLocal.setOffset(offsetVarsLocales--);
        this.variablesLocales.put(variableLocal.getNombre().getLexema(), variableLocal);
    }

    @Override
    public void chequear() throws SemanticException {
        // Lo tengo que hacer antes de apilar este bloque, asi sigo el offset del bloque contenedor
        setOffsetInicialVarsLocales();

        if(!chequeado){
            TablaDeSimbolos.apilarBloque(this);
            for(NodoSentencia sentencia : sentencias){
                sentencia.chequear();
                chequearCodigoMuerto(sentencia);
            }

            TablaDeSimbolos.desapilarBloqueActual();
        }
        chequeado = true;
    }

    public void generar(){
        if(!generado){
            TablaDeSimbolos.apilarBloque(this);
            for(NodoSentencia sentencia : sentencias){
                sentencia.generar();
            }

            TablaDeSimbolos.desapilarBloqueActual();

            TablaDeSimbolos.gen("FMEM "+variablesLocales.size()+" ; Libera el espacio reservado utilizado para almacenar las variables locales");
        }
        generado = true;
    }

    @Override
    public boolean isReturn() {
        if(sentencias.size() != 0)
            return getUltimaSentencia().isReturn();
        else return false;
    }

    public void chequearCodigoMuerto(NodoSentencia sentencia) throws SemanticException{
        if(sentencia.isReturn() && !sentencia.equals(getUltimaSentencia())){
             throw new SemanticException("Código inalcanzable en el método " + TablaDeSimbolos.metodoActual.getId().getLexema()
                     + " en la linea " + TablaDeSimbolos.metodoActual.getId().getLinea());
        }
    }

    private NodoSentencia getUltimaSentencia(){
        return sentencias.get(sentencias.size()-1);
    }

    public void setOffsetInicialVarsLocales(){
        if(TablaDeSimbolos.getBloqueActual() == null){
            offsetVarsLocales = 0;
        }else{
            offsetVarsLocales = TablaDeSimbolos.getBloqueActual().getOffsetVarsLocales();
        }
    }

    public int getOffsetVarsLocales(){
        return offsetVarsLocales;
    }
}
