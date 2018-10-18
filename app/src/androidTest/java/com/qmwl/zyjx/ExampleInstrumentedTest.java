package com.qmwl.zyjx;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.qmwl.zyjx.bean.HxUserData;
import com.qmwl.zyjx.utils.DBDao;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.qmwl.zyjx", appContext.getPackageName());
    }


    @Test
    public void addData(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        DBDao intance = DBDao.getIntance(appContext);
        intance.add_userData("123","gg","image");
    }

    @Test
    public void selecterDb(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        DBDao intance = DBDao.getIntance(appContext);
//        intance.add_userData("123","gg","image");
        HxUserData userData = intance.get_userData("123");
        Log.i("TAG","name:"+userData.getUserName()+"   userImage:"+userData.getUserImage());
        String a = userData.getUserImage();
    }
}
