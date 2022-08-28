package FileManager;

import java.io.*;

public class FileManager {

    String line;
    String previousLine;
    int lineNumber;
    int posCharActual;
    char charActual;
    File sourceCode;/* = new File(
            "C:\\Users\\ema_c\\Desktop\\Compiladores\\Etapa 1\\Compilador\\MiniJavaSourceCode.txt"
    );*/
    boolean reachedEOF;

    BufferedReader br;

    public FileManager(String fileRoute) throws IOException {
        posCharActual = 0;
        lineNumber = -1;
        //System.out.print(sourceCode);
        sourceCode = new File(fileRoute);
        reachedEOF = false;
        br = new BufferedReader(new FileReader(sourceCode));
        getNextLine();
    }

    public void getNextLine() throws IOException {
        previousLine = line;
        line = br.readLine();
        //System.out.println(line);
        lineNumber++;
        if(line == null){
            reachedEOF = true;
        }else {
            //line = br.readLine(); //Aca nunca entra creo
            posCharActual = 0;
        }
    }

    public char getNextChar() throws IOException {

        if(reachedEOF){
            charActual = '\u001a';
        }else {
            if (posCharActual == line.length()){
                getNextLine();
                return '\n';
            }
            else{
                charActual = line.charAt(posCharActual);
                posCharActual++;
            }
        }

        //if(reachedEOF) charActual = '\u001a';
        return charActual;
    }

    public int getLineNumber(){
        return lineNumber;
    }

    public int getColumn(){
        return posCharActual;
    }

    public boolean reachedEndOfFile(){
        return reachedEOF;
    }

    public String getLine(){
        return line;
    }
    public String getPreviousLine(){
        return previousLine;
    }
}
