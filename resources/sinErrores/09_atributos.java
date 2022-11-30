class A {

    public boolean buleano;
    public int num;
    public B b;

    static void main() {

        var a = new A();
        a.met();
        System.printB(buleano);

    }

    void met(){
        var b = new B();

        buleano = true;
        num = 5;

        if(buleano){
            b.atributo = new C();
            b.atributo.numero = num;
        }
    }

}

class B {

    public C atributo;

}

class C {

    public int numero;

}