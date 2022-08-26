package FileManager;

import java.io.*;

public class FileManager {

    String line;
    String nextLine;
    int lineNumber;
    int posCharActual;
    char charActual;
    File sourceCode = new File(
            "C:\\Users\\ema_c\\Desktop\\Compiladores\\Etapa 1\\MiniJavaSourceCode.txt"
    );
    boolean reachedEOF;

    BufferedReader br;

    public FileManager(String fileRoute) throws FileNotFoundException {
        posCharActual = 0;
        lineNumber = 0;
        System.out.print(sourceCode);
        //sourceCode = new File(fileRoute);
        reachedEOF = false;

        br = new BufferedReader(new FileReader(sourceCode));
    }

    public void getNextLine() throws IOException {
        line = br.readLine();
        System.out.println(line);
        lineNumber++;
        if(nextLine == null){
            charActual = '\u001a'; //EOF
            reachedEOF = true;
        }
        else {
            //line = br.readLine(); //Aca nunca entra creo
            posCharActual = 0;
        }
    }

    public char getNextChar() throws IOException {

        if(line == null) getNextLine();
        else{

        }
        if(line!=null){
            if(posCharActual == line.length())
                getNextLine();

        }

        charActual = line.charAt(posCharActual);
        posCharActual++;

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
