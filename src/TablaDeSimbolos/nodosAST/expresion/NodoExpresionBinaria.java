package TablaDeSimbolos.nodosAST.expresion;

import TablaDeSimbolos.nodosAST.expresion.NodoExpresion;
import lexycal.Token;

public class NodoExpresionBinaria extends NodoExpresion {
    NodoExpresion ladoIzquierdo;
    NodoExpresion ladoDerecho;
    Token operador;
}
