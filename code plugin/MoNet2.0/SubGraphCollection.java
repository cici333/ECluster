 package com.wuxuehong.plugin;
 
 import java.io.Serializable;
 import java.util.ArrayList;
 import java.util.HashMap;
 
 public class SubGraphCollection
   implements Serializable
 {
   public ArrayList subGraphs;
 
   public SubGraphCollection()
   {
     this.subGraphs = new ArrayList();
   }
 
   public SubGraph findSubGraph(Node1 n)
   {
     SubGraph[] sg = (SubGraph[])this.subGraphs.toArray(new SubGraph[0]);
     for (int i = 0; i < sg.length; i++) {
       if (sg[i].nodes.contains(n))
         return sg[i];
     }
     return null;
   }
 
   public int findSubGraphIndex(Node1 n)
   {
     SubGraph[] sg = (SubGraph[])this.subGraphs.toArray(new SubGraph[0]);
     for (int i = 0; i < sg.length; i++) {
       if (sg[i].nodes.contains(n))
         return i;
     }
     return -1;
   }
 
   public SubGraph findSubGraph(String nodeID)
   {
     SubGraph[] sg = (SubGraph[])this.subGraphs.toArray(new SubGraph[0]);
     for (int i = 0; i < sg.length; i++)
     {
       Node1 n = (Node1)sg[i].nodeMap.get(nodeID);
       if (n != null) {
         return sg[i];
       }
     }
     return null;
   }
 
   public void add(SubGraph sg)
   {
     this.subGraphs.add(sg);
   }
 
   public void remove(SubGraph sg)
   {
     this.subGraphs.remove(sg);
   }
 }

