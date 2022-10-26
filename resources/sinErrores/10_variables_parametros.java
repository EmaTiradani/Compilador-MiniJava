//Prueba resolucion de nombres de variables y de parametros

class C{
    public int c1;
}

class B extends C{
    public int b1;
    private int b2;

    void m1(int p){
        c1 = 4+3;
        b1 = 8;
        p = 3;
        b2 = 3;
        {
            p += c1 + b1 + b2;
        }
        b1 -= p;
    }
}

class A extends B{
    public int a1;
    private int a2;

    void m0(int a){
        a1 = a;
    }

    void m1(int p1){
        b1 = c1;
        p1 += 3 + (a1 - b1) * a2;
        {
            {
                p1 += 2 / (a2+b1);
            }
        }
    }

    static void main(){}

}