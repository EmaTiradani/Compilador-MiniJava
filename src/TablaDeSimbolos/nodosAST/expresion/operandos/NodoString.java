package TablaDeSimbolos.nodosAST.expresion.operandos;

import TablaDeSimbolos.TablaDeSimbolos;
import TablaDeSimbolos.Tipo;
import TablaDeSimbolos.nodosAST.expresion.NodoOperando;
import exceptions.SemanticException;
import lexycal.Token;

public class NodoString extends NodoOperando {

    Token stringLit;
    private static int offset = 0;

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

    private static int getOffset(){
        offset++;
        return offset;
    }

    @Override
    public void generar() {// TODO lo que tengo anotado aca y en notion
        //TablaDeSimbolos.gen("PUSH "+stringLit.getLexema());
        int offset = getOffset();
        TablaDeSimbolos.gen(".DATA"); // Es necesario este .data?
        String lexemaSinComillas = sacarComillasInicioFinLexema(stringLit.getLexema());
        /*TablaDeSimbolos.gen("litString_"+offset+lexemaSinComillas+": DW \""+lexemaSinComillas+"\",0");
        TablaDeSimbolos.gen(".CODE");
        TablaDeSimbolos.gen("PUSH litstring_"+offset+lexemaSinComillas);*/
        TablaDeSimbolos.gen("string_" + TablaDeSimbolos.stringsCounter + ": DW \""+lexemaSinComillas+"\",0");
        TablaDeSimbolos.gen(".CODE");
        TablaDeSimbolos.gen("PUSH string_"+TablaDeSimbolos.stringsCounter);
        TablaDeSimbolos.stringsCounter++;

    }

    private String sacarComillasInicioFinLexema(String lex){
        String newLex = "";
        for(int i=1; i<lex.length()-1; i++){ // TODO guarda con esto que explota con una String vacia ""
            newLex+=lex.charAt(i);
        }
        return newLex;
    }
}
