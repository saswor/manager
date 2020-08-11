package com.manage.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.manage.common.utils.http.HttpUtils;
import com.manage.framework.config.ManageConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 获取地址类
 * 
 */
public class AddressUtils
{
    private static final Logger log = LoggerFactory.getLogger(AddressUtils.class);

    public static final String IP_URL = "http://www.baidu.com";

    public static String getRealAddressByIP(String ip)
    {
        String address = "";
        try
        {
            if (ManageConfig.isAddressEnabled())
            {
                address = HttpUtils.sendPost(IP_URL, "ip=" + ip);
                JSONObject json = JSONObject.parseObject(address);
                JSONObject object = json.getObject("data", JSONObject.class);
                String region = object.getString("region");
                String city = object.getString("city");
                address = region + " " + city;
            }
        }
        catch (Exception e)
        {
            log.error("获取地理位置异常:", e);
        }
        return address;
    }
}
