package TablaDeSimbolos.nodosAST.sentencia;

import TablaDeSimbolos.TablaDeSimbolos;
import TablaDeSimbolos.*;
import TablaDeSimbolos.nodosAST.expresion.NodoExpresion;
import exceptions.SemanticException;
import lexycal.Token;

public class NodoReturn extends NodoSentencia {

    Token tokenReturn;
    NodoExpresion retorno; // Puede ser null
    int cantVariablesLocales;
    Metodo metodoContenedor;

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

        metodoContenedor = TablaDeSimbolos.metodoActual;
        cantVariablesLocales = TablaDeSimbolos.getBloqueActual().getVariablesLocales().size();
    }

    @Override
    public void generar(){
        TablaDeSimbolos.gen("FMEM " + cantVariablesLocales + " ; Libera el espacio reservado para las variables locales");
        int offsetReturn = metodoContenedor.getArgumentos().size();
        if(metodoContenedor.getEstatico()){
            offsetReturn += 3; // ED, PR y la primer var
        }else{
            offsetReturn += 4; // Idem arriba pero con this
        }
        // TODO lo de aca arriba lo podria pasar a la clase Metodo creo
        if(retorno == null){
            if(metodoContenedor.getTipoRetorno().mismoTipo(new Tipo("void"))){
                TablaDeSimbolos.gen("STOREFP ; Actualiza el FP para que apunte al RA del llamador");
                TablaDeSimbolos.gen("RET " + offsetReturn + " ; Retorno del metodo");
            }else{
                retorno.generar();
                TablaDeSimbolos.gen("STORE " + offsetReturn + " ; Guarda el valor de la expresion retorno en el espacio reservado para el return");
                TablaDeSimbolos.gen("STOREFP ; Actualiza el FP para que apunte al RA del llamador");
                TablaDeSimbolos.gen("RET "+ offsetReturn + " ; Retorno del metodo");
            }
        }

    }
}
