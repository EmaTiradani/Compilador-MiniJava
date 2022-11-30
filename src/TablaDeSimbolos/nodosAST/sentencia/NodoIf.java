package TablaDeSimbolos.nodosAST.sentencia;

import TablaDeSimbolos.TablaDeSimbolos;
import TablaDeSimbolos.Tipo;
import TablaDeSimbolos.nodosAST.expresion.NodoExpresion;
import exceptions.SemanticException;
import lexycal.Token;

public class NodoIf extends NodoSentencia {

    Token tokenIf;
    NodoExpresion condicion;
    NodoSentencia sentenciaIf;
    NodoSentencia sentenciaElse;

    private static int contadorEtiquetaIf = 0;
    private static int contadorEtiquetaElse = 0;

    public NodoIf(Token tokenIf, NodoExpresion condicion, NodoSentencia sentencia, NodoSentencia sentenciaElse) {

        this.tokenIf = tokenIf;
        this.condicion = condicion;
        this.sentenciaIf = sentencia;
        this.sentenciaElse = sentenciaElse;
    }

    public NodoExpresion getCondicion() {
        return condicion;
    }

    public void setCondicion(NodoExpresion condicion) {
        this.condicion = condicion;
    }

    public NodoSentencia getSentenciaIf() {
        return sentenciaIf;
    }

    public void setSentenciaIf(NodoSentencia sentencia) {
        this.sentenciaIf = sentencia;
    }

    public NodoSentencia getSentenciaElse() {
        return sentenciaElse;
    }

    public void setSentenciaElse(NodoSentencia sentencia) {
        this.sentenciaElse = sentencia;
    }

    @Override
    public void chequear() throws SemanticException {
        if(condicion.chequear().mismoTipo(new Tipo("boolean"))){

            // Meto un bloque artificial del if para tener el scope aca, sino se va a agregar en el bloque que contiene al if
            NodoBloque bloqueIf = new NodoBloque();
            bloqueIf.setOffsetInicialVarsLocales();
            TablaDeSimbolos.apilarBloque(bloqueIf);

            sentenciaIf.chequear();

            TablaDeSimbolos.desapilarBloqueActual(); //Una vez que chequee las variables con el alcance correcto, elimino el bloque


            if(sentenciaElse != null){

                NodoBloque bloqueElse = new NodoBloque();// Lo mismo que los comments del bloque del if
                TablaDeSimbolos.apilarBloque(bloqueElse);
                sentenciaElse.chequear();
                TablaDeSimbolos.desapilarBloqueActual();
            }

        }else{
            throw new SemanticException(" La condicion del if debe ser una expresion booleana", tokenIf);
        }
    }

    @Override
    public void generar() {
        condicion.generar();

        String etiquetaOutIf = nuevaEtiquetaIf();
        String etiquetaElse = nuevaEtiquetaElse(); // Lo pongo igual aunque no tenga sentencia else, asi quedan con el mismo numero todos los if-else

        if(sentenciaElse == null){
            TablaDeSimbolos.gen("BF " + etiquetaOutIf + " ; Salta afuera del if si la condicion es falsa");
            sentenciaIf.generar();
            // Hay que ponerle el NOP porque los saltos condicionales eliminan el valor del tope de la pila
            TablaDeSimbolos.gen(etiquetaOutIf +": NOP");
        }else{
            TablaDeSimbolos.gen("BF " + etiquetaElse + " ; Salta a la sentencia else la condicion es falsa");
            sentenciaIf.generar();
            TablaDeSimbolos.gen("JUMP " + etiquetaOutIf + " ; Salteo el else");
            TablaDeSimbolos.gen(etiquetaElse + ": NOP"); // Aca le tendria que poner el NOP? O me puede comer una instruccion?
            sentenciaElse.generar();
            TablaDeSimbolos.gen(etiquetaOutIf + ": NOP") ;
        }
    }

    private String nuevaEtiquetaIf(){
        String nuevaEtiquetaIf = "label_outIf" + contadorEtiquetaIf;
        contadorEtiquetaIf++;
        return nuevaEtiquetaIf;
    }

    private String nuevaEtiquetaElse(){
        String nuevaEtiquetaElse = "label_else" + contadorEtiquetaElse;
        contadorEtiquetaElse++;
        return nuevaEtiquetaElse;
    }
}
