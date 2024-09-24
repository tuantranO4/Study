public class PointMain {
    public static void main(String[] args) {
        Point p1 = new Point();
        Point p2=new Point();
        p1.x=2;
        p1.y=3;
        p2.x=5;
        p2.y=6;
        System.out.println("normal: "+p1.x+','+p1.y);
        p1.move(4, 5);
        System.out.println("After move: "+p1.x+','+p1.y);
        p2.mirror(3, 3); //if we mirror the p1, we mirror the updated p1(moved ver.), so we do p2.
        System.out.println("After mirror: "+p2.x+','+p2.y);

        Point p3 = new Point();
        p3.x=1;
        p3.y=1;
        p3.mirrorSingular(8);
        System.out.println("After singular mirror: "+p3.x+','+p3.y);

        Point disPoint=new Point();
        disPoint.x=3;
        disPoint.y=4;

        Point dPoint=new Point();
        dPoint.x=6;
        dPoint.y=8;

        double gg =disPoint.distance(dPoint);
        System.out.println("Distance between disPoint and dPoint: "+gg);
    }
}
