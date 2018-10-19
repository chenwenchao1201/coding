package com.earl.learn.test;

import com.earl.learn.mock.EasyMockInt;
import com.earl.learn.mock.EasyMockService;
import org.easymock.EasyMock;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author wenchaochen
 * @Title: EasyMockDemo
 * @ProjectName mock-test
 * @Description: TODO
 * @date 2018/10/18  17:18
 */
public class EasyMockDemo {
    @Test
    public void  easyMockTest(){
        //生成mock对象
        EasyMockInt my = EasyMock.createMock(EasyMockInt.class);
        //给mock出来的对象设置行为,如调用getBoolean就返回false，getInt（10）则返回5
        EasyMock.expect(my.getBoolean()).andReturn(false);
        EasyMock.expect(my.getChar()).andReturn('b');
        EasyMock.expect(my.getString()).andReturn("world");
        EasyMock.expect(my.getInt(10)).andReturn(5);
        EasyMock.expect(my.getInt(-10)).andThrow(new Exception());
        //void的测试方法
        my.dosomething();
        EasyMock.expectLastCall();
        //调用次数限制说明
        EasyMock.expect(my.getInt(10)).andReturn(1);
        EasyMock.expect(my.getInt(10)).andReturn(4).atLeastOnce();
        EasyMock.expect(my.getInt(10)).andReturn(1).anyTimes();

        EasyMock.replay(my);

        //对mock的对象进行单元测试
        assertEquals(5,my.getInt(10));
        assertEquals(false,my.getBoolean());
        assertEquals('b',my.getChar());
        assertEquals("world",my.getString());

        //验证mock对象
        EasyMock.verify(my);
        //重置mock对象，可以继续使用
        EasyMock.reset(my);
        //创建按顺序的mock对象
        EasyMockInt my1 = EasyMock.createStrictMock(EasyMockInt.class);
        //创建默认返回无效值的mock对象
        EasyMockInt my2 = EasyMock.createNiceMock(EasyMockInt.class);
    }

    @Test
    public void testeasyMockFunation() {
        int num = 5;
        EasyMockInt easyMockInt = EasyMock.createMock(EasyMockInt.class);
        //预期输入5，返回10
        EasyMock.expect(easyMockInt.getInt(5)).andReturn(10);
        EasyMock.replay(easyMockInt);
        EasyMockService easyMockService = new EasyMockService();
        easyMockService.setEasyMockInt(easyMockInt);
        assertEquals(false, easyMockService.easyMockFunation(num));
    }

}
