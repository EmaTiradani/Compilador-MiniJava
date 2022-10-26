///[Error:=|9]
//

class A {
    public int a1;

    A(int x) {
        m1(x);
        a1 = x;
        a1 = new B();
    }

    void m1(int p1) {


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
