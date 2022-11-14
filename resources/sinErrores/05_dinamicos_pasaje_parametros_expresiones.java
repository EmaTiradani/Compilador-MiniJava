class A {

    static void main() {

        new A().met(true && false);
        new A().met2((24+1)*4 % 3)

    }

    void met(boolean b){

        if(b){
            System.printS("Esto no se escribe");
        }else{
            System.printS("Esto si se escribe");
        }

    }

    void met2(int i){

        System.printI(i);

    }

}