package com.dust.ioc.requests;

import com.dust.ioc.services.DrawShape;
import com.google.inject.Inject;

public class DrawRequest {
    DrawShape d;
    
    @Inject
    public DrawRequest(DrawShape d) {
        this.d = d;
    }
    
    public void request() {
        d.draw();
    }
}
