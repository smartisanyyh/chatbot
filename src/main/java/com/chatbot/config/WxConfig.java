package com.chatbot.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;

public class WxConfig {

    @ConfigProperty(name = "wx.appId")
    String appid;
    @ConfigProperty(name = "wx.secret")
    String secret;
    @ConfigProperty(name = "wx.token", defaultValue = "xx")
    String token;
    @ConfigProperty(name = "wx.aesKey", defaultValue = "xxx")
    String aesKey;

    @ApplicationScoped
    public WxMaService wxMaService() {
        WxMaDefaultConfigImpl wxMaDefaultConfig = new WxMaDefaultConfigImpl();
        wxMaDefaultConfig.setAppid(appid);
        wxMaDefaultConfig.setSecret(secret);
        wxMaDefaultConfig.setToken(token);
        wxMaDefaultConfig.setAesKey(aesKey);
        WxMaService maService = new WxMaServiceImpl();
        maService.setWxMaConfig(wxMaDefaultConfig);
        return maService;
    }
}
