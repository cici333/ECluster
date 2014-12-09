package com.wuxuehong.plugin;

import java.util.HashMap;
import java.util.Vector;

import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.java.plugin.Plugin;

import com.wuxuehong.bean.Node;
import com.wuxuehong.interfaces.NewAlgorithm;

public class IPCA extends Plugin implements NewAlgorithm{

	@Override
	public void drawCharts(String[] arg0, HashMap<String, Vector<Node>[]> arg1,
			Composite arg2, HashMap<String, RGB> arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getAlgorithmName() {
		// TODO Auto-generated method stub
		return "IPCA";
	}

	@Override
	public Vector<Node>[] getClusters(String[] args) {
		double tinThreshold, diameterThreshold;
		try{
			tinThreshold = Double.parseDouble(args[0]);
			diameterThreshold = Double.parseDouble(args[1]);
		}catch (Exception e){
			return null;
		}
		
		IPCAAlgorithm ipcaalgorithm = new IPCAAlgorithm(tinThreshold, diameterThreshold);
		return ipcaalgorithm.getResult();
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "A distance based clustering algorithm ";
	}

	@Override
	public String getEvaluateNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getParaValues() {
		// TODO Auto-generated method stub
		return new String[] { "0.6", "2" };
	}

	@Override
	public String[] getParams() {
		// TODO Auto-generated method stub
		return new String[] { "Tin Threshold", "Diameter Threshold" };
	}

	@Override
	public int getStyle() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public void variableInit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doStart() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doStop() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
