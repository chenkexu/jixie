package com.qmwl.zyjx.utils;


import org.simple.eventbus.EventBus;

/**
 * 消息处理
 * @ClassName: EventManager 
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
public class EventManager {

	public static void register(Object object) {
            EventBus.getDefault().register(object);

    }  
  
    public static void unregister(Object object) {
            EventBus.getDefault().unregister(object);

    }  
  
    public static void post(String tag, Object object) {
        EventBus.getDefault().post(object,tag);
    }  
}
