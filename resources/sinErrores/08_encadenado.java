class A {

    static void main() {

        var clase = new A();
        clase.met().nombreClase();

    }

    A met(){

        return new A();

    }

    char nombreClase(){
        return 'a';
    }

}