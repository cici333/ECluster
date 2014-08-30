package hf_measure;




import java.util.*;

public class ClusterVo implements Comparable{
	
	private Set<String> proteins = new HashSet<String>();  //the collection of the proteinss
	private String clusterid;
	private String term;  //term id
	private double fmeasure;
	
	private double hfmeasureTb;  //topological 
	private double hfmeasureTf;  //untopological

	private String fmeasureFun;
	private int funnum;// the number of proteins who have functin term
	private int background;
	
	private String hfmeasureFun;
	private int hfunnum; 
	private int hbackground;
	
	private boolean cotainUnknown = false;   //是否包含功能未知的蛋白质
	private Set<String> probFunctions = new HashSet<String>(); //可能含有的功能
	private Set<String> unknownProteins = new HashSet<String>();
		
	private Map<String,Integer> edgeMap = new HashMap<String,Integer>();
	
	public static String tb = "Hf-measure(Tb)";
	public static String tf = "Hf-measure(Tf)";
	public static String ff = "F-measure";
	
	private boolean isNull_Tb = true;
	private boolean isNull_Tf = true;
	private boolean isNull_Ff = true;
	
	public static String comparePara;
	
	public String getClusterid() {
		return clusterid;
	}

	public void setClusterid(String clusterid) {
		this.clusterid = clusterid;
	}

	public Set<String> getProteins() {
		return proteins;
	}

	public void setProteins(Set<String> proteins) {
		this.proteins = proteins;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public int getFunnum() {
		return funnum;
	}

	public void setFunnum(int funnum) {
		this.funnum = funnum;
	}

	public int getBackground() {
		return background;
	}

	public void setBackground(int background) {
		this.background = background;
	}

	public String getFmeasureFun() {
		return fmeasureFun;
	}

	public void setFmeasureFun(String fmeasureFun) {
		this.fmeasureFun = fmeasureFun;
	}

	public String getHfmeasureFun() {
		return hfmeasureFun;
	}

	public void setHfmeasureFun(String hfmeasureFun) {
		this.hfmeasureFun = hfmeasureFun;
	}

	/*
	public int compareTo(ClusterVo c) {
		// TODO Auto-generated method stub
		double a = hfmeasureTb/fmeasure;
		double b = c.getHfmeasureTb()/c.getFmeasure();
		return (int) (a*100000000-b*100000000);
	}
*/
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		ClusterVo c = (ClusterVo) o;
			
		if(comparePara.equals(tb))
		return new Double(getHfmeasureTb()).compareTo(new Double(c.getHfmeasureTb()));
		else if(comparePara.equals(tf))
			return new Double(getHfmeasureTf()).compareTo(new Double(c.getHfmeasureTf()));
		else if(comparePara.equals(ff))
			return new Double(getFmeasure()).compareTo(new Double(c.getFmeasure()));
		
		return 1;
	}

	public int getHfunnum() {
		return hfunnum;
	}

	public void setHfunnum(int hfunnum) {
		this.hfunnum = hfunnum;
	}

	public int getHbackground() {
		return hbackground;
	}

	public void setHbackground(int hbackground) {
		this.hbackground = hbackground;
	}

	public boolean isCotainUnknown() {
		return cotainUnknown;
	}

	public void setCotainUnknown(boolean cotainUnknown) {
		this.cotainUnknown = cotainUnknown;
	}

	public Set<String> getProbFunctions() {
		return probFunctions;
	}

	public void setProbFunctions(Set<String> probFunctions) {
		this.probFunctions = probFunctions;
	}

	public Set<String> getUnknownProteins() {
		return unknownProteins;
	}

	public void setUnknownProteins(Set<String> unknownProteins) {
		this.unknownProteins = unknownProteins;
	}

	public Map<String, Integer> getEdgeMap() {
		return edgeMap;
	}

	public void setEdgeMap(Map<String, Integer> edgeMap) {
		this.edgeMap = edgeMap;
	}

	public double getFmeasure() {
		return fmeasure;
	}

	public void setFmeasure(double fmeasure) {
		this.fmeasure = fmeasure;
		isNull_Ff = false;
	}

	public double getHfmeasureTb() {
		return hfmeasureTb;
		
	}

	public void setHfmeasureTb(double hfmeasure) {
		this.hfmeasureTb = hfmeasure;
		isNull_Tb = false;
	}

	public double getHfmeasureTf() {
		return hfmeasureTf;
	}

	public void setHfmeasureTf(double hfmeasure2) {
		this.hfmeasureTf = hfmeasure2;
		isNull_Tf = false;
	}
	
	public double getMeasure(String m){
		if(m.equals(tb)){
			return hfmeasureTb;		
		}
		if(m.equals(tf)){
			return hfmeasureTf;		
		}
		if(m.equals(ff)){
			return fmeasure;		
		}
		else
			return (Double) null;
	}

	public boolean isNull_Tb() {
		return isNull_Tb;
	}

	public boolean isNull_Tf() {
		return isNull_Tf;
	}

	public boolean isNull_Ff() {
		return isNull_Ff;
	}


}
