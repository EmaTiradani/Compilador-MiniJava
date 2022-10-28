//[Error:m2|9]

class A {
    public int a1;

    void m1(int p1)

    {
        B.m2(5);
    }

    B m2(int p1){

    }

    static void main(){
    }
}

class B{
    private int b1;

    int m2(){}
}