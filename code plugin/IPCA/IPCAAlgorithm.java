package com.wuxuehong.plugin;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;
import java.util.Vector;
import java.util.Map.Entry;

import com.wuxuehong.bean.Edge;
import com.wuxuehong.bean.Node;
import com.wuxuehong.interfaces.GraphInfo;

public class IPCAAlgorithm {
	 ArrayList<Node> sortedNodes;
	 ArrayList<Cluster> alcluster;
	 double tinThreshold, diameterThreshold;
	 
	 
	IPCAAlgorithm(double tinThreshold, double diameterThreshold){
		this.diameterThreshold = diameterThreshold;
		this.tinThreshold = tinThreshold;	
	}
	
	public Vector<Node>[] getResult(){
	
		weightNodesAndEages();
		selectSeeds();	
		Vector<Node>[] Clusters = new Vector[alcluster.size()];
		int i = 0;
		for(Cluster c : alcluster){
			Clusters[i] = new Vector<Node>(c.nodeSet);
			i++;
		}
		
		return Clusters;
		
		
	}
	/**
	 * Step 1
	 * */
	public void weightNodesAndEages(){
		HashMap<Node, Double> nodenMap = new HashMap<Node, Double>();
		double edgeWeight;
		Node sNode, tNode;
		
		for(Edge e : GraphInfo.edgelist){
			edgeWeight = 0;
			sNode = e.getNode1();
			tNode = e.getNode2();
			for(Node neighbor : sNode.getNeighbours()){
				if(tNode.getNeighbours().contains(neighbor)){				
					edgeWeight++;
				}
			}
			if(nodenMap.containsKey(sNode)){
				nodenMap.put(sNode, nodenMap.get(sNode)+edgeWeight);
			}else{
				nodenMap.put(sNode,edgeWeight);
			}
			if(nodenMap.containsKey(tNode)){
				nodenMap.put(tNode, nodenMap.get(tNode)+edgeWeight);
			}else{
				nodenMap.put(tNode,edgeWeight);
			}
		}
		
		ArrayList<Entry<Node,Double>> sortedNodeMap = new ArrayList<Entry<Node,Double>>(nodenMap.entrySet());
		Collections.sort(sortedNodeMap, new Comparator<Entry<Node,Double>>() {
			public int compare(Entry<Node, Double> o1, Entry<Node, Double> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});
		
		sortedNodes = new ArrayList<Node>();
		for(Entry<Node,Double> en : sortedNodeMap){
			sortedNodes.add(en.getKey());
		}
	}
	
	/**
	 * Step 2
	 */
	public void selectSeeds(){
		alcluster = new ArrayList<Cluster>();
		while (!sortedNodes.isEmpty()){		
			/*select the topest one to  be seed */     			
			Cluster newCluster = new Cluster(sortedNodes.remove(0));		
			extendingCluster(newCluster);
				
			/************remove all vertices in the cluster from the queue pq*******************/
			for(Node n : newCluster.nodeSet){
				sortedNodes.remove(n);
			}
							
			alcluster.add(newCluster);		
		}
		
	}
	
	public void extendingCluster(Cluster cluster){
		ArrayList<Entry<Node,Double>> neighborsMap = getNeighborsMap(cluster);
		if(neighborsMap.isEmpty()){
			return;
		}
		Entry<Node, Double> tempEn = neighborsMap.get(0);
		if(tempEn.getValue() >= tinThreshold * cluster.nodeSet.size()){ // condition 1
			if(spJudgement(cluster, tempEn.getKey())){// condition 2
				cluster.nodeSet.add(tempEn.getKey());
				extendingCluster(cluster);
			}else{
				return;
			}
		}else{
			return;
		}
		
	}
	
	public ArrayList<Entry<Node,Double>> getNeighborsMap(Cluster cluster){
		HashMap<Node, Double> neighborsMap = new HashMap<Node, Double>();
		
		//Getting the neighbours of cluster and calculate its value
		for(Node n : cluster.nodeSet){
			for(Node neighbour : n.getNeighbours()){
				if(!cluster.nodeSet.contains(neighbour)){
					if(neighborsMap.containsKey(neighbour)){
						neighborsMap.put(neighbour, neighborsMap.get(neighbour)+1);
					}else{
						neighborsMap.put(neighbour, 1.0D);
					}				
				}
			}
		}
			
		/*****sorted by value*****/
		ArrayList<Entry<Node,Double>> SotredNeighborsmap = new ArrayList(neighborsMap.entrySet());
		Collections.sort(SotredNeighborsmap, new Comparator<Entry<Node,Double>>() {
			public int compare(Entry<Node, Double> o1, Entry<Node, Double> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});
		
		return SotredNeighborsmap;		
	}
	
	public boolean spJudgement(Cluster cluster, Node vNode){
		HashSet<Node> tempMergeNodes = new HashSet<Node>();
		HashMap<Node, Integer> distances = new HashMap<Node, Integer>();
		HashMap<Node, Boolean> isInQ = new HashMap<Node, Boolean>();
		
		tempMergeNodes.addAll(cluster.nodeSet);
		tempMergeNodes.add(vNode);
	
		for(Node n : tempMergeNodes){
			
			distances.clear();
			isInQ.clear();
			for(Node node :  tempMergeNodes){
				distances.put(node, Integer.MAX_VALUE);
				isInQ.put(node, false);
			}
			
			Queue<Node> nQueue = new ArrayDeque<Node>();
			
			nQueue.add(n);
			isInQ.put(n, true);
			distances.put(n, 0);
			
			while(!nQueue.isEmpty()){
				Node currentN = nQueue.remove();
				isInQ.put(currentN, false);
				for(Node neighbor : currentN.getNeighbours()){
					if( tempMergeNodes.contains(neighbor)){
						if(distances.get(neighbor) > distances.get(currentN)+1){
							distances.put(neighbor,  distances.get(currentN)+1);
							if(isInQ.get(neighbor) == false){
								nQueue.add(neighbor);
								isInQ.put(neighbor, true);
							}
						}
					}
				}
			}
			
			for(Integer d : distances.values()){
				if(d > diameterThreshold){
					return false;
				}
			}
		}

		
		return true;		
	}

}
