package com.springboot.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * 诊断
 */
public class IcdDisease implements Serializable{

    private static final long serialVersionUID = 4856973525588417136L;

    @TableId(type = IdType.INPUT)
    private String icdCode;                 //ICD-10编码

    private String icdName;                 //ICD诊断名称

    private String icdPinyin;               //ICD首字母

    private String icdWb;                   //ICD五笔码

    private Integer status;                 //状态(0：未启用，1：启用)

    private Integer diseaseType;            //诊断类型(0:普通诊断,1:常见诊断)

    private Long    docId;                  //医生ID

    private Integer standard;                //是否标准ICD，1-是，0-非

    private Integer diseaseCategory = 1;    // 诊断分类：1-西医, 2-中药

    public String getIcdCode() {
        return icdCode;
    }

    public void setIcdCode(String icdCode) {
        this.icdCode = icdCode == null ? null : icdCode.trim();
    }

    public String getIcdName() {
        return icdName;
    }

    public void setIcdName(String icdName) {
        this.icdName = icdName == null ? null : icdName.trim();
    }

    public String getIcdPinyin() {
        return icdPinyin;
    }

    public void setIcdPinyin(String icdPinyin) {
        this.icdPinyin = icdPinyin == null ? null : icdPinyin.trim();
    }

    public String getIcdWb() {
        return icdWb;
    }

    public void setIcdWb(String icdWb) {
        this.icdWb = icdWb;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDiseaseType() {
        return diseaseType;
    }

    public void setDiseaseType(Integer diseaseType) {
        this.diseaseType = diseaseType;
    }

    public Long getDocId() {
        return docId;
    }

    public void setDocId(Long docId) {
        this.docId = docId;
    }

    public Integer getStandard() {
        return standard;
    }

    public void setStandard(Integer standard) {
        this.standard = standard;
    }

    public Integer getDiseaseCategory() {
        return diseaseCategory;
    }

    public void setDiseaseCategory(Integer diseaseCategory) {
        this.diseaseCategory = diseaseCategory;
    }
}