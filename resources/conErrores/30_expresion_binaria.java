//[Error:==|9]
interface I{}
class A implements I{
    public int a1;

    void m1(int p1) {


        var v1 = new A() == new B();
    }

    int m2(){}

    static void main(){}
}

class B implements I{}