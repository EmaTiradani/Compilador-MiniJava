package TablaDeSimbolos.nodosAST.expresion.operandos;

import TablaDeSimbolos.Clase;
import TablaDeSimbolos.TablaDeSimbolos;
import TablaDeSimbolos.Tipo;
import TablaDeSimbolos.nodosAST.expresion.NodoExpresion;
import exceptions.SemanticException;
import lexycal.Token;
import TablaDeSimbolos.*;

import java.util.ArrayList;
import java.util.List;

public class NodoMetodoEncadenado extends NodoEncadenado {

    protected List<NodoExpresion> parametros;
    protected Token idMet;

    public NodoMetodoEncadenado(Token tokenIdMet) {
        idMet = tokenIdMet;
        parametros = new ArrayList<>();
        esLadoIzquierdo = false;
    }

    @Override
    public Tipo chequear(Tipo tipoEncadenadoLadoIzq) throws SemanticException {
        Tipo tipoMetodo;
        Clase claseContenedora = TablaDeSimbolos.getClase(tipoEncadenadoLadoIzq.getType());
        if(claseContenedora == null)
            claseContenedora = TablaDeSimbolos.getInterfaz(tipoEncadenadoLadoIzq.getType());
        if(claseContenedora != null){
            Metodo metodo = claseContenedora.getMetodoQueConformaParametros(idMet, parametros);
            if(metodo == null){
                throw new SemanticException("El metodo "+idMet.getLexema()+" no es accesible en la clase "+claseContenedora.getNombreClase(), idMet);
            }else{
                tipoMetodo = metodo.getTipoRetorno();
            }
        }else{
            throw new SemanticException("El tipo no es de una clase declarada", idMet);
        }

        if(nodoEncadenado == null){
            return tipoMetodo;
        }else{
            return nodoEncadenado.chequear(tipoMetodo);
        }
    }

    @Override
    public Token getToken() {
        return idMet;
    }

    @Override
    public boolean esAsignable() {
        if(nodoEncadenado == null){
            return false;
        }else{
            return nodoEncadenado.esAsignable();
        }
    }

    @Override
    public boolean esLlamable() {
        if(nodoEncadenado == null){
            return true;
        }else{
            return nodoEncadenado.esLlamable();
        }    }

    @Override
    public Tipo chequearThis(Tipo tipoEncadenadoLadoIzq) throws SemanticException {
        return chequear(tipoEncadenadoLadoIzq);
    }

    @Override
    public void generar() {

    }


    public List<NodoExpresion> getParametros() {
        return parametros;
    }

    public void setParametros(List<NodoExpresion> parametros) {
        this.parametros = parametros;
    }


}
