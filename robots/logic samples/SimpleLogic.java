import java.awt.geom.Point2D.Double;


public final class SimpleLogic {
	private final double maxVelocity;
	private final double maxAngularVelocity;
	private double velocity;
	private double angularVelocity;
	private double duration;

	public SimpleLogic(double maxVelocity, double maxAngularVelocity)
	{
		this.maxVelocity = maxVelocity;
		this.maxAngularVelocity = maxAngularVelocity;

		duration = 10;
	}

	public void update(Double robot, Double target, double dir) {
		double distance = distanceTo(robot, target);
		velocity = 0;
		angularVelocity = 0;

		if (distance < 0.5)
			return;

		double angleToTarget = angleTo(robot, target);
		double direction = Math.signum(asNormalizedRadians(dir - angleToTarget) - 3.141592653589793D);
		angularVelocity = (maxAngularVelocity * direction);
		if (Math.abs(angleToTarget - dir) < 0.1) {
		  angularVelocity = 0;
		  velocity = maxVelocity;
		}
	}

	public double getVelocity() {
		return velocity;
	}

	public double angularVelocity() {
		return angularVelocity;
	}

	public double getDuration() {
		return duration;
	}

	private double distanceTo(Double from, Double to)
	{
		double diffX = from.getX() - to.getX();
		double diffY = from.getY() - to.getY();
		return Math.sqrt(diffX * diffX + diffY * diffY);
	}

	private double angleTo(Double from, Double to) {
		double diffX = to.getX() - from.getX();
		double diffY = to.getY() - from.getY();
		
		return asNormalizedRadians(Math.atan2(diffY, diffX));
	}

	private double asNormalizedRadians(double angle) {
		while (angle < 0)
		{
			angle += 2*Math.PI;
		}
		while (angle >= 2*Math.PI)
		{
			angle -= 2*Math.PI;
		}
		return angle;
	}
}
