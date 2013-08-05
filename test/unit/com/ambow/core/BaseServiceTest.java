/* 
 * BaseServiceTest.java
 * 
 * Created on 2007-4-9
 * 
 * Copyright(C) 2007, by Ambow Research&Development Branch.
 * 
 * Original Author: Peng Qing
 * Contributor(s):
 * 
 * Changes 
 * -------
 * $Log: BaseServiceTest.java,v $
 * Revision 1.1  2008/05/09 05:57:52  lixin
 * ���⿼����Ŀ
 *
 * Revision 1.2  2007/08/14 02:14:28  pengqing
 * *** empty log message ***
 *
 * Revision 1.1  2007/07/19 06:53:09  pengqing
 * *** empty log message ***
 *
 * Revision 1.1  2007/04/07 18:37:40  yaoshunxiang
 * *** empty log message ***
 *
 * Revision 1.3  2007/05/25 03:05:19  pengqing
 * fix bug, and add a error log
 *
 * Revision 1.2  2007/04/10 02:36:00  pengqing
 * �����һ������õ���
 *
 * Revision 1.1  2007/04/10 01:36:01  pengqing
 * ��ʼ��Ŀ������ambow-core
 *
 *
 */
package com.ambow.core;

import java.io.IOException;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.ext.mssql.InsertIdentityOperation;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

/**
 * 采用Spring 的自动装配，和DBUnit 的自动设置测试初始数据<br>
 * 测试完成后，将会完全清理测试中产生的数据。
 * 
 * @author Peng Qing
 * 
 */
public abstract class BaseServiceTest extends
		AbstractTransactionalDataSourceSpringContextTests {
	
	Logger log = Logger.getLogger(BaseServiceTest.class);

	protected DatabaseConnection connection;

	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * 测试开始之前，读取配置文件，使用dbunit初始化测试数据。
	 */
	protected void onSetUpBeforeTransaction() throws Exception {
		super.onSetUpBeforeTransaction();

		IDataSet dataSet = this.getDataSet();
		try {
			this.connection = new DatabaseConnection(dataSource.getConnection());
			if (dataSet != null) {
				InsertIdentityOperation.INSERT
						.execute(this.connection, dataSet);
			}
		} catch (Exception e) {
			log.error("测试初始化脚本出错，原因：" + e.getMessage());
			InsertIdentityOperation.DELETE.execute(connection, dataSet);
			throw e;
		}
	}

	/**
	 * 测试结束之后，读取配置文件，使用dbunit清空测试数据。
	 */
	protected void onTearDownAfterTransaction() throws Exception {
		super.onTearDownAfterTransaction();
		IDataSet dataSet = this.getDataSet();
		if (dataSet != null) {
			InsertIdentityOperation.DELETE.execute(connection, dataSet);
		}
		this.connection.close();
	}

	/**
	 * applicaitonContext配置文件 默认为applicationContext*.xml<br>
	 * 如果子函数需要限定载入的applicatonContext.xml,重载此函数<br>
	 * 
	 * @see org.springframework.test.AbstractTransactionalDataSourceSpringContextTests#getConfigLocations()
	 */
	protected String[] getConfigLocations() {
		setAutowireMode(AUTOWIRE_BY_NAME);

		return new String[] { "classpath*:spring/applicationContext-test.xml",
				"classpath*:spring/applicationContext-hibernate.xml",
				"classpath*:spring/applicationContext-service.xml" };
	}

	protected IDataSet getDataSet() throws DataSetException, IOException {
		ReplacementDataSet dataSet = new ReplacementDataSet(new FlatXmlDataSet(
				this.getClass().getResourceAsStream(getDataSetFile())));
		dataSet.addReplacementObject("[NULL]", null);

		return dataSet;
	}

	/**
	 * 需要在子类重载此方法，获得初始化数据的xml 文件
	 */
	protected abstract String getDataSetFile();

}
