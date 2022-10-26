//[Error:m2|9]

class A {
    public int a1;

    void m1(int p1)

    {
        B.m2(this);
    }


    static void main(){
    }
}

class B extends A{
    private int b1;

    int m2(B b){}
}