//[Error:m2|9]

class A {
    public int a1;

    void m1(int p1)

    {
        B.m2(1);
    }

    int m2(){

    }

    static void main(){}

}

class B {
    static void m2(){}
}