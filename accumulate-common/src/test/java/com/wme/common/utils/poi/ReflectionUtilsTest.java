package com.wme.common.utils.poi;

import com.wme.common.utils.ReflectionUtils;
import org.junit.Test;

/**
 * @Title: accumulate-parent
 * @Auther: ming
 * @Date: 2017/11/17
 * @Version: 1.0
 */
public class ReflectionUtilsTest {

    @Test
    public void test(){
        User user = new User();
        user.setName("test");
        user.setPwd("123");

        Object name = ReflectionUtils.getFieldValue(user, "name");
        System.out.println(name);
        ReflectionUtils.invokeSetterMethod(user ,"name", "new value");
        name = ReflectionUtils.invokeGetterMethod(user, "name");
        System.out.print(name);

        Class<Object> superClassGenricType = ReflectionUtils.getSuperClassGenricType(User.class);
    }

    class User<T> extends ExcelEntry{
        private String name;
        private String pwd;

        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getPwd() {
            return pwd;
        }
        public void setPwd(String pwd) {
            this.pwd = pwd;
        }
    }

}
