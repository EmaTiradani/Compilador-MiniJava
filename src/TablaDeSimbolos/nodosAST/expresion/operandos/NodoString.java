package TablaDeSimbolos.nodosAST.expresion.operandos;

import TablaDeSimbolos.TablaDeSimbolos;
import TablaDeSimbolos.Tipo;
import TablaDeSimbolos.nodosAST.expresion.NodoOperando;
import exceptions.SemanticException;
import lexycal.Token;

public class NodoString extends NodoOperando {

    Token stringLit;

    public NodoString(Token stringLit) {
        this.stringLit = stringLit;
    }

    public Token getToken() {
        return stringLit;
    }

    public void setStringLit(Token stringLit) {
        this.stringLit = stringLit;
    }

    @Override
    public Tipo chequear() throws SemanticException {
        return new Tipo("String");
    }

    @Override
    public void generar() {// TODO lo que tengo anotado aca y en notion
        //TablaDeSimbolos.gen("PUSH "+stringLit.getLexema());
        TablaDeSimbolos.gen(".DATA"); // Es necesario este .data?
        String lexemaSinComillas = sacarComillasInicioFinLexema(stringLit.getLexema());
        TablaDeSimbolos.gen("litString_"+lexemaSinComillas+": DW \""+lexemaSinComillas+"\",0");
    }

    private String sacarComillasInicioFinLexema(String lex){
        String newLex = "";
        for(int i=1; i<lex.length()-1; i++){ // TODO guarda con esto que explota con una String vacia ""
            newLex+=lex.charAt(i);
        }
        return newLex;
    }
}
