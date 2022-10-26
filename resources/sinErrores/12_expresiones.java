//Prueba expresiones binarias y unarias, expresiones parentizadas, etc.

class A{
    static void main(){}
}

class C{}

class B extends A{
    public A a1;
    public B b1;
    public C c1;

    void m1(int p1){
        p1 = (1+3);
        p1 = ((p1+6)*5);
        var a = ((1>9) && (4==7) || true) && !false;
        var b = (((a1 == a1)));
        var c = (((b1 != a1)));
        var d = a || (b && c);
        d = a1 == b1;
        d = b1 == a1;
        d = c1 != null;
        var e = 1 / (4 % 6) *8 + (p1 - 7);
        var f = (1<8) && (p1 >= e) || (p1<=4) || (e>8);
        var g = !a;
        e = +3;
        e = -2;
    }
}