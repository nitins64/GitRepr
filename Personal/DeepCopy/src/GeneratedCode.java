
public class GeneratedCode
{
	public static Destination.Base CreateBaseFromBase(Source.Base input)
	{
	    Destination.Base output = new Destination.Base();
	    output.setSbase(input.getSbase());
	    output.setIntBase(input.getIntBase());
	    for(java.lang.String in : input.getListStrings())
	    {
	        output.getListStrings().add(in);
	    }
	    for(Source.InnerClass in : input.getListInnerClass())
	    {
	        output.getListInnerClass().add(CreateInnerClassFromInnerClass(in));
	    }
	    output.setColor(Destination.MyColor.valueOf(input.getColor().toString()));
	    output.setCommonData(input.getCommonData());
	    output.setInnerClass( CreateInnerClassFromInnerClass( input.getInnerClass() ));

	    return output;
	}
	
	public static Destination.TestClass CreateTestClassFromTestClass(Source.TestClass input)
	{
	    Destination.TestClass output = new Destination.TestClass();
	    output.setS(input.getS());
	    output.setA(input.getA());

	    return output;
	}

	public static Destination.InnerClass CreateInnerClassFromInnerClass(Source.InnerClass input)
	{
	    Destination.InnerClass output = new Destination.InnerClass();
	    output.setList(input.getList());
	    output.setA_inner(input.getA_inner());
	    output.setS_inner(input.getS_inner());
	    for(Source.TestClass in : input.getTest())
	    {
	        output.getTest().add(CreateTestClassFromTestClass(in));
	    }

	    return output;
	}

	public static Destination.Serialize CreateSerializeFromSerialize(Source.Serialize input)
	{
	    Destination.Serialize output = new Destination.Serialize();
	    output.setA(input.getA());
	    output.setS(input.getS());
	    for(Source.InnerClass in : input.getInnerclassList())
	    {
	        output.getInnerclassList().add(CreateInnerClassFromInnerClass(in));
	    }
	    output.setColorParent(Destination.MyColor.valueOf(input.getColorParent().toString()));
	    output.setSbase(input.getSbase());
	    output.setIntBase(input.getIntBase());
	    for(java.lang.String in : input.getListStrings())
	    {
	        output.getListStrings().add(in);
	    }
	    for(Source.InnerClass in : input.getListInnerClass())
	    {
	        output.getListInnerClass().add(CreateInnerClassFromInnerClass(in));
	    }
	    output.setColor(Destination.MyColor.valueOf(input.getColor().toString()));
	    output.setCommonData(input.getCommonData());
	    output.setInnerClass( CreateInnerClassFromInnerClass( input.getInnerClass() ));

	    return output;
	}

}
