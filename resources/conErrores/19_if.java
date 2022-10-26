//[Error:a|9]

class A {
    public int a1;

    void m1(int p1){
        var a = 3;
        if(true){
            var a = 2;
        }
    }

    static void main(){}

}