package org.jeecgframework.test;

import java.io.IOException;
import java.util.List;

import org.dom4j.DocumentException;
import org.jeecgframework.database.MiniDatabase;

/**   
 *  
 * @Description: 
 * @author ly   
 * @date 2014-6-1 下午5:12:48 
 *    
 */
public class MiniDatabaseTest {
	
	private static final String _uuid = "3db78e08-efa0-457d-b0e7-6a2d7fd6d01a";
	private static final String databseXml = "mini-database.xml";
	private MiniDatabase dao = new MiniDatabase();
	
	
	public static void main(String[] args) throws IOException {
//		new MiniDatabaseTest().createDatabase();
//		new MiniDatabaseTest().insertData();
//		new MiniDatabaseTest().updateData();
//		new MiniDatabaseTest().deleteData();
		new MiniDatabaseTest().getAll();
	}
	
	/**
	 * 创建数据库
	 */
	public void createDatabase(){
		try {
			dao.createDatabase(databseXml);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 插入数据
	 */
	public void insertData(){
		Person person = new Person();
		person.set_uuid(_uuid);
		person.setAge(14);
		person.setName("麦克");
		person.setSex("男");
		person.setMoney(5000.50);
		try {
			dao.addData(databseXml, "test", person);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 修改数据
	 */
	public void updateData(){
		Person person = new Person();
		person.set_uuid(_uuid);
		person.setAge(15);
		person.setName("海尔");
		person.setSex("男");
		person.setMoney(6000.50);
		try {
			dao.updateData(databseXml, "test", person);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除数据
	 */
	public void deleteData(){
		Person person = new Person();
		person.set_uuid(_uuid);
		try {
			dao.deleteData(databseXml, "test", person);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getAll(){
		try {
			List list = dao.loadDatas(databseXml, "test");
			System.out.println(list.toString());
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}


