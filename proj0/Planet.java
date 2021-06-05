public class Planet{

	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;

	/**Declaring variables only as static can lead to change in their values 
	 * by one or more instances of a class in which it is declared.
	Declaring them as static final will help you to create a CONSTANT. 
	Only one copy of variable exists which can’t be reinitialize.*/

	final static double gravConstant = 6.67e-11;


	public Planet(double xP, double yP, double xV, double yV,
		double m, String img){
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}

	public Planet(Planet p){
		xxPos = p.xxPos;
		yyPos = p.yyPos;
		xxVel = p.xxVel;
		yyVel = p.yyVel;
		mass = p.mass;
		imgFileName = p.imgFileName;
	}

	public double calcDistance(Planet p){
		double xDis = this.xxPos - p.xxPos;
		double yDis = this.yyPos - p.yyPos;
		return Math.sqrt(xDis*xDis + yDis*yDis);
	}

	public double calcForceExertedBy(Planet p){
		double dis = calcDistance(p);
		double force = gravConstant*p.mass*this.mass/(dis*dis);
		return force;
	}

	public double calcForceExertedByX(Planet p){
		double xDis = p.xxPos - this.xxPos;
		double force = calcForceExertedBy(p);
		double dis = calcDistance(p);
		double forceX = force*xDis/dis;
		return forceX;
	}

	public double calcForceExertedByY(Planet p){
		double yDis = p.yyPos - this.yyPos;
		double force = calcForceExertedBy(p);
		double dis = calcDistance(p);
		double forceY = force*yDis/dis;
		return forceY;
	}

	public double calcNetForceExertedByX(Planet[] planets) {
		double netForceX = 0;
		for (int i=0; i < planets.length; i ++){
			if (this.equals(planets[i])){
				continue;
			}
			netForceX = netForceX + calcForceExertedByX(planets[i]);
		}
		return netForceX;
	}
	/** Use enhanced for*/
	public double calcNetForceExertedByY(Planet[] planets) {
		double netForceY = 0;
		for (Planet s: planets){
			if (this.equals(s)){
				continue;
			}
			netForceY = netForceY+ calcForceExertedByY(s);
		}
		return netForceY;
	}

	public void update(double dt, double fX, double fY){
		double aX = fX/this.mass;
		double aY = fY/this.mass;
		this.xxVel = this.xxVel + dt*aX;
		this.yyVel = this.yyVel + dt*aY;
		this.xxPos = this.xxPos + dt*this.xxVel;
		this.yyPos = this.yyPos + dt*this.yyVel;
	}
}