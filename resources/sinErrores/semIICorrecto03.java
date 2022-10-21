// Acceso simple a una variable de instancia
// Este caso tambien ejercita que no chequen los metodos mas de una vez cuando
// son heredados

class A {
    private int a1;
    
    
     void m1(){
        a1 = a1;
    }
    

}


class B extends A{
    
}


class Init{
    static void main()
    { }
}


