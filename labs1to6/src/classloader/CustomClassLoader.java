package classloader;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;

public class CustomClassLoader extends ClassLoader
{
	public CustomClassLoader()
	{
		super(CustomClassLoader.class.getClassLoader());
	}

	public Class loadClass(String className) throws ClassNotFoundException
	{
		return findClass(className);
	}

	public Class findClass(String className)
	{
		byte classByte[];
		Class result = null;
		result = (Class) classes.get(className);
		if (result != null)
		{
			return result;
		}

		try
		{
			return findSystemClass(className);
		}
		catch (Exception e)
		{
		}
		try
		{
			String classPath = ((String) ClassLoader.getSystemResource(
			        className.replace('.', File.separatorChar) + ".class").getFile()).substring(1);
			classByte = loadClassData(classPath);
			result = defineClass(className, classByte, 0, classByte.length, null);
			classes.put(className, result);
			return result;
		}
		catch (Exception e)
		{
			return null;
		}
	}

	private byte[] loadClassData(String className) throws IOException
	{
		File f;
		f = new File(className);
		int size = (int) f.length();
		byte buff[] = new byte[size];
		FileInputStream fis = new FileInputStream(f);
		DataInputStream dis = new DataInputStream(fis);
		dis.readFully(buff);
		dis.close();
		return buff;
	}

	private Hashtable classes = new Hashtable();

	/*
	 * private class CustomClassLoaderTest {
	 * public static void main(String [] args) throws Exception{
	 * CustomClassLoader test = new CustomClassLoader();
	 * test.loadClass(lab2.HelloWorld);
	 * }
	 * }
	 */
	
	public class HelloWorld
	{
		
	}

}