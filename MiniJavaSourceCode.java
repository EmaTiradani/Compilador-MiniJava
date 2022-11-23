class Init extends System{

    static void main(){
        var v1 = new A();
        var v2 = v1.met4(5, 7).met3();
        debugPrint(v2);
        debugPrint(new A().met4(10, 10).met4(20,20).met3());
    }

}


class A{

    public int a1, a2;
    public char a3;

    void met1(int p1, int p2){
        a1 = p1;
        a2 = p2;
        a3 = 'b';
    }

    void met2(){
        debugPrint(a1);
        debugPrint(met3());

    }

    int met3(){
        return a1 + a2;
    }

    A met4(int p1, int p2){
        var v1 = new A();
        v1.met1(p1, p2);
        return v1;
    }

}
