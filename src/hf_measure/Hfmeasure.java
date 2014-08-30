package hf_measure;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.java.plugin.Plugin;

import com.wuxuehong.bean.Node;
import com.wuxuehong.interfaces.NewAlgorithm;

public class Hfmeasure extends Plugin implements NewAlgorithm {
	
	private HashMap<String,List<ClusterVo>> results = new HashMap<String,List<ClusterVo>>();
	public static String[] algorithm;
	public static Composite composite;
	public static HashMap<String, RGB> colorlist;
	public static HashMap<String, Vector<Node>[]> resultList;
	public static Hfmeasure hf;
	
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Two type of measurements to evaluate identified clusters";
	}

	@Override
	public int getStyle() {
		// TODO Auto-generated method stub
		return Evaluation;
	}
	
	@Override
	protected void doStart() throws Exception {
		// TODO Auto-generated method stub
	}
	@Override
	protected void doStop() throws Exception {
		// TODO Auto-generated method stub
	}


	@Override
	public String getAlgorithmName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<Node>[] getClusters(String[] para) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getParams() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getParaValues() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getEvaluateNames() {
		// TODO Auto-generated method stub
		return "hF-measure";
	}
	
	private MainComposite mc;

	@Override
	public void drawCharts(String[] algorithm,
			HashMap<String, Vector<Node>[]> resultList, Composite composite,
			HashMap<String, RGB> colorlist) {
		
		this.algorithm = algorithm;
		this.resultList = resultList;
		this.composite = composite;
		this.colorlist = colorlist;
		hf = this;
		
		//System.out.println("It is Hfmeasure...wakaka....****");
		// TODO Auto-generated method stub
		composite.setLayout(new FillLayout());
		
		if(mc == null ){
			mc = new MainComposite(composite, SWT.NONE);
		//	System.out.println("***************1********************");
		}
		if(mc.isDisposed()){
			if(composite != null){
				for(int i = 0, l = composite.getChildren().length; i <l; i++){
					composite.getChildren()[i].dispose();
				}
			}
			mc = new MainComposite(composite, SWT.NONE);
			composite.layout();
		//	System.out.println("***************2********************");
			return;
		}
		composite.layout();
		
		if(algorithm.length != 0){
		//	System.out.println("***************3********************");
			mc.setData(algorithm, resultList, colorlist, results);			
			//originalgorithm = algorithm.clone();
		}
		
	}

	@Override
	public void variableInit() {
		// TODO Auto-generated method stub
		
	}

	public static void turnBack(){
		hf.drawCharts(algorithm, resultList, composite, colorlist);
	}
}
