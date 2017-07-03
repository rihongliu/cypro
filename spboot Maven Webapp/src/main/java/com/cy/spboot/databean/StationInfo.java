package com.cy.spboot.databean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * StationInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "STATION_INFO")
public class StationInfo implements java.io.Serializable {

	// Fields

	private Long pkId;		// 
	private String name;		// 站名
	private String code;		// 站号
	private String type;		// 站类型(0国家站、1自动站、2区域站)
	private String height;		// 海拔高度
	private String longitude;	// 经度
	private String latitude;	// 纬度
	private Long orgId;			// 所属单位
	private String orgName;		// 所属单位名称
	private Long disId;			// 所属地区ID
	private String disName;		// 所属地区名称
	private String state ;		// 该站点是否在用
	private String disCode ;	// 所属地区CODE 
	private Integer sortId; 		//用来排序的字段
	
	/** 9个格点的插值 */
	protected double interValue;

	// Constructors

	/** default constructor */
	public StationInfo() {
	}

	/** minimal constructor */
	public StationInfo(Long pkId) {
		this.pkId = pkId;
	}

	/** full constructor */
	public StationInfo(Long pkId, String name, String code, String type,
			String height, String longitude, String latitude, Long orgId,
			String orgName, Long disId, String disName,Integer sortId) {
		this.pkId = pkId;
		this.name = name;
		this.code = code;
		this.type = type;
		this.height = height;
		this.longitude = longitude;
		this.latitude = latitude;
		this.orgId = orgId;
		this.orgName = orgName;
		this.disId = disId;
		this.disName = disName;
		this.sortId = sortId;
	}

	// Property accessors
	@Id
	@SequenceGenerator(name="seq_generator",sequenceName="SEQ_STATION_INFO",allocationSize=1)
	@GeneratedValue(generator="seq_generator",strategy=GenerationType.SEQUENCE)
	@Column(name = "PK_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getPkId() {
		return this.pkId;
	}

	public void setPkId(Long pkId) {
		this.pkId = pkId;
	}

	@Column(name = "NAME", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "CODE", length = 50)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "TYPE", length = 10)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "HEIGHT", length = 10)
	public String getHeight() {
		return this.height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	@Column(name = "LONGITUDE", length = 10)
	public String getLongitude() {
		return this.longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	@Column(name = "LATITUDE", length = 10)
	public String getLatitude() {
		return this.latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	@Column(name = "ORG_ID", precision = 22, scale = 0)
	public Long getOrgId() {
		return this.orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	@Column(name = "ORG_NAME", length = 100)
	public String getOrgName() {
		return this.orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@Column(name = "DIS_ID", precision = 22, scale = 0)
	public Long getDisId() {
		return this.disId;
	}

	public void setDisId(Long disId) {
		this.disId = disId;
	}

	@Column(name = "DIS_NAME", length = 100)
	public String getDisName() {
		return this.disName;
	}

	public void setDisName(String disName) {
		this.disName = disName;
	}
	@Column(name = "STATE", length = 100)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	@Column(name = "DIS_CODE", length = 100)
	public String getDisCode() {
		return disCode;
	}

	public void setDisCode(String disCode) {
		this.disCode = disCode;
	}

	@Transient
	public double getInterValue() {
		return interValue;
	}

	public void setInterValue(double interValue) {
		this.interValue = interValue;
	}

	@Column(name = "SORT_ID", precision = 19, scale = 0)
	public Integer getSortId() {
		return sortId;
	}

	public void setSortId(Integer sortId) {
		this.sortId = sortId;
	}
	

}