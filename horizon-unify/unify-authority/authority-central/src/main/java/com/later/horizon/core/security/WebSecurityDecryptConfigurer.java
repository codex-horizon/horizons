package com.later.horizon.core.security;

import com.later.horizon.common.constants.Constants;
import com.later.horizon.common.helper.CommonHelper;
import com.later.horizon.common.helper.RSAHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.origin.OriginTrackedValue;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.Ordered;
import org.springframework.core.env.*;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Properties;

@Component
public class WebSecurityDecryptConfigurer extends AbstractEnvironment implements BeanFactoryPostProcessor, EnvironmentAware, Ordered {

    private static final Logger Logger = LoggerFactory.getLogger(WebSecurityDecryptConfigurer.class);

    private static final String EncryptRegex = "(?<=ENC\\().+(?=\\))";

    private static void decryptRefreshProperties(PropertySource<?> propertySource, Environment environment, MutablePropertySources propertySources, String publicKey) {
        @SuppressWarnings("unchecked") Map<String, Object> source = (Map<String, Object>) propertySource.getSource();
        Properties properties = new Properties();
        for (String key : source.keySet()) {
            Object value = source.get(key);
            if (ObjectUtils.isEmpty(value)) {
                continue;
            }
            if (value instanceof String) {
                if (CommonHelper.matchRegex(EncryptRegex, String.valueOf(value))) {
                    String str = CommonHelper.findRegex(EncryptRegex, String.valueOf(value));
                    String decrypt = RSAHelper.decrypt(str, publicKey, false);
                    properties.put(key, decrypt);
                }
            }
            if (value instanceof OriginTrackedValue) {
                Object finalValue = ((OriginTrackedValue) value).getValue();
                if (CommonHelper.matchRegex(EncryptRegex, String.valueOf(finalValue))) {
                    String str = CommonHelper.findRegex(EncryptRegex, String.valueOf(finalValue));
                    String decrypt = RSAHelper.decrypt(str, publicKey, false);
                    properties.put(key, decrypt);
                }
            }
        }
        if (!CollectionUtils.isEmpty(properties)) {
            for (Object key : properties.keySet()) {
                String finalKey = (String) key;
                propertySources.remove(finalKey);
                Properties finalProperties = new Properties();
                finalProperties.setProperty(finalKey, properties.getProperty(finalKey));
                propertySources.addFirst(new PropertiesPropertySource(finalKey, finalProperties));
                BindResult<Properties> bindResult = Binder.get(environment).bind(finalKey, Bindable.of(Properties.class));
                if (bindResult.isBound()) {
                    if (Logger.isDebugEnabled()) {
                        Logger.debug("in WebSecurityDecryptConfigurer decryptRefreshProperties {} coverage complete.", finalKey);
                    }
                }
            }
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
    }

    @Override
    public void setEnvironment(Environment environment) {
        // 获取用户自定义明文密码
        String passwordSeed = environment.getProperty(Constants.RSA_PasswordSeed);
        if (StringUtils.hasText(passwordSeed)) {
            // 获取加密公钥
            String publicKey = RSAHelper.getPublicKey(passwordSeed);

            MutablePropertySources propertySources = ((ConfigurableEnvironment) environment).getPropertySources();
            for (PropertySource<?> propertySource : propertySources) {
                if (propertySource instanceof MapPropertySource) {
                    decryptRefreshProperties(propertySource, environment, propertySources, publicKey);
                }
            }

            // 移除加密公钥
            RSAHelper.removeKey(publicKey);
        }
    }

    @Override
    public int getOrder() {
        // 让其初始化顺序最低，即最后初始化。
        return Ordered.LOWEST_PRECEDENCE;
    }

}
