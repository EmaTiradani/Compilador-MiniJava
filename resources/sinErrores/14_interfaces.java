interface I1{
    int met(); // Con el primer offset
}

interface I2{
    void met3();
    void met2();
    int met(); // Con el tercer offset
}


class A implements I1{

    static void main(){}
    int met(){}// Con el segundo offset
}

class B extends A implements I2{

    void met5(){}
    void met6(){}
    void met7(){}
    void met8(){}
    int met(){} // Con el 5to offset

}