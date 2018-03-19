package com.wme.base.utils;


import com.wme.base.vo.APIResponce;
import com.wme.base.vo.UserFavour;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertNotNull;

/**
 * Created by baowp on 2015/8/25.
 */
public class JaxbUtilsTest {

    @Test
    public void testParse() throws IOException {
        InputStream ins = getClass().getResourceAsStream("input.xml");
        String exampleString = IOUtils.toString(ins);
        APIResponce response = JaxbUtils.parse(exampleString, APIResponce.class);
        assertNotNull(response);
    }

    @Test
    public void testToXml() {
        APIResponce response = new APIResponce();
        UserFavour favour = new UserFavour();
        favour.setMovie("mov");
        response.setFavour(favour);
        response.setUserid(234);
        response.setUuid(1234);
        String xml = JaxbUtils.toXml(response);
        assertNotNull(xml);
    }
}
