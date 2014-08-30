package hf_measure;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import jxl.Workbook;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Table;



public class HfmeasureTable {
	private Table table;
	private HashMap<String,List<ClusterVo>> results;
	private boolean hftf = false;
	private boolean hftb = false;
	private boolean hff = false;
	private String annotationName;
	String measures = "";
	private boolean a = true;
	
	

	public HfmeasureTable(boolean hftf, boolean hftb , boolean hff, HashMap<String,List<ClusterVo>> results, String annotationName){
		this.hff = hff;
		this.hftb = hftb;
		this.hftf = hftf;
		this.results = results;
		this.annotationName = annotationName;
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		Shell shell = new Shell();
		shell.setSize(626, 438);
		shell.setText("SWT Application");
		shell.setLayout(new GridLayout(1, true));
		
		Composite composite = new Composite(shell, SWT.NONE);
		Composite group = new Composite(shell, SWT.NONE);
		
		
		group.setLayout(new FillLayout(SWT.HORIZONTAL));
		group.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		final TableViewer tv = new TableViewer(group, SWT.FULL_SELECTION);

		table = tv.getTable();
		
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		tv.setContentProvider(new HfmeasureContentProvider());
		tv.setLabelProvider(new HfmeasureLabelProvider(hftb, hftf));
		
		TableColumn idcol = new TableColumn(table, SWT.CENTER);
		idcol.setText("Cluster");
		idcol.setWidth(100);
		idcol.setMoveable(true);
		
		idcol.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				tv.setSorter(a?HfmeasureSorter.ID_ASC:HfmeasureSorter.ID_DESC);
				a = !a;	
			}
		});
		
		TableColumn sizecol = new TableColumn(table, SWT.CENTER);
		sizecol.setText("Size");
		sizecol.setWidth(100);
		sizecol.setMoveable(true);
		
		sizecol.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				tv.setSorter(a?HfmeasureSorter.SIZE_ASC:HfmeasureSorter.SIZE_DESC);
				a = !a;
			}
		});
		
		
		if(hftb){
			TableColumn col = new TableColumn(table, SWT.CENTER);
			col.setText(ClusterVo.tb);
			col.setWidth(150);
			col.setMoveable(true);
			
			col.addSelectionListener(new SelectionAdapter(){
				public void widgetSelected(SelectionEvent e) {
					tv.setSorter(a?HfmeasureSorter.HF_TB_ASC:HfmeasureSorter.HF_TB_DESC);
					a = !a;
				}
			});
			
			
			measures += ClusterVo.tb+" ";
			
		}
		if(hftf){
			TableColumn col = new TableColumn(table, SWT.CENTER);
			col.setText(ClusterVo.tf);
			col.setWidth(150);
			col.setMoveable(true);
			
			col.addSelectionListener(new SelectionAdapter(){
				public void widgetSelected(SelectionEvent e) {
					tv.setSorter(a?HfmeasureSorter.HF_TF_ASC:HfmeasureSorter.HF_TF_DESC);
					a = !a;
				}
			});
			
			
			measures += ClusterVo.tf+" ";
		}
		
		if(hftb || hftf){
			
			TableColumn col1 = new TableColumn(table, SWT.CENTER);
			col1.setText("Function hf with max HFmeasure");
			col1.setWidth(150);
			col1.setMoveable(true);
			
			col1.addSelectionListener(new SelectionAdapter(){
				public void widgetSelected(SelectionEvent e) {
					a = !a;
					tv.setSorter(a?HfmeasureSorter.HF_FUN_ASC:HfmeasureSorter.HF_FUN_DESC);
				}
			});
			
			
			TableColumn col2 = new TableColumn(table, SWT.CENTER);
			col2.setText("the number of proteins contain function hf in this complex");
			col2.setWidth(150);
			col2.setMoveable(true);
			
			col2.addSelectionListener(new SelectionAdapter(){
				public void widgetSelected(SelectionEvent e) {
					a = !a;
					tv.setSorter(a?HfmeasureSorter.HF_FUN_NUM_ASC:HfmeasureSorter.HF_FUN_NUM_DESC);
				}
			});
						
			TableColumn col3 = new TableColumn(table, SWT.CENTER);
			col3.setText("the number of proteins contain function hf in this network");
			col3.setWidth(150);
			col3.setMoveable(true);
			
			col3.addSelectionListener(new SelectionAdapter(){
				public void widgetSelected(SelectionEvent e) {
					a = !a;
					tv.setSorter(a?HfmeasureSorter.HF_BACKG_ASC:HfmeasureSorter.HF_BACKG_DESC);
				}
			});
			
		}
		
		if(hff){
			TableColumn col1 = new TableColumn(table, SWT.CENTER);
			col1.setText(ClusterVo.ff);
			col1.setWidth(150);
			col1.setMoveable(true);
			
			col1.addSelectionListener(new SelectionAdapter(){
				public void widgetSelected(SelectionEvent e) {
					a = !a;
					tv.setSorter(a?HfmeasureSorter.FMEASURE_ASC:HfmeasureSorter.FMEASURE_DESC);
				}
			});
			
			
			TableColumn col2 = new TableColumn(table, SWT.CENTER);
			col2.setText("Function f with max Fmeasure");
			col2.setWidth(150);
			col2.setMoveable(true);
			
			col2.addSelectionListener(new SelectionAdapter(){
				public void widgetSelected(SelectionEvent e) {
					a = !a;
					tv.setSorter(a?HfmeasureSorter.F_FUN_ASC:HfmeasureSorter.F_FUN_DESC);
				}
			});
			
			
			TableColumn col3 = new TableColumn(table, SWT.CENTER);
			col3.setText("the number of proteins contain function f in this complex");
			col3.setWidth(150);
			col3.setMoveable(true);
			
			col3.addSelectionListener(new SelectionAdapter(){
				public void widgetSelected(SelectionEvent e) {
					a = !a;
					tv.setSorter(a?HfmeasureSorter.F_FUN_NUM_ASC:HfmeasureSorter.F_FUN_DESC);
				}
			});
			
			
			TableColumn col4 = new TableColumn(table, SWT.CENTER);
			col4.setText("the number of proteins contain function f in this network");
			col4.setWidth(150);
			col4.setMoveable(true);
			
			col4.addSelectionListener(new SelectionAdapter(){
				public void widgetSelected(SelectionEvent e) {
					a = !a;
					tv.setSorter(a?HfmeasureSorter.F_BACKG_ASC:HfmeasureSorter.F_BACKG_DESC);
				}
			});
			
			
			measures += ClusterVo.ff;
		}
		
		
			
				
		composite.setLayout(new RowLayout(SWT.HORIZONTAL));
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		final Combo combo = new Combo(composite, SWT.NONE);
		if(results != null){
			for(String name : results.keySet()){
				combo.add(name);
				combo.setData(name, results.get(name));
			}
		
		combo.addModifyListener(new ModifyListener() {			
			@Override
			public void modifyText(ModifyEvent e) {
				// TODO Auto-generated method stub
				String text = combo.getText();
				List<ClusterVo> clusters = (List<ClusterVo>) combo.getData(text);
				tv.setInput(clusters);
				tv.refresh();
			}
		});
			combo.select(0);
		}
		Button btnExport = new Button(composite, SWT.NONE);
		btnExport.setText("Export");
		btnExport.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				saveExcelTable(table,"Gene Annotation:"+annotationName+"   "+"Algorithm："+combo.getText()+"   "+"Evaluation: "+measures);
			}
		});
		
	
		shell.open();
		shell.layout();
	}
	
	/**
	 * 保存table信息到excel文件
	 * @param table
	 * @param para
	 */

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
	
}
