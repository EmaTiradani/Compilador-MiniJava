//[Error:m2|9]

class A {
    public int a1;

    void m1(int p1)

    {
        B.m2();
    }

    int m2(){

    }

    static void main(){}

}

interface I{
    int m2();
}

class B implements I{
    int m2(){}
}