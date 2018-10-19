package com.earl.learn.mock;

/**
 * @author wenchaochen
 * @Title: EasyMockService
 * @ProjectName mock-test
 * @Description: TODO
 * @date 2018/10/19  10:04
 */
public class EasyMockService {
    private EasyMockInt easyMockInt;

    public void setEasyMockInt(EasyMockInt easyMockInt) {
        this.easyMockInt = easyMockInt;
    }

    public boolean easyMockFunation(int num) {
        //这个方法返回结果 依赖于getBoolean方法，但getInt方法还没实现
        return easyMockInt.getInt(num) > num;
    }
}
