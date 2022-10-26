//Prueba this

class A{
    private int a;

    A m1(){
        this.a = 4;
        this.a = this.m2();
        this.m1();
        this.m1().a = 3;
        return this;
    }

    int m2(){

    }

    static void main(){}
}