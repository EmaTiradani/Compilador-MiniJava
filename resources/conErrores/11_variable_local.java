//[Error:p1|14]

class A {
    public int a1;

    void m1(int p1)

    {
        var a = 9;

        {
            var b = 'a';
            {
                var p1 = 3;
            }
        }
    }

    static void main(){}

}