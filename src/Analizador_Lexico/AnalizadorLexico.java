package Analizador_Lexico;

import FileManager.FileManager;

import java.io.IOException;

public class AnalizadorLexico {

    String lexema;
    char caracterActual;
    FileManager fileManager;
    PalabrasReservadas tokenIdentifier;

    public AnalizadorLexico(FileManager fileManager) throws IOException {
        this.fileManager = fileManager;
        actualizarCaracterActual();
        tokenIdentifier = new PalabrasReservadas();
    }

    public Token getToken() throws ExcepcionLexica, IOException {
        lexema = "";
        return e0();
    }
//    /t es para tabs
    public Token e0() throws ExcepcionLexica, IOException {
        //System.out.print(caracterActual);

        switch(caracterActual) {

            //Whitespaces
            case ' ':
            case '\n': {
                actualizarCaracterActual();
                return e0();
            }

            //Operators
            case '>': {
                actualizarLexema();
                actualizarCaracterActual();
                return e8(); //Tiene que ver si es un >=
            }

            case '<': {
                actualizarLexema();
                actualizarCaracterActual();
                return e9(); //Tiene que ver si es un <=
            }
            case '!': {
                actualizarLexema();
                actualizarCaracterActual();
                return e10(); //Tiene que ver si es un !=, pero ! tambien es aceptado
            }
            case '=': {
                actualizarLexema();
                actualizarCaracterActual();
                return e11(); //Tiene que ver si es un ==
            }
            case '+': {
                actualizarLexema();
                actualizarCaracterActual();
                return e12();
            }
            case '-': {
                actualizarLexema();
                actualizarCaracterActual();
                return e13();
            }
            case '*': {
                actualizarLexema();
                actualizarCaracterActual();
                return e14();
            }
            case '/': {
                //actualizarLexema();
                //actualizarCaracterActual();
                return e4(); // TODO aca va lo de los comentarios o a la division
            }
            case '&': {
                actualizarLexema();
                actualizarCaracterActual();
                return e15();
            }
            case '|': {
                actualizarLexema();
                actualizarCaracterActual();
                return e16();
            }
            case '%': {
                actualizarLexema();
                actualizarCaracterActual();
                return e17();
            }

            //Punctuation
            case '(': {
                actualizarLexema();
                actualizarCaracterActual();
                return e18();
            }
            case ')': {
                actualizarLexema();
                actualizarCaracterActual();
                return e19();
            }
            case '{': {
                actualizarLexema();
                actualizarCaracterActual();
                return e20();
            }
            case '}': {
                actualizarLexema();
                actualizarCaracterActual();
                return e21();
            }
            case ';': {
                actualizarLexema();
                actualizarCaracterActual();
                return e22();
            }
            case ',': {
                actualizarLexema();
                actualizarCaracterActual();
                return e23();
            }
            case '.': {
                actualizarLexema();
                actualizarCaracterActual();
                return e24();
            }
            case '\'': {
                actualizarLexema();
                actualizarCaracterActual();
                return ec_1();
            }

            case '"' : return sl_1();

            case '\u001a' : return e30();

            default:
                if(Character.isUpperCase(caracterActual)){
                    actualizarLexema();
                    actualizarCaracterActual();
                    return e3();
                }else{
                    if(Character.isLowerCase(caracterActual)){
                        actualizarLexema();
                        actualizarCaracterActual();
                        return e1();
                    }else{
                        if(Character.isDigit(caracterActual)){
                            actualizarLexema();
                            actualizarCaracterActual();
                            return e2();
                        }else{
                            actualizarLexema();
                            throw new ExcepcionLexica(lexema, fileManager.getLineNumber(), fileManager.getColumn(), "No es un caracter valido", fileManager.getLine());
                        }
                    }
                }

        }
    }

    private Token e1() throws IOException, ExcepcionLexica{
        //actualizarLexema();
        //actualizarCaracterActual();
        if(Character.isDigit(caracterActual) || Character.isLetter(caracterActual) || caracterActual == '_'){
            actualizarLexema();
            actualizarCaracterActual();
            return e1();
        }else{
            TokenId id = tokenIdentifier.getTokenId(lexema);
            if(id == null){
                return new Token(TokenId.idMetVar, lexema, fileManager.getLineNumber());
            }else{
                return new Token(id, lexema, fileManager.getLineNumber());
            }
        }
    }
    private Token e2() throws IOException, ExcepcionLexica{//Digitos
        if(Character.isDigit(caracterActual)){
            actualizarLexema();
            actualizarCaracterActual();
            return e2();
        }else{
            if(lexema.length() > 9) throw new ExcepcionLexica(lexema, fileManager.getLineNumber(), fileManager.getColumn(), "Digito mayor a 9 cifras ", fileManager.getPreviousLine());
            else
                return new Token(TokenId.intLiteral, lexema, fileManager.getLineNumber());
        }
    }
    private Token e3() throws IOException, ExcepcionLexica{
        if(Character.isLetter(caracterActual) || Character.isDigit(caracterActual) || caracterActual == '_'){
            actualizarLexema();
            actualizarCaracterActual();
            return e3();
        }else{
            TokenId id = tokenIdentifier.getTokenId(lexema);
            if(id == null){
                return new Token(TokenId.idClase, lexema, fileManager.getLineNumber());
            }else{
                return new Token(id, lexema, fileManager.getLineNumber());
            }
        }
    }

    private Token e4() throws IOException, ExcepcionLexica {
        actualizarCaracterActual();
        if(caracterActual == '/') { //Comments single line
            actualizarCaracterActual();
            return e5();
        }else {
            if(caracterActual == '*'){ //Comments multiple line
                return e6();
            } else{ //Division token
                actualizarLexema();
                return new Token(TokenId.op_division, lexema, fileManager.getLineNumber());
            }
        }
    }

    private Token e5() throws IOException, ExcepcionLexica { //Comentario single line
        actualizarCaracterActual();
        if(caracterActual == '\n')
            return e0();
        else
            return e5();
    }

    private Token e6() throws IOException, ExcepcionLexica{ //Comentario multilinea
        actualizarCaracterActual();
        if(caracterActual == '\n' || caracterActual != '*'){
            actualizarCaracterActual();
            return e6();
        }else{
            return e7();
        }
    }
    private Token e7() throws IOException, ExcepcionLexica{
        actualizarCaracterActual();
        if(caracterActual == '*'){
            actualizarCaracterActual();
            return e7();
        }else{
            if(caracterActual == '/'){
                actualizarCaracterActual();
                return e0();
            }else{
                return e6(); //Si no es / ni * vuelvo a e6
            }
        }
    }
    private Token e8() throws IOException, ExcepcionLexica {
        actualizarLexema();
        if(caracterActual == '=') {
            actualizarLexema();
            return new Token(TokenId.op_mayorIgual, lexema, fileManager.getLineNumber());
        }
        else{
            //throw new ExcepcionLexica(lexema, fileManager.getLineNumber(), fileManager.getColumn(), );
            return new Token(TokenId.op_mayor, lexema, fileManager.getLineNumber());
        }
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
            //throw new ExcepcionLexica(lexema, fileManager.getLineNumber(), fileManager.getColumn(), "& no es una simbolo valido, se esperaba la combinacion &&", fileManager.getLine());
            return new Token(TokenId.op_igual, lexema, fileManager.getLineNumber());
        }

    }

    private  Token e11() throws IOException, ExcepcionLexica {
        actualizarLexema();
        actualizarCaracterActual();
        if(caracterActual == '=') {
            actualizarLexema();
            return new Token(TokenId.asignacion, lexema, fileManager.getLineNumber());
        }
        else{
            return new Token(TokenId.op_igual, lexema, fileManager.getLineNumber());
        }
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
            throw new ExcepcionLexica(lexema, fileManager.getLineNumber(), fileManager.getColumn(), "& no es una simbolo valido, se esperaba la combinacion &&", fileManager.getPreviousLine());
    }
    private Token e16() throws IOException, ExcepcionLexica{
        actualizarLexema();
        actualizarCaracterActual();
        if(caracterActual == '|')
            return new Token(TokenId.op_or, lexema, fileManager.getLineNumber());
        else
            throw new ExcepcionLexica(lexema, fileManager.getLineNumber(), fileManager.getColumn(), "| no es un simbolo valido, se esperaba ||", fileManager.getPreviousLine());
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
    private Token ec_1() throws IOException, ExcepcionLexica {
        actualizarLexema();
        actualizarCaracterActual();
        if(caracterActual == '\'' || caracterActual == '\\')
            throw new ExcepcionLexica(lexema, fileManager.getLineNumber(), fileManager.getColumn(), "Caracter mal cerrado, se esperaba un '", fileManager.getLine());
        return null;
    }

    private  Token sl_1() throws IOException, ExcepcionLexica {

       actualizarLexema();
       actualizarCaracterActual();

        if(caracterActual == '\\'){
            return sl_2();
        }else{
            if(caracterActual == '\n' || caracterActual == '\u001a') throw new ExcepcionLexica(lexema, fileManager.getLineNumber(), fileManager.getColumn(), "StringLiteral sin cerrar, se esperaba un \" ", fileManager.getPreviousLine());
            else
                if(caracterActual == '"') {
                    return sl_3();
                }else
                    return sl_1();
        }
    }
    private Token sl_3() throws IOException, ExcepcionLexica {
        actualizarLexema();
        actualizarCaracterActual();
        return new Token(TokenId.stringLiteral, lexema, fileManager.getLineNumber());
    }

    private Token sl_2() throws IOException, ExcepcionLexica {
        actualizarLexema();
        actualizarCaracterActual();
        if(caracterActual == '\n') throw new ExcepcionLexica(lexema, fileManager.getLineNumber(), fileManager.getColumn(), "StringLiteral sin cerrar, se esperaba un \" ", fileManager.getPreviousLine());
        else{
            return sl_1(); //Char != de enter
        }

    }

    private Token e30() {
        return new Token(TokenId.EOF, lexema, fileManager.getLineNumber());
    }



    private void actualizarLexema(){
        lexema = lexema + caracterActual;
    }
    public void actualizarCaracterActual() throws IOException {
        caracterActual = fileManager.getNextChar();
    }
}
