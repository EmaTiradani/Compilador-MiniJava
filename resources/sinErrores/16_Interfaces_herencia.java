interface I1 {

    void m1();

    void m2();

}

class A implements I1{


    static void main() {
        debugPrint(5);
    }

    void m1(){}

    void m2(){}

    void m3(){}

}

class X extends A{

    void m1(){}

}

class B implements I1{

    void met1(){
        debugPrint(15);
    }

    void a1(){}

    void a0(){}

    void m1(){}

    void m2(){}

}

class C implements I1 {

    int c1(){}
    int c2(){}
    int c3(){}
    int c4(){}
    int c5(){}
    int c6(){}
    int c7(){}

    void m1(){}

    void m2(){}
}