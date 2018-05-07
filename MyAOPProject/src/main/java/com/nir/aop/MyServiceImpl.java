package com.nir.aop;

import java.util.ArrayList;
import java.util.List;

/**
 * User: NirB
 * Date: 3/30/13
 * Time: 1:39 AM
 */
@NeedFilter
public class MyServiceImpl implements MyService {

    @Override
    public java.util.List<String> myLogic() {
        System.out.println("in target object");
        List<String> result = new ArrayList<String>();
        result.add("A");
        result.add("B");
        result.add("C");
        result.add("D");
        return result;
    }
}
