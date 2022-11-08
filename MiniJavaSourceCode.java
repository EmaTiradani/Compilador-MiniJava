class A {

    static void main() {

        System.printSln("Start");

        if(false || true){
            debugPrint(2);
        }else{
            //debugPrint(5);
            System.printB(true);
        }

        System.println();

        debugPrint(10);

        debugPrint(System.read());



    }

}
