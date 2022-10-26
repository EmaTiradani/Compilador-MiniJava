package TablaDeSimbolos.nodosAST.sentencia;

import TablaDeSimbolos.TablaDeSimbolos;
import TablaDeSimbolos.Tipo;
import TablaDeSimbolos.nodosAST.expresion.NodoExpresion;
import exceptions.SemanticException;
import lexycal.Token;

public class NodoReturn extends NodoSentencia {

    Token tokenReturn;
    NodoExpresion retorno; // Puede ser null

    public NodoReturn(Token retorno) {
        this.tokenReturn = retorno;
    }

    public NodoExpresion getRetorno() {
        return retorno;
    }

    public void setRetorno(NodoExpresion retorno) {
        this.retorno = retorno;
    }

    public void insertarExpresion(NodoExpresion expresion){
        retorno = expresion;
    }

    @Override
    public void chequear() throws SemanticException {
        Tipo tipoMetodo = TablaDeSimbolos.metodoActual.getTipoRetorno();

        if(retorno == null){
            if(!(tipoMetodo.mismoTipo(new Tipo(tokenReturn.getLexema())))){
                if(!tipoMetodo.mismoTipo(new Tipo("void"))){
                    throw new SemanticException(" se esperaba un retorno de tipo "+tipoMetodo.getType(), tokenReturn);
                }
            }
        }else{
            if(TablaDeSimbolos.metodoActual.getTipoRetorno().mismoTipo(new Tipo("void"))){
                throw new SemanticException(" el metodo no deberia tener un return ya que es void", tokenReturn);
            }
            Tipo tipoExpresion = retorno.chequear();
            if(!tipoMetodo.checkSubtipo(tipoExpresion)){
                throw new SemanticException(" la expresion no es de un tipo compatible con el retorno del metodo", tokenReturn);
            }
        }
    }
}
