import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

//        "robotMapPixel": [
//                          {
//                              "x": 1545,
//                              "y": 911
//                          }, {
//                              "x": 1995,
//                              "y": 769
//                          }, {
//                              "x": 852,
//                              "y": 1985
//                          }, {
//                              "x": 2686,
//                              "y": 2941
//                          }
//                      ],
//                      "rmfMapPixel": [
//                          {
//                              "x": 507.7,
//                              "y": -825.4
//                          }, {
//                              "x": 859.9,
//                              "y": -955.3
//                          }, {
//                              "x": -19.32,
//                              "y": 15.59
//                          }, {
//                              "x": 1466,
//                              "y": 756.5
//                          }
//                      ]

		ArrayList<Point> robot_map_pts = new ArrayList<Point>();
		ArrayList<Point> rmf_map_pts = new ArrayList<Point>();

		robot_map_pts.add(new Point(1545, 911));
		robot_map_pts.add(new Point(1995, 769));
		robot_map_pts.add(new Point(852, 1985));
		robot_map_pts.add(new Point(2686, 2941));

		rmf_map_pts.add(new Point(507.7, -825.4));
		rmf_map_pts.add(new Point(859.9, -955.3));
		rmf_map_pts.add(new Point(-19.32, 15.59));
		rmf_map_pts.add(new Point(1466, 756.5));

		MapTransformer rmf2robot_trans = new MapTransformer(robot_map_pts, rmf_map_pts);

		Point robotMapCoord = rmf2robot_trans.transform_position(2022, 2745);

		System.out.println(robotMapCoord.getX() + " " + robotMapCoord.getY());
		System.out.println(convertMapToPosePositionX(robotMapCoord.getX(), -0.049, 0.050005815) + " "
				+ convertMapToPosePositionY(robotMapCoord.getY(), 0.016, 0.049994766));

	}

	/**
	 * Converts a map X position to the navigation pose X
	 * 
	 * @param mapX          Obtain from DDS_RobotCoordinate.x
	 * @param rosOriginX    Obtain from .yaml 'origin' [0]
	 * @param rosResolution Obtain from .yaml 'resolution'
	 * @return Navigation Pose X
	 */
	public static double convertMapToPosePositionX(double mapX, double rosOriginX, double rosResolution) {
		if (rosResolution == 0.0) {
			System.out.println("ROS resolution is 0! Please check input again!");
			return 0.0;
		}
		int originMapX = (int) (0 - (rosOriginX / rosResolution));
		double posePostionX = ((double) (mapX - originMapX)) * rosResolution;
		return posePostionX;
	}

	/**
	 * Converts a map Y position to the navigation pose Y
	 * 
	 * @param mapX          Obtain from DDS_RobotCoordinate.y
	 * @param rosOriginX    Obtain from .yaml 'origin' [1]
	 * @param rosResolution Obtain from .yaml 'resolution'
	 * @param mapHeight     Obtain from .png 'height' (Check image properties)
	 * @return Navigation Pose Y
	 */
	public static double convertMapToPosePositionY(double mapY, double rosOriginY, double rosResolution) {
		if (rosResolution == 0.0) {
			System.out.println("ROS resolution is 0! Please check input again!");
			return 0.0;
		}
		int originMapY = (int) (0 - (rosOriginY / rosResolution));
		double posePostionY = ((double) (mapY - originMapY) * rosResolution);
		posePostionY = -posePostionY;
		return posePostionY;
	}

}
