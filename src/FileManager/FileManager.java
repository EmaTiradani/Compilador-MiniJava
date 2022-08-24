package FileManager;

import java.io.*;

public class FileManager {

    String line;
    int posCharActual;
    char charActual;

    public FileManager() throws FileNotFoundException {
    }

    File sourceCode = new File(
            "C:\\Users\\ema_c\\Desktop\\Compiladores\\Etapa 1\\MiniJavaSourceCode.txt"
    );

    BufferedReader br = new BufferedReader(new FileReader(sourceCode));

    public void getNextLine() throws IOException {
        System.out.println(br.readLine());
        String line = br.readLine();
        if(line == null){
             charActual = '\u001a'; //EOF
        }
        else
            line = br.readLine();
            posCharActual = 0;
    }

    public char getNextChar() throws IOException {
        if(posCharActual == line.length()-1)
            getNextLine();

        charActual = line.charAt(posCharActual);
        posCharActual++;

        return charActual;
    }


}
