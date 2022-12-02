class Init {

    static void main(){
        new B().met();
    }
}

class A{

    public int a1;
    public int a2;
    public int a3;
    public int a4;
    public int a5;
    public int a6;


}

class B extends A{

    public int a7;

    void met() {
        var v1 = 7;
        var v2 = 8;
        a1 = 1;
        a2 = 2;
        a3 = 3;
        a4 = 4;
        a5 = 5;
        a6 = v2;
        a7 = v1;
        System.printIln(a1);
        System.printIln(a2);
        System.printIln(a3);
        System.printIln(a4);
        System.printIln(a5);
        System.printIln(a6);
        System.printIln(a7);
    }
}
