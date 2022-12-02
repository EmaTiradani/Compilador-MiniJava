interface C{

    int m2();
}

interface B{

    int m2();
}
interface A extends B, C{

    void m1();
}

class F {
    int a(){}
    int b(){}
    int d(){}

    int m1(){}
}

class E extends F{
    int m3(){
        System.printS("M3");
    }
}

class D extends E implements A{

    int a(){}
    int b(){}
    int c(){}
    int d(){}

    int m1(){
        System.printIln(86);
    }

    int m2(){
        System.printI(50);
        return 24;
    }



}

class Clase{

    static void main(){
        var a = new D();
        a.m1();
        a.m2();
        a.m3();
    }
}