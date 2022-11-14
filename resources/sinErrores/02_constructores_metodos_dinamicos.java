class A {

    static void main() {

        new A().met();
        new B().met2(78)

    }

    void met() {

        System.printI(86);

    }

}

class B {

    void met2(int i){

        System.printI(i);

    }

}