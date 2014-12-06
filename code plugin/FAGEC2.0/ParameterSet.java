package com.wuxuehong.plugin;

public abstract interface ParameterSet
{
  public abstract void setDefaultParams();

  public abstract ParameterSet copy();

  public abstract String toString();
}

