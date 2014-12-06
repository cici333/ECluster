 package com.wuxuehong.plugin;
 
 import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.wuxuehong.bean.Edge;
import com.wuxuehong.bean.Node;
 
 public class SubGraph
 {
   private List<Node> nodes;
   private List<Edge> edges;
   private HashMap<String, Integer> degreeMap;
 
   public SubGraph()
   {
     this.nodes = new LinkedList<Node>();
     this.edges = new LinkedList<Edge>();
     this.degreeMap = new HashMap();
   }
   
   
 
   public double getDensity()
   {
     int possibleEdgeNum = 0; int actualEdgeNum = 0; int loopCount = 0;
     double density = 0.0D;
     possibleEdgeNum = this.nodes.size() * (this.nodes.size() - 1) / 2;
     actualEdgeNum = this.edges.size();
     density = actualEdgeNum / possibleEdgeNum;
     return density;
   }
 
   /**
    * @author TangYu
    * @date: 2014年9月4日 下午7:04:09
    * @description: initial edges and degree map
    */
   public void initial()
   {
	   String nodeId, node2Id;
	   for (Node node : this.nodes) {
		   nodeId = node.getNodeID();
		   if(!degreeMap.containsKey(nodeId)){
			   degreeMap.put(nodeId, new Integer(0));  
		   }
		    	 
    	 for(Edge adjacentEdge : node.getAdjacentEdges()){
    		 if(nodes.contains(adjacentEdge.getNode2())){
    			 node2Id = adjacentEdge.getNode2().getNodeID();
    			 edges.add(adjacentEdge);
    			 degreeMap.put(nodeId,  new Integer(degreeMap.get(nodeId)+1));
    			 degreeMap.put(node2Id, degreeMap.containsKey(node2Id)? new Integer(degreeMap.get(node2Id)+1) : new Integer(1));
    		 }
    	 }   	     
	   }
   }
   
 
   public int getDegree(String name)
   {
     return ((Integer)getDegreeMap().get(name)).intValue();
   }
 
   public List<Node> getNodes() {
     return this.nodes;
   }
 
   public List<Edge> getEdges() {
     return this.edges;
   }
 
   public HashMap<String, Integer> getDegreeMap() {
     return this.degreeMap;
   }
 
   public void setDegreeMap(HashMap<String, Integer> degreeMap) {
     this.degreeMap = degreeMap;
   }
 }
