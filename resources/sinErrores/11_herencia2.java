class A {

    public boolean buleano;
    public int num;
    public B b;

    static void main() {

        var a = new A();
        a.metodo();

        a = new B();
        a.metodo();
        a.met2();
        a.met(); // met() en B

        a = new C();
        a.met(); // met() en C
        a.met2();
        a.metodo();

        var b = new B();
        b.metodo();
        b.met2();

        b = new C();
        b.metodo();
        b.met(); // met() en C




    }

    void metodo(){}

}

class B extends A{

    int met(){}

    int met2(){}

}

class C extends B{

    int met(){}

}