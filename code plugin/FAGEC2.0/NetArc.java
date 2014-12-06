 package com.wuxuehong.plugin;
 
 class NetArc
 {
   private int firstNode;
   private int secondNode;
   private int rootIndex;
 
   public NetArc(int index1, int index2)
   {
     setFirstNode(index1);
     setSecondNode(index2);
   }
 
   public int getFirstNode() {
     return this.firstNode;
   }
   public void setFirstNode(int firstNode) {
     this.firstNode = firstNode;
   }
   public int getSecondNode() {
     return this.secondNode;
   }
   public void setSecondNode(int secondode) {
     this.secondNode = secondode;
   }
   public int getRootGraphIndex() {
     return this.rootIndex;
   }
   public void setRootGraphIndex(int rootIndex) {
     this.rootIndex = rootIndex;
   }
 }
