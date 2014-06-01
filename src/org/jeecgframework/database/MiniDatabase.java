package org.jeecgframework.database;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.google.gson.Gson;


/**   
 *  
 * @Description: 
 * @author ly   
 * @date 2014-5-31 下午5:54:18 
 *    
 */
public class MiniDatabase {

	private static final String _uuid = "_uuid";
	
	/**
	 * 读取集合所有数据
	 * @param path
	 * @param tablename
	 * @return
	 * @throws DocumentException
	 */
	public List<Map> loadDatas(String path, String tablename) throws DocumentException{
		Gson gson = new Gson();
		List<Map> list = new ArrayList<Map>();
		SAXReader saxReader = new SAXReader();
		Document doc = saxReader.read(path);
		Element database = doc.getRootElement();
		Iterator<Element> tables = database.elementIterator();
		while(tables.hasNext()){
			Element table = tables.next();
			if(table.attribute("name").getValue().equals(tablename)){
				Iterator<Element> datas = table.elementIterator();
				while(datas.hasNext()){
					Element data = datas.next();
					Map map = gson.fromJson(data.getText(),Map.class);
					list.add(map);
				}
				break;
			}
		}
		return list;
	}
	
	/**
	 * 删除集合数据
	 * @param path
	 * @param tablename
	 * @param obj
	 * @throws DocumentException
	 * @throws IOException
	 */
	public boolean deleteData(String path, String tablename, Object obj) throws DocumentException, IOException{
		boolean flag = false;
		Gson gson = new Gson();
		SAXReader saxReader = new SAXReader();
		Document doc = saxReader.read(path);
		Element database = doc.getRootElement();
		Iterator<Element> tables = database.elementIterator();
		while(tables.hasNext()){
			Element table = tables.next();
			if(table.attribute("name").getValue().equals(tablename)){
				Iterator<Element> datas = table.elementIterator();
				while(datas.hasNext()){
					Element data = datas.next();
					Map map = gson.fromJson(data.getText(), Map.class);
					Map newd = gson.fromJson(gson.toJson(obj), Map.class);
					if(map.get(_uuid).equals(newd.get(_uuid))){
						table.remove(data);
						flag = true;
						break;
					}
				}
				break;
			}
		}
		Writer fileWriter = new FileWriter(path);
		XMLWriter xmlWriter = new XMLWriter(fileWriter);
		xmlWriter.write(doc);
		xmlWriter.close();
		fileWriter.close();
		
		System.out.println("------------deletedata success-----------");
		return flag;
	}
	
	/**
	 * 修改集合数据
	 * @param path
	 * @param tablename
	 * @param obj
	 * @return
	 * @throws DocumentException 
	 * @throws IOException 
	 */
	public boolean updateData(String path, String tablename, Object obj) throws DocumentException, IOException{
		boolean flag = false;
		Gson gson = new Gson();
		
		SAXReader saxReader = new SAXReader();
		Document doc = saxReader.read(path);
		Element database = doc.getRootElement();
		Iterator<Element> tables = database.elementIterator();	
		while(tables.hasNext()){
			Element table = tables.next();
			if(table.attribute("name").getValue().equals(tablename)){
				Iterator<Element> datas = table.elementIterator(); 
				while(datas.hasNext()){
					Element data = datas.next();
					Map map = gson.fromJson(data.getText(), Map.class);
					Map newmap = gson.fromJson(gson.toJson(obj),Map.class);
					if(map.get(_uuid).equals(newmap.get(_uuid))){
						map.putAll(newmap);
						Element newdata = table.addElement("data");
						newdata.addText(gson.toJson(map));
						table.remove(data);
						flag = true;
						break;
					}
				}
				break;
			}
		}
		Writer fileWriter = new FileWriter(path);
		XMLWriter xmlWriter = new XMLWriter(fileWriter);
		xmlWriter.write(doc);
		xmlWriter.close();
		fileWriter.close();
		
		System.out.println("------------updatedata success-----------");
		return flag;
	}
	
	/**
	 * 插入集合数据
	 * @param path
	 * @param tablename
	 * @param obj
	 * @return
	 * @throws DocumentException
	 * @throws IOException 
	 */
	public boolean addData(String path, String tablename, Object obj) throws DocumentException, IOException{
		boolean flag = false;
		Gson gson = new Gson();
		
		SAXReader saxReader = new SAXReader(); 
		Document doc = saxReader.read(path);
		Element database = doc.getRootElement();
		Iterator<Element> tables = database.elementIterator();
		while(tables.hasNext()){
			Element table = tables.next();
			if(table.attribute("name").getValue().equals(tablename)){
				Element data = table.addElement("data");
				Map<String, String> base = new HashMap();
				base.put(_uuid, UUID.randomUUID().toString());
				//将java对象先转化成map
				String json = gson.toJson(obj);
				Map map = gson.fromJson(json, Map.class);
				//再将uuid添加进map中，然后再转成json
				map.putAll(base);
				data.addText(gson.toJson(map));
				flag = true;
				break;
			}
		}
		Writer fileWriter = new FileWriter(path);
		XMLWriter xmlWriter = new XMLWriter(fileWriter);
		xmlWriter.write(doc);
		xmlWriter.close();
		fileWriter.close();
		
		System.out.println("------------adddata success-----------");
		return flag;
	}
	
	/**
	 * 创建数据库
	 * @param path
	 * @throws IOException
	 */
	public void createDatabase(String path) throws IOException{
		
		Document document = DocumentHelper.createDocument();
		Element database = document.addElement("database");
		Element table = database.addElement("table");
		table.addAttribute("name", "test");
		Element systemIndexs = database.addElement("table");
		systemIndexs.addAttribute("name", "system.indexs");
		Element systemUsers = database.addElement("table");
		systemUsers.addAttribute("name", "system.users");
		
		Writer fileWriter = new FileWriter(path);
		XMLWriter xmlWriter = new XMLWriter(fileWriter);
		xmlWriter.write(document);
		xmlWriter.close();
		fileWriter.close();
		System.out.println("--------------------createdatabase success------------");
	}
	
	
	
}


