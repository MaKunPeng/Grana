package com.dust.config;

public enum Feature {
    First, SECOND, THIRD;

    private Feature() {
        mask = (1 << ordinal());
    }
    
    public final int mask;
    
    public final int getMask() {
        return mask;
    }
    
    /**
     * 判断特性是否被设置在int常量中
     * 
     * @param features
     * @param feature
     * @return
     */
    public static boolean isEnabled(int features, Feature feature) {
        return (features & feature.mask) != 0;
    }
    
    /**
     * 设置特性是否被激活
     * 
     * @param features
     * @param feature
     * @param state
     * @return
     */
    public static int config(int features, Feature feature, boolean state) {
        if (state) {
            features |= feature.mask;
        } else {
            features &= ~feature.mask;
        }
        return features;
    }
    
    /**
     * 将传入的所有特性集中在一个整型常量中
     * 
     * @param features
     * @return
     */
    public static int of(Feature[] features) {
        if (features == null) {
            return 0;
        }
        
        int value = 0;
        for (Feature feature : features) {
            value |= feature.mask;
        }
        return value;
    }

    public static void main(String[] args) {
        System.out.println(Feature.First.ordinal());
        System.err.println(Feature.THIRD.mask);
    }
}
