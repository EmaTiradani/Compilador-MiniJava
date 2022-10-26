//[Error:=|9]

class A {
    public int a1;

    void m1(int p1) {
        var v1 = 2;

        this.m2().a1 = new B();
    }

    A m2(){}

    static void main(){}
}

class B{}