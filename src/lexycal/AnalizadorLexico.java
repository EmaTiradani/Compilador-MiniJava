package lexycal;

import exceptions.LexicalException;
import fileManager.FileManager;

import java.io.IOException;

public class AnalizadorLexico {

    String lexema;
    char caracterActual;
    FileManager fileManager;
    PalabrasReservadas tokenIdentifier;

    String lineaComienzoComment;
    int nroLineaComienzoComment;
    int columnaComienzoComment;

    public AnalizadorLexico(FileManager fileManager) throws IOException {
        this.fileManager = fileManager;
        actualizarCaracterActual();
        tokenIdentifier = new PalabrasReservadas();
    }

    public Token getToken() throws LexicalException, IOException {
        lexema = "";
        return e0();
    }

    public Token e0() throws LexicalException, IOException {

        switch(caracterActual) {

            //Whitespaces
            case ' ':
            case '\t':
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
                return e4();
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

            //EOF
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
                            throw new LexicalException(lexema, fileManager.getLineNumber(), fileManager.getColumn(), "No es un caracter valido", fileManager.getLine());
                        }
                    }
                }

        }
    }

    private Token e1() throws IOException, LexicalException {
        if(Character.isDigit(caracterActual) || Character.isLetter(caracterActual) || caracterActual == '_'){
            actualizarLexema();
            actualizarCaracterActual();
            return e1();
        }else{
            TokenId id = tokenIdentifier.getTokenId(lexema);
            if (id == null) {
                return new Token(TokenId.idMetVar, lexema, fileManager.getLineNumber());
            }else { // Si es una palabra reservada retorna el id de la misma
                return new Token(id, lexema, fileManager.getLineNumber());
            }
        }
    }
    private Token e2() throws IOException, LexicalException {//Digitos
        if(Character.isDigit(caracterActual)){
            actualizarLexema();
            actualizarCaracterActual();
            return e2();
        }else{
            if(lexema.length() <= 9) {
                return new Token(TokenId.intLiteral, lexema, fileManager.getLineNumber());
            }else{
                if (caracterActual == '\n' || caracterActual == '\u001a')
                    throw new LexicalException(lexema, fileManager.getLineNumber(), fileManager.getPreviousLine().length() + 1, "Digito mayor a 9 cifras ", fileManager.getPreviousLine());
                else
                    throw new LexicalException(lexema, fileManager.getLineNumber(), fileManager.getColumn(), "Digito mayor a 9 cifras ", fileManager.getLine());
            }
        }
    }

    private Token e3() throws IOException, LexicalException {
        if(Character.isLetter(caracterActual) || Character.isDigit(caracterActual) || caracterActual == '_'){
            actualizarLexema();
            actualizarCaracterActual();
            return e3();
        }else{
                return new Token(TokenId.idClase, lexema, fileManager.getLineNumber());
        }
    }

    private Token e4() throws IOException, LexicalException {
        actualizarCaracterActual();
        if(caracterActual == '/') { //Comments single line
            actualizarCaracterActual();
            return e5();
        }else {
            if(caracterActual == '*'){ //Comments multiple line
                lineaComienzoComment = fileManager.getLine();
                nroLineaComienzoComment = fileManager.getLineNumber();
                columnaComienzoComment = fileManager.getColumn();
                return e6();
            } else{ //Division token
                return new Token(TokenId.op_division, "/", fileManager.getLineNumber());
            }
        }
    }

    private Token e5() throws IOException, LexicalException { //Comentario single line
        actualizarCaracterActual();
        if(caracterActual == '\n' || caracterActual == '\u001a')
            return e0();
        else
            return e5();
    }

    private Token e6() throws IOException, LexicalException { //Comentario multilinea
        if(caracterActual != '*'){
            if(caracterActual == '\u001a'){
                throw new LexicalException("/*", nroLineaComienzoComment, columnaComienzoComment, "Comentario multilinea sin cerrar", lineaComienzoComment);
            }else{
                actualizarCaracterActual();
                return e6();
            }
        }else{
            actualizarCaracterActual();
            return e7();
        }
    }
    private Token e7() throws IOException, LexicalException {
        if(caracterActual == '*'){
            actualizarCaracterActual();
            return e7();
        }else{
            if(caracterActual == '/'){
                actualizarCaracterActual();
                return e0();
            }else{
                actualizarCaracterActual();
                return e6(); //Si no es / ni * vuelvo a e6
            }
        }
    }
    private Token e8() throws IOException, LexicalException {
        if(caracterActual == '=') {
            actualizarLexema();
            actualizarCaracterActual();
            return new Token(TokenId.op_mayorIgual, lexema, fileManager.getLineNumber());
        }
        else
            return new Token(TokenId.op_mayor, lexema, fileManager.getLineNumber());
    }
    private Token e9() throws IOException {
        if(caracterActual == '='){
            actualizarLexema();
            actualizarCaracterActual();
            return new Token(TokenId.op_menorIgual, lexema, fileManager.getLineNumber());
        }else
            return new Token(TokenId.op_menor, lexema, fileManager.getLineNumber());
    }

    private  Token e10() throws IOException, LexicalException {
        if(caracterActual == '='){
            actualizarLexema();
            actualizarCaracterActual();
            return new Token(TokenId.op_distinto, lexema, fileManager.getLineNumber());
        }else
            return new Token(TokenId.op_negacion, lexema, fileManager.getLineNumber());
    }

    private  Token e11() throws IOException, LexicalException {
        if(caracterActual == '=') {
            actualizarLexema();
            actualizarCaracterActual();
            return new Token(TokenId.op_igual, lexema, fileManager.getLineNumber());
        }
        else
            return new Token(TokenId.asignacion, lexema, fileManager.getLineNumber());
    }

    private  Token e12() throws IOException {
        if(caracterActual == '=') {
            actualizarLexema();
            actualizarCaracterActual();
            return new Token(TokenId.incremento, lexema, fileManager.getLineNumber());
        }else
            return new Token(TokenId.op_suma, lexema, fileManager.getLineNumber());
    }

    private  Token e13() throws IOException, LexicalException {
        if(caracterActual == '=') {
            actualizarLexema();
            actualizarCaracterActual();
            return new Token(TokenId.decremento, lexema, fileManager.getLineNumber());
        }else
            return new Token(TokenId.op_resta, lexema, fileManager.getLineNumber());
    }
    private  Token e14() throws IOException, LexicalException {
        return new Token(TokenId.op_multiplicacion, lexema, fileManager.getLineNumber());
    }

    private Token e15() throws IOException, LexicalException {

        if(caracterActual == '&'){
            actualizarLexema();
            actualizarCaracterActual();
            return new Token(TokenId.op_and, lexema, fileManager.getLineNumber());
        }
        else {
            if (caracterActual == '\n' || caracterActual == '\u001a')
                throw new LexicalException(lexema, fileManager.getLineNumber(), fileManager.getPreviousLine().length() + 1, "& no es un simbolo valido, se esperaba la combinacion &&", fileManager.getPreviousLine());
            else
                throw new LexicalException(lexema, fileManager.getLineNumber(), fileManager.getColumn(), "& no es un simbolo valido, se esperaba la combinacion &&", fileManager.getLine());
        }
    }

    private Token e16() throws IOException, LexicalException {
        if(caracterActual == '|'){
            actualizarLexema();
            actualizarCaracterActual();
            return new Token(TokenId.op_or, lexema, fileManager.getLineNumber());
        }else
        if(caracterActual == '\n' || caracterActual == '\u001a')
            throw new LexicalException(lexema, fileManager.getLineNumber(), fileManager.getPreviousLine().length()+1, "| no es un simbolo valido, se esperaba la combinacion ||", fileManager.getPreviousLine());
        else
            throw new LexicalException(lexema, fileManager.getLineNumber(), fileManager.getColumn(), "| no es un simbolo valido, se esperaba la combinacion ||", fileManager.getLine());
    }
    private  Token e17() throws IOException, LexicalException {
        return new Token(TokenId.op_modulo, lexema, fileManager.getLineNumber());
    }
    private  Token e18() throws IOException, LexicalException {
        return new Token(TokenId.punt_parentIzq, lexema, fileManager.getLineNumber());
    }
    private  Token e19() throws IOException, LexicalException {
        return new Token(TokenId.punt_parentDer, lexema, fileManager.getLineNumber());
    }
    private  Token e20() throws IOException, LexicalException {
        return new Token(TokenId.punt_llaveIzq, lexema, fileManager.getLineNumber());
    }
    private  Token e21() throws IOException, LexicalException {
        return new Token(TokenId.punt_llaveDer, lexema, fileManager.getLineNumber());
    }
    private  Token e22() throws IOException, LexicalException {
        return new Token(TokenId.punt_puntoYComa, lexema, fileManager.getLineNumber());
    }
    private  Token e23() throws IOException, LexicalException {
        return new Token(TokenId.punt_coma, lexema, fileManager.getLineNumber());
    }
    private  Token e24() throws IOException, LexicalException {
        return new Token(TokenId.punt_punto, lexema, fileManager.getLineNumber());
    }
    private Token ec_1() throws IOException, LexicalException {

        if(caracterActual == '\\'){
            actualizarLexema();
            actualizarCaracterActual();
            return ec_3();
        }else
            if(caracterActual == '\n' || caracterActual == '\u001a')
                if(caracterActual == '\'')
                    throw new LexicalException(lexema, fileManager.getLineNumber(), fileManager.getColumn(), "Caracter vacio no es valido", fileManager.getLine());
                else
                    throw new LexicalException(lexema, fileManager.getLineNumber(), fileManager.getPreviousLine().length()+1, "Caracter mal cerrado", fileManager.getPreviousLine());
            else{
                actualizarLexema();
                actualizarCaracterActual();
                return ec_2();
            }

    }

    private Token ec_3() throws LexicalException, IOException {
        if(caracterActual == '\n' || caracterActual == '\u001a')
            throw new LexicalException(lexema, fileManager.getLineNumber(), fileManager.getColumn(), "Caracter mal cerrado", fileManager.getPreviousLine());
        else {
            if (caracterActual == 'u') {
                actualizarLexema();
                actualizarCaracterActual();
                return uni_1();
            } else {
                actualizarLexema();
                actualizarCaracterActual();
                return ec_2();
            }
        }
    }

    private Token ec_2() throws IOException, LexicalException {
        if(caracterActual == '\''){
            actualizarLexema();
            actualizarCaracterActual();
            return ec_4();
        }
        else {
            if(caracterActual == '\n' || caracterActual == '\u001a')
                throw new LexicalException(lexema, fileManager.getLineNumber(), fileManager.getPreviousLine().length()+1, "Caracter mal cerrado, se esperaba un '", fileManager.getPreviousLine());
            else
                throw new LexicalException(lexema, fileManager.getLineNumber(), fileManager.getColumn(), "Caracter mal cerrado, se esperaba un '", fileManager.getLine());
        }
    }
    private Token ec_4() throws IOException, LexicalException {
        return new Token(TokenId.charLiteral, lexema, fileManager.getLineNumber());
    }

    private Token uni_1() throws IOException, LexicalException {
        if(caracterActual == '\n' || caracterActual == '\u001a' || caracterActual == '\\'){
            throw new LexicalException(lexema, fileManager.getLineNumber(), fileManager.getColumn(), "Caracter mal cerrado", fileManager.getPreviousLine());
        }else{
            if(caracterActual == '\''){
                actualizarLexema();
                actualizarCaracterActual();
                return ec_4();
            }else{
                for(int i=0; i<4; i++){
                    if(Character.isDigit(caracterActual) || caracterActual=='A' || caracterActual == 'B' || caracterActual == 'C' ||caracterActual == 'D' ||caracterActual == 'E' || caracterActual == 'F'
                    || caracterActual == 'a' || caracterActual == 'b' || caracterActual == 'c' || caracterActual == 'd' || caracterActual == 'e' || caracterActual == 'f'){
                        actualizarLexema();
                        actualizarCaracterActual();
                    }else{
                        actualizarLexema();
                        throw new LexicalException(lexema, fileManager.getLineNumber(), fileManager.getColumn(), "Caracter unicode incorrecto", fileManager.getLine());
                    }
                }
                if(caracterActual != '\''){
                    if(caracterActual == '\n' || caracterActual == '\u001a'){
                        throw new LexicalException(lexema, fileManager.getLineNumber(), fileManager.getPreviousLine().length()+1, "Caracter unicode incorrecto", fileManager.getPreviousLine());
                    }else {
                        throw new LexicalException(lexema, fileManager.getLineNumber(), fileManager.getColumn(), "Caracter unicode incorrecto", fileManager.getLine());
                    }
                }else{
                    actualizarLexema();
                    actualizarCaracterActual();
                    return new Token(TokenId.charLiteral, lexema, fileManager.getLineNumber());
                }

            }
        }
    }

    private  Token sl_1() throws IOException, LexicalException {
       actualizarLexema();
       actualizarCaracterActual();

        if(caracterActual == '\\'){
            return sl_2();
        }else{
            if(caracterActual == '\n' || caracterActual == '\u001a') throw new LexicalException(lexema, fileManager.getLineNumber(), fileManager.getPreviousLine().length()+1, "StringLiteral sin cerrar, se esperaba un \" ", fileManager.getPreviousLine());
            else
                if(caracterActual == '"') {
                    return sl_3();
                }else
                    return sl_1();
        }
    }
    private Token sl_3() throws IOException, LexicalException {
        actualizarLexema();
        actualizarCaracterActual();
        return new Token(TokenId.stringLiteral, lexema, fileManager.getLineNumber());
    }

    private Token sl_2() throws IOException, LexicalException {//Estado donde me como las barras
        actualizarLexema();
        actualizarCaracterActual();
        if(caracterActual == '\n' || caracterActual == '\u001a') throw new LexicalException(lexema, fileManager.getLineNumber(), fileManager.getColumn(), "StringLiteral sin cerrar, se esperaba un \" ", fileManager.getPreviousLine());
        else{
            if(caracterActual != '\\')
                return sl_1();
            else
                return sl_2();
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
