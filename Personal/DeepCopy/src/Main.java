import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import Source.InnerClass;
import difflib.*;

public class Main
{

	static public enum MyType
	{
		A, B;
	}
	static class Pair<T,V>
	{
		public T index1;
		public V index2;
		public Pair(T index1, V index2)
		{
			this.index1 = index1;
			this.index2 = index2;
		}
	}
	
	public static int threeSumClosest(int[] num, int target) {
    
		LinkedList<Pair<Integer,Pair<Integer,Integer>>> sumofTwoIndexes = new LinkedList<>();
		
		for(int i = 0 ; i < num.length - 1; i++)
		{
			for(int j = i + 1; j < num.length; j++ )
			{
				Pair<Integer, Integer> indexes = new Pair<Integer, Integer>(i, j);
				sumofTwoIndexes.add(new Pair<Integer, Pair<Integer, Integer>>(new Integer(num[i]+num[j]), indexes));
			}
		}
		
		Integer closestSum = 0;
		Boolean initialized = false;
		for(int i = 0 ; i < num.length; i++)
		{
			for(Pair<Integer,Pair<Integer,Integer>> sumofTwoIndex : sumofTwoIndexes)
			{
				if(i != sumofTwoIndex.index2.index1 && i != sumofTwoIndex.index2.index2)
				{
					int sumOfThree = num[i] + sumofTwoIndex.index1;
					if(!initialized)
					{
						closestSum = sumOfThree;
						initialized = true;
					}
					else
					{
						if( Math.abs(sumOfThree - target) < Math.abs(closestSum - target))
						{
							closestSum = sumOfThree;
						}
					}
				}
			}
		}
		
		return closestSum;
    }
	
	public static void main(String[] args) throws IllegalAccessException,
			NoSuchFieldException, InstantiationException, Exception
	{
		
		
		int abc = 10;
		
		abc = abc + 10;
		
		abc = abc + 20;
		
		abc = abc + 40;
		
		int[] input = new int[] {-1, 2, 1, -4};
		int out = threeSumClosest(input, 1);
		
		// TODO Auto-generated method stub

		ClassWithDiffernceCopy();
		
		SimpleObjectCopy();

		ComplexObjectCopy();
		
		MyType a = MyType.A;
		switch(a)
		{
		case A : ;
		case B : ;
		default : ;
		}
		
	}

	private static void SimpleObjectCopy() throws IllegalAccessException,
			NoSuchFieldException, InstantiationException, Exception
	{

		Source.Base input = new Source.Base();
		input.setIntBase(1400);
		input.setSbase("Sneha");

		Source.InnerClass innerClass = new Source.InnerClass();
		innerClass.setA_inner(2345);
		input.setInnerClass(innerClass);
		Destination.Base output = new Destination.Base();

		String testName = "SimpleObjectCopy";
		Common.ComplexObjectCreator creator = new Common.ComplexObjectCreator();
		creator.CopyComplexObject(input, input.getClass(), output,
				output.getClass());
		ValidateResult(input, output, testName + "v1");
		creator.PrintFunctions();

		output = GeneratedCode.CreateBaseFromBase(input);
		ValidateResult(input, output, testName + "v2");
	}

	private static void ClassWithDiffernceCopy() throws IllegalAccessException,
			NoSuchFieldException, InstantiationException, Exception
	{

		String testName = "ClassWithDiffernceCopy";
		
		Source.ClassWithDifference input = new Source.ClassWithDifference();
		input.setIntBase(1400);
		input.setSbase("Sneha");

		Destination.ClassWithDifference output = new Destination.ClassWithDifference();

		Common.ComplexObjectCreator creator = new Common.ComplexObjectCreator();
		creator.getFieldIgnoreList().add("Destination.ClassWithDifference.listStrings");
		creator.CopyComplexObject(input, input.getClass(), output,
				output.getClass());
		
		ValidateResult(input, output, testName + "v1");
		creator.PrintFunctions();

		//output = GeneratedCode.CreateBaseFromBase(input);
		//ValidateResult(input, output, testName + "v2");
	}

	private static void ValidateResult(Object input,
			Object output, String testName) throws Exception
	{
		if (!input.toString().equals(output.toString()))
		{
			List<String> inputList = new ArrayList<String>(Arrays.asList(input
					.toString().split("\n")));
			List<String> outputList = new ArrayList<String>(
					Arrays.asList(output.toString().split("\n")));

			Patch patch = DiffUtils.diff(inputList, outputList);

			for (Delta delta : patch.getDeltas())
			{
				System.out.println(delta);
			}

			System.out.println(testName + " Failed");
		} else
		{
			System.out.println(testName + " passed");
		}
	}

	private static void ComplexObjectCopy() throws IllegalAccessException,
			NoSuchFieldException, InstantiationException, Exception
	{
		Source.Serialize input = new Source.Serialize();
		input.setIntBase(300);
		input.setSbase("kavya");
		Source.InnerClass innerClass = new Source.InnerClass();
		innerClass.setA_inner(1);
		innerClass.setS_inner("test");
		Source.TestClass t = new Source.TestClass();
		t.setA(1);
		t.setS("Test");
		// innerClass.getTest().add(t);
		t = new Source.TestClass();
		t.setA(2);
		t.setS("Test2");
		// innerClass.getTest().add(t);

		input.getInnerclassList().add(innerClass);
		innerClass = new Source.InnerClass();
		innerClass.setA_inner(2);
		innerClass.setS_inner("test2");

		t = new Source.TestClass();
		t.setA(1);
		t.setS("Test");
		innerClass.getTest().add(t);
		t = new Source.TestClass();
		t.setA(22);
		t.setS("Test2ZZ");
		innerClass.getTest().add(t);

		input.getInnerclassList().add(innerClass);
		input.setA(12);
		input.setS("NitinZZ");

		input.setIntBase(222222);

		Destination.Serialize output = new Destination.Serialize();
		String testName = "SimpleObjectCopy";
		Common.ComplexObjectCreator creator = new Common.ComplexObjectCreator();
		creator.CopyComplexObject(input, input.getClass(), output,
				output.getClass());
		ValidateResult(input, output, testName + "v1");
		creator.PrintFunctions();

		output = GeneratedCode.CreateSerializeFromSerialize(input);
		ValidateResult(input, output, testName + "v2");

	}

}
