package fr.gagoi.gps.core;

public class Link {
	
	private String startPoint, endPoint;
	private int weight;

	
	// L'objet Link est une liaison entre "startPoint" et "endPoint", elle possede un poids "weight", qui correspond à la distance. Cette liaison ne fonctionne que dans un sens.
	public Link(String startPoint, String endPoint, int weight) {
		this.setStartPoint(startPoint);
		this.setEndPoint(endPoint);
		this.setWeight(weight);
	}

	public String getStartPoint() {
		return startPoint;
	}
	public void setStartPoint(String startPoint) {
		this.startPoint = startPoint;
	}

	public String getEndPoint() {
		return endPoint;
	}
	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}

	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	@Override
	public String toString(){
		return ("Link : ["+getStartPoint()+", "+getEndPoint()+", "+getWeight()+"]");
	}

}
