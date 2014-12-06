 package com.wuxuehong.plugin;
 
 import java.io.BufferedReader;
 import java.io.FileReader;
 import java.io.FileWriter;
 import java.io.PrintWriter;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.TreeSet;
 
 public class CalculateDeleteEdgeOrder
 {
   public CalculateDeleteEdgeOrder(Graph net)
   {
     try
     {
       PrintWriter pw = new PrintWriter(new FileWriter("Yeast DIP Core Delete Edge based on Betweenness 3.txt", true), true);
       ArrayList deletedEdges = new ArrayList();
       BufferedReader in = new BufferedReader(new FileReader("Yeast DIP Core Delete Edge based on Betweenness 3.txt"));
       String st;
       while ((st = in.readLine()) != null)
       {
      
         String[] result = st.split("\\t");
         deletedEdges.add(result[0].trim() + result[1].trim());
       }
       net.removeDeletedEdges(deletedEdges);
       Node1 n1;
       Node1 n2;
       for (; net.edges.size() > 0; n2.adj.remove(n1.nodeID))
       {
         Edge e = net.getGreatestBetweennessEdge();
         net.edges.remove(e);
         pw.println(e.node1_ID + "\t" + e.node2_ID + "\t" + e.betweenness);
         pw.flush();
         net.edgeMap.remove(e.node1_ID + e.node2_ID);
         net.edgeMap.remove(e.node2_ID + e.node1_ID);
         n1 = (Node1)net.nodeMap.get(e.node1_ID);
         n2 = (Node1)net.nodeMap.get(e.node2_ID);
         n1.adj.remove(n2.nodeID);
       }
 
       pw.close();
     }
     catch (Exception ex)
     {
       ex.printStackTrace();
     }
   }
 }
