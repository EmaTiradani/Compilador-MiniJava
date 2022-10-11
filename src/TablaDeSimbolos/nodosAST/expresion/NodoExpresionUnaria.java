package TablaDeSimbolos.nodosAST.expresion;

import TablaDeSimbolos.nodosAST.expresion.NodoExpresion;
import TablaDeSimbolos.nodosAST.expresion.NodoOperando;
import lexycal.Token;

public class NodoExpresionUnaria extends NodoExpresion {
    NodoOperando ladoDer;
    Token operador;
}
