package Analizador_Lexico;

import FileManager.FileManager;

import java.io.IOException;

public class AnalizadorLexico {

    String lexema;
    char caracterActual;
    FileManager fileManager;

    public AnalizadorLexico(FileManager fileManager) throws IOException {
        this.fileManager = fileManager;
        actualizarCaracterActual();
    }

    public Token getToken() throws ExcepcionLexica, IOException {
        lexema = "";
        return e0();
    }
//    /t es para tabs
    public Token e0() throws ExcepcionLexica, IOException {
        System.out.print(caracterActual);

        switch(caracterActual){

            case ' ' : {
                actualizarCaracterActual();
                return e0();
            }

            case '>' : {
                actualizarLexema();
                actualizarCaracterActual();
                return e8(); //Tiene que ver si es un >=
            }

            case '<' : {
                actualizarLexema();
                actualizarCaracterActual();
                return e9(); //Tiene que ver si es un <=
            }
            case '!' : {
                actualizarLexema();
                actualizarCaracterActual();
                return e10(); //Tiene que ver si es un !=, pero ! tambien es aceptado
            }
            case '=' : {
                actualizarLexema();
                actualizarCaracterActual();
                return e11(); //Tiene que ver si es un ==
            }
            case '+' : {
                actualizarLexema();
                actualizarCaracterActual();
                return e12();
            }
            case '-' : {
                actualizarLexema();
                actualizarCaracterActual();
                return e13();
            }
            case '*' : {
                actualizarLexema();
                actualizarCaracterActual();
                return e14();
            }
            case '/' : {
                actualizarLexema();
                actualizarCaracterActual();
                return e4(); // TODO aca va lo de los comentarios
            }




            case '"' : return e12();

            default: throw new ExcepcionLexica(lexema, fileManager.getLineNumber(), fileManager.getColumn());
        }
    }

    private Token e4() throws IOException, ExcepcionLexica {
        actualizarLexema();
        actualizarCaracterActual();
        if(caracterActual == '/'){
            actualizarCaracterActual();
            if(caracterActual == '\n') throw new ExcepcionLexica(lexema, fileManager.getLineNumber(), fileManager.getColumn());
            else{
                return e0();
            }
            return e4();
        }
        if(caracterActual == '*')
            return e6(); //Comentario multilinea, deberia limpiar la barra '/' del lexema, no?
        return new Token(TokenId.op_division, lexema, fileManager.getLineNumber());
    }

    private Token e6() throws IOException, ExcepcionLexica{ //Comentario multilinea
        actualizarLexema();
        actualizarCaracterActual();
        if(caracterActual == '\n' || caracterActual != '*'){
            actualizarCaracterActual();
            return e6();
        }else{
            return e7();
        }
    }
    private Token e7() throws IOException, ExcepcionLexica{
        actualizarLexema();
        actualizarCaracterActual();
        if(caracterActual == '*'){
            actualizarCaracterActual();
            return e7();
        }else{
            if(caracterActual == '/'){
                return e0();
            }else{
                return e6(); //Si no es / ni * vuelvo a e6
            }
        }
    }
    private Token e8() throws IOException {
        return new Token(TokenId.punt_punto, lexema, fileManager.getLineNumber());
    }
    private Token e9() {
        return new Token(TokenId.punt_punto, lexema, fileManager.getLineNumber());
    }


    private  Token e11() throws IOException, ExcepcionLexica {
        actualizarLexema();
        actualizarCaracterActual();
        if(caracterActual == '=') {
            actualizarLexema();
            return new Token(TokenId.op_igual, lexema, fileManager.getLineNumber());
        }
        else throw new ExcepcionLexica(lexema, fileManager.getLineNumber(), fileManager.getColumn()); //TODO está bien esta crotada de los distintos return?
    }

    private  Token e12() throws IOException, ExcepcionLexica {

       actualizarLexema();
       actualizarCaracterActual();

        /*if(caracterActual != '\n'){
            return
        }*/

        if(caracterActual == '\\'){
            return e13();
        }/*else{
            if(caracterActual == '\n' || caracterActual == '\u001a') throw new ExcepcionLexica(lexema, fileManager.getLineNumber(), fileManager.getColumn());
            else
                return
        }*/

        return null;
    }

    private Token e13() throws IOException, ExcepcionLexica {
        actualizarLexema();
        actualizarCaracterActual();
        if(caracterActual == '\\'){
            e13();
        }
        else{
            if(caracterActual == '\n') throw new ExcepcionLexica(lexema, fileManager.getLineNumber(), fileManager.getColumn());
            else{
                return null;
            }
        }
        return null;
    }

    private void actualizarLexema(){
        lexema = lexema + caracterActual;
    }
    public void actualizarCaracterActual() throws IOException {
        caracterActual = fileManager.getNextChar();
    }
}
