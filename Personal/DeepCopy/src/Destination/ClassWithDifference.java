package Destination;

import java.util.ArrayList;
import java.util.List;

import Common.CommonData;
import Common.Helper;

public class ClassWithDifference
{
	private String sbase = "Base";
	private Integer intBase = 100;
	private List<String> listStrings = new ArrayList<String>();
	private List<InnerClass> listInnerClass = new ArrayList<InnerClass>();
	private MyColor color = MyColor.Green;
	private CommonData commonData = new CommonData();
	public String getSbase() {
		return sbase;
	}
	public void setSbase(String sbase) {
		this.sbase = sbase;
	}
	public Integer getIntBase() {
		return intBase;
	}
	public void setIntBase(Integer intBase) {
		this.intBase = intBase;
	}
	
	@Override
	public String toString()
	{
		return Helper.TOString(this);
	}
	public List<String> getListStrings()
	{
		return listStrings;
	}
	public void setListStrings(List<String> listStrings)
	{
		this.listStrings = listStrings;
	}
	public MyColor getColor()
	{
		return color;
	}
	public void setColor(MyColor color)
	{
		this.color = color;
	}
	public CommonData getCommonData()
	{
		return commonData;
	}
	public void setCommonData(CommonData commonData)
	{
		this.commonData = commonData;
	}
	public List<InnerClass> getListInnerClass()
	{
		return listInnerClass;
	}
	public void setListInnerClass(List<InnerClass> listInnerClass)
	{
		this.listInnerClass = listInnerClass;
	}

}
