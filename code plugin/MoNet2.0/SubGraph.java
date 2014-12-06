 package com.wuxuehong.plugin;
 
 import java.io.PrintStream;
 import java.io.Serializable;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.LinkedList;
 import java.util.TreeSet;
 
 public class SubGraph extends Graph
   implements Serializable
 {
   ArrayList externalEdges;
   ArrayList externalNodes;
   ArrayList otherEdges;
   public boolean isModule;
   public boolean merg;
 
   public SubGraph()
   {
     this.externalEdges = new ArrayList();
     this.externalNodes = new ArrayList();
     this.otherEdges = new ArrayList();
     this.isModule = false;
     this.merg = true;
   }
 
   public SubGraph(ArrayList ns, ArrayList es, ArrayList extN, ArrayList extE, ArrayList othE, ArrayList rmvE)
   {
     this.externalEdges = new ArrayList();
     this.externalNodes = new ArrayList();
     this.otherEdges = new ArrayList();
     this.isModule = false;
     this.merg = true;
     this.nodes = ns;
     this.edges = es;
     this.externalEdges = extE;
     this.externalNodes = extN;
     this.otherEdges = othE;
     this.removedEdges = rmvE;
     createNodeMap();
     createEdgeMap();
   }
 
   public void createNodeMap()
   {
     this.nodeMap = new HashMap();
     for (int i = 0; i < this.nodes.size(); i++)
     {
       Node1 n = (Node1)this.nodes.get(i);
       this.nodeMap.put(n.nodeID, n);
     }
   }
 
   public void createEdgeMap()
   {
     this.edgeMap = new HashMap();
     for (int i = 0; i < this.edges.size(); i++)
     {
       Edge e = (Edge)this.edges.get(i);
       this.edgeMap.put(e.node1_ID + e.node2_ID, e);
       this.edgeMap.put(e.node2_ID + e.node1_ID, e);
     }
   }
 
   public ArrayList createSubGraph()
   {
     ArrayList subNs = new ArrayList();
     System.out.println("Net node size\t" + this.nodes.size() + "\tEdge size\t" + this.edges.size() + "\tremoved Edge size\t" + this.removedEdges.size());
     resetNode1s();
     resetEdges();
     try
     {
       for (int i = 0; i < this.nodes.size(); i++)
       {
         Node1 start = (Node1)this.nodes.get(i);
         if (start.pathFlag)
           continue;
         ArrayList inEdges = new ArrayList();
         ArrayList subNodes = new ArrayList();
         if (!subNodes.contains(start))
           subNodes.add(start);
         LinkedList q = new LinkedList();
         q.addLast(start);
         start.pathFlag = true;
         while (!q.isEmpty())
         {
           Node1 v = (Node1)q.removeFirst();
           for (Iterator itr = v.adj.iterator(); itr.hasNext(); )
           {
             String p1 = (String)itr.next();
             Node1 w = (Node1)this.nodeMap.get(p1);
             Edge e = (Edge)this.edgeMap.get(v.nodeID + w.nodeID);
             if (e != null)
             {
               if (!inEdges.contains(e))
                 inEdges.add(e);
             }
             else {
               System.out.println("No such edge");
             }
             if (w.pathFlag)
               continue;
             if (!subNodes.contains(w))
               subNodes.add(w);
             w.pathFlag = true;
             q.addLast(w);
           }
 
         }
 
         System.out.println("sub nodes " + subNodes.size() + "\tsub edges " + inEdges.size());
         if (subNodes.size() == this.nodes.size())
           break;
         ArrayList extEdges = new ArrayList();
         ArrayList extNodes = new ArrayList();
         ArrayList otherEdges = new ArrayList();
         ArrayList rmvEdges = new ArrayList();
         for (Iterator iter = this.removedEdges.iterator(); iter.hasNext(); )
         {
           Edge aE = (Edge)iter.next();
           Node1 n1 = (Node1)this.nodeMap.get(aE.node1_ID);
           Node1 n2 = (Node1)this.nodeMap.get(aE.node2_ID);
           if ((subNodes.contains(n1)) && (subNodes.contains(n2))) {
             rmvEdges.add(aE);
           }
           else if ((subNodes.contains(n1)) && (!subNodes.contains(n2)))
           {
             extEdges.add(aE);
             if (!extNodes.contains(n2))
               extNodes.add(n2);
           }
           else if ((!subNodes.contains(n1)) && (subNodes.contains(n2)))
           {
             extEdges.add(aE);
             if (!extNodes.contains(n1))
               extNodes.add(n1);
           }
           else {
             otherEdges.add(aE);
           }
         }
 
         for (Iterator iter2 = this.externalEdges.iterator(); iter2.hasNext(); )
         {
           Edge aE = (Edge)iter2.next();
           Node1 n1 = (Node1)this.nodeMap.get(aE.node1_ID);
           Node1 n2 = (Node1)this.nodeMap.get(aE.node2_ID);
           if ((subNodes.contains(n1)) && (subNodes.contains(n2))) {
             rmvEdges.add(aE);
           }
           else if ((subNodes.contains(n1)) && (!subNodes.contains(n2)))
           {
             extEdges.add(aE);
             if (!extNodes.contains(n2))
               extNodes.add(n2);
           }
           else if ((!subNodes.contains(n1)) && (subNodes.contains(n2)))
           {
             extEdges.add(aE);
             if (!extNodes.contains(n1))
               extNodes.add(n1);
           }
           else {
             otherEdges.add(aE);
           }
         }
 
         System.out.println("Sub nodes\t" + subNodes.size() + "\tIn Edges\t" + inEdges.size() + "\tExt nodes\t" + extNodes.size() + "\tExt Edge\t" + extEdges.size() + "\tOther Edges\t" + otherEdges.size());
         SubGraph subN = new SubGraph(subNodes, inEdges, extNodes, extEdges, otherEdges, rmvEdges);
         System.out.println("Remove edges size " + this.removedEdges.size() + "\t sub size\t" + subN.nodes.size() + "\tsub edge\t" + subN.edges.size());
         subNs.add(subN);
       }
 
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
     if (subNs.size() > 0)
       System.out.println("subNs " + subNs.size());
     return subNs;
   }
 }
