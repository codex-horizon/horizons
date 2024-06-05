package com.later.horizon.core.configurer.security;

import com.later.horizon.common.constants.Constants;
import com.later.horizon.common.exception.BusinessException;
import com.later.horizon.common.helper.CommonHelper;
import com.later.horizon.common.helper.EncryptRSAHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.bind.*;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.boot.origin.OriginTrackedValue;
import org.springframework.boot.origin.PropertySourceOrigin;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.*;
import org.springframework.util.StringUtils;

import java.util.Properties;

@Slf4j
public class EnvironmentPropertyContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final String Encrypt_Regex_Exp = "(?<=ENC\\().+(?=\\))";

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        String plaintextDecrypt = environment.getProperty(Constants.Env_Run_EnvironmentVariableEncipher);
        boolean specifiesCiphertext = Boolean.FALSE;
        try {
            specifiesCiphertext = Boolean.parseBoolean(plaintextDecrypt);
        } catch (Exception ignored) {
        }
        if (specifiesCiphertext) {
            String passwordSeed = environment.getProperty(Constants.Env_Run_EnvironmentVariablePasswordSeed);
            if (StringUtils.hasText(passwordSeed)) {
                // 获取加密公钥
                String publicKey = EncryptRSAHelper.getPublicKey(passwordSeed);
                // 解析加密配置文件
                environmentDecryptParser(environment, publicKey);
                // 移除加密公钥
                EncryptRSAHelper.removeKey(publicKey);
            } else {
                throw new BusinessException(Constants.ProveProveState.Env_Run_RSA_PasswordSeed_Empty);
            }
        }
    }

    private void environmentDecryptParser(Environment environment, String publicKey) {
        MutablePropertySources mutablePropertySources = ((ConfigurableEnvironment) environment).getPropertySources();
        Properties finalProperties = new Properties();
        for (PropertySource<?> propertySources : mutablePropertySources) {
            // ----------------------------- 解析容器原生配置文件 -----------------------------
            if (propertySources instanceof OriginTrackedMapPropertySource) {
                finalProperties.putAll(((OriginTrackedMapPropertySource) propertySources).getSource());
            }
            // ----------------------------- 解析容器个性配置文件 -----------------------------
            if (propertySources instanceof CompositePropertySource) {
                CompositePropertySource compositePropertySource = (CompositePropertySource) propertySources;
                compositePropertySource.getPropertySources().forEach(propertySource -> {
                    Properties properties = (Properties) propertySource.getSource();
                    properties.keySet().forEach(key -> finalProperties.put(key, OriginTrackedValue.of(compositePropertySource.getProperty((String) key), PropertySourceOrigin.get(propertySource, (String) key))));
                });
            }
        }
        decryptParser(finalProperties, publicKey, mutablePropertySources, environment);
    }

    private void decryptParser(Properties properties, String publicKey, MutablePropertySources mutablePropertySources, Environment environment) {
        properties.keySet().forEach(key -> {
            OriginTrackedValue originTrackedValue = (OriginTrackedValue) properties.get(key);
            String value = originTrackedValue.toString();
            if (CommonHelper.matchRegex(Encrypt_Regex_Exp, value)) {
                String finalKey = (String) key;
                String finalValue = EncryptRSAHelper.decrypt(CommonHelper.findRegex(Encrypt_Regex_Exp, value), publicKey, Boolean.FALSE);
                mutablePropertySources.remove(finalKey);
                // ----------------------------- 原生配置文件解析容器 -----------------------------
                // OriginTrackedMapPropertySource originTrackedMapPropertySource = new OriginTrackedMapPropertySource(finalKey, properties, Boolean.TRUE);

                // ----------------------------- 个性配置文件解析容器 -----------------------------
                // CompositePropertySource compositePropertySource = new CompositePropertySource(finalKey);
                // PropertySource<?> propertiesPropertySource = new PropertiesPropertySource(finalKey, properties);
                // compositePropertySource.addPropertySource(propertiesPropertySource);
                mutablePropertySources.addFirst(new PropertiesPropertySource(finalKey, new Properties() {{
                    setProperty(finalKey, finalValue);
                }}));
                BindResult<Properties> bindResult = Binder.get(environment).bind(finalKey, Bindable.of(Properties.class), new BindHandler() {
                    @Override
                    public <T> Bindable<T> onStart(ConfigurationPropertyName name, Bindable<T> target, BindContext context) {
                        log.info("In EnvironmentPropertyContextInitializer Binding Begins {} .", name);
                        // return target;
                        return BindHandler.super.onStart(name, target, context);
                    }

                    @Override
                    public Object onSuccess(ConfigurationPropertyName name, Bindable<?> target, BindContext context, Object result) {
                        log.info("In EnvironmentPropertyContextInitializer Binding Is Successful {} .", name);
                        // return result;
                        return BindHandler.super.onSuccess(name, target, context, result);
                    }

                    @Override
                    public Object onCreate(ConfigurationPropertyName name, Bindable<?> target, BindContext context, Object result) {
                        log.info("In EnvironmentPropertyContextInitializer Creation Is Successful {} .", name);
                        // return result;
                        return BindHandler.super.onCreate(name, target, context, result);
                    }

                    @Override
                    public Object onFailure(ConfigurationPropertyName name, Bindable<?> target, BindContext context, Exception error) throws Exception {
                        log.info("In EnvironmentPropertyContextInitializer Binding Failed {} .", name);
                        // return "没有找到匹配的属性";
                        return BindHandler.super.onFailure(name, target, context, error);
                    }

                    @Override
                    public void onFinish(ConfigurationPropertyName name, Bindable<?> target, BindContext context, Object result) throws Exception {
                        log.info("In EnvironmentPropertyContextInitializer Binding Ends {} .", name);
                        BindHandler.super.onFinish(name, target, context, result);
                    }
                });
                if (bindResult.isBound()) {
                    log.info("In EnvironmentPropertyContextInitializer DecryptParser Key {} Coverage Complete.", finalKey);
                }
            }
        });
        log.info("In EnvironmentPropertyContextInitializer All Coverage Complete.");
    }
}
