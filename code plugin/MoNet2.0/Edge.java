 package com.wuxuehong.plugin;
 
 import java.io.Serializable;
 
 public class Edge
   implements Serializable
 {
   String edgeID;
   String node1_ID;
   String node2_ID;
   boolean flag;
   double betweenness;
   double tempBetweeen;
   boolean reached;
   int depth;
 
   public Edge()
   {
     this.flag = false;
     this.betweenness = 0.0D;
     this.tempBetweeen = 0.0D;
     this.reached = false;
     this.depth = 0;
   }
 
   public Edge(String eID, String n1_ID, String n2_ID)
   {
     this.flag = false;
     this.betweenness = 0.0D;
     this.tempBetweeen = 0.0D;
     this.reached = false;
     this.depth = 0;
     this.edgeID = eID;
     this.node1_ID = n1_ID;
     this.node2_ID = n2_ID;
   }
 
   public Edge(String n1_ID, String n2_ID)
   {
     this.flag = false;
     this.betweenness = 0.0D;
     this.tempBetweeen = 0.0D;
     this.reached = false;
     this.depth = 0;
     this.node1_ID = n1_ID;
     this.node2_ID = n2_ID;
   }
 
   public void clean()
   {
     this.betweenness = 0.0D;
     this.tempBetweeen = 0.0D;
     this.flag = false;
     this.depth = 0;
     this.reached = false;
   }
 
   public void reset()
   {
     this.flag = false;
     this.tempBetweeen = 0.0D;
     this.depth = 0;
     this.reached = false;
   }
 }

