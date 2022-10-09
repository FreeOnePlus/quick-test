package com.freeoneplus.quick_test.common;

import org.springframework.stereotype.Component;

/**
 * 基础数据类型的随机生成核心方法
 */
@Component
public class BasicDataTypeRandom {

    private final String LOWER_CHARS = "abcdefghijklmnopqistuvwxyz";
    private final String UPPER_CHARS = "ABCDEFGHIJKLMNOPQISTUVWXYZ";
    private final String ALL_CHARS = "abcdefghijklmnopqistuvwxyzABCDEFGHIJKLMNOPQISTUVWXYZ";

    /**
     * 获取一个随机小写字母
     * @return
     */
    public char getLowerChar(){
        int index = (int) Math.floor(Math.random() * 26);
        return LOWER_CHARS.charAt(index);
    }

    /**
     * 获取一个随机大写字母
     * @return
     */
    public char getUpperChar(){
        int index = (int) Math.floor(Math.random() * 26);
        return UPPER_CHARS.charAt(index);
    }

    /**
     * 获取一个随机大小写字母
     * @return
     */
    public char getChar(){
        int index = (int) Math.floor(Math.random() * 52);
        return ALL_CHARS.charAt(index);
    }

    /**
     * 获取一个范围内的随机整数，结果是左闭右开区间
     * @param begin
     * @param end
     * @return [begin,end)
     */
    public long getLong(long begin , long end){
        return (long) Math.floor(Math.random() * (end - begin)) + begin;
    }

    /**
     * 获取一个范围内的随机整数，结果是左闭右开区间
     * @param begin
     * @param end
     * @return [begin,end)
     */
    public int getInteger(int begin , int end){
        return (int) Math.floor(Math.random() * (end - begin)) + begin;
    }

    /**
     * 获取一个范围内的随机Byte，结果是左闭右开区间
     * @param begin
     * @param end
     * @return [begin,end]
     */
    public byte getByte(byte begin , byte end){
        begin = begin < -128 ? -128 : begin;
        end = end > 127 ? 127 : end;
        return (byte) getInteger(begin, end + 1);
    }

    /**
     * 获取一个范围内的随机short，结果是左闭右开区间
     * @param begin
     * @param end
     * @return [begin,end]
     */
    public short getShort(short begin , short end){
        begin = begin < -32768 ? -32768 : begin;
        end = end > 32767 ? 32767 : end;
        return (short) getInteger(begin, end + 1);
    }

    /**
     * 获取一个范围内的随机浮点数，小数位数由 decimal 控制，结果是左右闭区间
     * @param begin
     * @param end
     * @param decimal
     * @return [begin,end]
     */
    public double getDouble(double begin , double end , int decimal){
        double scalb = Math.pow(10, decimal);
        return Math.round((Math.random() * (end - begin) + begin) * scalb) / scalb;
    }

    /**
     * 获取一个随机布尔值
     * @return [true,false]
     */
    public boolean getBoolean(){
        return getInteger(0,2) == 0 ? false : true;
    }

    /**
     * 获取一个范围内的随机浮点数，小数位数由 decimal 控制，结果是左右闭区间
     * @param begin
     * @param end
     * @param decimal
     * @return [begin,end]
     */
    public double getFloat(float begin , float end , int decimal){
        float scalb = (float) Math.pow(10, decimal);
        return Math.round((Math.random() * (end - begin) + begin) * scalb) / scalb;
    }






}
