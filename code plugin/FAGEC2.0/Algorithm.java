package com.wuxuehong.plugin;

public abstract interface Algorithm
{
  public abstract void mergeComplex(Complex paramComplex1, Complex paramComplex2);

  public abstract Complex[] clustering(Network paramNetwork);

  public abstract ParameterSet getParameterSet();
}
