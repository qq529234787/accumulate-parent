package com.wme.common.utils.poi;

import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;

/**
 * @Title: 封装Excel导出的内容设置
 * @Auther: ming
 * @Date: 2017/10/21
 * @Version: 1.0
 */
public class ExcelEntry implements Serializable {
		
	private static final long serialVersionUID = 1L;

	/**
	 *  导出的 数据List
	 */
	private List<?> dataList ;

	/**
	 *  对应每个sheet里的标题，即顶部大字
	 */
	private String[] titles;

	/**
	 *  对应每个页签的表头的每一列的名称
	 */
	private List<String[]> headNames;

	/**
	 *  对应每个sheet里的每行数据的对象的属性名称
	 */
	private List<String[]> fieldNames;

	/**
	 *  输出数据的流
	 */
	private OutputStream out;


	public List<?> getDataList() {
		return dataList;
	}

	public void setDataList(List<?> dataList) {
		this.dataList = dataList;
	}

	public String[] getTitles() {
		return titles;
	}

	public void setTitles(String[] titles) {
		this.titles = titles;
	}

	public List<String[]> getHeadNames() {
		return headNames;
	}

	public void setHeadNames(List<String[]> headNames) {
		this.headNames = headNames;
	}

	public List<String[]> getFieldNames() {
		return fieldNames;
	}

	public void setFieldNames(List<String[]> fieldNames) {
		this.fieldNames = fieldNames;
	}

	public OutputStream getOut() {
		return out;
	}

	public void setOut(OutputStream out) {
		this.out = out;
	}
}
