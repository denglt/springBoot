package com.springboot.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 药品目录
 */
@TableName("olt_dp_drug_catalogue")
public class DrugCatalogue implements Serializable{

    private static final long serialVersionUID = -2248027251566926465L;

    @TableId(type = IdType.INPUT)
    private Long drugId;                //药品目录流水

    private String drugCode;            //药品目录编号

    private String drugChemName;        //化学名-通用名

    private String drugPinyin;          //首字母

    private String drugWb;              //化学名五笔码

    private String drugTradeName;       //商品名

    private String drugSpec;            //规格

    private String drugDose;            //剂型

    private String drugManufaturer;     //生产厂家

    private String drugLicenseNo;       //国药准字

    private Integer drugSourceType;     //药品来源 药品目录来源1华润，2国药

    private String drugPackingUnit;     //包装单位

    private String drugDosageUnit;      //含量单位

    private Double drugDosageNum;       //含量

    private Double drugApperanceNum;    //药品形状数量

    private String drugApperanceUnit;   //药品形状单位

    private String drugTradeCode;       //药品交易平台编码

    private Integer drugSpecType;       //一品两规：1-国产；2-进口；3-合资

    private Integer status;             //药品状态(0：未启用，1：启用)

    private String remark;              //备注

    private String drugPhoto;           //药品图片

    private Integer type;               //0-药品，1-商品，2-保健品

    private Integer dismounting;        //是否拆零销售，1-是，0/null-否

    private Integer ruSynced;

    private Date ruSyncTime;


    @TableField(exist = false)
    private String tcmPackSpecSet;


    /**
     * 医院药品编码
     */
    @TableField(exist = false)
    private String hosDrugCode;



    private String relDrugCode;  // 关联药品编码

    private Integer lackStatus; // 缺货状态：0:有货；1:缺货

    private Date auditCommitTime; //审核提交时间

    private Integer auditStatus; //审核状态：0:未审核 1:已审核

    private String auditDesc; //修改字段记录

    private String  defaultUsageCode ; // 默认用药方式编码

    private String defaultFrequencyCode; // 默认用药频次编码

    private String defaultRemarkCode; //默认服药时间编码

    private Integer hosAdminStatus; //监管平台 0:停用 1:启用

    private Integer coldChain;

    private String batchNo;

    /**
     * 是否管制标识
     */
    private Integer controlFlag;

    /*
     * 是否可配送
     */
    private Integer deliveryFlag;

    private String drugBarCode;

    private Date createTime;

    private Integer dailyUseSplitFlag; // 每天用药标记:0-不按天拆分或不支持拆分,1-按天拆分

    // 库存数量
    private Long stock;

    private Integer requiredMediLevel;//开药所需要的医生职称

    private Integer maxAllowedCount;

    private Integer medInsLimitFlag; // 是否为医保范围限制药品(0:不是 1:是)

    private String medInsLimitDesc;  // 医保限制范围说明

    private String drugUseRemark; //开方用药备注

    /**
     * 是否支持配置送到院: 0-不支持, 1-支持
     */
    private Integer deliveryHosFlag;

    /**
     * 是否支持配置送到院: 0-不支持, 1-支持
     */
    private Integer deliveryPivasFlag;

    private String drugLibraryCode;
    private String defaultDecoctionMethodCode;
    private String defaultDecoctionMethodName;

    // 药品医保编码
    private String medInsCode;

    public void setDefaultDecoctionMethodName(String defaultDecoctionMethodName) {
        this.defaultDecoctionMethodName = defaultDecoctionMethodName;
    }

    public String getDefaultDecoctionMethodName() {
        return defaultDecoctionMethodName;
    }

    private String drugProductionPlace;

    public Integer getMaxAllowedCount() {
        return maxAllowedCount;
    }

    public void setMaxAllowedCount(Integer maxAllowedCount) {
        this.maxAllowedCount = maxAllowedCount;
    }

    public Integer getRequiredMediLevel() {
        return requiredMediLevel;
    }

    public void setRequiredMediLevel(Integer requiredMediLevel) {
        this.requiredMediLevel = requiredMediLevel;
    }

    public Integer getControlFlag() {
        return controlFlag;
    }

    public void setControlFlag(Integer controlFlag) {
        this.controlFlag = controlFlag;
    }

    public Long getDrugId() {
        return drugId;
    }

    public void setDrugId(Long drugId) {
        this.drugId = drugId;
    }

    public String getDrugCode() {
        return drugCode;
    }

    public void setDrugCode(String drugCode) {
        this.drugCode = drugCode;
    }

    public String getDrugChemName() {
        return drugChemName;
    }

    public void setDrugChemName(String drugChemName) {
        this.drugChemName = drugChemName;
    }

    public String getDrugPinyin() {
        return drugPinyin;
    }

    public void setDrugPinyin(String drugPinyin) {
        this.drugPinyin = drugPinyin;
    }

    public String getDrugWb() {
        return drugWb;
    }

    public void setDrugWb(String drugWb) {
        this.drugWb = drugWb;
    }

    public String getDrugTradeName() {
        return drugTradeName;
    }

    public void setDrugTradeName(String drugTradeName) {
        this.drugTradeName = drugTradeName;
    }

    public String getDrugSpec() {
        return drugSpec;
    }

    public void setDrugSpec(String drugSpec) {
        this.drugSpec = drugSpec;
    }

    public String getDrugDose() {
        return drugDose;
    }

    public void setDrugDose(String drugDose) {
        this.drugDose = drugDose;
    }

    public String getDrugManufaturer() {
        return drugManufaturer;
    }

    public void setDrugManufaturer(String drugManufaturer) {
        this.drugManufaturer = drugManufaturer;
    }

    public String getDrugLicenseNo() {
        return drugLicenseNo;
    }

    public void setDrugLicenseNo(String drugLicenseNo) {
        this.drugLicenseNo = drugLicenseNo;
    }

    public Integer getDrugSourceType() {
        return drugSourceType;
    }

    public void setDrugSourceType(Integer drugSourceType) {
        this.drugSourceType = drugSourceType;
    }

    public String getDrugPackingUnit() {
        return drugPackingUnit;
    }

    public void setDrugPackingUnit(String drugPackingUnit) {
        this.drugPackingUnit = drugPackingUnit;
    }

    public String getDrugDosageUnit() {
        return drugDosageUnit;
    }

    public void setDrugDosageUnit(String drugDosageUnit) {
        this.drugDosageUnit = drugDosageUnit;
    }

    public Double getDrugDosageNum() {
        return drugDosageNum;
    }

    public void setDrugDosageNum(Double drugDosageNum) {
        this.drugDosageNum = drugDosageNum;
    }

    public Double getDrugApperanceNum() {
        return drugApperanceNum;
    }

    public void setDrugApperanceNum(Double drugApperanceNum) {
        this.drugApperanceNum = drugApperanceNum;
    }

    public String getDrugApperanceUnit() {
        return drugApperanceUnit;
    }

    public void setDrugApperanceUnit(String drugApperanceUnit) {
        this.drugApperanceUnit = drugApperanceUnit;
    }

    public String getDrugTradeCode() {
        return drugTradeCode;
    }

    public void setDrugTradeCode(String drugTradeCode) {
        this.drugTradeCode = drugTradeCode;
    }

    public Integer getDrugSpecType() {
        return drugSpecType;
    }

    public void setDrugSpecType(Integer drugSpecType) {
        this.drugSpecType = drugSpecType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDrugPhoto() {
        return drugPhoto;
    }

    public void setDrugPhoto(String drugPhoto) {
        this.drugPhoto = drugPhoto;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getDismounting() {
        return dismounting;
    }

    public void setDismounting(Integer dismounting) {
        this.dismounting = dismounting;
    }

    public Integer getRuSynced() {
        return ruSynced;
    }

    public void setRuSynced(Integer ruSynced) {
        this.ruSynced = ruSynced;
    }

    public Date getRuSyncTime() {
        return ruSyncTime;
    }

    public void setRuSyncTime(Date ruSyncTime) {
        this.ruSyncTime = ruSyncTime;
    }

    public String getRelDrugCode() {
        return relDrugCode;
    }

    public void setRelDrugCode(String relDrugCode) {
        this.relDrugCode = relDrugCode;
    }

    public Integer getLackStatus() {
        return lackStatus;
    }

    public void setLackStatus(Integer lackStatus) {
        this.lackStatus = lackStatus;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Date getAuditCommitTime() {
        return auditCommitTime;
    }

    public void setAuditCommitTime(Date auditCommitTime) {
        this.auditCommitTime = auditCommitTime;
    }

    public String getAuditDesc() {
        return auditDesc;
    }

    public void setAuditDesc(String auditDesc) {
        this.auditDesc = auditDesc;
    }

    public String getDefaultUsageCode() {
        return defaultUsageCode;
    }

    public void setDefaultUsageCode(String defaultUsageCode) {
        this.defaultUsageCode = defaultUsageCode;
    }

    public String getDefaultFrequencyCode() {
        return defaultFrequencyCode;
    }

    public void setDefaultFrequencyCode(String defaultFrequencyCode) {
        this.defaultFrequencyCode = defaultFrequencyCode;
    }

    public String getDefaultRemarkCode() {
        return defaultRemarkCode;
    }

    public void setDefaultRemarkCode(String defaultRemarkCode) {
        this.defaultRemarkCode = defaultRemarkCode;
    }

    public Integer getHosAdminStatus() {
        return hosAdminStatus;
    }

    public void setHosAdminStatus(Integer hosAdminStatus) {
        this.hosAdminStatus = hosAdminStatus;
    }

    public Integer getColdChain() {
        return coldChain;
    }

    public void setColdChain(Integer coldChain) {
        this.coldChain = coldChain;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Integer getDeliveryFlag() {
        return deliveryFlag;
    }

    public void setDeliveryFlag(Integer deliveryFlag) {
        this.deliveryFlag = deliveryFlag;
    }

    public String getDrugBarCode() {
        return drugBarCode;
    }

    public void setDrugBarCode(String drugBarCode) {
        this.drugBarCode = drugBarCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getDailyUseSplitFlag() {
        return dailyUseSplitFlag;
    }

    public void setDailyUseSplitFlag(Integer dailyUseSplitFlag) {
        this.dailyUseSplitFlag = dailyUseSplitFlag;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public Integer getMedInsLimitFlag() {
        return medInsLimitFlag;
    }

    public void setMedInsLimitFlag(Integer medInsLimitFlag) {
        this.medInsLimitFlag = medInsLimitFlag;
    }

    public String getMedInsLimitDesc() {
        return medInsLimitDesc;
    }

    public void setMedInsLimitDesc(String medInsLimitDesc) {
        this.medInsLimitDesc = medInsLimitDesc;
    }

    public String getDrugUseRemark() {
        return drugUseRemark;
    }

    public void setDrugUseRemark(String drugUseRemark) {
        this.drugUseRemark = drugUseRemark;
    }


    public String getHosDrugCode() {
        return hosDrugCode;
    }

    public void setHosDrugCode(String hosDrugCode) {
        this.hosDrugCode = hosDrugCode;
    }

    public Integer getDeliveryHosFlag() {
        return deliveryHosFlag;
    }

    public void setDeliveryHosFlag(Integer deliveryHosFlag) {
        this.deliveryHosFlag = deliveryHosFlag;
    }

    public Integer getDeliveryPivasFlag() {
        return deliveryPivasFlag;
    }

    public void setDeliveryPivasFlag(Integer deliveryPivasFlag) {
        this.deliveryPivasFlag = deliveryPivasFlag;
    }

    public String getDrugLibraryCode() {
        return drugLibraryCode;
    }

    public void setDrugLibraryCode(String drugLibraryCode) {
        this.drugLibraryCode = drugLibraryCode;
    }

    public String getDefaultDecoctionMethodCode() {
        return defaultDecoctionMethodCode;
    }

    public void setDefaultDecoctionMethodCode(String defaultDecoctionMethodCode) {
        this.defaultDecoctionMethodCode = defaultDecoctionMethodCode;
    }

    public String getTcmPackSpecSet() {
        return tcmPackSpecSet;
    }

    public void setTcmPackSpecSet(String tcmPackSpecSet) {
        this.tcmPackSpecSet = tcmPackSpecSet;
    }

    public String getDrugProductionPlace() {
        return drugProductionPlace;
    }

    public void setDrugProductionPlace(String drugProductionPlace) {
        this.drugProductionPlace = drugProductionPlace;
    }

    public String getMedInsCode() {
        return medInsCode;
    }

    public void setMedInsCode(String medInsCode) {
        this.medInsCode = medInsCode;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DrugCatalogue{");
        sb.append("drugId=").append(drugId);
        sb.append(", drugCode='").append(drugCode).append('\'');
        sb.append(", drugChemName='").append(drugChemName).append('\'');
        sb.append(", drugPinyin='").append(drugPinyin).append('\'');
        sb.append(", drugWb='").append(drugWb).append('\'');
        sb.append(", drugTradeName='").append(drugTradeName).append('\'');
        sb.append(", drugSpec='").append(drugSpec).append('\'');
        sb.append(", drugDose='").append(drugDose).append('\'');
        sb.append(", drugManufaturer='").append(drugManufaturer).append('\'');
        sb.append(", drugLicenseNo='").append(drugLicenseNo).append('\'');
        sb.append(", drugSourceType=").append(drugSourceType);
        sb.append(", drugPackingUnit='").append(drugPackingUnit).append('\'');
        sb.append(", drugDosageUnit='").append(drugDosageUnit).append('\'');
        sb.append(", drugDosageNum=").append(drugDosageNum);
        sb.append(", drugApperanceNum=").append(drugApperanceNum);
        sb.append(", drugApperanceUnit='").append(drugApperanceUnit).append('\'');
        sb.append(", drugTradeCode='").append(drugTradeCode).append('\'');
        sb.append(", drugSpecType=").append(drugSpecType);
        sb.append(", status=").append(status);
        sb.append(", remark='").append(remark).append('\'');
        sb.append(", drugPhoto='").append(drugPhoto).append('\'');
        sb.append(", type=").append(type);
        sb.append(", dismounting=").append(dismounting);
        sb.append(", ruSynced=").append(ruSynced);
        sb.append(", ruSyncTime=").append(ruSyncTime);
        sb.append(", tcmPackSpecSet='").append(tcmPackSpecSet).append('\'');
        sb.append(", hosDrugCode='").append(hosDrugCode).append('\'');
        sb.append(", relDrugCode='").append(relDrugCode).append('\'');
        sb.append(", lackStatus=").append(lackStatus);
        sb.append(", auditCommitTime=").append(auditCommitTime);
        sb.append(", auditStatus=").append(auditStatus);
        sb.append(", auditDesc='").append(auditDesc).append('\'');
        sb.append(", defaultUsageCode='").append(defaultUsageCode).append('\'');
        sb.append(", defaultFrequencyCode='").append(defaultFrequencyCode).append('\'');
        sb.append(", defaultRemarkCode='").append(defaultRemarkCode).append('\'');
        sb.append(", hosAdminStatus=").append(hosAdminStatus);
        sb.append(", coldChain=").append(coldChain);
        sb.append(", batchNo='").append(batchNo).append('\'');
        sb.append(", controlFlag=").append(controlFlag);
        sb.append(", deliveryFlag=").append(deliveryFlag);
        sb.append(", drugBarCode='").append(drugBarCode).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append(", dailyUseSplitFlag=").append(dailyUseSplitFlag);
        sb.append(", stock=").append(stock);
        sb.append(", requiredMediLevel=").append(requiredMediLevel);
        sb.append(", maxAllowedCount=").append(maxAllowedCount);
        sb.append(", medInsLimitFlag=").append(medInsLimitFlag);
        sb.append(", medInsLimitDesc='").append(medInsLimitDesc).append('\'');
        sb.append(", drugUseRemark='").append(drugUseRemark).append('\'');
        sb.append(", deliveryHosFlag=").append(deliveryHosFlag);
        sb.append(", deliveryPivasFlag=").append(deliveryPivasFlag);
        sb.append(", drugLibraryCode='").append(drugLibraryCode).append('\'');
        sb.append(", defaultDecoctionMethodCode='").append(defaultDecoctionMethodCode).append('\'');
        sb.append(", defaultDecoctionMethodName='").append(defaultDecoctionMethodName).append('\'');
        sb.append(", medInsCode='").append(medInsCode).append('\'');
        sb.append(", drugProductionPlace='").append(drugProductionPlace).append('\'');
        sb.append('}');
        return sb.toString();
    }
}