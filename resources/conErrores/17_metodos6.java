//[Error:m2|9]

class A {
    public int a1;
    static void main(){}
    void m1(int p1)

    {
        m2(new A());
    }

    int m2(I b){}
}
interface I{}
class B extends A{ }