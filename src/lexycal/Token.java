package lexycal;

public class Token {

    TokenId tokenId;
    String lexema;
    int linea;

    public Token (TokenId tokenId, String lexema, int linea){
        this.tokenId = tokenId;
        this.lexema = lexema;
        this.linea = linea;
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
