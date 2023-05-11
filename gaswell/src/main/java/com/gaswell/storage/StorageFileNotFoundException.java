package com.gaswell.storage;

/**
 * @author Lei Wang
 * @Date: 2021/12/18/ 15:20
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */
public class StorageFileNotFoundException extends StorageException{
    public StorageFileNotFoundException(String message) {
        super(message);
    }

    public StorageFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
