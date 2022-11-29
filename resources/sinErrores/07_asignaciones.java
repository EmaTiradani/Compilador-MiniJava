class A {

    static void main() {

        new A().met();

    }

    void met(){

        var clase = new A();
        System.printC(clase.nombreClase());
        var entero = 86;
        System.printIln(entero);
        var booleano = true;

        booleano = false;
        System.printBln(booleano);
        entero = 2022;
        System.printIln(entero);
        entero += 1;
        System.printIln(entero);
        entero -= 10;
        System.printIln(entero);
        clase = new A();

        while(entero > 100){
            entero -= 100;
            System.printS("buenas");
        }

    }

    char nombreClase(){
        return 'a';
    }

}