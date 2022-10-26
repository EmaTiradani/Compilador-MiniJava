//Prueba metodos y metodos estaticos

class A{
    void m1(){

    }

    int m2(int p1, char p2){

    }
}

class B extends A{
    private B b2;

    int m3(A p1){
        m4();
    }

    static void m4(){
        m4();
        m5(8+7,new B());
    }

    static char m5(int p1,B p2){

    }
}

class C extends B{
    public A a1;
    public B b1;
    public C c1;

    void m6(){
        m1();
        m2(1,'a');
        m3(a1);
        m3(b1);
        m3(c1);
        m7(c1);
    }

    int m7(C p1){
        B.m4();
    }
}

class D{
    private B b1;

    B m8(){}

    static void main(){
        B.m4();
        B.m5(7+7, new B());
        B.m5(7+7, new C());
    }
}