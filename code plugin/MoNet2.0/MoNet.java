 package com.wuxuehong.plugin;
 
 import com.wuxuehong.bean.Node;
 import com.wuxuehong.interfaces.NewAlgorithm;
 import java.util.HashMap;
 import java.util.Vector;
 import org.eclipse.swt.graphics.RGB;
 import org.eclipse.swt.widgets.Composite;
 import org.java.plugin.Plugin;
 
 public class MoNet extends Plugin
   implements NewAlgorithm
 {
   public Vector BuildCharts(Vector[] v)
   {
     return null;
   }
 
   public String getAlgorithmName()
   {
     return "MoNet";
   }
 
   public Vector[] getClusters(String[] para)
   {
     MoNetAlgorithm test1 = new MoNetAlgorithm();
     return test1.getResult();
   }
 
   public String getDescription()
   {
     return "A cluster algorithm named Monet.";
   }
 
   public String getEvaluateNames()
   {
     return null;
   }
 
   public String[] getParaValues()
   {
     return null;
   }
 
   public String[] getParams()
   {
     return null;
   }
 
   protected void doStart()
     throws Exception
   {
   }
 
   protected void doStop()
     throws Exception
   {
   }
 
   public void drawCharts(String[] algorithm, HashMap<String, Vector<Node>[]> resultList, Composite composite, HashMap<String, RGB> colorlist)
   {
   }
 
   public int getStyle()
   {
     return 1;
   }
 
   public void variableInit()
   {
   }
 }
