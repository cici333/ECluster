 package com.wuxuehong.plugin;
 
 import java.io.FileWriter;
 import java.io.PrintStream;
 import java.io.PrintWriter;
 import java.io.Serializable;
 import java.util.ArrayList;
 import java.util.Collections;
 import java.util.Comparator;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.LinkedList;
 import java.util.TreeSet;
 
 public class Graph
   implements Serializable
 {
   public HashMap nodeMap;
   public HashMap edgeMap;
   public ArrayList nodes;
   public ArrayList edges;
   public ArrayList subGraphs;
   ArrayList removedEdges;
   boolean dividFlag;
 
   public Graph()
   {
     this.nodeMap = new HashMap();
     this.edgeMap = new HashMap();
     this.nodes = new ArrayList();
     this.edges = new ArrayList();
     this.subGraphs = new ArrayList();
     this.removedEdges = new ArrayList();
     this.dividFlag = true;
   }
 
   public Graph(ArrayList e, ArrayList n)
   {
     this.nodeMap = new HashMap();
     this.edgeMap = new HashMap();
     this.nodes = new ArrayList();
     this.edges = new ArrayList();
     this.subGraphs = new ArrayList();
     this.removedEdges = new ArrayList();
     this.dividFlag = true;
     this.edges = e;
     this.nodes = n;
   }
 
   public boolean findEdge(Object e)
   {
     return this.edges.contains(e);
   }
 
   public boolean findVextex(Object v)
   {
     return this.nodes.contains(v);
   }
 
   public void cleanAllNode1s()
   {
     for (Iterator itr = this.nodes.iterator(); itr.hasNext(); ((Node1)itr.next()).clean());
   }
 
   public void resetNode1s() {
     for (Iterator itr = this.nodes.iterator(); itr.hasNext(); ((Node1)itr.next()).reset());
   }
 
   public void cleanAllEdges() {
     for (Iterator itr = this.edges.iterator(); itr.hasNext(); ((Edge)itr.next()).clean());
   }
 
   public void resetEdges() {
     for (Iterator itr = this.edges.iterator(); itr.hasNext(); ((Edge)itr.next()).reset());
   }
 
   public boolean isConnected() {
     return true;
   }
 
   public ArrayList getSubGraphs()
   {
     return this.subGraphs;
   }
 
   public void addEdge(Edge aE)
   {
     if (aE != null)
     {
       this.edgeMap.put(aE.node1_ID + aE.node2_ID, aE);
       this.edgeMap.put(aE.node2_ID + aE.node1_ID, aE);
       Node1 n1 = (Node1)this.nodeMap.get(aE.node1_ID);
       Node1 n2 = (Node1)this.nodeMap.get(aE.node2_ID);
       n1.adj.add(n2.nodeID);
       n2.adj.add(n1.nodeID);
       n1.extAdj.remove(n2.nodeID);
       n2.extAdj.remove(n1.nodeID);
       this.edges.add(aE);
     }
     else {
       System.out.println("Edge " + aE + " is not exist");
     }
   }
 
   public void removeEdge(Edge e)
   {
     if (e != null)
     {
       this.edges.remove(e);
       this.edgeMap.remove(e.node1_ID + e.node2_ID);
       this.edgeMap.remove(e.node2_ID + e.node1_ID);
       Node1 n1 = (Node1)this.nodeMap.get(e.node1_ID);
       Node1 n2 = (Node1)this.nodeMap.get(e.node2_ID);
       n1.adj.remove(n2.nodeID);
       n2.adj.remove(n1.nodeID);
       n1.extAdj.add(n2.nodeID);
       n2.extAdj.add(n1.nodeID);
     }
     else {
       System.out.println("Edge " + e + " is not exist");
     }
   }
 
   public void removeDeletedEdges(ArrayList deletedEdges)
   {
     int size = deletedEdges.size();
     for (int i = 0; i < size; i++)
     {
       String nodes = (String)deletedEdges.get(i);
       Edge e = (Edge)this.edgeMap.get(nodes);
       if (e != null)
       {
         this.edges.remove(e);
         this.edgeMap.remove(e.node1_ID + e.node2_ID);
         this.edgeMap.remove(e.node2_ID + e.node1_ID);
         Node1 n1 = (Node1)this.nodeMap.get(e.node1_ID);
         Node1 n2 = (Node1)this.nodeMap.get(e.node2_ID);
         n1.adj.remove(n2.nodeID);
         n2.adj.remove(n1.nodeID);
       }
       else {
         System.out.println("Edge " + e + " is not exist");
       }
     }
   }
 
   public void removeDeletedEdges(int size, ArrayList deletedEdges)
   {
     for (int i = 0; i < size; i++)
     {
       String nodes = (String)deletedEdges.get(i);
       Edge e = (Edge)this.edgeMap.get(nodes);
       if (e != null)
       {
         this.edges.remove(e);
         this.edgeMap.remove(e.node1_ID + e.node2_ID);
         this.edgeMap.remove(e.node2_ID + e.node1_ID);
         Node1 n1 = (Node1)this.nodeMap.get(e.node1_ID);
         Node1 n2 = (Node1)this.nodeMap.get(e.node2_ID);
         n1.adj.remove(n2.nodeID);
         n2.adj.remove(n1.nodeID);
       }
       else {
         System.out.println("Edge " + e + " is not exist");
       }
     }
   }
 
   public int[][] getMatrix(int numOfDeletedEdges, ArrayList deletedEdges)
   {
     removeDeletedEdges(numOfDeletedEdges, deletedEdges);
     return getMatrix();
   }
 
   public int[][] getMatrix()
   {
     int size = this.nodes.size();
     int[][] net = new int[size][size];
     for (int i = 0; i < size; i++)
     {
       for (int j = 0; j < size; j++) {
         if (i != j)
           net[i][j] = 0;
         else {
           net[i][j] = 1;
         }
       }
     }
     System.out.println("Number of Edges is " + this.edges.size());
     for (Iterator iter = this.edges.iterator(); iter.hasNext(); )
     {
       Edge edge = (Edge)iter.next();
       int index1 = this.nodes.indexOf((Node1)this.nodeMap.get(edge.node1_ID));
       int index2 = this.nodes.indexOf((Node1)this.nodeMap.get(edge.node2_ID));
       net[index1][index2] = 1;
       net[index2][index1] = 1;
     }
 
     return net;
   }
 
   public Edge getGreatestBetweennessEdge()
   {
     Edge e = null;
     System.out.println("Size of Edges " + this.edges.size() + "\tSize of Node1s " + this.nodes.size());
     calculateEdgeShortestPathBetweenness();
     Collections.shuffle(this.edges);
     Collections.sort(this.edges, new Comparator()
     {
       final Graph this$0 = Graph.this;
 
       public int compare(Object o1, Object o2)
       {
         if (((Edge)o2).betweenness < ((Edge)o1).betweenness)
           return -1;
         return ((Edge)o2).betweenness <= ((Edge)o1).betweenness ? 0 : 1;
       }
     });
     return (Edge)this.edges.get(0);
   }
 
   public void calculateEdgeShortestPathBetweenness()
   {
     try
     {
       cleanAllEdges();
       cleanAllNode1s();
       ArrayList geneID = new ArrayList(this.nodeMap.keySet());
       int size = geneID.size();
       for (int i = 0; i < size; i++)
       {
         String geneid = (String)geneID.get(i);
         Node1 n = (Node1)this.nodeMap.get(geneid);
         shortestPath(n);
         updateEdgeBetweenness(n);
         for (Iterator iter = this.edges.iterator(); iter.hasNext(); )
         {
           Edge e = (Edge)iter.next();
           e.betweenness += e.tempBetweeen;
         }
 
       }
 
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
   }
 
   public void printAllShortestPath()
   {
     try
     {
       PrintWriter pw = new PrintWriter(new FileWriter("Yeast core PIN Large Component All Shortest path.txt"));
       cleanAllEdges();
       cleanAllNode1s();
       ArrayList geneID = new ArrayList(this.nodeMap.keySet());
       int size = geneID.size();
       for (int i = 0; i < size; i++)
       {
         String geneid = (String)geneID.get(i);
         Node1 n = (Node1)this.nodeMap.get(geneid);
         shortestPath(n);
         for (int j = 0; j < this.nodes.size(); j++)
         {
           Node1 node = (Node1)this.nodes.get(j);
           pw.print(node.dist + "\t");
         }
 
         pw.println();
         pw.flush();
         updateEdgeBetweenness(n);
         for (Iterator iter = this.edges.iterator(); iter.hasNext(); )
         {
           Edge e = (Edge)iter.next();
           e.betweenness += e.tempBetweeen;
         }
 
       }
 
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
   }
 
   public void shortestPath(Node1 start)
   {
     resetNode1s();
     resetEdges();
     try
     {
       LinkedList q = new LinkedList();
       q.addLast(start);
       start.dist = 0;
       start.weight = 1.0D;
       while (!q.isEmpty())
       {
         Node1 v = (Node1)q.removeFirst();
         for (Iterator itr = v.adj.iterator(); itr.hasNext(); )
         {
           String p1 = (String)itr.next();
           Node1 w = (Node1)this.nodeMap.get(p1);
           if ((w.dist == 2147483647) || (w.dist > v.dist + 1))
           {
             v.dist += 1;
             w.weight = v.weight;
             w.path = v;
             if (!w.parents.contains(v))
               w.parents.add(v);
             v.hasChild = true;
             q.addLast(w);
           } else {
             if (w.dist != v.dist + 1)
               continue;
             w.weight += v.weight;
             if (!w.parents.contains(v))
               w.parents.add(v);
             v.hasChild = true;
           }
         }
       }
 
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
   }
 
   public void shortestPath(int pos)
   {
     resetNode1s();
     resetEdges();
     try
     {
       Node1 start = (Node1)this.nodes.get(pos);
       LinkedList q = new LinkedList();
       q.addLast(start);
       start.dist = 0;
       start.weight = 1.0D;
       while (!q.isEmpty())
       {
         Node1 v = (Node1)q.removeFirst();
         for (Iterator itr = v.adj.iterator(); itr.hasNext(); )
         {
           String p1 = (String)itr.next();
           Node1 w = (Node1)this.nodeMap.get(p1);
           if ((w.dist == 2147483647) || (w.dist > v.dist + 1))
           {
             v.dist += 1;
             w.weight = v.weight;
             w.path = v;
             if (!w.parents.contains(v))
               w.parents.add(v);
             v.hasChild = true;
             q.addLast(w);
           } else {
             if (w.dist != v.dist + 1)
               continue;
             w.weight += v.weight;
             if (!w.parents.contains(v))
               w.parents.add(v);
             v.hasChild = true;
           }
         }
       }
 
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
   }
 
   public void updateEdgeBetweenness(Node1 n)
   {
     if ((!n.hasChild) && (n.dist == 0))
       return;
     Collections.sort(this.nodes, new Comparator()
     {
       final Graph this$0 = Graph.this;
 
       public int compare(Object o1, Object o2)
       {
         if (((Node1)o2).dist < ((Node1)o1).dist)
           return -1;
         return ((Node1)o2).dist <= ((Node1)o1).dist ? 0 : 1;
       }
     });
     for (Iterator iter = this.nodes.iterator(); iter.hasNext(); )
     {
       Node1 n1 = (Node1)iter.next();
       if (n1.parents.size() <= 0)
         continue;
       for (Iterator iter3 = n1.parents.iterator(); iter3.hasNext(); )
       {
         Node1 n3 = (Node1)iter3.next();
         Edge e = (Edge)this.edgeMap.get(n1.nodeID + n3.nodeID);
         e.tempBetweeen = (n3.weight / n1.weight * (1.0D + n1.tempBetweeen));
         n3.tempBetweeen += e.tempBetweeen;
       }
     }
   }
 
   public double getEdgeBetweenness(Node1 n)
   {
     double betw = 0.0D;
     if ((!n.hasChild) && (n.dist != 2147483647))
       return betw;
     for (Iterator iter = n.adj.iterator(); iter.hasNext(); )
     {
       String p1 = (String)iter.next();
       Node1 n1 = (Node1)this.nodeMap.get(p1);
       if (n1.dist <= n.dist)
         continue;
       Edge e = (Edge)this.edgeMap.get(n.nodeID + n1.nodeID);
       e.tempBetweeen = (n.weight / n1.weight * (1.0D + getEdgeBetweenness(n1)));
       betw += e.tempBetweeen;
     }
 
     return betw;
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
         ArrayList subNode1s = new ArrayList();
         if (!subNode1s.contains(start))
           subNode1s.add(start);
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
             if (!subNode1s.contains(w))
               subNode1s.add(w);
             w.pathFlag = true;
             q.addLast(w);
           }
 
         }
 
         System.out.println("sub nodes " + subNode1s.size() + "\tsub edges " + inEdges.size());
         if (subNode1s.size() == this.nodes.size())
           break;
         ArrayList extEdges = new ArrayList();
         ArrayList extNode1s = new ArrayList();
         ArrayList otherEdges = new ArrayList();
         ArrayList rmvEdges = new ArrayList();
         for (Iterator iter = this.removedEdges.iterator(); iter.hasNext(); )
         {
           Edge aE = (Edge)iter.next();
           Node1 n1 = (Node1)this.nodeMap.get(aE.node1_ID);
           Node1 n2 = (Node1)this.nodeMap.get(aE.node2_ID);
           if ((!subNode1s.contains(n1)) || (!subNode1s.contains(n2))) {
             if ((subNode1s.contains(n1)) && (!subNode1s.contains(n2)))
             {
               extEdges.add(aE);
               if (!extNode1s.contains(n2))
                 extNode1s.add(n2);
             }
             else if ((!subNode1s.contains(n1)) && (subNode1s.contains(n2)))
             {
               extEdges.add(aE);
               if (!extNode1s.contains(n1))
                 extNode1s.add(n1);
             }
             else {
               otherEdges.add(aE);
             }
           }
         }
         System.out.println("Sub nodes\t" + subNode1s.size() + "\tIn Edges\t" + inEdges.size() + "\tExt nodes\t" + extNode1s.size() + "\tExt Edge\t" + extEdges.size() + "\tOther Edges\t" + otherEdges.size());
         SubGraph subN = new SubGraph(subNode1s, inEdges, extNode1s, extEdges, otherEdges, rmvEdges);
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
 
   public void printEdgeBetweenness()
   {
     Edge e = null;
     System.out.println("Size of Edges " + this.edges.size() + "\tSize of Node1s " + this.nodes.size());
     calculateEdgeShortestPathBetweenness();
     Collections.shuffle(this.edges);
     Collections.sort(this.edges, new Comparator()
     {
       final Graph this$0 = Graph.this;
 
       public int compare(Object o1, Object o2)
       {
         if (((Edge)o2).betweenness < ((Edge)o1).betweenness)
           return -1;
         return ((Edge)o2).betweenness <= ((Edge)o1).betweenness ? 0 : 1;
       }
 
     });
     try
     {
       PrintWriter pw = new PrintWriter(new FileWriter("Betweenness.txt"));
       for (int i = 0; i < this.edges.size(); i++)
       {
         Edge ae = (Edge)this.edges.get(i);
         pw.println(ae.node1_ID + "\t" + ae.node2_ID + "\t" + ae.betweenness);
       }
 
       pw.flush();
     }
     catch (Exception ex)
     {
       ex.printStackTrace();
     }
   }
 }

