package com.example.woratio.r;

import org.rosuda.JRI.RMainLoopCallbacks;
import org.rosuda.JRI.Rengine;

/**
 * @filename MyRengine
 * @description
 * @author Felix
 * @date 2020/6/8 21:45
 */
public class MyRengine extends Rengine{

	public MyRengine(String[] var1, boolean var2, RMainLoopCallbacks var3) {
		super(var1, var2, var3);
	}

	public void setCallback(RMainLoopCallbacks cb) {
	}
}
