package TablaDeSimbolos.nodosAST.expresion.operandos;

import TablaDeSimbolos.TablaDeSimbolos;
import TablaDeSimbolos.Tipo;
import TablaDeSimbolos.nodosAST.sentencia.NodoVarLocal;
import exceptions.SemanticException;
import lexycal.Token;
import TablaDeSimbolos.*;

public class NodoAccesoVar extends NodoAcceso{

    Token idVar;
    private int offset;
    private boolean esLadoIzqAsig;
    private NodoVarLocal varLocal;
    private Argumento argumento;
    private Atributo atributo;


    public NodoAccesoVar(Token idVar) {
        this.idVar = idVar;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    @Override
    public Tipo chequear() throws SemanticException {
        Tipo tipoVar;
        varLocal = TablaDeSimbolos.getVarLocalClaseActual(idVar.getLexema());
        if(varLocal != null){
            tipoVar = varLocal.getTipo();
        }else{
            argumento = TablaDeSimbolos.metodoActual.getArgumento(idVar.getLexema());
            if(argumento != null){
                tipoVar = argumento.getTipoParametro();
            }else{
                atributo = TablaDeSimbolos.claseActual.getAtributo(idVar.getLexema());
                if(atributo != null){
                    if(!TablaDeSimbolos.metodoActual.getEstatico()){
                        tipoVar = atributo.getTipo();
                    }else{
                        throw new SemanticException(" Se intento acceder a un atributo de instancia desde un entorno estatico", idVar);
                    }
                }else{
                    throw new SemanticException(" Se intento acceder al atributo "+idVar.getLexema()+"pero no existe(o no es accesible)", idVar);
                }
            }
        }

        // Chequeo el encadenado si es que tiene uno TODO (revisar si me olvide de otro mas)
        if(encadenado != null){
            return encadenado.chequear(tipoVar);
        }
        return tipoVar;
    }

    @Override
    public Token getToken() {
        return idVar;
    }

    @Override
    public boolean esAsignable() {
        if(encadenado == null){
            return true;
        }else{
            return encadenado.esAsignable();
        }
    }

    @Override
    public boolean esLlamable() {
        if(encadenado == null){
            return false;
        }else{
            return encadenado.esLlamable();
        }
    }

    @Override
    public void generar() {
        // Hay que ver si es un Parametro formal (que yo le digo argumento porque soy un nabo), variable local o un atributo de clase y a partir de eso genero el codigo
        if(atributo != null) {
            if (!esLadoIzqAsig || encadenado != null) {
                TablaDeSimbolos.gen("LOADREF " + atributo.getOffset() + " ; Apila el valor de la variable local en el tope de la pila");
            } else {// Si es lado izquierdo o si tiene un encadenado tengo que poner la expresion en el tope de la pila
                TablaDeSimbolos.gen("SWAP ; Pone el valor de la expresion en el tope de la pila");
                TablaDeSimbolos.gen("STOREREF " + atributo.getOffset() + " ; Guarda el valor de la expresion en el atributo " + atributo.getId());
            }
        }else if(argumento != null){
            if(!esLadoIzqAsig || encadenado != null){
                TablaDeSimbolos.gen("LOAD " + argumento.getOffset() + " ; Apila al valor del parametro ");
            }else{
                TablaDeSimbolos.gen("STORE " + argumento.getOffset() + " ; Guardo el valor de la expresion en el parametro");
            }
        }else{ // Si es una variable local
            if(!esLadoIzqAsig || encadenado != null){
                TablaDeSimbolos.gen("LOAD " + varLocal.getOffset() + " ; Apila al valor de la variable local " + varLocal.getNombre().getLexema());
            }else{
                TablaDeSimbolos.gen("STORE " + varLocal.getOffset() + " ; Guardo el valor de la expresion en la variable local " + varLocal.getNombre().getLexema());
            }
        }

        if(encadenado != null){
            if(this.esLadoIzquierdo)
                encadenado.setLadoIzquierdoAsignacion();
            encadenado.generar();
        }
    }
}
