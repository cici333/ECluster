package com.wuxuehong.plugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/** 
 *
 * @author: wuxuehong
 * @E-mail: wuxuehong214@163.com 
 * @date：2011-4-2 下午11:32:17 
 * 
 */

public class GeneExpression {
	
	private int n=0;
	private Map<String,ProteinBean> strToProtein = new HashMap<String,ProteinBean>(); 
	
	public void initialize(String filename) throws IOException{
		System.out.print("Reading gene expression data ....");
		long time = System.currentTimeMillis();
		readGeneExpression(filename);
		System.out.println((System.currentTimeMillis()-time)+"ms");
	}
	
	/**
	 * 读取基因表达信息
	 * @param filename
	 * @throws IOException
	 */
	public void readGeneExpression(String filename) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
		String str,temp,s1 = null;
		Scanner s= null;
		ProteinBean pb = null;
		double total = 0;
		double data = 0;
		str = br.readLine();
		while(str!=null){
			try{
			s = new Scanner(str);
			total = 0;
			s.next();  //neglect rid of the first element
			temp = s.next().toUpperCase();  //get the protein name
			if("FOUND".equals(temp)||"NON-ANNOTATED".equals(temp)){
				str = br.readLine();
				continue;
			}
			n=0;
			pb = new ProteinBean(temp);
			while(s.hasNext()){
				s1 = s.next();
				data = Double.parseDouble(s1);
				total+=data;
				pb.getDatas().add(data);
				n++;
			}
			total=total/n;
			pb.setAvg_exp(total);
			pb.setDevitation(getDevitation(pb.getDatas(), total, n));
			strToProtein.put(temp, pb);
			}catch(Exception e){
				System.out.println("Exception happen....");
				continue;
			}
			str = br.readLine();
		}
	}
	
	/**
	 * 获取标准差
	 * @return
	 */
	public double getDevitation(List<Double> datas,double avg,int n){
		double total = 0;
		for(int i=0;i<datas.size();i++){
			total = total+Math.pow((datas.get(i).doubleValue()-avg), 2);
		}
		total = total/(n-1);
		total = Math.sqrt(total);
		return total;
	}
	
	/**
	 * 计算任意两个蛋白质间PCC值
	 * @param protein1
	 * @param protein2
	 * @return
	 */
	public double getPCC(String protein1,String protein2){
		double pcc = 0;
		ProteinBean pb1 = strToProtein.get(protein1);
		ProteinBean pb2 = strToProtein.get(protein2);
		if(pb1==null||pb2==null)return pcc;
		for(int i=0;i<n;i++){
			double d = ((pb1.datas.get(i).doubleValue()-pb1.avg_exp)/pb1.devitation)*((pb2.datas.get(i).doubleValue()-pb2.avg_exp)/pb2.devitation);
			pcc = pcc+d;
	}
		pcc = pcc/(n-1);
		return pcc;
	}
	
	class ProteinBean{
		private String  name;   //蛋白质名称
		private List<Double> datas =null; //表达值
		private double devitation;  //标准方差
		private double avg_exp;//平均表达值
		public ProteinBean(String name) {
			super();
			this.name = name;
			datas = new ArrayList<Double>();
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public List<Double> getDatas() {
			return datas;
		}
		public void setDatas(List<Double> datas) {
			this.datas = datas;
		}
		public double getDevitation() {
			return devitation;
		}
		public void setDevitation(double devitation) {
			this.devitation = devitation;
		}
		public double getAvg_exp() {
			return avg_exp;
		}
		public void setAvg_exp(double avgExp) {
			avg_exp = avgExp;
		}
	}
}
