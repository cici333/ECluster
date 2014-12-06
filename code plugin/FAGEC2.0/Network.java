 package com.wuxuehong.plugin;
 
 import java.util.ArrayList;
 import java.util.Iterator;
 
 public class Network
 {
   private ArrayList<NetNode> alNodes;
   private ArrayList alArcs;
 
   public Network()
   {
     this.alNodes = null;
     this.alArcs = null;
   }
 
   public String getNodeIdentifier(int index) {
     String identifier = ((NetNode)this.alNodes.get(index)).getIdentifier();
     return identifier;
   }
   public int getDegree(int index) {
     NetNode node = (NetNode)this.alNodes.get(index);
     int degree = node.getDegree();
     return degree;
   }
   public int[] neighborsArray(int index) {
     NetNode node = (NetNode)this.alNodes.get(index);
     ArrayList alNeighbors = node.getAlNeighbors();
     int[] neighbors = new int[alNeighbors.size()];
     int j = 0;
     for (Iterator i = alNeighbors.iterator(); i.hasNext(); j++) {
       neighbors[j] = ((Integer)i.next()).intValue();
     }
     return neighbors;
   }
   public ArrayList getAlArcs() {
     return this.alArcs;
   }
   public void setAlArcs(ArrayList alArcs) {
     this.alArcs = alArcs;
   }
   public  ArrayList<NetNode> getAlNodes() {
     return this.alNodes;
   }
   public void setAlNodes( ArrayList<NetNode> alNodes) {
     this.alNodes = alNodes;
   }
 }

