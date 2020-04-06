package com.xd.kobepjpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "sys_user", schema = "dbo", catalog = "kobep")
@Data
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class SysUser implements Serializable {

  @Id
  private String kid;
  @Column(name = "user_id")
  private Integer userId;
  private String username;
  private String password;
  private String email;
  private String mobile;
  private String qq;
  private String wechat;
  private String weibo;
  private String avatar;
  @Column(name = "qq_openid")
  private String qqOpenid;
  @Column(name = "wechat_openid")
  private String wechatOpenid;
  @Column(name = "weibo_openid")
  private String weiboOpenid;
  @Column(name = "create_time")
  private java.sql.Timestamp createTime;
  @Column(name = "modify_time")
  private java.sql.Timestamp modifyTime;
  @Column(name = "del_flag")
  private String delFlag;
  private Short sex;


}
