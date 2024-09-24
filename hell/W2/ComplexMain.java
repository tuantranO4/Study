public class ComplexMain {
    public static void main(String[] args) {
    Complex a = new Complex();
    a.re = 3.0;
    a.im = 4.0;
    double abs = a.abs();
    System.out.println("Abs val: "+abs);

    Complex b = new Complex();
    b.re=1.0;
    b.im=3;
    Complex res = new Complex();
    res = a.add(b);
    System.out.println("add :" +res.re+','+res.im);
    Complex res2 = a.sub(b);
    System.out.println("sub :" +res2.re+','+res2.im);
    Complex res3 = a.mul(b);
    System.out.println("mul :" +res3.re+','+res3.im);
    }
}
