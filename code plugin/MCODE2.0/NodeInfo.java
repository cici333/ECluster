package com.wuxuehong.plugin;

import com.wuxuehong.bean.Node;
import java.util.LinkedList;
import java.util.List;

public class NodeInfo 
{
	private List<Node> neighbours;
	private double weight; // node score = kValue * coreDensity
	private boolean beSeen;
	private double coreDensity; // the density of core neighbors
	private int kValue; // k value (k-core)
	private double density; // the density of all neighbors(including v)
  

  public NodeInfo()
  {
     this.beSeen = false;
     this.coreDensity = 0.0;
     this.kValue = 0;
     this.density = 0.0;
     this.weight = 0.0;
  }

  public double getWeight() {
     return this.weight;
  }

  public void setWeight(double weight) {
     this.weight = weight;
  }

  public boolean isBeSeen() {
     return this.beSeen;
  }

  public void setBeSeen(boolean beSeen) {
     this.beSeen = beSeen;
  }

public double getCoreDensity() {
	return coreDensity;
}

public void setCoreDensity(double coreDensity) {
	this.coreDensity = coreDensity;
}

public int getkValue() {
	return kValue;
}

public void setkValue(int kValue) {
	this.kValue = kValue;
}

public double getDensity() {
	return density;
}

public void setDensity(double density) {
	this.density = density;
}
  
  
  
}

