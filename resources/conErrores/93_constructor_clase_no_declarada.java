///[Error:C|13]
//Se utiliza new con un constructor con nombre de clase que no esta declarada
class A {
    public int a1;

    A(int x) {

    }

    void m1(int p1)

    {
        new C(p1);

    }

    void m2()
    {}



}


class B extends A{

}


class Init{
    static void main()
    { }
}
