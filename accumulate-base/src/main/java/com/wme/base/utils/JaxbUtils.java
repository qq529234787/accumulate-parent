package com.wme.base.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

/**
 * Created by wangmingne on 15-7-13.
 */
public class JaxbUtils {

    private static final Logger logger = LoggerFactory.getLogger(JaxbUtils.class);

    public static <T> T parse(String xml, Class<T> clazz) {
        InputStream stream = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            T bean = (T) jaxbUnmarshaller.unmarshal(stream);
            return bean;
        } catch (JAXBException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static String toXml(Object bean) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(bean.getClass());
            Marshaller marshaller = jaxbContext.createMarshaller();
            StringWriter writer = new StringWriter();
            marshaller.marshal(bean, writer);
            return writer.toString();
        } catch (JAXBException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}
