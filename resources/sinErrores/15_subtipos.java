//Prueba subtipos


interface I0{}
interface I1{}
interface I2 extends I1{}
interface I3 extends I1{}
interface I4 extends I2,I3{}

class A implements I4{}
class B extends A implements I2,I3{}
class C extends B implements I1{}

class Init2{
    private I1 i1;
    private I2 i2;
    private I3 i3;
    private I4 i4;

    static void main(){}
    void m1(){
        i1 = new A();
        i1 = new B();
        i1 = new C();

        i2 = new A();
        i2 = new B();
        i2 = new C();

        i3 = new A();
        i3 = new B();
        i3 = new C();

        i4 = new A();
        i4 = new B();
        i4 = new C();
    }
}
