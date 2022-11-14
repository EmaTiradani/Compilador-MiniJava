class A {

    static void main() {

        new A().met(new A().met2());

    }

    void met(boolean b){

        if(b){
            System.printS("Esto se escribe");
        }else{
            System.printS("Esto no se escribe");
        }

    }

    boolean met2(){

        return true;

    }

}