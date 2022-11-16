interface I1{
    int met(); // Con el primer offset
}


class A implements I1{

    static void main(){}
    int met(){}// Con el segundo offset
}

class B implements I1{

    void met5(){}
    void met6(){}
    void met7(){}
    void met8(){}
    int met(){} // Con el 5to offset

}

