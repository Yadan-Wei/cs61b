/** NBody is a class that will actually run your simulation. */

public class NBody{

	public static void main(String[] args){
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		double radius = readRadius(filename);
		Planet[] planets = readPlanets(filename);
		int planetsNum = planets.length;
		StdDraw.enableDoubleBuffering();
		double time = 0;
		while(time != T){
			double[] xForces = new double[planetsNum];
			double[] yForces = new double[planetsNum];
			for (int i = 0; i < planetsNum; i ++){
				xForces[i] = planets[i].calcNetForceExertedByX(planets);
				yForces[i] = planets[i].calcNetForceExertedByY(planets);		
			}
			StdDraw.setScale(-radius, radius);
			StdDraw.picture(0, 0, "images/starfield.jpg");
			for (int i = 0; i < planetsNum; i ++){
				planets[i].update(dt, xForces[i], yForces[i]);
				planets[i].draw();
			}
			StdDraw.show();
			StdDraw.pause(10);		
			time = time + dt;
		}
		StdOut.printf("%d\n", planets.length);
		StdOut.printf("%.2e\n", radius);
		for (int i = 0; i < planets.length; i++) {
    		StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                  	   planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                  	   planets[i].yyVel, planets[i].mass, planets[i].imgFileName);   
		}

	}

	public static double readRadius(String file){
		In in = new In(file);
		int planetsNum = in.readInt();
		double radius = in.readDouble();
		return radius;
	}

	public static Planet[] readPlanets(String file){
		In in = new In(file);
		int planetsNum = in.readInt();
		double radius = in.readDouble();
		Planet[] planets = new Planet[planetsNum];
		for (int i = 0; i < planetsNum; i++){
			planets[i] = new Planet(in.readDouble(), in.readDouble(),
				in.readDouble(),in.readDouble(), in.readDouble(),in.readString());
		}
		return planets;
	}
}