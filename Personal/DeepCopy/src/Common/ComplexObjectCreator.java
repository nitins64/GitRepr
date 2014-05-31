package Common;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ComplexObjectCreator
{
	private List<String> functionList = new ArrayList<String>();
	private List<String> fieldIgnoreList = new ArrayList<String>();
	
	private String CreateFunctionMethod(String inputClassName, String outputClassName,
			   String inputFullClassName, String outputFullClassName,
			   String body)
	{
		String functionTemplate = new StringBuilder()
		.append("public static %4$s Create%1$sFrom%2$s(%3$s input)\n")
		.append("{\n")
		.append("    %4$s output = new %4$s();\n")
		.append("%5$s\n")
		.append("    return output;\n")
		.append("}\n")
		.toString();
		
		return String.format(functionTemplate, inputClassName,outputClassName, inputFullClassName, outputFullClassName, body);
	}
	
	private String CreateSimplePropertyCopy(String outputSetPropertyName, String inputGetPropertyName)
	{
		String propertyTemplate = new StringBuilder()
		.append("    output.%1$s(input.%2$s());\n")
		.toString();

		return String.format(propertyTemplate, outputSetPropertyName, inputGetPropertyName);
	}
	
	private String CreateEnumPropertyCopy(String outputSetPropertyName, String outputEnumClassName, String inputGetPropertyName)
	{
		String enumCopyTemplate = new StringBuilder()
		.append("    output.%1$s(%3$s.valueOf(input.%2$s().toString()));\n")
		.toString();

		return String.format(enumCopyTemplate, outputSetPropertyName, inputGetPropertyName, outputEnumClassName);
	}
	
	
	public void PrintFunctions()
	{
		for(String s : functionList)
		{
			System.out.println(s);
		}
	}
	
	public void CopyComplexObject(Object input, Class inputClass,
			Object output, Class outputClass) throws IllegalAccessException,
			NoSuchFieldException, InstantiationException
	{
		
		String inputClassName = inputClass.getSimpleName();
		String outputClassName = outputClass.getSimpleName();
		String inputFullClassName = inputClass.getPackage().getName() + "." + inputClassName; 
		String outputFullClassName = outputClass.getPackage().getName() + "." + outputClassName;
		StringBuilder body = new StringBuilder();
		while (outputClass != null)
		{
			for (Field outputField : outputClass.getDeclaredFields())
			{
				
				if(isFieldToIgnore(outputField,body))
				{
					continue;
				}
				
				Field inputField = inputClass.getDeclaredField(outputField
						.getName());
				inputField.setAccessible(true);
				outputField.setAccessible(true);
				CopyField(inputField, outputField, input, output, body);
			}

			outputClass = outputClass.getSuperclass();
			inputClass = inputClass.getSuperclass();

		}
		String functionMethod = CreateFunctionMethod(inputClassName, outputClassName, inputFullClassName, outputFullClassName, body.toString());
		if(!functionList.contains(functionMethod))
			functionList.add(functionMethod);
	}

	private void CopyField(Field inputField, Field outputField,
			Object input, Object output, StringBuilder body) throws IllegalAccessException,
			NoSuchFieldException, InstantiationException
	{
		
		if (isListType(outputField.getGenericType()))
		{
			CopyListField(inputField, outputField, input, output,body);
		} else if (isSimpleType(outputField.getGenericType()))
		{
			CopySimpleField(inputField, outputField, input, output,body);
		} else if (isComplexType(outputField.getGenericType()))
		{
			CopyComplexField(inputField, outputField, input, output,body);
		} else
		{
			System.out.println("Don't know how to copy"
					+ outputField.toString() + ":"
					+ outputField.getGenericType().toString());
		}
	}

	private boolean isFieldToIgnore(Field outputField, StringBuilder body)
	{
		// TODO Auto-generated method stub
		String fieldName = outputField.getDeclaringClass().getName() + "." + outputField.getName();
		if( fieldIgnoreList.contains(fieldName))
		{
			body.append("    //todo: Ignoring copy of " + fieldName + "\n");
			return true;
		}
		return false;
	}

	private void CopyComplexField(Field inputField, Field outputField,
			Object input, Object output, StringBuilder body) throws IllegalAccessException,
			NoSuchFieldException, InstantiationException
	{
		Object sourceObject = inputField.get(input);
		Object objectDestination = null;
		String outputSetPropertyName = getFieldSetPropertyName(outputField);
		String inputGetPropertyName = getFieldGetPropertyName(inputField);
		String outputClassName = outputField.getType().getName();
		String inputClassName = inputField.getType().getName();
		
		if(outputClassName == inputClassName)
		{
			AppendSimplePropertyCopy(inputField, outputField, body);
			objectDestination = sourceObject;
		}
		else
		{
			if (outputField.getType().isEnum())
			{
				// field.getGenericType().getClass()
				boolean found = false;
				for (Object enumConst : outputField.getType().getEnumConstants())
				{
					if (enumConst.toString().compareTo(sourceObject.toString()) == 0)
					{
						objectDestination = enumConst;
						found = true;
						body.append(CreateEnumPropertyCopy(outputSetPropertyName, outputClassName, inputGetPropertyName));
						break;
					}
				}
				if (!found)
				{
					throw new InstantiationException(String.format(
							"Did not find enum:%s in destination:%s",
							sourceObject.toString(), outputField.getType()));
				}
	
			} else
			{
				AppendComplexObjectCopy(inputField, outputField, body);
				objectDestination = outputField.getType().newInstance();
				CopyComplexObject(sourceObject, sourceObject.getClass(),
						objectDestination, objectDestination.getClass());
			}
		}
		outputField.set(output, objectDestination);

	}

	private void CopyListField(Field inputField, Field outputField,
			Object input, Object output, StringBuilder body) throws IllegalAccessException,
			NoSuchFieldException, InstantiationException
	{
		{
			List<Object> listSource = (List<Object>) inputField.get(input);
			List<Object> listDestination = (List<Object>) outputField
					.get(output);
			Class<?> classDestination = ((ParameterizedType) outputField
					.getGenericType()).getActualTypeArguments()[0].getClass();
			
			AppendListObjectCopy(inputField, outputField, body);
			
			for (Object objectInput : listSource)
			{
				{
					if (isSimpleType(objectInput.getClass()
							.getGenericSuperclass()))
					{
						listDestination.add(objectInput);
					} else
					{
						Object objectDestination = classDestination
								.newInstance();
						CopyComplexObject(objectInput, objectInput.getClass(),
								objectDestination, objectDestination.getClass());
						listDestination.add(objectDestination);
					}
				}
			}
		}

	}

	private void AppendListObjectCopy(Field inputField, Field outputField,
			StringBuilder body) throws InstantiationException, IllegalAccessException, NoSuchFieldException
	{
		String outputSetPropertyName = getFieldSetPropertyName(outputField);
		String outputGetPropertyName = getFieldGetPropertyName(outputField);
		String inputGetPropertyName = getFieldGetPropertyName(inputField);
		String inputClassName = inputField.getType().getSimpleName();
		String outputClassName = outputField.getType().getSimpleName();
		Class<?> inputClassDestination = (Class)(((ParameterizedType) inputField
				.getGenericType()).getActualTypeArguments()[0]);
		
		Class<?> outputClassDestination = (Class)(((ParameterizedType) outputField
				.getGenericType()).getActualTypeArguments()[0]);
		StringBuilder propertyTemplate = new StringBuilder();
		
		if(isSimpleType(inputClassDestination))
		{
			propertyTemplate.append("    for(%1$s in : input.%2$s())\n")
									.append("    {\n")
									.append("        output.%3$s().add(in);\n")
									.append("    }\n");
		}
		else
		{
			propertyTemplate.append("    for(%1$s in : input.%2$s())\n")
			.append("    {\n")
			.append("        output.%3$s().add(Create%4$sFrom%5$s(in));\n")
			.append("    }\n");

			//dummy call to generate function defination
			Object objectInput = inputClassDestination.newInstance();
			Object objectDestination = outputClassDestination.newInstance();
			
			CopyComplexObject(objectInput, objectInput.getClass(),
					objectDestination, objectDestination.getClass());
			
		}
		
		String outValue = String.format(propertyTemplate.toString(), inputClassDestination.getName(), inputGetPropertyName, outputGetPropertyName, inputClassDestination.getSimpleName(), outputClassDestination.getSimpleName());
		
		body.append(outValue);
		
	}
	
	private static String getFieldGetPropertyName(Field field)
	{
		return "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1, field.getName().length());
				
	}
	
	private static String getFieldSetPropertyName(Field field)
	{
		return "set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1, field.getName().length());
	}
	
	private void CopySimpleField(Field inputField, Field outputField,
			Object input, Object output, StringBuilder body) throws IllegalAccessException,
			NoSuchFieldException
	{
		AppendSimplePropertyCopy(inputField, outputField, body);
		
		outputField.set(output, inputField.get(input));

	}

	private void AppendSimplePropertyCopy(Field inputField, Field outputField,
			StringBuilder body)
	{
		String outputSetPropertyName = getFieldSetPropertyName(outputField);
		String inputGetPropertyName = getFieldGetPropertyName(inputField);
		body.append(CreateSimplePropertyCopy(outputSetPropertyName, inputGetPropertyName));
	}

	private void AppendComplexObjectCopy(Field inputField, Field outputField,
			StringBuilder body)
	{
		String outputSetPropertyName = getFieldSetPropertyName(outputField);
		String inputGetPropertyName = getFieldGetPropertyName(inputField);
		String inputClassName = inputField.getType().getSimpleName();
		String outputClassName = outputField.getType().getSimpleName();
		String propertyTemplate = new StringBuilder()
												.append("    output.%1$s( Create%3$sFrom%4$s( input.%2$s() ));\n")
												.toString();

		String outValue = String.format(propertyTemplate, outputSetPropertyName, inputGetPropertyName, inputClassName, outputClassName);
		
		body.append(outValue);
	}

	
	private boolean isSimpleType(Type type)
	{
		if (!type.toString().contains(".") || type.toString().contains("java."))
		{
			return true;
		}

		return false;
	}

	private boolean isComplexType(Type type)
	{
		// TODO Auto-generated method stub
		return true;
	}

	private boolean isEnumType(Type type)
	{
		// TODO Auto-generated method stub
		return type.getClass().isEnum();
	}

	private boolean isListType(Type type)
	{
		if (type.toString().contains("java.util.List"))
		{
			return true;
		}
		// TODO Auto-generated method stub
		return false;
	}

	public List<String> getFieldIgnoreList()
	{
		return fieldIgnoreList;
	}


}
