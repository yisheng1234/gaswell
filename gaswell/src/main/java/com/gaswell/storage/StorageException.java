package com.gaswell.storage;

/**
 * @author Lei Wang
 * @Date: 2021/12/18/ 15:19
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */
public class StorageException extends RuntimeException{
    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
