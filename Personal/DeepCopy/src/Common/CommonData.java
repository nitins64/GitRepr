package Common;

public class CommonData
{
	private int data = 100;

	public int getData()
	{
		return data;
	}

	public void setData(int data)
	{
		this.data = data;
	}
	

	@Override
	public String toString()
	{
		return Helper.TOString(this);
	}
	
	
}
