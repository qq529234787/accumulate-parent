package com.wme.common.utils.poi;

import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Title: Excel 导出工具类测试
 * @Auther: ming
 * @Date: 2017/10/21
 * @Version: 1.0
 */
public class PoiExportExcelTest {

    public static void main(String[] args){

        //exportExcel(1,"test");

        Integer[] intArr = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        for (int i=0; i<intArr.length; i++){
            final Integer j = intArr[i];
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    exportExcel(j,"test"+j);
                }
            });
            thread.start();
        }
    }

    public static void exportExcel(int count,String name){
        System.out.println("Thread " + name + " is running");
        try {
            FileOutputStream  outputStream = new FileOutputStream(new File("D:\\"+name+".xls"));

            List<String[]> headNames = new ArrayList<String[]>();
            headNames.add(new String[] { "用户名", "密码" });
            List<String[]> fieldNames = new ArrayList<String[]>();
            fieldNames.add(new String[] { "name", "pwd" });

            List<User> list = new ArrayList<>();
            for (int i=0; i<count*3; i++) {
                User user = new User();
                user.setName("测试name-"+i);
                user.setPwd("pwd-"+i);
                list.add(user);
            }

            ExcelEntry excelEntry = new ExcelEntry();
            // 设置要导出Excel 的list
            excelEntry.setDataList(list);
            excelEntry.setTitles(new String[]{"qq","ee"});
            excelEntry.setHeadNames(headNames);
            excelEntry.setFieldNames(fieldNames);
            excelEntry.setOut(outputStream);

            new PoiExportExcel().exportExcelBySheet(excelEntry);
            excelEntry.getOut().close();

            /*FileInputStream inputStream = new FileInputStream(new File("D:\\test1.xls"));
            FileOutputStream  outputStream2 = new FileOutputStream(new File("D:\\"+name+".xls"));
            excelEntry.setOut(outputStream2);
            new PoiExportExcel().exportExcelBySheet(excelEntry, inputStream,true);
            outputStream2.close();*/

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

class User{
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


