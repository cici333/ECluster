 package com.wuxuehong.plugin;
 
 import com.wuxuehong.bean.Node;
 import java.util.ArrayList;
 
 class NetNode extends Node
 {
   private String identifier;
   private int rootIndex;
   private ArrayList alNeighbors;
   private int degree;
   private int complexID;
 
   public NetNode(String identifier)
   {
     super(identifier);
 
     this.identifier = identifier;
     this.alNeighbors = new ArrayList();
     this.degree = 0;
   }
 
   public ArrayList getAlNeighbors() {
     return this.alNeighbors;
   }
 
   public void setAlNeighbors(ArrayList alNeighbors) {
     this.alNeighbors = alNeighbors;
   }
 
   public int getDegree() {
     return this.degree;
   }
 
   public void setDegree(int degree) {
     this.degree = degree;
   }
 
   public String getIdentifier() {
     return this.identifier;
   }
 
   public void setIdentifier(String identifier) {
     this.identifier = identifier;
   }
 
   public int getRootGraphIndex() {
     return this.rootIndex;
   }
 
   public void setRootGraphIndex(int rootIndex) {
     this.rootIndex = rootIndex;
   }
 
   public int getComplexID() {
     return this.complexID;
   }
 
   public void setComplexID(int complexID) {
     this.complexID = complexID;
   }
 }
