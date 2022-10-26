//Prueba definicion de variables locales

class A {
    public int a1;

    void m0(int a){}

    void m1(int p1)

    {
        var a = 3;
        var b = 'c';
        var c = "Cadena";
        var d = 9*7;
        var e = +0;
        var f = true;
        {
            b = 'o';
            c = "Hola";
            d = 7;
            f = false;
            {
                var g = d+a;
            }
            var g = c == "Chau";
        }
        var g = new A();
        d += d;
        e = 7+7+7*(5*4-(3));
        f = false && true;
    }

    static void main(){}

}