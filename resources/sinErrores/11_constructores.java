//Prueba creacion de instancias de clases concretas

interface I{}

class A implements I{
    static void main(){
        new Object();
        new A();
        new String();
        new System();
        new B();
        new C();
    }
}

class B extends A{
    public I b1;
    public A b2;
    private B b3;
    private C b4;
    public Object b5;
    public String b6;

    void m1(){
        b1 = new A();
        b1 = new B();
        b1 = new C();

        b2 = new A();
        b2 = new B();
        b2 = new C();

        b3 = new B();
        b3 = new C();

        b4 = new C();
    }
}

class C extends B{

    char m2(){
        b5 = new String();
        b5 = new A();
        b5 = new B();
        b5 = new C();
        b6 = new String();
    }
}