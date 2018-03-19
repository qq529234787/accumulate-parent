package com.wme.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;

/**
 * Created by Wangmingen on 2015/10/14.
 */

@Controller
@RequestMapping(value = "/file")
public class UploadController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/toUpload" )
    public String toUpload() {
        return "upload";
    }

    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public String upload(@RequestParam("file") MultipartFile[] files, HttpServletResponse response) throws IOException {
        for(int i = 0;i<files.length;i++){
            logger.info("upload file start. fileName {}.", files[i].getOriginalFilename());
            if(!files[i].isEmpty()){
                int pre = (int) System.currentTimeMillis();
                try {
                    // 拿到输出流，同时重命名上传的文件
                    FileOutputStream os = new FileOutputStream("D:/" + new Date().getTime()+ "_" + files[i].getOriginalFilename());
                    // 拿到上传文件的输入流
                    InputStream in = files[i].getInputStream();
                    // 以写字节的方式写文件
                    int len;
                    byte[] buffer = new byte[2048];
                    while(( len = in.read(buffer)) != -1){
                        os.write(buffer,0,len);
                    }
                    os.flush();
                    os.close();
                    in.close();
                    int fin = (int) System.currentTimeMillis();
                    logger.info("upload file end. fileName {} ,time {} ms.", files[i].getOriginalFilename(), fin-pre);
                } catch (Exception e) {
                    logger.error(e.getMessage(),e);
                }
            }
        }

        return "redirect:/file/uploadSuccess";
    }

    @RequestMapping(value = "/upload2",method = RequestMethod.POST )
    public String upload2(HttpServletRequest request,HttpServletResponse response) throws IOException {
        // 创建一个通用的多部分解析器
        StandardServletMultipartResolver multipartResolver = new StandardServletMultipartResolver();
        // 判断 request 是否有文件上传,即多部分请求
        if(multipartResolver.isMultipart(request)){
            // 转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
            // 取得request中的所有文件名
            Iterator<String> iter = multiRequest.getFileNames();
            while(iter.hasNext()){
                // 取得上传文件
                MultipartFile file = multiRequest.getFile(iter.next());
                if(file != null){
                    // 记录上传过程起始时的时间，用来计算上传时间
                    int pre = (int) System.currentTimeMillis();
                    // 取得当前上传文件的文件名称
                    String myFileName = file.getOriginalFilename();
                    // 如果名称不为"", 说明该文件存在，否则说明该文件不存在
                    if(myFileName.trim() != ""){
                        //重命名上传后的文件名
                        String fileName = new Date().getTime() + file.getOriginalFilename();
                        //定义上传路径
                        File localFile = new File(fileName);
                        file.transferTo(localFile);
                    }
                    //记录上传该文件后的时间
                    int fin = (int) System.currentTimeMillis();
                    logger.info("upload file end. fileName {} ,time {} ms.", myFileName, fin-pre);
                }
            }
        }

        return "redirect:/file/uploadSuccess";
    }

    @RequestMapping(value = "/uploadSuccess")
    public void uploadSuccess(HttpServletResponse response) throws IOException {
        response.getWriter().print("upload success");
    }

}
