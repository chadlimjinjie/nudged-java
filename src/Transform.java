
// https://github.com/axelpale/nudged-py/blob/master/nudged/transform.py
import java.util.ArrayList;
import java.lang.Math;

public class Transform {

	private double s;
	private double r;
	private double tx;
	private double ty;

	public Transform() {};

	// Public, to allow user access
	public Transform(double s, double r, double tx, double ty) {
		this.s = s;
		this.r = r;
		this.tx = tx;
		this.ty = ty;
	};

	public ArrayList<Point> transform(ArrayList<Point> points) {
		ArrayList<Point> transformedPoints = new ArrayList<Point>();
		for (Point point : points) {
			transformedPoints.add(transform_one(point));
		}
		return transformedPoints;
	}

	public Point transform_one(Point point) {
		return new Point(this.s * point.getX() - this.r * point.getY() + this.tx,
				this.r * point.getX() + this.s * point.getY() + this.ty);
	}

//    def get_matrix(self):
//        return [[self.s, -self.r, self.tx],
//                [self.r,  self.s, self.ty],
//                [     0,       0,       1]]
	public double[][] get_matrix() {
		double[][] matrix = { { this.s, -this.r, this.tx }, { this.r, this.s, this.ty }, { 0.0, 0.0, 0.0 } };
		return matrix;
	}

//    def get_rotation(self):
//        return math.atan2(self.r, self.s)
	public double get_rotation() {
		return Math.atan2(this.r, this.s);
	}

//    def get_scale(self):
//        return math.sqrt(self.r * self.r + self.s * self.s)
	public double get_scale() {
		return Math.sqrt(this.r * this.r + this.s * this.s);
	}

//    def get_translation(self):
//        return [self.tx, self.ty]
	public Point get_translation() {
		return new Point(this.tx, this.ty);
	}

}
