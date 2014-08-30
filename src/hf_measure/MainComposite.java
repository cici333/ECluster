package hf_measure;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;




import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.experimental.chart.swt.ChartComposite;

import clustere.dialogs.ProgressBarDialog;

import com.wuxuehong.bean.Node;

public class MainComposite extends Composite {
	
	private String description = "hF-measure is a measurement using hierarchical similarity, "
			+ "it can discriminate between different types of errors which cannot be distinguished by F-measure. "
			+ "hF-measure(Tf) is a topology-free measurement and hF-measure(Tb) " +
			"is a topology-based measurement.";

	private String[] algorithm;
	private HashMap<String, Vector<Node>[]> resultList;
	private HashMap<String, RGB> colorlist;
	private Set<String> measures;
	
	private Button button5,button6,button7;
	private String bp = "GO:0008150";
	private String mf = "GO:0003674";
	private String cc = "GO:0005575";
	

	
	Composite parentC;
	private  SashForm sashForm;
	private static Button rback;
	private String annotationName;
	
	private Button button4,buttons; //graph / table
	
	private Button button1,button2,button3; //tf tb f
	
	private HashMap<String,List<ClusterVo>> results;
	
	private String tbinfo = "Hf-measure(Tb) is a topology-based measurement";
	private String tfinfo = "Hf-measure(Tf) is a topology-free measurement.";
	private String ffinfo = "F-measure is A measure that combines Precision and Recall is the harmonic mean of precision and recall, the traditional F-measure or balanced F-score.\nf-measure=2*precision*recall/(precision+recall)\n";
	private String info;
	
	
	
	public void setData(String[] algorithm,
			HashMap<String, Vector<Node>[]> resultList,
			HashMap<String, RGB> colorlist, HashMap<String,List<ClusterVo>> results){
		this.algorithm = algorithm;
		this.resultList = resultList;
		this.colorlist = colorlist;
		this.results = results;
		
		//new NewThread(parentC,algorithm,resultList,colorlist);
		
		try {
			calculate();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	class NewThread implements Runnable{
		Composite comp;
		HashMap<String, RGB> colorlist;
		HashMap<String, Vector<Node>[]> resultList;
		String[] algorithm;
		ProgressBarDialog pbd;
		Thread t;
		ProteinNetwork pn;
		boolean hftf, hftb, hff, graph, table;
		DAGanalysis dag;
		
		NewThread(Composite comp,String[] algorithm,HashMap<String, Vector<Node>[]> resultList,HashMap<String, RGB> colorlist, ProteinNetwork pn, boolean hftf, boolean  hftb, boolean  hff, DAGanalysis dag, boolean  graph, boolean  table){
			this.comp = comp;
			this.colorlist = colorlist;
			this.resultList = resultList;
			this.algorithm = algorithm;
			this.pn = pn;
			this.hff = hff;
			this.hftf = hftf;
			this.hftb = hftb;
			this.dag = dag;
			this.graph = graph;
			this.table = table;
			t = new Thread(this);
			t.start();
		}
		public void run() {
			comp.getDisplay().asyncExec(new Runnable(){
				public void run() {
					pbd = new ProgressBarDialog(comp.getShell(), SWT.NONE,t);
					pbd.getLabel().setText("Calculating the charts.......");
				}
			});
			try {
			for(String algo : algorithm){
				Vector<Node>[] clusters = resultList.get(algo);
				
				if(!results.containsKey(algo)){
					pn.setClusters(new ArrayList<ClusterVo>());
					pn.readClusters(clusters);
					System.out.println("***************4********************");
					if(hftb){
						pn.calculateTopological(dag);				
					}
					if(hftf){
						pn.calculateUntopology(dag);					
					}
					if(hff && !hftf && !hftb){													
						pn.calculateUntopology(dag);
							
					}
					
					results.put(algo, pn.clusters);
				}
				else{
					
					System.out.println("***************5********************");
					if(hftb && results.get(algo).get(0).isNull_Tb()){
						pn.setClusters(results.get(algo));
						pn.calculateTopological(dag);
						System.out.println("***************6********************");
						results.put(algo, pn.clusters);
						
					}
					if(hftf && results.get(algo).get(0).isNull_Tf()){
						pn.setClusters(results.get(algo));					
						pn.calculateUntopology(dag);
						System.out.println("***************7********************");
						results.put(algo, pn.clusters);
						
					}
					if(hff && !hftf && !hftb && results.get(algo).get(0).isNull_Ff()){
						pn.setClusters(results.get(algo));				
						pn.calculateUntopology(dag);
						System.out.println("***************8********************");	
						results.put(algo, pn.clusters);
					}
					
					
				}				
			}
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			comp.getDisplay().asyncExec(new Runnable(){
				public void run() {
					/*try {
						calculate();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
					if(graph){
						createGraph(algorithm, results, parentC, colorlist, info);
					}else if(table){
						HfmeasureTable hft = new HfmeasureTable(hftf, hftb, hff, results, annotationName);
						hft.open();
					}
					pbd.getLabel().setText("complete!");
					pbd.dispose();
				}
			 });
		}
	}
	public void calculate() throws IOException{
		
		boolean hftf = false;
		boolean hftb = false;
		boolean hff = false;
		boolean graph = false, table = false;
		measures = new HashSet<String>();
		info = "";
		if(button2.getSelection()){
			hftb = true;
			measures.add(ClusterVo.tb);
			info += tbinfo+"\n";
			System.out.println("***************TB********************");
		}
		if(button1.getSelection()){
			hftf = true;
			measures.add(ClusterVo.tf);
			info += tfinfo+"\n";
			System.out.println("***************TF********************");

		}
		if(button3.getSelection()){
			hff = true;
			measures.add(ClusterVo.ff);
			info += ffinfo;
			System.out.println("***************FF********************");
		}
		if(!hftf && !hftb && !hff){
			MessageDialog.openWarning(new Shell(), "warning", "please choose one evaluation method!");
			return ;
		}
			
		String annotation, pcf;
		
		ProteinNetwork pn = new ProteinNetwork();
		pn.readProteins();
		
		DAGanalysis dag = new DAGanalysis();
		String top = bp;
		if(button5.getSelection()){
			top = bp;
			annotation = "ComplexFunction/GO_Process_annotation.txt";
			pcf = "ComplexFunction/GO_Process.txt";
			annotationName = "GO Process";
		}
		else if(button6.getSelection()){
			top = mf;
			annotation = "ComplexFunction/GO_Function_annotation.txt";
			pcf = "ComplexFunction/GO_Function.txt";
			annotationName = "GO Function";
		}
		else {
			top = cc;
			annotation = "ComplexFunction/GO_Component_annotation.txt";
			pcf = "ComplexFunction/GO_Component.txt";
			annotationName = "GO Component";
		}
		pn.readGeneOntology(annotation);
		dag.buildDAGwithMF(pcf);
		dag.rankDAG(top);
		
		if(button4.getSelection()){
			graph = true;
		}else if(buttons.getSelection()){
			table = true;
		}
		
		new NewThread(parentC,algorithm,resultList,colorlist, pn, hftf, hftb, hff, dag, graph, table);
	/*	for(String algo : algorithm){
			Vector<Node>[] clusters = resultList.get(algo);
			
			if(!results.containsKey(algo)){
				pn.setClusters(new ArrayList<ClusterVo>());
				pn.readClusters(clusters);
				System.out.println("***************4********************");
				if(hftb){
					pn.calculateTopological(dag);				
				}
				if(hftf){
					pn.calculateUntopology(dag);					
				}
				if(hff && !hftf && !hftb){													
					pn.calculateUntopology(dag);
						
				}
				
				results.put(algo, pn.clusters);
			}
			else{
				
				System.out.println("***************5********************");
				if(hftb && results.get(algo).get(0).isNull_Tb()){
					pn.setClusters(results.get(algo));
					pn.calculateTopological(dag);
					System.out.println("***************6********************");
					results.put(algo, pn.clusters);
					
				}
				if(hftf && results.get(algo).get(0).isNull_Tf()){
					pn.setClusters(results.get(algo));					
					pn.calculateUntopology(dag);
					System.out.println("***************7********************");
					results.put(algo, pn.clusters);
					
				}
				if(hff && !hftf && !hftb && results.get(algo).get(0).isNull_Ff()){
					pn.setClusters(results.get(algo));				
					pn.calculateUntopology(dag);
					System.out.println("***************8********************");	
					results.put(algo, pn.clusters);
				}
				
				
			}
			
		}*/
		
		
	}
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public MainComposite(Composite parent, int style) {
		
		
		
		super(parent, style);
		setLayout(new GridLayout(1, true));
		this.parentC = parent;
		if (sashForm != null)
			sashForm.dispose();
		parentC.setLayout(new FillLayout());
		
		//evaluation description
		Group group = new Group(this, SWT.NONE);
		group.setLayoutData(new GridData(GridData.FILL_BOTH));
		group.setText("Evaluation Description");
		group.setLayout(new FillLayout(SWT.HORIZONTAL));
//		group.setLayout(new FillLayout());
		Text text = new Text(group,SWT.WRAP|SWT.V_SCROLL|SWT.H_SCROLL);
		text.append(description);
		
		//gene annotation
		Group group3 = new Group(this, SWT.NONE);
		group3.setText("Go annotation");
		group3.setLayoutData(new GridData(GridData.FILL_BOTH));
		group3.setLayout(new GridLayout(3,true));
		button5 = new Button(group3,SWT.RADIO);
		button5.setText("Process");
		button5.setSelection(true);
		button6 = new Button(group3,SWT.RADIO);
		button6.setText("Function");
		button7 = new Button(group3,SWT.RADIO);
		button7.setText("Componment");
		

		Composite com = new Composite(this, SWT.NONE);
		com.setLayoutData(new GridData(GridData.FILL_BOTH));
		com.setLayout(new GridLayout(2,true));
		
		
		Group group1 = new Group(com,SWT.NONE);
		group1.setLayoutData(new GridData(GridData.FILL_BOTH));
		group1.setLayout(new GridLayout(1,true));
		group1.setText("Choose Evaluation");
		button2 = new Button(group1,SWT.CHECK);
		button2.setText(ClusterVo.tb);
		button2.setSelection(true);
		button1 = new Button(group1,SWT.CHECK);
		button1.setText(ClusterVo.tf);
		button3 = new Button(group1,SWT.CHECK);
		button3.setText(ClusterVo.ff);
		
		Group group2 = new Group(com,SWT.NONE);
		group2.setText("Show type");
		group2.setLayoutData(new GridData(GridData.FILL_BOTH));
		group2.setLayout(new GridLayout(1, true));
		button4 = new Button(group2,SWT.RADIO);
		button4.setText("graph");
		button4.setSelection(true);
		buttons = new Button(group2,SWT.RADIO);
		buttons.setText("table");
		
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	

	public void createGraph(String[] algorithm, final HashMap<String, List<ClusterVo>> results, Composite composite, HashMap<String, RGB> colorlist,String showInfo){
		if(this!=null)
			this.dispose();
		if (sashForm != null)
			sashForm.dispose();
		composite.setLayout(new FillLayout());
		sashForm = new SashForm(composite, SWT.VERTICAL);
		
		SashForm comp1 = new SashForm(sashForm, SWT.VERTICAL);
		Composite desComp = new Composite(comp1,SWT.NONE);
		desComp.setLayout(new GridLayout(5,true));
		GridData gdd = new GridData(GridData.FILL_BOTH);
		gdd.horizontalSpan = 4;
		gdd.verticalSpan = 2;
		Group group1 = new Group(desComp,SWT.NONE);
		group1.setText("Description");
		group1.setLayoutData(gdd);
		group1.setLayout(new FillLayout());
		Text text = new Text(group1,SWT.WRAP|SWT.V_SCROLL);
		text.setText(showInfo);
		Group group2 = new Group(desComp,SWT.NONE);
		//group2.setText("Navigation");
		group2.setLayout(new FillLayout());
		rback = new Button(group2, SWT.NONE);
		rback.setText("Return");
		
		group2.setLayoutData(new GridData(GridData.FILL_BOTH));
		//combo.addSelectionListener(new Controllor(composite,algorithm,colorlist));
		rback.addSelectionListener(new SelectionAdapter() {
			 public void widgetSelected(SelectionEvent e) {   
	             Hfmeasure.turnBack();
	            }   
		});
		Group group3 = new Group(desComp,SWT.NONE);
		group3.setLayoutData(new GridData(GridData.FILL_BOTH));
		group3.setText("Gene Annotation");
		group3.setLayout(new FillLayout());
		Label anno = new Label(group3,SWT.NONE);
		anno.setText(annotationName);
		anno.setAlignment(SWT.CENTER);
		
		Composite com = new Composite(comp1,SWT.BORDER);
		com.setLayout(new GridLayout(2,true));
		GridData gd = new GridData(GridData.FILL_BOTH);
		if(measures.size()<3){
			comp1.setWeights(new int[]{2,3});
		}else{
			comp1.setWeights(new int[]{1,5});
		}
			
		/**********************画曲线图*************************/
		int i = 1;
		for(String measure : measures){
			
			ChartComposite form1 = createLineChart(algorithm, measure, results, com, colorlist, i);
			form1.setLayoutData(gd);
			i++;
		}
		
		/**********************画柱状图************************/
	
		composite.layout();
				
	}
	
	/**
	 * 曲线图
	 * @param algorithm
	 * @param para
	 * @param resultList
	 * @param composite
	 * @param colorlist
	 * @return
	 */
	
	public  ChartComposite createLineChart(String[] algorithm, String para,
			final HashMap<String, List<ClusterVo>> results, Composite composite,
			HashMap<String, RGB> colorlist, int index){
		 XYSeries series = null;
		 ClusterVo cluster = null;
		 List<ClusterVo> clusterlist = null;
		 cluster.comparePara = para;
		XYSeriesCollection dataset1 = new XYSeriesCollection();
		for (int i = 0; i < algorithm.length; i++) {
			series = new XYSeries(algorithm[i]);
			 clusterlist = results.get(algorithm[i]);
			 Collections.sort(clusterlist);
			for (int j = 0; j < clusterlist.size(); j++) {
				cluster = (ClusterVo)clusterlist.get(j);
				series.add(j+1, cluster.getMeasure(para));			
			}
			dataset1.addSeries(series);
		}
		JFreeChart chart1 = ChartFactory.createXYLineChart(null, "Fig "+index,
				para+" distribution", dataset1, PlotOrientation.VERTICAL,
				true, true, true);
		XYPlot cate1 = (XYPlot) chart1.getPlot();
		XYLineAndShapeRenderer lineRender =  (XYLineAndShapeRenderer) cate1.getRenderer();
		 for(int i=0;i<algorithm.length;i++){
			 RGB rgb = colorlist.get(algorithm[i]);
			 lineRender.setSeriesPaint(i, new java.awt.Color(rgb.red,rgb.green,rgb.blue));
			 }
		 ChartComposite frame1 = new ChartComposite(composite, SWT.NONE, chart1);
		 return frame1;
	}
	

	/**
	 * 保存table信息到excel文件
	 * @param table
	 * @param para
	 */
	/*
	public void saveExcelTable(Table table,String para){
		FileDialog fd = new FileDialog(table.getShell(),SWT.SAVE);
		fd.setFilterExtensions(new String[]{"*.xls"});
		fd.setFilterNames(new String[]{"Excel.xls"});
		String filename = fd.open();
		if(filename==null||filename.equals(""))return;
		TableItem[] ti = table.getItems();
		try{
		  WritableWorkbook book=
			  Workbook.createWorkbook(new File(filename));
			  WritableSheet sheet=book.createSheet("第一页",0);
             
			  WritableFont font3=new WritableFont(WritableFont.createFont("楷体 _GB2312"),17,WritableFont.NO_BOLD );
			  WritableCellFormat format1=new WritableCellFormat(font3); 
			  sheet.mergeCells(0, 0, 15, 0);
			  jxl.write.Label label1 = new jxl.write.Label(0,0,para);
			  label1.setCellFormat(format1);
			  sheet.addCell(label1);
			  TableColumn[] tc = table.getColumns();
			  for(int i=0;i<tc.length;i++){
				  sheet.addCell(new jxl.write.Label(i,1,tc[i].getText()));
			  }
			  for(int i=0;i<ti.length;i++){
				  for(int j=0;j<tc.length;j++){
					  sheet.addCell(new jxl.write.Label(j,i+2,ti[i].getText(j)));
				  }
			  }
//			  jxl.write.Number number = new jxl.write.Number(1,0,789.123);
//			  sheet.addCell(number);
			  book.write();
			  book.close();
			  }catch(Exception e){
			         MessageDialog.openError(table.getShell(), "Error", e.toString());
			         return;
			  }
			 MessageDialog.openInformation(table.getShell(), "Success", "File Saved Successfully");
	}
	
	*/

}
