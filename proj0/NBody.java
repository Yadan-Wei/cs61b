/** NBody is a class that will actually run your simulation. */

public class NBody{

	
	
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