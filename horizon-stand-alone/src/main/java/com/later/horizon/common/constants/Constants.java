package com.later.horizon.common.constants;

import lombok.Getter;

public class Constants {

    // Request Header Keys
    public static final String Header_Application_Name = "Application-Name";
    public static final String Header_Application_Name_None = "Application-Name-None";
    public static final String Header_Trace_Id = "Trace-Id";
    public static final String Header_Trace_Id_None = "Trace-Id-None";
    public static final String Header_Access_Token = "Access-Token";
    public static final String Header_Refresh_Token = "Refresh-Token";
    public static final String Header_RSA_PublicKey = "RSA-PublicKey";
    public static final String Header_URL_Encipher = "URL_Encipher";

    // Request Session Keys
    public static final String Session_Captcha = "SessionCaptcha:";
    public static final String Session_Security_Context = "SPRING_SECURITY_CONTEXT";

    // Request From Keys
    public static final String Form_Parameter_Name_Username = "username";
    public static final String Form_Parameter_Name_Password = "password";
    public static final String Form_Parameter_Name_Code = "code";

    // Environment Run Parameters Keys
    public static final String Env_Run_Profiles_Active = "native";
    public static final String Env_Run_EncipherEnvironmentVariable = "encipherEnvironmentVariable";
    public static final String Env_Run_PasswordSeedEnvironmentVariable = "passwordSeedEnvironmentVariable";
    public static final String Env_Run_EncipherRequestUrl = "encipherRequestUrl";
    public static final String Env_Run_PasswordSeedRequestUrl = "passwordSeedRequestUrl";

    // Login System Values
    public static final String Default_Certifiers_Username_Value = "super";
    public static final String Default_Certifiers_Password_Value = "123456";

    @Getter
    public enum ProveStatus implements IEnumProve<String>, IEnumProveStatus<Long> {

        Example("Example", "这是一个例子", 1234567890L),
        ServletRequest_Obtain_Failed("ServletRequest_Obtain_Failed", "ServletRequest获取失败", 1234567890L),
        Header_RSA_PublicKey_Non_Exist("Header_RSA_PublicKey_Non_Exist", "Header_RSA_PublicKey不存在", 1234567890L),
        RSA_PublicKey_Expire("RSA_PublicKey_Expire", "RSA 公钥过期", 1234567890L),
        RSA_PublicKey_Encrypt_Failed("RSA_PublicKey_Encrypt_Failed", "RSA 公钥加密失败", 1234567890L),
        RSA_SecretKey_Initialize_Failed("RSA_PublicKey_Expire", "RSA 公钥过期", 1234567890L),
        RSA_SecretKey_Decrypt_Failed("RSA_SecretKey_Decrypt_Failed", "RSA 密钥解密失败", 1234567890L),
        Env_Run_RSA_PasswordSeed_Empty("Env_Run_RSA_PasswordSeed_Empty", "环境运行 RSA 密码种子为空", 1234567890L),
        AES_SecretKey_Encrypt_Failed("AES_SecretKey_Encrypt_Failed", "AES 密钥解密失败", 1234567890L),
        AES_SecretKey_Decrypt_Failed("AES_SecretKey_Decrypt_Failed", "AES 密钥解密失败", 1234567890L),

        Succeeded("Succeeded",  "业务处理 成功", 200L),
        Failed("Failed", "业务处理 失败", 201L),
        Warned("Warned", "业务处理 成功", 202L),

        Data_Status_Deleted("Data_Status_Deleted", "数据 已删除（硬删）", 0L),
        Data_Status_NORMAL("Data_Status_Normal",  "数据 可用中", 1L),
        Data_Status_Removed("Data_Status_Removed",  "数据 已移除（软删）", 2L),

        User_Name_Exists("User_Name_Exists", "用户名称 已存在", 1234567890L),
        User_Non_Existent("User_Non_Existent", "用户名称 不存在", 1234567890L),
        ;

        // 状态码
        private final String code;
        // 面向开发人员描述
        private final String message;
        // 用于Database数据记录状态
        private final Long status;

        ProveStatus(String code, String message, Long status) {
            this.code = code;
            this.message = message;
            this.status = status;
        }

    }

}
