package com.springboot.app;

import java.io.File;

/**
 * 结论：In a Java program you can't change your current working directory
 * @Description:
 * @Package: com.springboot.app
 * @Author: denglt
 * @Date: 2019-11-12 22:18
 * @Copyright: 版权归 HSYUNTAI 所有
 */
public class ChangeUserDir {
    public static void main(String[] args) {
        System.setProperty("user.dir","/Users/denglt/onGithub/springBoot"); // -Duser.dir
        File directory = new File("src/main/webapp");//设定为当前文件夹
        try{
            System.out.println(directory.getCanonicalPath());//获取标准的路径
            System.out.println(directory.getAbsolutePath());//获取绝对路径
            System.out.println(directory.exists());  // false  ，上面的user.dir并没有生效
            System.out.println(directory.isDirectory()); // false

            directory = new File("/Users/denglt/onGithub/springBoot/src/main/webapp");
            System.out.println(directory.exists()); // true
            System.out.println(directory.isDirectory()); // true
        }catch(Exception e){}
    }
}
