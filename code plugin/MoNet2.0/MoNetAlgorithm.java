 package com.wuxuehong.plugin;
 
 import com.wuxuehong.bean.Node;
 import com.wuxuehong.interfaces.GraphInfo;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.TreeSet;
 import java.util.Vector;
 
 public class MoNetAlgorithm
 {
   ArrayList deletedEdges;
   IdentifyModulesByAgglomerative idma;
 
   public MoNetAlgorithm()
   {
     this.deletedEdges = new ArrayList();
     try
     {
       Graph yeastProtein = readYeastDIPCoreLargeComponent();
       this.idma = new IdentifyModulesByAgglomerative();
       this.idma.identifyModules(yeastProtein, this.deletedEdges);
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
   }
 
   public Vector[] getResult()
   {
     ArrayList al = this.idma.printModules2();
     Vector[] vector = new Vector[al.size()];
     for (int i = 0; i < al.size(); i++)
     {
       SubGraph temp = (SubGraph)al.get(i);
       vector[i] = new Vector();
       for(Node1 n : (ArrayList<Node1>)temp.nodes){
    	   vector[i].add(GraphInfo.nodemap.get(n.getNodeID()));
       }
     }
     return vector;
   }
 
   public Graph readYeastDIPCoreLargeComponent()
   {
     Graph yeastP = new Graph();
     try
     {
       Vector edgelist = GraphInfo.edgelist;
       String[] result = new String[2];
       for (int i = 0; i < edgelist.size(); i++)
       {
         com.wuxuehong.bean.Edge edge = (com.wuxuehong.bean.Edge)edgelist.get(i);
         result[0] = edge.getNode1().getNodeID();
         result[1] = edge.getNode2().getNodeID();
         this.deletedEdges.add(result[0].trim() + result[1].trim());
         if (result[0].equals(result[1]))
           continue;
         Node1 n1 = (Node1)yeastP.nodeMap.get(result[0]);
         Node1 n2 = (Node1)yeastP.nodeMap.get(result[1]);
         if ((n1 == null) && (n2 == null))
         {
           n1 = new Node1(result[0]);
           yeastP.nodes.add(n1);
           yeastP.nodeMap.put(result[0].trim(), n1);
           n2 = new Node1(result[1]);
           yeastP.nodes.add(n2);
           yeastP.nodeMap.put(result[1].trim(), n2);
           n1.adj.add(n2.nodeID.trim());
           n2.adj.add(n1.nodeID.trim());
         }
         else if ((n1 == null) && (n2 != null))
         {
           n1 = new Node1(result[0]);
           yeastP.nodes.add(n1);
           yeastP.nodeMap.put(result[0].trim(), n1);
           n1.adj.add(n2.nodeID.trim());
           n2.adj.add(n1.nodeID.trim());
         }
         else if ((n1 != null) && (n2 == null))
         {
           n2 = new Node1(result[1]);
           yeastP.nodes.add(n2);
           yeastP.nodeMap.put(result[1].trim(), n2);
           n1.adj.add(n2.nodeID.trim());
           n2.adj.add(n1.nodeID.trim());
         }
         else if ((n1 != null) && (n2 != null))
         {
           n1.adj.add(n2.nodeID.trim());
           n2.adj.add(n1.nodeID.trim());
         }
         Edge e = new Edge(result[0], result[1]);
         yeastP.edges.add(e);
         yeastP.edgeMap.put(result[0].trim() + result[1].trim(), e);
         yeastP.edgeMap.put(result[0].trim() + result[1].trim(), e);
       }
 
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
     return yeastP;
   }
 }
