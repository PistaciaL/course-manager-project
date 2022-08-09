package org.lr.service;

import org.lr.handler.MyException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class AvatarService {

    private static final String PATH_PREFIX=System.getProperty("user.dir")+"/src/main/resources/static/";
    private static final String AVATAR_PATH_PREFIX = "avatar-img/";

    @Async("MyThreadPoolExecutor")
    public void uploadAvatar(byte[] b, String fileName) throws IOException {
        String fullPath = PATH_PREFIX+AVATAR_PATH_PREFIX+fileName;
        File file = new File(fullPath);
        if(!file.exists()){
            file.createNewFile();
            FileOutputStream out = new FileOutputStream(file);
            out.write(b);
            out.close();
        }
    }

    public byte[] downloadAvatar(String fileName) throws MyException, IOException {
        String fullPath = PATH_PREFIX+AVATAR_PATH_PREFIX+fileName;
        File file = new File(fullPath);
        if(!file.exists() || !file.canRead()){
            throw new MyException("头像文件不存在");
        }
        FileInputStream inputStream = new FileInputStream(file);
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        inputStream.close();
        return bytes;
    }
}
