package com.later.horizon.common.constants;

import lombok.Getter;

public class Constants {
    public static final String Filter_Configurer_Authority_Key_Application_message = "Authority.ApplicationName";
    public static final String Filter_Configurer_Authority_Key_Ignore_Uris = "Authority.IgnoreUris";
    public static final String Filter_Configurer_Authority_Key_Origin = "Authority.Origin";
    public static final String Filter_Configurer_Authority_Key_Open_Csrf = "Authority.Open.Csrf";
    public static final String Header_Key_Application_Name = "Application-Name";
    public static final String Header_Application_Name_None = "Application_Name_None";
    public static final String Header_Trace_Id = "Trace-Id";
    public static final String Header_Trace_Id_None = "Trace_Id_None";
    public static final String Header_Key_Access_Token = "Access-Token";
    public static final String Header_Key_Refresh_Token = "Refresh-Token";
    public static final String Header_Key_Rsa_Public_Key = "Rsa-Public-Key";
    public static final String Form_Parameter_Username_Lowercase = "username";
    public static final String Form_Parameter_Password_Lowercase = "password";
    public static final String Session_Captcha = "SessionCaptcha:";
    public static final String Username = "super";
    public static final String Env_Cfg_PlaintextDecrypt = "Cfg.PlaintextDecrypt";
    public static final String Env_RSA_PasswordSeed = "RSA.PasswordSeed";
    public static final String Default_Administrator_Login_Username = "administrator";
    public static final String Default_Login_Password = "123456";

    @Getter
    public enum BizStatus implements IEnum<String> {

        Request_Header_PublicKey_Non_Exist("Request_Header_PublicKey_Non_Exist", "请求头公钥不存在"),
        Request_Obtain_Fail("Request_Obtain_Fail", "Request获取失败"),
        Response_Obtain_Fail("Response_Obtain_Fail", "Response获取失败"),
        Session_Obtain_Fail("Session_Obtain_Fail", "Session获取失败"),
        ServletRequestAttributes_Obtain_Fail("ServletRequestAttributes_Obtain_Fail", "ServletRequestAttributes获取失败"),
        Rsa_PublicKey_Expire("Rsa_PublicKey_Expire", "公钥失效"),
        Rsa_SecretKey_Initialize_Failed("Rsa_PublicKey_Expire", "密钥初始化失败"),
        Rsa_PublicKey_Encrypt_Failed("Rsa_PublicKey_Expire", "公钥加密失败"),
        Rsa_SecretKey_Decrypt_Failed("Rsa_PublicKey_Expire", "私钥解密失败"),
        Authentication_User_Non_Exist("Authentication_User_Non_Exist", "用户不存在"),
        Captcha_Non_Match("Session_Captcha_Non_Match", "会话验证码不匹配"),
        Jpa_Query_Service_Failed("Jpa_Query_Service_Failed", "查询服务失败"),
        Cfg_Decrypt_Obtain_Fail("Cfg_Decrypt_Obtain_Fail", "配置解密密码获取失败"),

        Sso_User_Not_Found("Sso_User_Not_Found","Sso用户未找到"),


        Sso_Client_Details_Not_Found("Sso_Client_Details_Not_Found","Sso客户端未找到"),

        ;

        private final String code;

        private final String message;

        BizStatus(String code, String message) {
            this.code = code;
            this.message = message;
        }

    }

    @Getter
    public enum BizResponseStatus implements IEnum<String> {

        Biz_Ok_Response("Biz_Ok_Response", "业务响应成功"),
        Biz_Failed_Response("Biz_Failed_Response", "业务响应失败"),

        ;

        private final String code;

        private final String message;

        BizResponseStatus(String code, String message) {
            this.code = code;
            this.message = message;
        }

    }

    @Getter
    public enum DataStatus implements IEnum<String>, IEnumStatus<Integer> {

        Deleted(0, "Deleted", "已删除"),
        Saved(1, "Saved", "已保存"),

        Disabled(2, "Disabled", "已禁用"),
        Enabled(3, "Enabled", "已启用"),

        Locked(4, "Locked", "已锁定"),
        Unlocked(5, "Unlocked", "未锁定"),
        ;

        private final Integer status;

        private final String code;

        private final String message;

        DataStatus(Integer status, String code, String message) {
            this.status = status;
            this.code = code;
            this.message = message;
        }

    }

}
