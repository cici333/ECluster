 package com.wuxuehong.plugin;
 
 import com.wuxuehong.bean.Node;
 import java.io.Serializable;
 import java.util.ArrayList;
 import java.util.TreeSet;
 
 public class Node1 extends Node
   implements Serializable
 {
   public String nodeID;
   public TreeSet adj;
   TreeSet extAdj;
   int dist;
   Node1 path;
   ArrayList parents;
   double weight;
   boolean hasChild;
   boolean pathFlag;
   double betweenness;
   double tempBetweeen;
 
   public Node1(String id)
   {
     super(id);
     this.adj = new TreeSet();
     this.extAdj = new TreeSet();
     this.parents = new ArrayList();
     this.hasChild = false;
     this.pathFlag = false;
     this.betweenness = 0.0D;
     this.tempBetweeen = 0.0D;
     this.nodeID = id;
   }
 
   public void clean()
   {
     this.betweenness = 0.0D;
     this.tempBetweeen = 0.0D;
     this.dist = 2147483647;
     this.path = null;
     this.hasChild = false;
     this.pathFlag = false;
     this.weight = 0.0D;
     this.extAdj = new TreeSet();
     this.parents = new ArrayList();
   }
 
   public void reset()
   {
     this.dist = 2147483647;
     this.path = null;
     this.hasChild = false;
     this.pathFlag = false;
     this.tempBetweeen = 0.0D;
     this.weight = 0.0D;
     this.parents = new ArrayList();
   }
 }
