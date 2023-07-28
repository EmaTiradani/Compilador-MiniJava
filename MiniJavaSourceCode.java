interface I1{
     int met(); // Con el primer offset

}


class A implements I1{

    static void main(){}
    int met(){}// Con el segundo offset
    void pepe(){}
    int as(){}
}

class B {

    void met5(int p1, int p2, char p3){

        var a = p1 + p2;
    }
    void pepe(){
        met5(2, 3, 'c');
    }
    int as(){
        var a = 2;
        return 3;
    }
    //int met(){} // Con el 5to offset

}

class C extends B{
    int as(){}
}

class D extends C{
    int orga(){}
}