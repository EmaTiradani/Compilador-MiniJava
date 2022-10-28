//[Error:m2|9]

class A {
    public int a1;

    void m1(int p1)

    {
        B.m2(new A());
    }

    static void main(){}

}

class B extends A{
    static void m2(B b){}
}