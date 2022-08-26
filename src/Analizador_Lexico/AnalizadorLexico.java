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

            //Whitespaces
            case ' ' : {
                actualizarCaracterActual();
                return e0();
            }

            //Operators
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
            case '&' : {
                actualizarLexema();
                actualizarCaracterActual();
                return e15();
            }
            case '|' : {
                actualizarLexema();
                actualizarCaracterActual();
                return e16();
            }
            case '%' : {
                actualizarLexema();
                actualizarCaracterActual();
                return e17();
            }

            //Punctuation
            case '(' : {
                actualizarLexema();
                actualizarCaracterActual();
                return e18();
            }
            case ')' : {
                actualizarLexema();
                actualizarCaracterActual();
                return e19();
            }
            case '{' : {
                actualizarLexema();
                actualizarCaracterActual();
                return e20();
            }
            case '}' : {
                actualizarLexema();
                actualizarCaracterActual();
                return e21();
            }
            case ';' : {
                actualizarLexema();
                actualizarCaracterActual();
                return e22();
            }
            case ',' : {
                actualizarLexema();
                actualizarCaracterActual();
                return e23();
            }
            case '.' : {
                actualizarLexema();
                actualizarCaracterActual();
                return e24();
            }
            case 'a' :
            case 'b' :
            case 'c' :

                    {

            }


            case '"' : return sl_1();

            default:
                if(Character.isUpperCase(caracterActual)){
                    return e3();
                }else{
                    if(Character.isLowerCase(caracterActual)){
                        return e1();
                    }
                }
                throw new ExcepcionLexica(lexema, fileManager.getLineNumber(), fileManager.getColumn());
        }
    }

    private Token e1() throws IOException, ExcepcionLexica{
        actualizarLexema();
        actualizarCaracterActual();


    }

    private Token e4() throws IOException, ExcepcionLexica { //Arreglar esto que rompi todo
        actualizarLexema();
        actualizarCaracterActual();
        if(caracterActual == '/') {
            actualizarCaracterActual();
            return e5();
        }else {
            if(caracterActual != '*') throw new ExcepcionLexica(lexema, fileManager.getLineNumber(), fileManager.getColumn());
            else{
                return e6();
            }
        }

        /*}
        if(caracterActual == '*')
            return e6(); //Comentario multilinea, deberia limpiar la barra '/' del lexema, no?
        return new Token(TokenId.op_division, lexema, fileManager.getLineNumber());*/
    }

    private Token e5() throws IOException, ExcepcionLexica {
        actualizarLexema();
        actualizarCaracterActual();
        if(caracterActual == '\n')
            return e0();
        else
            return e5();
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

    private  Token e10() throws IOException, ExcepcionLexica {
        actualizarLexema();
        actualizarCaracterActual();
        if(caracterActual == '='){
            actualizarLexema();
            actualizarCaracterActual();
            return new Token(TokenId.op_distinto, lexema, fileManager.getLineNumber());
        }else{
            throw new ExcepcionLexica(lexema, fileManager.getLineNumber(), fileManager.getColumn());
        }

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

    private  Token e12() {
        Token token = new Token(TokenId.op_suma, lexema, fileManager.getLineNumber());
        return token;
    }

    private  Token e13() throws IOException, ExcepcionLexica{
        return new Token(TokenId.op_resta, lexema, fileManager.getLineNumber());
    }
    private  Token e14() throws IOException, ExcepcionLexica{
        return new Token(TokenId.op_multiplicacion, lexema, fileManager.getLineNumber());
    }
    private Token e15() throws IOException, ExcepcionLexica{
        actualizarLexema();
        actualizarCaracterActual();
        if(caracterActual == '&')
            return new Token(TokenId.op_and, lexema, fileManager.getLineNumber());
        else
            throw new ExcepcionLexica(lexema, fileManager.getLineNumber(), fileManager.getColumn());
    }
    private Token e16() throws IOException, ExcepcionLexica{
        actualizarLexema();
        actualizarCaracterActual();
        if(caracterActual == '|')
            return new Token(TokenId.op_or, lexema, fileManager.getLineNumber());
        else
            throw new ExcepcionLexica(lexema, fileManager.getLineNumber(), fileManager.getColumn());
    }
    private  Token e17() throws IOException, ExcepcionLexica{
        return new Token(TokenId.op_modulo, lexema, fileManager.getLineNumber());
    }
    private  Token e18() throws IOException, ExcepcionLexica{
        return new Token(TokenId.punt_parentIzq, lexema, fileManager.getLineNumber());
    }
    private  Token e19() throws IOException, ExcepcionLexica{
        return new Token(TokenId.punt_parentDer, lexema, fileManager.getLineNumber());
    }
    private  Token e20() throws IOException, ExcepcionLexica{
        return new Token(TokenId.punt_llaveIzq, lexema, fileManager.getLineNumber());
    }
    private  Token e21() throws IOException, ExcepcionLexica{
        return new Token(TokenId.punt_llaveDer, lexema, fileManager.getLineNumber());
    }
    private  Token e22() throws IOException, ExcepcionLexica{
        return new Token(TokenId.punt_puntoYComa, lexema, fileManager.getLineNumber());
    }
    private  Token e23() throws IOException, ExcepcionLexica{
        return new Token(TokenId.punt_coma, lexema, fileManager.getLineNumber());
    }
    private  Token e24() throws IOException, ExcepcionLexica{
        return new Token(TokenId.punt_punto, lexema, fileManager.getLineNumber());
    }

    private  Token sl_1() throws IOException, ExcepcionLexica {

       actualizarLexema();
       actualizarCaracterActual();

        if(caracterActual == '\\'){
            return sl_2();
        }else{
            if(caracterActual == '\n' || caracterActual == '\u001a') throw new ExcepcionLexica(lexema, fileManager.getLineNumber(), fileManager.getColumn());
            else
                if(caracterActual == '"')
                    return sl_3();
                else
                    return sl_1();
        }
    }
    private Token sl_3() throws IOException, ExcepcionLexica {
        return new Token(TokenId.stringLiteral, lexema, fileManager.getLineNumber());
    }

    private Token sl_2() throws IOException, ExcepcionLexica {
        actualizarLexema();
        actualizarCaracterActual();
        if(caracterActual == '\\'){
            sl_2();
        }
        else{
            if(caracterActual == '\n') throw new ExcepcionLexica(lexema, fileManager.getLineNumber(), fileManager.getColumn());
            else{
                return sl_1(); //Char != de enter o /
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
