package com.gaswell.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Lei Wang
 * @Date: 2021/12/18/ 15:21
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */
@ConfigurationProperties("storage")
public class StorageProperties {
    /**
     * Folder location for storing files
     */
    private String location = "upload-dir";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
