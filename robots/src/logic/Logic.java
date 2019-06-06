package logic;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;

import log.Logger;

public final class Logic {
	static private Object robot;
    static private Constructor<?> constr;
    static private HashMap<String, Method> methods;

	private Logic() { }
	static private Object invoke(String name, Object... params) {
		if (robot == null)
			return null;

		try {
			return methods.get(name).invoke(robot, params);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			Logger.error("Logic error with method invoke");
		}
		return null;
	}

	static public void update(Point2D.Double robot, Point2D.Double target, double dir) {
		invoke("update", robot, target, dir);
	}
	static public double getVelocity() {
		try {
			return (double)invoke("velocity");
		} catch (Exception e) {
			return 0;
		}
	}
	static public double angularVelocity() {
		try {
			return (double)invoke("angularVelocity");
		} catch (Exception e) {
			return 0;
		}
	}
	static public double getDuration() {
		try {
			return (double)invoke("duration");
		} catch (Exception e) {
			return 0;
		}
	}

	static public void updateLogic(double maxVelocity, double maxAngularVelocity) {
		if (robot != null || constr == null)
			return;

		try {
			createInstance(maxVelocity, maxAngularVelocity);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			Logger.error("Logic error with instance create");
		}
	}

	static public boolean classFromURL(File file, File dir) {
		try {
			URLClassLoader classLoader = new URLClassLoader(new URL[] {dir.toURI().toURL()});
			String name = file.getName().substring(0, file.getName().lastIndexOf('.'));
			Class<?> newClass = classLoader.loadClass(name);
			classLoader.close();
			Constructor<?> newConstr = newClass.getDeclaredConstructor(
					new Class[] { Double.TYPE, Double.TYPE });

			Method update = newClass.getDeclaredMethod("update", new Class[] {Point2D.Double.class, Point2D.Double.class, Double.TYPE});
			Method velocity = newClass.getDeclaredMethod("getVelocity", new Class[] {});
			Method angularVelocity = newClass.getDeclaredMethod("angularVelocity", new Class[] {});
			Method duration = newClass.getDeclaredMethod("getDuration", new Class[] {});

			robot = null;
			constr = newConstr;
			methods = new HashMap<String, Method>(4);
			methods.put("update", update);
			methods.put("velocity", velocity);
			methods.put("angularVelocity", angularVelocity);
			methods.put("duration", duration);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IOException e) {
			return false;
        }
		return true;
	}

	static private void createInstance(double arg1, double arg2)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		robot = constr.newInstance(
				new Object[]{arg1, arg2});
	}
}
