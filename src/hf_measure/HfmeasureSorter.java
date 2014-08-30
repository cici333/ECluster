package hf_measure;


import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;


public class HfmeasureSorter extends ViewerSorter {

    private static final int ID = 1;
    private static final int SIZE = 2;
    private static final int HF_TB = 3;
    private static final int HF_TF = 4;
    private static final int HF_FUN = 5;
    private static final int HF_FUN_NUM = 6;
    private static final int HF_BACKG = 7;
    private static final int FMEASURE = 8;
    private static final int F_FUN = 9;
    private static final int F_FUN_NUM = 10;
    private static final int F_BACKG = 11;
    
    
    public static final HfmeasureSorter ID_ASC = new HfmeasureSorter(ID);
    public static final HfmeasureSorter ID_DESC = new HfmeasureSorter(-ID);
    public static final HfmeasureSorter SIZE_ASC = new HfmeasureSorter(SIZE);
    public static final HfmeasureSorter SIZE_DESC = new HfmeasureSorter(-SIZE);
    public static final HfmeasureSorter HF_TB_ASC = new HfmeasureSorter(HF_TB);
    public static final HfmeasureSorter HF_TB_DESC = new HfmeasureSorter(-HF_TB);
    public static final HfmeasureSorter HF_TF_ASC = new HfmeasureSorter(HF_TF);
    public static final HfmeasureSorter HF_TF_DESC = new HfmeasureSorter(-HF_TF);
    public static final HfmeasureSorter HF_FUN_ASC = new HfmeasureSorter(HF_FUN);
    public static final HfmeasureSorter HF_FUN_DESC = new HfmeasureSorter(-HF_FUN);
    public static final HfmeasureSorter HF_FUN_NUM_ASC = new HfmeasureSorter(HF_FUN_NUM);
    public static final HfmeasureSorter HF_FUN_NUM_DESC = new HfmeasureSorter(-HF_FUN_NUM);
    public static final HfmeasureSorter HF_BACKG_ASC = new HfmeasureSorter(HF_BACKG);
    public static final HfmeasureSorter HF_BACKG_DESC = new HfmeasureSorter(-HF_BACKG);
    public static final HfmeasureSorter FMEASURE_ASC = new HfmeasureSorter(FMEASURE);
    public static final HfmeasureSorter FMEASURE_DESC = new HfmeasureSorter(-FMEASURE);
    public static final HfmeasureSorter F_FUN_ASC = new HfmeasureSorter(F_FUN);
    public static final HfmeasureSorter F_FUN_DESC = new HfmeasureSorter(-F_FUN);
    public static final HfmeasureSorter F_FUN_NUM_ASC = new HfmeasureSorter(F_FUN_NUM);
    public static final HfmeasureSorter F_FUN_NUM_DESC = new HfmeasureSorter(-F_FUN_NUM);
    public static final HfmeasureSorter F_BACKG_ASC = new HfmeasureSorter(F_BACKG);
    public static final HfmeasureSorter F_BACKG_DESC = new HfmeasureSorter(-F_BACKG);
	
	private int column;

	public HfmeasureSorter(int column){
		this.column = column;
	}
	
	public int compare(Viewer viewer, Object e1, Object e2) {
		ClusterVo c1 = (ClusterVo)e1;
		ClusterVo c2 = (ClusterVo)e2;
		
		switch(column){
		case 1:{
			Integer str1 = Integer.parseInt(c1.getClusterid());   //cluster id
			Integer str2 = Integer.parseInt(c2.getClusterid());
			return str1.compareTo(str2);
	     	}
		case -1:{
			Integer str1 = Integer.parseInt(c1.getClusterid());
			Integer str2 = Integer.parseInt(c2.getClusterid());
			return str2.compareTo(str1);
	     	}
		case 2:{
			Integer str1 = c1.getProteins().size();                //一个cluster中节点个数
			Integer str2 = c2.getProteins().size();
			return str1.compareTo(str2);
	     	}
		case -2:{
			Integer str1 = c1.getProteins().size();
			Integer str2 = c2.getProteins().size();
			return str2.compareTo(str1);
	     	}
		case 3:{
			Double str1 = c1.getHfmeasureTb();               //整个网络中含有某种功能的蛋白质数量
			Double str2 = c2.getHfmeasureTb();
			System.out.println("%%%%%%%%%%%%%%%%%%Tb");
			return str1.compareTo(str2);
	     	}
		case -3:{
			Double str1 = c1.getHfmeasureTb();
			Double str2 = c2.getHfmeasureTb();
			System.out.println("%%%%%%%%%%%%%%%%%%Tb---");
			return str2.compareTo(str1);
	     	}
		case 4:{
			Double str1 = c1.getHfmeasureTf();               //整个网络中含有某种功能的蛋白质数量
			Double str2 = c2.getHfmeasureTf();
			return str1.compareTo(str2);
	     	}
		case -4:{
			Double str1 = c1.getHfmeasureTf();
			Double str2 = c2.getHfmeasureTf();
			return str2.compareTo(str1);
	     	}
		case 5:{
			String str1 = c1.getHfmeasureFun();                        //pvalue
			String str2 = c2.getHfmeasureFun();  
			return str1.compareTo(str2);
	     	}
		case -5:{
			String str1 = c1.getHfmeasureFun();                        //pvalue
			String str2 = c2.getHfmeasureFun();  
			return str2.compareTo(str1);
	     	}
		case 6:{
			Integer str1 = c1.getHfunnum();
			Integer str2 = c2.getHfunnum();
			return str1.compareTo(str2);
	     	}
		case -6:{
			Integer str1 = c1.getHfunnum();
			Integer str2 = c2.getHfunnum();
			return str2.compareTo(str1);
	     	}
		case 7:{
			Integer str1 = c1.getHbackground();                 //function code
			Integer str2 = c2.getHbackground(); 
			return str1.compareTo(str2);
	     	}
		case -7:{
			Integer str1 = c1.getHbackground();                 //function code
			Integer str2 = c2.getHbackground(); 
			return str2.compareTo(str1);
	     	}
		case 8:{
			Double str1 = c1.getFmeasure();
			Double str2 = c2.getFmeasure();
			return str1.compareTo(str2);
	     	}
		case -8:{
			Double str1 = c1.getFmeasure();
			Double str2 = c2.getFmeasure();
			return str2.compareTo(str1);
	     	}
		case 9:{
			String str1 = c1.getFmeasureFun();                        //pvalue
			String str2 = c2.getFmeasureFun();  
			return str1.compareTo(str2);
	     	}
		case -9:{
			String str1 = c1.getFmeasureFun();                        //pvalue
			String str2 = c2.getFmeasureFun();  
			return str2.compareTo(str1);
	     	}
		case 10:{
			Integer str1 = c1.getFunnum();
			Integer str2 = c2.getFunnum();			
			return str1.compareTo(str2);
	     	}
		case -10:{
			Integer str1 = c1.getFunnum();
			Integer str2 = c2.getFunnum();
			return str2.compareTo(str1);
	     	}
		case 11:{
			Integer str1 = c1.getBackground();                 //function code
			Integer str2 = c2.getBackground(); 
			return str1.compareTo(str2);
	     	}
		case -11:{
			Integer str1 = c1.getBackground();                 //function code
			Integer str2 = c2.getBackground();  
			return str2.compareTo(str1);
	     	}
		}
			
		return 0;
	}
	
}
