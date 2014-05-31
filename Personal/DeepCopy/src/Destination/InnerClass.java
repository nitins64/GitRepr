package Destination;
import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;

import Common.Helper;



public class InnerClass {
	
	/**
	 * 
	 */
	private ArrayList<Integer> list = new ArrayList<>();
	private int a_inner = 2;
	private String s_inner = "sharma";
	private List<TestClass> test = new ArrayList<>();
	
	public InnerClass() {
		getList().add(1);
		getList().add(2);
		getList().add(3);
	}
	
	public int getA_inner() {
		return a_inner;
	}
	public void setA_inner(int a_inner) {
		this.a_inner = a_inner;
	}
	public String getS_inner() {
		return s_inner;
	}
	public void setS_inner(String s_inner) {
		this.s_inner = s_inner;
	}

	public List<TestClass> getTest() {
		return test;
	}

	public void setTest(List<TestClass> test) {
		this.test = test;
	}
	
	@Override
	public String toString()
	{
		return Helper.TOString(this);
	}

	public ArrayList<Integer> getList()
	{
		return list;
	}

	public void setList(ArrayList<Integer> list)
	{
		this.list = list;
	}

}