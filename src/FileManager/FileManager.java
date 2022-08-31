package FileManager;

import java.io.*;

public class FileManager {

    String line;
    String previousLine;
    int lineNumber;
    int posCharActual;
    char charActual;
    File sourceCode;
    boolean reachedEOF;

    BufferedReader br;

    public FileManager(String fileRoute) throws IOException {
        posCharActual = 0;
        lineNumber = 0;
        sourceCode = new File(fileRoute);
        reachedEOF = false;
        br = new BufferedReader(new FileReader(sourceCode));
        getNextLine();
    }

    public void getNextLine() throws IOException {
        previousLine = line;
        line = br.readLine();
        lineNumber++;
        if(line == null){
            reachedEOF = true;
        }else {
            posCharActual = 0;
        }
    }

    public char getNextChar() throws IOException {

        if(reachedEOF){
            charActual = '\u001a';
        }else {
            if (posCharActual == line.length()){
                getNextLine();
                charActual ='\n';
                return charActual;
            }
            else{
                charActual = line.charAt(posCharActual);
                posCharActual++;
            }
        }

        return charActual;
    }

    public int getLineNumber(){
        if(charActual == '\n' || charActual == '\u001a')
            return lineNumber-1;
        else
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
