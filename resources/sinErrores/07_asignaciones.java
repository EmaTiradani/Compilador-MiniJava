class A {

    static void main() {

        new A().met();

    }

    void met(){

        var clase = new A();
        System.printC(clase.nombreClase());
        var entero = 86;
        var booleano = true;

        booleano = false;
        entero = 2022;


    }

    char nombreClase(){
        return 'a';
    }

}