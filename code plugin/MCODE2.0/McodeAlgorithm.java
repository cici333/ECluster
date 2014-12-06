 package com.wuxuehong.plugin;
 
 import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.omg.CORBA.Current;

import com.wuxuehong.bean.Node;
import com.wuxuehong.interfaces.GraphInfo;
 
 /**
  * 
  * @author TangYu
  * @modify date: 2014年8月27日
  *
  */
 public class McodeAlgorithm
 {
   private double nodeWeightCutoff;
   private double fluffDensityCutoff;
   private boolean isFluff;
   private boolean isHaircut;
   private int kCore;
   private int maxDepth;
   private int degreeCutoff;
   private HashMap<Node, NodeInfo> nodeToInfoMap;
 
   public List<List<NodeInfo>> complexes = new LinkedList();
   private McodeMain mm;
   
   public McodeAlgorithm(McodeMain mm)
   {
     this.mm = mm;
   }
 
   public void vertex_weighting()
   {
    
	  /**
	   * Step 1 weighting  the nodes 
	   * 
	   */
	   
	   nodeToInfoMap = new HashMap<Node, NodeInfo>();
	   for(Node node : GraphInfo.nodelist) {
		   nodeToInfoMap.put(node, new NodeInfo());		   
	   }
	   
     for(Entry<Node, NodeInfo> entry: nodeToInfoMap.entrySet()) {
    	 Node node = entry.getKey();
    	 NodeInfo nInfo = entry.getValue();
    	 
    	 if (node.getNeighbours().size() < 2){   	   
    	   if(node.getNeighbours().size() == 1){
    		   nInfo.setDensity(1.0D);
    		   nInfo.setCoreDensity(1.0D);
    	   }
    	   
    	 }
    	 else{
    		 // calculate k-core 
    		 List neighbours = new LinkedList();
    		 neighbours.add(node);
    		 neighbours.addAll(node.getNeighbours());
    		 SubGraph neighborsGraph = getSubgraph(neighbours);
    		 
    		 //calculate density
    		 nInfo.setDensity(calDensity(neighborsGraph));
    		 	 
    		 Object[] object = getHighestKCore(neighborsGraph); 
    		 SubGraph Kcore = (SubGraph)object[1];
    		 int k = ((Integer)object[0]).intValue();
    		 nInfo.setkValue(k);
    		 // calculate coreDensity
    		 double coreDensity = 0.0D;
    		 if (Kcore != null) {
    			 coreDensity = calDensity(Kcore);
    			 nInfo.setCoreDensity(coreDensity);
    		 }
    		 
     
    		 // calculate score
    		 if(node.getNeighbours().size() > degreeCutoff){
    			 nInfo.setWeight(k * coreDensity);
    		//	 count ++;
    		 }else{
    			 nInfo.setWeight(0.0); 
    		 }
    	 }
     }
     
     List<Entry<Node, NodeInfo>> rankedList = new ArrayList(nodeToInfoMap.entrySet()); 
     Collections.sort(rankedList, new Comparator<Entry<Node, NodeInfo>>() {
		@Override
		public int compare(Entry<Node, NodeInfo> e1, Entry<Node, NodeInfo> e2) {
			// TODO Auto-generated method stub
			if (e1.getValue().getWeight() - e2.getValue().getWeight() > 0.0D){
			       return -1;
			}
			if (e1.getValue().getWeight() - e2.getValue().getWeight() < 0.0D) {
			       return 1;
			}
			return 0;		
		}  	 
	});
    
    
     for(Entry<Node, NodeInfo> en : rankedList){
    	 System.out.println(en.getKey().getNodeID()+" "+en.getValue().getWeight());
     }
     
     /**
      * Step 2 
      */
     
     
     
     System.out.println("Step 2");
     getComplexes(rankedList);
 

   }
   
 

   public void fluffComplex(List<Node> complex) {
	   HashSet<Node> candidateNodes = new HashSet<Node>();
	   NodeInfo nInfo;
	   
	   
	   for (Node startNode : complex){
		   for (Node node : startNode.getNeighbours()) {
			   nInfo = nodeToInfoMap.get(node);
			   if (!nInfo.isBeSeen() && !candidateNodes.contains(node)
					   && nInfo.getDensity() > fluffDensityCutoff){
				   candidateNodes.add(node);
				   nInfo.setBeSeen(true); //There is no such sentence in MCODE(Cytoscape) 
			   }			   
		   }
       }
	   
	   if(!candidateNodes.isEmpty()){
		   complex.addAll(candidateNodes);
	   }
   }
 
 
   public boolean getComplex(Node seed, List<Node> complex, int recursionIndex)
   {
	   NodeInfo sInfo = nodeToInfoMap.get(seed);
	   if(sInfo.isBeSeen())
		   return true;
	   sInfo.setBeSeen(true);
	   
	   if(recursionIndex > maxDepth){
		   return true;
	   }
	   for (Node node: seed.getNeighbours()) {
		   
		   if ((!nodeToInfoMap.get(node).isBeSeen()) && 
				   (nodeToInfoMap.get(node).getWeight() >= sInfo.getWeight() * (1.0D - this.nodeWeightCutoff))) {
			   if(!complex.contains(node)){
				   complex.add(node);
			   }
			   getComplex(node, complex, recursionIndex+1);
		   }
	   }    
	   
	   return true;
   }
 
   int count = 0;
   public void getComplexes(List<Entry<Node, NodeInfo>> rankedList)
   {
	   Node seed;
	   for (Entry<Node, NodeInfo> entry : rankedList) {
	    	seed = entry.getKey();
	       if (!entry.getValue().isBeSeen()) {
	         List complex = new LinkedList();
	         getComplex(seed, complex, 1);
	         if(complex.size() > 0){
	        	 complex.add(seed);
	             SubGraph complexGraph = getSubgraph(complex); 
	             SubGraph complex2core = getKCoreGraph(complexGraph, kCore); //default value of kCore is 2
	             /**
	              * @author TangYu
	              * @modify date: 2014年8月28日
	              * @description In origin paper, if both options are specified, fluff is run first, then haircut which is run first in MCODE(Plug-in).
	              *
	              */
	             if(complex2core != null){           	 
	            	if(isHaircut){
	             		complex.clear();
	             		complex.addAll(complex2core.getNodes());
	             	}
	            	if(isFluff){
	            		fluffComplex(complex);
	            	}           	
	            	 complexes.add(complex);            	 
	             }             
	         }       
	       }
	    }
   }
 
   public SubGraph getSubgraph(List<Node> nodes)
   {
     SubGraph sg = new SubGraph(); 
     sg.getNodes().addAll(nodes);
     sg.initial();
     return sg;
   }
 
   public Object[] getHighestKCore(SubGraph gpInputGraph)
   {
     int i = 1;
     SubGraph gpCurCore = null; SubGraph gpPrevCore = null;
 
     while ((gpCurCore = getKCoreGraph(gpInputGraph, i)) != null) {
       gpInputGraph = gpCurCore;
       gpPrevCore = gpCurCore;
       i++;
     }
 
     Integer k = new Integer(i - 1);
     Object[] returnArray = new Object[2];
     returnArray[0] = k;
     returnArray[1] = gpPrevCore;
 
     return returnArray;
   }
 
   public SubGraph getKCoreGraph(SubGraph sub, int key)
   {
     SubGraph sg = new SubGraph();
     
     while (true){
       int havedelete = 0;
       sg.getNodes().clear();
       sg.getEdges().clear();
       for (Node node : sub.getNodes()) {
 
         if (sub.getDegree(node.getNodeID()) >= key)
           sg.getNodes().add(node);
         else {
           havedelete++;
         }
       }
    
       if (sub.getNodes().size() == havedelete) {
         return null;
       }
       if (havedelete <= 0){ 
    	   break;
       }
       sub = getSubgraph(sg.getNodes());
      
     }
 
     if (sg.getNodes().size() != 0) {
       sg.initial();
       return sg;
     }
 
     return null;
    
   }
   
   public double calDensity(SubGraph neighborsGraph){
	   double nSize, eSize;	   
	   nSize = neighborsGraph.getNodes().size();
	   eSize = neighborsGraph.getEdges().size();
	   return 2 * eSize / (nSize * (nSize - 1));
   }

   public void setNodeWeightCutoff(double nodeWeightCutoff) {
	this.nodeWeightCutoff = nodeWeightCutoff;
   }

   public void setFluffDensityCutoff(double fluffDensityCutoff) {
	this.fluffDensityCutoff = fluffDensityCutoff;
   }
   public void setFluff(boolean isFluff) {
		this.isFluff = isFluff;
	}
   public void setHaircut(boolean isHaircut) {
		this.isHaircut = isHaircut;
	}

	public void setkCore(int kCore) {
		this.kCore = kCore;
	}

	public void setMaxDepth(int maxDepth) {
		this.maxDepth = maxDepth;
	}

	public void setDegreeCutoff(int degreeCutoff) {
		this.degreeCutoff = degreeCutoff;
	}


 

 }

