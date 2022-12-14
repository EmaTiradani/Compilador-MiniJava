package TablaDeSimbolos.nodosAST.expresion;

import TablaDeSimbolos.TablaDeSimbolos;
import TablaDeSimbolos.Tipo;
import TablaDeSimbolos.nodosAST.expresion.NodoExpresion;
import TablaDeSimbolos.nodosAST.expresion.NodoOperando;
import exceptions.SemanticException;
import lexycal.Token;

public class NodoExpresionUnaria extends NodoExpresion {
    NodoOperando ladoDer;
    Token operador;

    public NodoExpresionUnaria(Token operador, NodoOperando operando){
        this.operador = operador;
        this.ladoDer = operando;
    }

    public NodoOperando getLadoDer() {
        return ladoDer;
    }

    public void setLadoDer(NodoOperando ladoDer) {
        this.ladoDer = ladoDer;
    }

    public Token getToken() {
        return operador;
    }

    public void setOperador(Token operador) {
        this.operador = operador;
    }

    public Tipo chequear() throws SemanticException {
        if(operador != null){
            if(operador.getLexema().equals("+") || operador.getLexema().equals("-")){
                if(ladoDer.chequear().mismoTipo(new Tipo("int"))){
                    return new Tipo("int");
                }else{
                    throw new SemanticException(" El operador unario "+operador.getLexema()+" trabaja con tipos enteros", operador);
                }
            }else{// Operador = "!", trabaja con un booleano
                if(ladoDer.chequear().mismoTipo(new Tipo("boolean"))){
                    return new Tipo("boolean");
                }else{
                    throw new SemanticException(" El operador '!' funciona con una expresion booleana unicamente", operador);
                }
            }
        }else{// Si el operador es null
            return ladoDer.chequear();
        }
    }

    public void generar() {
        ladoDer.generar();
        if(operador.getLexema().equals("+")){
            // le estoy diciendo positivo a un positivo, no hago nada no? No hay un operador
        }else if(operador.getLexema().equals("-")){
            TablaDeSimbolos.gen("NEG ; Menos unario");
        }else{ // Operador = "!"
            TablaDeSimbolos.gen("NOT ; Negacion logica");
        }
    }
}
