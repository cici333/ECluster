 package com.wuxuehong.plugin;
 
 import java.util.HashMap;
 
 public class FAGECParameterSet
   implements ParameterSet
 {
   private static FAGECParameterSet ourInstance = new FAGECParameterSet();
   private static HashMap currentParams = new HashMap();
   private static HashMap resultParams = new HashMap();
   private boolean overlapped;
   private double fThreshold;
   private int cliqueSizeThreshold;
   private int complexSizeThreshold;
   private boolean isWeak;
 
   public FAGECParameterSet()
   {
     setDefaultParams();
   }
 
   public FAGECParameterSet(double fThreshold, int cliqueSizeThreshold, int complexSizeThreshold, boolean isWeak, boolean overlapped)
   {
     setAllAlgorithmParams(
       fThreshold, 
       cliqueSizeThreshold, 
       complexSizeThreshold, 
       isWeak, 
       overlapped);
   }
 
   public static FAGECParameterSet getInstance()
   {
     return ourInstance;
   }
 
   public FAGECParameterSet getParamsCopy(String networkID)
   {
     if (networkID != null) {
       return ((FAGECParameterSet)currentParams.get(networkID)).copy();
     }
     FAGECParameterSet newParams = new FAGECParameterSet();
     return newParams.copy();
   }
 
   public ParameterSet getResultParams(String resultSet) {
     return ((ParameterSet)resultParams.get(resultSet)).copy();
   }
   public static void removeResultParams(String resultSet) {
     resultParams.remove(resultSet);
   }
 
   public void setParams(FAGECParameterSet newParams, String resultTitle, String networkID)
   {
     ParameterSet currentParamSet = new FAGECParameterSet(
       newParams.getFThreshold(), 
       newParams.getCliqueSizeThreshold(), 
       newParams.getComplexSizeThreshold(), 
       newParams.isWeak(), 
       newParams.isOverlapped());
 
     currentParams.put(networkID, currentParamSet);
     ParameterSet resultParamSet = new FAGECParameterSet(
       newParams.getFThreshold(), 
       newParams.getCliqueSizeThreshold(), 
       newParams.getComplexSizeThreshold(), 
       newParams.isWeak(), 
       newParams.isOverlapped());
 
     resultParams.put(resultTitle, resultParamSet);
   }
 
   public void setDefaultParams()
   {
     setAllAlgorithmParams(1.0D, 3, 3, true, false);
   }
 
   public void setAllAlgorithmParams(double fThreshold, int cliqueSizeThreshold, int complexSizeThreshold, boolean isWeak, boolean overlapped)
   {
     this.fThreshold = fThreshold;
     this.cliqueSizeThreshold = cliqueSizeThreshold;
     this.complexSizeThreshold = complexSizeThreshold;
     this.isWeak = isWeak;
     this.overlapped = overlapped;
   }
 
   public FAGECParameterSet copy()
   {
     FAGECParameterSet newParam = new FAGECParameterSet();
     newParam.setCliqueSizeThreshold(this.cliqueSizeThreshold);
     newParam.setComplexSizeThreshold(this.complexSizeThreshold);
     newParam.setFThreshold(this.fThreshold);
     newParam.setWeak(this.isWeak);
     newParam.setOverlapped(this.overlapped);
     return newParam;
   }
 
   public int getCliqueSizeThreshold() {
     return this.cliqueSizeThreshold;
   }
 
   public void setCliqueSizeThreshold(int cliqueSizeThreshold) {
     this.cliqueSizeThreshold = cliqueSizeThreshold;
   }
 
   public int getComplexSizeThreshold() {
     return this.complexSizeThreshold;
   }
 
   public void setComplexSizeThreshold(int complexSizeThreshold) {
     this.complexSizeThreshold = complexSizeThreshold;
   }
 
   public double getFThreshold() {
     return this.fThreshold;
   }
 
   public void setFThreshold(double fThreshold) {
     this.fThreshold = fThreshold;
   }
 
   public boolean isWeak() {
     return this.isWeak;
   }
 
   public void setWeak(boolean isWeak) {
     this.isWeak = isWeak;
   }
 
   public boolean isOverlapped() {
     return this.overlapped;
   }
 
   public void setOverlapped(boolean overlapped) {
     this.overlapped = overlapped;
   }
 
   public String toString()
   {
     String lineSep = System.getProperty("line.separator");
     StringBuffer sb = new StringBuffer();
     sb.append("   Algorithm:  FAG-EC" + lineSep);
     sb.append("   Clustering:" + lineSep + 
       "      DefinitionWay: " + (this.isWeak ? "Weak  In/OutThreshold: " + this.fThreshold : "Strong") + lineSep + 
       "      Overlapped: " + this.overlapped + (this.overlapped ? " CliqueSizeThreshold: " + this.cliqueSizeThreshold : "") + lineSep + 
       "      OutputThreshold: " + this.complexSizeThreshold + lineSep);
     return sb.toString();
   }
 }

/* Location:           C:\Users\USER\Desktop\Myplugins\FAG-EC.jar
 * Qualified Name:     com.wuxuehong.plugin.FAGECParameterSet
 * JD-Core Version:    0.6.0
 */