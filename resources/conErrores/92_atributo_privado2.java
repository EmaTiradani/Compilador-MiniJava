///[Error:a2|28]
//El atributo a2 no se puede acceder con C porque es un atributo privado de A
class A {
    public B a1;
    private int a2;
    
    A m1(int p1) {

       this.a2 = p1;
    }
    
    B m2() {}
         
    

}


class B extends A{
    void m3() {

    }
}

class C extends B {
    void m4() {
        this.a1 = new B();
        this.a2 = 1;
    }
}


class Init{
    static void main()
    { }
}


