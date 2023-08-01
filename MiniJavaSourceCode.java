interface I1{
     int met(); // Con el primer offset

}

interface I2{
    int met5();
}


class A implements I1, I2{
    public B b1;
    public int i;

    static void main(){
        var x = new B();
        x.met5().met5();
        System.printIln(x.i);
        System.printIln(x.q);
        System.printIln(x.w);
        System.printIln(x.e);
        x.be.met5();
        x.ce.met5();

        var asda = new A();
        var y = x;

        System.printIln(B.as());
    }
    int met(){
        var a = new B();
        a.met5().met5();
        /*boolean c,dsjak;
        char asd;*/
    }// Con el segundo offset
    void pepe(){}
    int met5(){
        System.printS("sip");
    }
}

class B implements I2{
    public int i;
    public int q;
    public int w;
    public int e;
    public B be;
    public C ce;

    B met5(){
        System.printSln("seguro?");
        i = 4;
        q = 3;
        w = 2;
        e = 7;
        be = new B();
        ce = new C();
        pepe(e, "formales ordenados");
        return this;
    }
    void pepe(int jaja, String noAnda){
        System.printS("anda: ");
        System.printIln(jaja);
        System.printS(noAnda);
    }
    static int as(){
        //var a = 2;
        return 3;
    }
    //int met(){} // Con el 5to offset

}

class C extends B{
}

class D extends C{
    int orga(){}
}