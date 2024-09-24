public class Complex {
    public double re;
    public double im;
    
    public double abs(){
        double abs = Math.sqrt(re*re+im*im);
        return abs;
    }
    public Complex add(Complex c){
        Complex res=new Complex();
        res.re=re+c.re;
        res.im=im+c.im;
        return res;
    }
    public Complex mul(Complex c){
        Complex res=new Complex();
        res.re=(re*c.re-im*c.im);
        res.im=(re*c.im+im*c.re);
        return res;
    }

    public Complex sub(Complex c){
        Complex res=new Complex();
        res.re=re-c.re;
        res.im=im-c.im;
        return res;
    }
}
