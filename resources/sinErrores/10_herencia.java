class A {

    public boolean buleano;
    public int num;
    public B b;

    static void main() {

        var a = new A();
        a = new B();
        a = new C();
        var b = new B();
        b = new C();

    }

}

class B extends A{

    public C atributo;

}

class C extends B{

    public int numero;

}
