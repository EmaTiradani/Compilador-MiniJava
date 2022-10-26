//[Error:=|19]

class A implements I1{

    static void main(){
    }
}

interface I1{}
interface I2{}
interface I3{}

class B extends A implements I2{
    public I1 i1;
    private I2 i2;
    private I3 i3;

    void m1(){
        i3 = this;
    }
}

class C extends A implements I3{}