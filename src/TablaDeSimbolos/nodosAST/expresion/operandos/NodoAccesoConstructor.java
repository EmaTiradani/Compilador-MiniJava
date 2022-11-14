package TablaDeSimbolos.nodosAST.expresion.operandos;

import TablaDeSimbolos.*;
import TablaDeSimbolos.TablaDeSimbolos;
import TablaDeSimbolos.Tipo;
import exceptions.SemanticException;
import lexycal.Token;

public class NodoAccesoConstructor extends NodoAcceso{

    Token idClase;
    ClaseConcreta claseDelConstructor;

    public NodoAccesoConstructor(Token idClase) {
        this.idClase = idClase;
    }

    @Override
    public Tipo chequear() throws SemanticException {
        claseDelConstructor = TablaDeSimbolos.getClase(idClase.getLexema());
        if(claseDelConstructor == null){
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
    public Token getToken() {
        return idClase;
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

    @Override
    public void generar() {
        // Creamos el CIR
        TablaDeSimbolos.gen("RMEM 1 ; Reserva memoria ");
        TablaDeSimbolos.gen("PUSH " + (claseDelConstructor.getAtributos().size()+1) + " ; Apila la cantidad de variables del CIR y la ref a la VT");
        TablaDeSimbolos.gen("PUSH simple_malloc ; Apila el malloc");
        TablaDeSimbolos.gen("CALL ; Llama a malloc");
        TablaDeSimbolos.gen("DUP ; Duplica el tope de la pila porque STOREREF consume la referencia al CIR");
        TablaDeSimbolos.gen("PUSH VT_" + claseDelConstructor.getNombreClase() +  " ; Apila la referencia a la VTable de la clase");
        TablaDeSimbolos.gen("STOREREF 0 ; Guarda la referencia a la VT");
        TablaDeSimbolos.gen("DUP ; Duplica el tope de la pila ya que es el this y el objeto creado por el constructor");
        // TODO esta parte no esta bien, hay que generarlo en la clase tambien
        TablaDeSimbolos.gen("PUSH " + claseDelConstructor.getConstructores().get(0).getId().getLexema());
        TablaDeSimbolos.gen("CALL");

        if(encadenado != null){
            if(this.esLadoIzquierdo)
                encadenado.setLadoIzquierdoAsignacion();
            encadenado.generar();
        }

    }
}
