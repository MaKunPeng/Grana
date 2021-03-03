package com.dust.xml.data;

import java.io.Serializable;
import java.util.List;

public class DustConfig implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private int connectLimit;
    private List<String> hostIpList;
    private String clusterName;
    
    public int getConnectLimit() {
        return connectLimit;
    }
    public void setConnectLimit(int connectLimit) {
        this.connectLimit = connectLimit;
    }
    public List<String> getHostIpList() {
        return hostIpList;
    }
    public void setHostIpList(List<String> hostIpList) {
        this.hostIpList = hostIpList;
    }
    public String getClusterName() {
        return clusterName;
    }
    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }
    
    @Override
    public String toString() {
        return "DustConfig [connectLimit=" + connectLimit + ", hostIpList=" + hostIpList + ", clusterName="
                + clusterName + "]";
    }
    
}
