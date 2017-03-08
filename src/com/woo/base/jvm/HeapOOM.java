package com.woo.base.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangfeng on 2016/12/30.
 */
public class HeapOOM {

    static class OOMObject {

    }

    public static void main (String args[]) throws Exception {
        List<OOMObject> list=new ArrayList<OOMObject>();

        while(true) {
            list.add(new OOMObject());
        }
    }
}
