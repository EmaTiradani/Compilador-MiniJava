//[Error:b1|9]

class A extends B{
    public int a1;

    void m1(int p1)

    {
        this.m2(1).b1 = 8;
    }

    B m2(int p1){

    }

    static void main(){
    }
}

class B{
    private int b1;
}