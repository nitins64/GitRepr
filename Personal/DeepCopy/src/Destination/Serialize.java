package Destination;
import java.util.*;
import java.io.Serializable;

import Common.Helper;



public class Serialize extends Base{
	
	private int a = 1;
	private String s = "nitin";
	private List<InnerClass> innerclassList = new ArrayList<InnerClass>();
	private MyColor colorParent = MyColor.Red;
			
	public int getA() {
		return a;
	}
	public void setA(int a) {
		this.a = a;
	}
	public String getS() {
		return s;
	}
	public void setS(String s) {
		this.s = s;
	}
	public MyColor getColorParent() {
		return colorParent;
	}
	
	public void setColorParent(MyColor color) {
		this.colorParent = color;
	}
	public List<InnerClass> getInnerclassList() {
		return innerclassList;
	}
	
	public void setInnerclassList(List<InnerClass> innerclassList) {
		this.innerclassList = innerclassList;
	}

	@Override
	public String toString()
	{
		return Helper.TOString(this);
	}

}
