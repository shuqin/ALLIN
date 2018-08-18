package cc.lovesq.web;

import lombok.Data;

@Data
public class LoginUser {

  /**
   * 域帐号
   */
  private String account;

  /**
   * buc user id
   */
  private String bucId;

  /**
   * buc account帐号(默认是邮箱)
   */
  private String bucAccount;

  /**
   * 员工工号
   */
  private String empId;

  /**
   * 姓名
   */
  private String name;

  /**
   * 花名
   */
  private String nickName;

  /**
   * 性别
   */
  private String gender;

  /**
   * 邮箱
   */
  private String email;

  /**
   * 手机
   */
  private String mobile;

  /**
   * 分机号
   */
  private String extensionPhone;

  /**
   * 贸易通
   */
  private String aliww;

  /**
   * 淘宝旺旺
   */
  private String tbww;

  /**
   * 部门名称
   */
  private String depName;

  /**
   * 用户登录 token
   */
  private String userToken;

}
