// Prueba un lado izquierdo con dos encadenamiento y atributos heredados

class A {
    public B a1;
    public int a2;
   
    
    
    
} 

class B extends A{
    public A a3;
    
     void m1(B p1)     
    {
        a1.a3.a2 = 4;
        
    }
}


class Init{
    static void main()
    { }
}


