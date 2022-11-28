class A {
    public int a;
    public int c;
    public int q;

    void m1(){
        a=7;
        c=3;
        q=1;
    }

}

class B extends A{
    public int d;

    void m2(){
        d=8;
    }
}


class Init{
    static void main()
    {
        var b = new B();
        b.m1();
        debugPrint(b.a);
        debugPrint(b.c);
        debugPrint(b.q);
        b.m2();
        debugPrint(b.d);
    }
}