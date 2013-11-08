package com.mycompany.config;

import java.util.LinkedHashMap;

public class ImageOperations {
	public int resizeWidthAmount = 400;
	public int resizeHeightAmount = 400;
	public boolean resizeHighQuality = false;
	public boolean resizeMaintainAspectRatio = true;
	public boolean resizeReduceOnly = true;
	
	public LinkedHashMap<String, String> asMap()
	{
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		map.put("resize-width-amount", resizeWidthAmount + "");
		map.put("resize-height-amount", resizeHeightAmount + "");
		map.put("resize-high-quality", resizeHighQuality + "");
		map.put("resize-maintain-aspect-ratio", resizeMaintainAspectRatio + "");
		map.put("resize-reduce-only", resizeReduceOnly + "");
		return map;
	}
}