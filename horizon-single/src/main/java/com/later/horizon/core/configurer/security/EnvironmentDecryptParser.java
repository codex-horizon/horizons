package com.later.horizon.core.configurer.security;

import com.later.horizon.common.constants.Constants;
import com.later.horizon.common.exception.BizException;
import com.later.horizon.common.helper.CommonHelper;
import com.later.horizon.common.helper.RSAHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.boot.origin.OriginTrackedValue;
import org.springframework.boot.origin.PropertySourceOrigin;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.Ordered;
import org.springframework.core.env.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Properties;

@Slf4j
@Component
public class EnvironmentDecryptParser implements BeanFactoryPostProcessor, Ordered, EnvironmentAware {

    private static final String Encrypt_Regex_Exp = "(?<=ENC\\().+(?=\\))";

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE; // 让其初始化顺序最低，即最后初始化。
    }

    @Override
    public void setEnvironment(Environment environment) {
        String cfgPlaintextDecrypt = environment.getProperty(Constants.Env_Cfg_PlaintextDecrypt); // 是否启用明文密码
        if (StringUtils.hasText(cfgPlaintextDecrypt) && Boolean.parseBoolean(cfgPlaintextDecrypt)) {
            String rsaPasswordSeed = environment.getProperty(Constants.Env_RSA_PasswordSeed);
            if (StringUtils.hasText(rsaPasswordSeed)) {
                String publicKey = RSAHelper.getPublicKey(rsaPasswordSeed); // 获取加密公钥
                this.environmentParser(environment, publicKey);
                RSAHelper.removeKey(publicKey); // 移除加密公钥
            } else {
                throw new BizException(Constants.BizStatus.Cfg_Decrypt_Obtain_Fail);
            }
        }
    }

    private void environmentParser(Environment environment, String publicKey) {
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
        this.decryptParser(finalProperties, publicKey, mutablePropertySources, environment);
    }

    private void decryptParser(Properties properties, String publicKey, MutablePropertySources mutablePropertySources, Environment environment) {
        properties.keySet().forEach(key -> {
            OriginTrackedValue originTrackedValue = (OriginTrackedValue) properties.get(key);
            String value = originTrackedValue.toString();
            if (CommonHelper.matchRegex(Encrypt_Regex_Exp, value)) {
                String finalKey = (String) key;
                String finalValue = RSAHelper.decrypt(CommonHelper.findRegex(Encrypt_Regex_Exp, value), publicKey, false);
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
                BindResult<Properties> bindResult = Binder.get(environment).bind(finalKey, Bindable.of(Properties.class));
                if (bindResult.isBound()) {
                    log.trace("in EnvironmentDecryptParser decryptParser key:{} coverage complete.", finalKey);
                }
            }
        });
        log.trace("in EnvironmentDecryptParser all coverage complete.");
    }

}
