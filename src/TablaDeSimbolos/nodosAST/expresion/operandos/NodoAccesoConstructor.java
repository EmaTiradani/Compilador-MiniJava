package TablaDeSimbolos.nodosAST.expresion.operandos;

import TablaDeSimbolos.*;
import TablaDeSimbolos.TablaDeSimbolos;
import TablaDeSimbolos.Tipo;
import exceptions.SemanticException;
import lexycal.Token;

public class NodoAccesoConstructor extends NodoAcceso{

    Token idClase;


    public NodoAccesoConstructor(Token idClase) {
        this.idClase = idClase;
    }

    @Override
    public Tipo chequear() throws SemanticException {
        Clase claseContenedora = TablaDeSimbolos.getClase(idClase.getLexema());
        if(claseContenedora == null){
            throw new SemanticException("La clase del constructor no existe", idClase);
        }
        // TODO tengo que chequear que la clase contenedora tenga el metodo constructor? En teoria es el constructor default.
        Tipo tipoConstructor = new Tipo(idClase.getLexema());
        if(encadenado == null){
            return tipoConstructor;
        }else{
            return encadenado.chequear(tipoConstructor);
        }
    }

    @Override
    public boolean esAsignable() {
        if(encadenado == null){
            return false;
        }else{
            return encadenado.esAsignable();
        }
    }

    @Override
    public boolean esLlamable() {
        if(encadenado == null){
            return true;
        }else{
            return encadenado.esLlamable();
        }
    }
}
