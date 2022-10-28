///[Error:a1|20]
//El atributo a1 es un atributo privado de la clase ancestro

class A {
    private int a1;

    void m1(int p1){}

    void m2()
    {}



}


class B extends A{

    void m3() {
        var x = this.a1;
    }

}


class Init{
    static void main()
    { }
}
