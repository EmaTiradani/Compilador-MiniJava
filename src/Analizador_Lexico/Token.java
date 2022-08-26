package Analizador_Lexico;

public class Token {

    TokenId tokenId;
    String lexema;
    int linea;

    public Token (TokenId tokenId, String lexema, int linea){
        tokenId = tokenId;
        lexema = lexema;
        linea = linea;
    }
    public TokenId getTokenId(){
        return tokenId;
    }
    public String getLexema(){
        return lexema;
    }
    public int getLinea(){
        return linea;
    }
}
