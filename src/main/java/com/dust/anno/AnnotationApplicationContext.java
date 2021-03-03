package com.dust.anno;

import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.dust.service.IService;

public class AnnotationApplicationContext {
    private Map<String, Class<?>> beanMap = new HashMap<>();
    
    public AnnotationApplicationContext(String rootPackagePath) {
        scanPackage(rootPackagePath);
    }

    /**
     * 根据给定的包路径返回该路径下所有符合条件的带注解的类
     * @param rootPackagePath
     */
    private void scanPackage(String rootPackagePath) {
        String pkgDir = rootPackagePath.replaceAll("\\.", "/");
        URL url = Thread.currentThread().getContextClassLoader().getResource(pkgDir);
        File file = new File(url.getFile());
        File[] listFiles = file.listFiles(new FileFilter() {
            
            @Override
            public boolean accept(File file) {
                String fileName = file.getName();
                if (file.isDirectory()) {
                    // 递归遍历目录树
                    scanPackage(rootPackagePath + "." + fileName);
                } else {
                    // 判断文件后缀
                    if (fileName.endsWith(".class")) {
                        return true;
                    }
                }
                return false;
            }
        });

        for (File classFile : listFiles) {
            String fName = classFile.getName();
            String className = fName.substring(0, fName.lastIndexOf("."));
            // 构建全类名
            String qualifiedClassName = rootPackagePath + "." + className;
            try {
                // 加载类，不初始化
                Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(qualifiedClassName);
                // 获取指定类型注解
                JSRMThridService annotation = clazz.getAnnotation(JSRMThridService.class);
                if (annotation != null) {
                    String name = annotation.name();
                    beanMap.put(name, clazz);                    
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    
    
    public String run(String name, String param) throws Exception {
        Class<?> clazz = this.beanMap.get(name);
        String result = null;
        if (clazz != null) {
            IService service;
            try {
                service = (IService) clazz.newInstance();
                result = service.handle(param);
            } catch (Exception e) {
                throw new Exception(e);
            } 
        }
        
        return result;
    }
    
}
