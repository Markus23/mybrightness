package de.mybukit.mybrightness.helper;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;
@SuppressWarnings({ "unchecked", "rawtypes" })
public class MyConfig {

	private static MyConfig instance = null;
	private SortedProperties prop = null;
	public static String PropertyFileName;

	public MyConfig(String file) {
		PropertyFileName=file;

	}

	private MyConfig() {
		InputStream input = null;
		try {
			input = new FileInputStream(PropertyFileName);
			this.prop = new SortedProperties();
			this.prop.load(input);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static synchronized MyConfig getInstance() {
		if (instance != null) {
			return instance;
		}
		synchronized (MyConfig.class) {

			if (instance == null) {
				instance = new MyConfig();
			}
		}
		return instance;
	}
	public void saveParamChanges(String key, String value) {
		try {
			this.prop.setProperty(key, value);
			OutputStream out = new FileOutputStream(PropertyFileName);
			
			this.prop.store(out, "Last changes: " + key + "=" + value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String getValue(String propKey) {
		return this.prop.getProperty(propKey);
	}

	public Set<String> getAllPropertyNames() {
		return this.prop.stringPropertyNames();
	}

	public boolean containsKey(String key) {
		return this.prop.containsKey(key);
	}
	public String getString(String name) {
		return this.prop.getProperty(name);
	}

	public int getInt(String name) {
		return Integer.parseInt(this.prop.getProperty(name));
	}

	public double getDouble(String name) {
		return Double.parseDouble(this.prop.getProperty(name));
	}

	public long getLong(String name) {
		return Long.parseLong(this.prop.getProperty(name));
	}

	public boolean getBoolean(String name) {
		return Boolean.parseBoolean(this.prop.getProperty(name));
	}


	public String getString(String name,String defaultstring) {
		if (!this.containsKey(name)) {
			this.prop.setProperty(name, defaultstring);
			saveParamChanges(name, defaultstring);
		}
		return this.prop.getProperty(name);
	}

	public int getInt(String name, int defaultInt) {
		if (!this.containsKey(name)) {
			this.prop.setProperty(name, Integer.toString(defaultInt));
			saveParamChanges(name, Integer.toString(defaultInt));
		}  
		return  Integer.parseInt(this.prop.getProperty(name));
	}

	public double getDouble(String name,double defaultdouble) {
		if (!this.containsKey(name)) {
			this.prop.setProperty(name, Double.toString(defaultdouble));
			saveParamChanges(name, Double.toString(defaultdouble));
		} 
		return Double.parseDouble(this.prop.getProperty(name));
	}

	public Long getLong(String name, long defaultlong) {
		if (!this.containsKey(name)) {
			this.prop.setProperty(name, Long.toString(defaultlong));
			saveParamChanges(name, Long.toString(defaultlong));
		} 
		
		return Long.parseLong(this.prop.getProperty(name));
	}
	public boolean getBoolean(String name,boolean defaultboolean) {
		if (!this.containsKey(name)) {
			this.prop.setProperty(name,Boolean.toString(defaultboolean));
			saveParamChanges(name, Boolean.toString(defaultboolean));
		}
		return Boolean.parseBoolean(this.prop.getProperty(name));
	}	    

	class SortedProperties extends Properties {

		private static final long serialVersionUID = 4786733291853770802L;


		public Enumeration keys() {
			Enumeration keysEnum = super.keys();
			Vector<String> keyList = new Vector<String>();
			while (keysEnum.hasMoreElements()) {
				keyList.add((String) keysEnum.nextElement());
			}
			Collections.sort(keyList);
			return keyList.elements();
		}
	}
}
