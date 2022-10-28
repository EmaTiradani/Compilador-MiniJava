//[Error:this|17]

class A {
    public int a1;

    void m1(int p1)

    {

    }

    int m2(){

    }

    static void main(){
        var a = this.a1;
    }

}