package dianfan.entities;

import java.util.ArrayList;
/**
 * @ClassName DataTable
 * @Description datatable返回参数模型
 * @author cjy
 * @date 2018年1月10日 上午11:00:03
 */
public class DataTable {

	private String draw;
	private Integer recordsTotal; //记录的总条数
	private Integer recordsFiltered; //显示的条数
	private Object data; //数据集合
	private String error; //错误信息
	
	public DataTable() {
		this.recordsTotal = 0;
		this.recordsFiltered = 0;
		this.data = new ArrayList<>();
	}
	
	public String getDraw() {
		return draw;
	}
	public void setDraw(String draw) {
		this.draw = draw;
	}
	public Integer getRecordsTotal() {
		return recordsTotal;
	}
	public void setRecordsTotal(Integer recordsTotal) {
		this.recordsTotal = recordsTotal;
	}
	public Integer getRecordsFiltered() {
		return recordsFiltered;
	}
	public void setRecordsFiltered(Integer recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return "DataTable [draw=" + draw + ", recordsTotal=" + recordsTotal + ", recordsFiltered=" + recordsFiltered
				+ ", data=" + data + ", error=" + error + "]";
	}
}
