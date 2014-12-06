 package com.wuxuehong.plugin;
 
 import java.io.FileWriter;
 import java.io.PrintStream;
 import java.io.PrintWriter;
 import java.util.ArrayList;
 import java.util.Iterator;
 import java.util.TreeSet;
 
 public class PrintGraph
 {
   private String subGraphFile;
   private String biolayoutInputFile;
   private String moduleFile;
 
   public PrintGraph()
   {
     this.subGraphFile = "Modules of Yeast DIP Core Network with 1230 links removed.txt";
     this.biolayoutInputFile = "Yeast DIP Core Biolayout.txt";
     this.moduleFile = "Yeast DIP Core modules.txt";
   }
 
   public void printGraph(Graph net)
   {
     try
     {
       PrintWriter pw = new PrintWriter(new FileWriter("C:\\Documents and Settings\\Administrator\\workspace\\Monet\\src\\ppi.txt"));
 
       for (Iterator iter = net.nodes.iterator(); iter.hasNext(); ) {
         Node1 localNode1 = (Node1)iter.next();
       }
       pw.flush();
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
   }
 
   public void printSubGraph(Graph aNet)
   {
     int communityNum = 0;
     int total = 0;
     try
     {
       PrintWriter pw = new PrintWriter(new FileWriter(this.subGraphFile));
       for (Iterator iter5 = aNet.subGraphs.iterator(); iter5.hasNext(); )
       {
         SubGraph net = (SubGraph)iter5.next();
         if (net.nodes.size() > 100)
         {
           Edge e;
           for (Iterator iter = net.edges.iterator(); iter.hasNext(); pw.println(e.edgeID + "\t" + e.node1_ID + "\t" + e.node2_ID)) {
             e = (Edge)iter.next();
           }
         }
         pw.flush();
         communityNum++;
       }
 
       pw.close();
       System.out.println("total nodes " + total);
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
   }
 
   public void pringModules(Graph net)
   {
     int communityNum = 0;
     int total = 0;
     try
     {
       PrintWriter pw = new PrintWriter(new FileWriter(this.moduleFile, true), true);
       pw.println("module\tmodule size\t");
       ArrayList subs = getModule(net);
       System.out.println("Number of moudles " + subs.size());
       for (Iterator iter2 = subs.iterator(); iter2.hasNext(); )
       {
         SubGraph sub = (SubGraph)iter2.next();
         pw.println(communityNum + "\t" + sub.nodes.size());
         total += sub.nodes.size();
         for (Iterator iter = sub.nodes.iterator(); iter.hasNext(); pw.println())
         {
           Node1 n = (Node1)iter.next();
           String[] link = (String[])n.adj.toArray(new String[0]);
           for (int i3 = 0; i3 < link.length; i3++) {
             pw.print(link[i3] + " ");
           }
           pw.print("\t");
           for (Iterator iter3 = sub.externalEdges.iterator(); iter3.hasNext(); )
           {
             Edge e = (Edge)iter3.next();
             if (e.node1_ID.equals(n.nodeID)) {
               pw.print(e.node2_ID + " ");
             }
             else if (e.node2_ID.equals(n.nodeID)) {
               pw.print(e.node1_ID + " ");
             }
           }
         }
 
         pw.flush();
         communityNum++;
       }
 
       pw.close();
       System.out.println("total nodes " + total);
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
   }
 
   public ArrayList getModule(Graph net)
   {
     ArrayList subs = new ArrayList();
     if (net.subGraphs.size() > 0)
     {
       SubGraph subN;
       for (Iterator iter = net.subGraphs.iterator(); iter.hasNext(); subs.addAll(getModule(subN))) {
         subN = (SubGraph)iter.next();
       }
     }
     else if (net.nodes.size() > 0) {
       subs.add((SubGraph)net);
     }return subs;
   }
 }
