///[Error:=|10]
class B {}

interface Int {}

class A extends B implements Int{

    static void main(){
        var x = new A();
        x = new B();
    }

}