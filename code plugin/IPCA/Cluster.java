package com.wuxuehong.plugin;

import java.util.HashSet;
import java.util.TreeMap;

import com.wuxuehong.bean.Node;

public class Cluster {
	HashSet<Node> nodeSet;
	Cluster(Node n){
		nodeSet = new HashSet<Node>();
		nodeSet.add(n);
	}
}
