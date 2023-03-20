package com.chatbot.adapter.converter;

import com.chatbot.adapter.SysConfigEntity;
import com.chatbot.domain.SysConfig;

public class ConfigConverter {

    public static SysConfig convert(SysConfigEntity sysConfigEntity) {
        SysConfig sysConfig = new SysConfig();
        sysConfig.setConfigName(sysConfigEntity.getConfigName());
        sysConfig.setConfigKey(sysConfig.getConfigKey());
        sysConfig.setConfigValue(sysConfig.getConfigValue());
        sysConfig.setConfigType(sysConfig.getConfigType());
        return sysConfig;
    }
}
