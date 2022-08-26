package FileManager;

import java.io.*;

public class FileManager {

    String line;
    String nextLine;
    int lineNumber;
    int posCharActual;
    char charActual;
    File sourceCode = new File(
            "C:\\Users\\default.LAPTOP-9ASHTB0Q\\Desktop\\Lenguajes\\Proyecto\\proyecto-ldp\\Compilador-MiniJava\\MiniJavaSourceCode.txt"
    );
    boolean reachedEOF;

    BufferedReader br;

    public FileManager(String fileRoute) throws IOException {
        posCharActual = 0;
        lineNumber = 0;
        System.out.print(sourceCode);
        //sourceCode = new File(fileRoute);
        reachedEOF = false;
        br = new BufferedReader(new FileReader(sourceCode));
        getNextLine();
    }

    public void getNextLine() throws IOException {
        line = br.readLine();
        System.out.println(line);
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
        }else{
            if(posCharActual == line.length())
                getNextLine();
            else{
                charActual = line.charAt(posCharActual);
                posCharActual++;
            }
        }

        if(reachedEOF) charActual = '\u001a';
        /*
        if(line!=null){
            if(posCharActual == line.length())
                getNextLine();

        }

        charActual = line.charAt(posCharActual);
        posCharActual++;
*/
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
}
