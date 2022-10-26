//Prueba variables y metodos encadenados

class A{
    public int a1;
    public char a2;
    private String a3;
    private A a4;
    private A a5;
    public B a6;

    A m1(){
        this.m1().m2(1,'a');
    }

    int m2(int p1,char p2){
        this.a5 = a4.m1();
        var v1 = a4.a1;
    }
}

class B{
    public B b1;
    public int b2;
    public A b3;

    B m1(){
        b3.a6.b2 = b3.m2(3,'a');
        return this.b3.a6;
    }
}

class Init{
    static void main(){}
}