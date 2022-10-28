//[Error:this|17]

class A {
    public int a1;

    static void m1(int p1)

    {

    }

    static A m2(){

    }

    static void main(){
        m2().m1(this.a1);
    }

}