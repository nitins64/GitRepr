package Source;
import java.util.*;

import Common.Helper;

public class TestClass {
	private String s = "";
	private int a = 1;
	public String getS() {
		return s;
	}
	public void setS(String s) {
		this.s = s;
	}
	public int getA() {
		return a;
	}
	public void setA(int a) {
		this.a = a;
	}
	
	@Override
	public String toString()
	{
		return Helper.TOString(this);
	}

	
}
