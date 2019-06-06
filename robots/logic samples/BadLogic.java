import java.awt.geom.Point2D.Double;


public final class BadLogic {
	private final double maxVelocity;
	private final double maxAngularVelocity;
	private double velocity;
	private double angularVelocity;
	private double duration;

	public BadLogic(double maxVelocity, double maxAngularVelocity)
	{
		this.maxVelocity = maxVelocity;
		this.maxAngularVelocity = maxAngularVelocity;
	}

	public void update(Double robot, Double target, double dir) {
		double distance = distanceTo(robot, target);
        if (distance < 0.5)
        {
        	velocity = 0;
        	angularVelocity = 0;
            return;
        }
        velocity = maxVelocity;
        double angleToTarget = angleTo(robot, target);
        angularVelocity = 0;
        if (angleToTarget > dir)
        {
            angularVelocity = maxAngularVelocity;
        }
        if (angleToTarget < dir)
        {
            angularVelocity = -maxAngularVelocity;
        }

        duration = 10;
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
