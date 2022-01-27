import java.util.ArrayList;
import java.util.List;

public class Transform {
    private double s;
    private double r;
    private double tx;
    private double ty;

    public Transform(double s, double r, double tx, double ty) {
        this.s = s;
        this.r = r;
        this.tx = tx;
        this.ty = ty;
    }

    public double getS() {
        return s;
    }

    public void setS(double s) {
        this.s = s;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public double getTx() {
        return tx;
    }

    public void setTx(double tx) {
        this.tx = tx;
    }

    public double getTy() {
        return ty;
    }

    public void setTy(double ty) {
        this.ty = ty;
    }

    public List<Point> transform(List<Point> points) {
        List<Point> transformedPoints = new ArrayList<Point>();
        for (Point point : points) {
            transformedPoints.add(transformOne(point));
        }
        return transformedPoints;
    }

    public Point transformOne(Point point) {
        return new Point(this.s * point.getX() - this.r * point.getY() + this.tx,
                this.r * point.getX() + this.s * point.getY() + this.ty);
    }

    public double[][] getMatrix() {
        double[][] matrix = {{this.s, -this.r, this.tx}, {this.r, this.s, this.ty}, {0.0, 0.0, 1.0}};
        return matrix;
    }

    public double getRotation() {
        return Math.atan2(this.r, this.s);
    }

    public double getScale() {
        return Math.sqrt(this.r * this.r + this.s * this.s);
    }

    public Point getTranslation() {
        return new Point(this.tx, this.ty);
    }

}
