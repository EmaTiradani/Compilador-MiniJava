package semantic;

public class NodoReturn extends NodoSentencia{
    NodoExpresion retorno;

    public NodoReturn(NodoExpresion retorno) {
        this.retorno = retorno;
    }

    public NodoExpresion getRetorno() {
        return retorno;
    }

    public void setRetorno(NodoExpresion retorno) {
        this.retorno = retorno;
    }

    @Override
    public void chequear() {

    }
}
