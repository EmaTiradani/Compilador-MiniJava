//[Error:m2|9]

class A {
    public int a1;

    void m1(int p1)

    {
        m2(new A());
    }

    int m2(B b){

    }

    static void main(){}

}

class B extends A{

}