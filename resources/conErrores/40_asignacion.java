//[Error:=|9]

class A {
    public int a1;

    void m1(int p1) {
        var v1 = 2;

        A.m2() = 2;
    }

    static void m2(){}

    static void main(){}
}