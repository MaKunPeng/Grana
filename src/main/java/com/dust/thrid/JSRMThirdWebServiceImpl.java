package com.dust.thrid;

import com.dust.anno.AnnotationApplicationContext;
import com.dust.service.TestThirdService;

public class JSRMThirdWebServiceImpl {
    AnnotationApplicationContext context;
    
    public JSRMThirdWebServiceImpl() {
        this.context = new AnnotationApplicationContext("com.dust.service");
    }
    
    public String process(String xml) {
        // 校验
        
        // 解析方法入参
        Object obj;
        long start = System.nanoTime();

        try {
//            String run = context.run("getTestThird", "xxxxx");
            TestThirdService service = new TestThirdService();
            service.handle("XXX");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        System.out.println(System.nanoTime() - start);
        return null;
    }
    
    public static void main(String[] args) {
        JSRMThirdWebServiceImpl serviceImpl = new JSRMThirdWebServiceImpl();
        serviceImpl.process("xxx");
    }
}
