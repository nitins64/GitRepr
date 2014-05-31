package Common;


import java.lang.reflect.Field;

public class Helper {

	public static String TOString(Object object)
	{
		StringBuilder build = new StringBuilder();
		Class clazz = object.getClass();
		
		while(clazz != null)
		{
			for(Field field : clazz.getDeclaredFields())
			{
				field.setAccessible(true);
				try
				{
					build.append(String.format("FieldName:%s Value:%s. \n", field.getName(), field.get(object).toString()));
				}catch(IllegalAccessException ex)
				{
					
				}
			}
			clazz = clazz.getSuperclass();
		}
		return build.toString();
	}
}
