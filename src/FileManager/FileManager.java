package FileManager;

import java.io.*;

public class FileManager {

    public FileManager() throws FileNotFoundException {
    }

    File sourceCode = new File(
            "C:\\Users\\ema_c\\Desktop\\Compiladores\\Etapa 1\\MiniJavaSourceCode.txt"
    );


    BufferedReader br = new BufferedReader(new FileReader(sourceCode));

    public String getNextLine() throws IOException {
        System.out.println(br.readLine());
        return br.readLine();
    }


}
