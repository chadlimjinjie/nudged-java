import java.util.List;

public class Transformer {

    private Transform trans;
    private double rotation;
    private double scale;
    private double[][] matrix;
    private Point translation;

    public Transform getTrans() {
        return trans;
    }

    public void setTrans(Transform trans) {
        this.trans = trans;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(double[][] matrix) {
        this.matrix = matrix;
    }

    public Point getTranslation() {
        return translation;
    }

    public void setTranslation(Point translation) {
        this.translation = translation;
    }

    public Transformer(List<Point> robot_map_pts, List<Point> rmf_map_pts) {
        trans = estimate(robot_map_pts, rmf_map_pts);
        rotation = trans.getRotation();
        scale = trans.getScale();
        matrix = trans.getMatrix();
        translation = trans.getTranslation();
    };

    public Transform estimate(List<Point> domainPoints, List<Point> rangePoints) {
        int N = Math.min(domainPoints.size(), rangePoints.size());
        double a1, b1, c1, d1;
        double a2, b2;
        double ad, bc, ac, bd;
        double a, b, c, d;
        a1 = b1 = c1 = d1 = a2 = b2 = ad = bc = ac = bd = a = b = c = d = 0.0;

        for (int i = 0; i < N; i++) {
            a = domainPoints.get(i).getX();
            b = domainPoints.get(i).getY();
            c = rangePoints.get(i).getX();
            d = rangePoints.get(i).getY();
            a1 += a;
            b1 += b;
            c1 += c;
            d1 += d;
            a2 += a * a;
            b2 += b * b;
            ad += a * d;
            bc += b * c;
            ac += a * c;
            bd += b * d;
        }

        // Denominator.
        // It is zero iff X[i] = X[j] for every i and j in [0, n).
        // In other words, iff all the domain points are the same.
        double den = N * a2 + N * b2 - a1 * a1 - b1 * b1;

        if (Math.abs(den) < 1e-8) {
            // The domain points are the same.
            // We assume the translation to the mean of the range
            // to be the best guess. However if N=0, assume identity.
            if (N == 0)
                return new Transform(1.0, 0.0, 0.0, 0.0);
            else
                return new Transform(1.0, 0.0, (c1 / N) - a, (d1 / N) - b);
        }

        // Estimators
        double s = (N * (ac + bd) - a1 * c1 - b1 * d1) / den;
        double r = (N * (ad - bc) + b1 * c1 - a1 * d1) / den;
        double tx = (-a1 * (ac + bd) + b1 * (ad - bc) + a2 * c1 + b2 * c1) / den;
        double ty = (-b1 * (ac + bd) - a1 * (ad - bc) + a2 * d1 + b2 * d1) / den;

        return new Transform(s, r, tx, ty);
    }

    /**
     * Converts robot position to RMF position
     *
     * @param x robot position X
     * @param y robot position Y
     */
    public Point transformPosition(double x, double y) {
        Point trans_pt = trans.transformOne(new Point(x, y));
        return trans_pt;
    }

    /**
     * Calculate new heading after map conversion
     *
     * @param heading robot heading in degree
     */
    public double transformHeading(double heading) {
        double rotation_deg = rotation * 57.29577951;
        double new_heading = Math.floorMod((int) (heading + rotation_deg), 360);
        return new_heading;
    }

}
