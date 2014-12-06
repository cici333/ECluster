package com.wuxuehong.plugin;

import com.wuxuehong.bean.Node;
import com.wuxuehong.interfaces.NewAlgorithm;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Vector;

import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.java.plugin.Plugin;
import org.omg.CORBA.SystemException;

public class Mcode extends Plugin
  implements NewAlgorithm
{
  protected void doStart()
    throws Exception
  {
  }

  protected void doStop()
    throws Exception
  {
  }
//
  public void drawCharts(String[] arg0, HashMap<String, Vector<Node>[]> arg1, Composite arg2, HashMap<String, RGB> arg3)
  {
  }

  public String getAlgorithmName()
  {
     return "Mcode";
  }

  public Vector<Node>[] getClusters(String[] params)
  {
     boolean isHaircut, isFluff;
     double nodeWeightCutoff, fluffDensityCutoff;
     int kCore, maxDepth, degreeCutoff;
    try
    {
    	nodeWeightCutoff = Double.parseDouble(params[0]);
    	isHaircut = Boolean.parseBoolean(params[1]);
    	isFluff = Boolean.parseBoolean(params[2]);
    	fluffDensityCutoff = Double.parseDouble(params[3]);
    	degreeCutoff = Integer.parseInt(params[4]);
    	kCore = Integer.parseInt(params[5]);
    	maxDepth =Integer.parseInt(params[6]);
    }
    catch (Exception e)
    {
     
       return null;
    }

     McodeMain mm = new McodeMain(nodeWeightCutoff, isHaircut, isFluff, fluffDensityCutoff, degreeCutoff, kCore, maxDepth);
     Vector[] v = mm.getClusters();
     return v;
  }

  public String getDescription()
  {
     return "Molecular Complex Detection";
  }

  public String getEvaluateNames()
  {
     return null;
  }

  public String[] getParaValues()
  {
     return new String[] { "0.2", "true", "true", "0.1", "1", "2", "100" };

  }

  public String[] getParams()
  {
     return new String[] { "node wight Cutoff (0.0 - 1.0)", "haircut", "fluff", "fluff Density Cutoff (0.0 - 1.0)", "degree Cutoff", "k-Core", "maxDepth" };

  }

  public int getStyle()
  {
     return 1;
  }

  public void variableInit()
  {
  }
}

