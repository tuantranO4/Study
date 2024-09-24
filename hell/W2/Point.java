public class Point {
    double x;
    double y;
    public void move(int dx, int dy){
        x+=dx;
        y+=dy;
    }
    public void mirror(int cx, int cy){
        x=2*cx-x;
        y=2*cy-y;
    }
    public void mirrorSingular(int s){
        x=2*s-x;
        y=2*s-y;
    }
    public double distance(Point p){
        double ans;
        ans=Math.sqrt((x-p.x)*(x-p.x)+(y-p.y)*(y-p.y));
        return ans;
    }
}
