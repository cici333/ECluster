 package com.wuxuehong.plugin;
 
 import com.wuxuehong.bean.Node;
 import java.util.Collection;
 import java.util.List;
 import java.util.Vector;
 
 public class McodeMain
 {
   private McodeAlgorithm ma;
 
   public McodeMain(double nodeWeightCutoff, boolean isHaircut, boolean isFluff, double fluffDensityCutoff, int degreeCutoff, int kCore, int maxDepth)
   {
     this.ma = new McodeAlgorithm(this);
     this.ma.setNodeWeightCutoff(nodeWeightCutoff);
     this.ma.setHaircut(isHaircut);
     this.ma.setFluff(isFluff);
     this.ma.setFluffDensityCutoff(fluffDensityCutoff);
     this.ma.setDegreeCutoff(degreeCutoff);
     this.ma.setkCore(kCore);
     this.ma.setMaxDepth(maxDepth);
   }
 
   public Vector<Node>[] getClusters() {
     this.ma.vertex_weighting();
     List complexes = this.ma.complexes;
     Vector[] v = new Vector[complexes.size()];
     for (int i = 0; i < complexes.size(); i++) {
       v[i] = new Vector();
       v[i].addAll((Collection)complexes.get(i));
     }
     return v;
   }
   
   

 }

