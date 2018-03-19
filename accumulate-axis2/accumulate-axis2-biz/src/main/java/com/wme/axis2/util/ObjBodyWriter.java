package com.wme.axis2.util;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMXMLBuilderFactory;
import org.apache.axiom.om.OMXMLParserWrapper;
import org.apache.axiom.om.impl.builder.StAXOMBuilder;
import org.apache.axis2.AxisFault;
import org.apache.axis2.databinding.utils.BeanUtil;
import org.apache.axis2.engine.DefaultObjectSupplier;
import org.apache.axis2.util.StreamWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings("unchecked")
public class ObjBodyWriter {

	private static final Logger logger = LoggerFactory.getLogger(ObjBodyWriter.class);

	private static String encoding = "UTF-8";

	private ObjBodyWriter(){}

	/**
	 * 将xml中的节点解析成Bean的形式
	 *
	 * @param clazz 指定Bean的类型
	 * @param xml 需要解析的xml的节点
	 * @return
	 */
	public static  <T> T readFromXMLString(String xml, Class<T> clazz) {
		if (StringUtils.isEmpty(xml)) {
			return null;
		}

		InputStream stream = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			T bean = (T) jaxbUnmarshaller.unmarshal(stream);
			return bean;
		} catch (JAXBException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 将Bean转换为xml
	 *
	 * @param bean 被转换对象
	 * @return
	 */
	public static String writeXmlString(Object bean){
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(bean.getClass());
			Marshaller marshaller = jaxbContext.createMarshaller();
			StringWriter writer = new StringWriter();
			marshaller.marshal(bean, writer);
			return writer.toString();
		} catch (JAXBException e) {
			logger.error(e.getMessage(), e);
		}

		return null;
	}

	/**
	 * 将xml中的节点解析成Bean的形式
	 * 
	 * @param cls
	 *            指定Bean的类型
	 * @param requestSoap
	 *            需要解析的xml的节点
	 * @return
	 * @throws AxisFault
	 *             异常
	 */
	public static Object parseXMLNodeToBean(Class cls, OMElement requestSoap,
			String nodeName, String arrayLocalName) throws AxisFault {

		// 遍历xml的节点
		Iterator rootIt = requestSoap.getChildElements();
		while (rootIt.hasNext()) {
			Object obj = rootIt.next();
			if (obj instanceof OMElement) {
				OMElement ele = (OMElement) obj;

				// 将节点直接解析成Bean
				if (nodeName.equals(ele.getLocalName())) {
					return BeanUtil.deserialize(cls, ele,
							new DefaultObjectSupplier(), arrayLocalName);
				}
			}
		}
		return null;
	}

	/**
	 * 将list转成OMElement
	 * 
	 * @param list
	 *            list
	 * @param rootNodeName
	 *            根节点的名称
	 * @param subNodeName
	 *            子节点的名称
	 * @return
	 * @throws Exception
	 */
	public static OMElement converListToOMElement(List list,
			String rootNodeName, String subNodeName) throws Exception {
		return BeanUtil.getOMElement(new QName(rootNodeName), list.toArray(),
				new QName(subNodeName), false, null);
	}

	/**
	 * 将对象转换成xml
	 * 
	 * @param obj Bean对象
	 * @return
	 */
	public static OMElement convertBeanToOMElement(Object obj, String nodeName) {
		XMLStreamReader reader = BeanUtil.getPullParser(obj,
				new QName(nodeName), null, true, false);
		StreamWrapper parser = new StreamWrapper(reader);
		OMXMLParserWrapper stAXOMBuilder = OMXMLBuilderFactory.createStAXOMBuilder(OMAbstractFactory.getOMFactory(), parser);
		return stAXOMBuilder.getDocumentElement();
	}

	/**
	 * 将对象转换成xml
	 *
	 * @param bean 对象
	 * @return
	 */
	public static String convertBeanToXml(Object bean) {
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

	/**
	 * String转换为OMElement
	 * @param xmlStr
	 * @param encoding
	 * @return
	 */
	public static OMElement toOMElement(String xmlStr, String encoding) {
		OMElement xmlValue;
		try {
			xmlValue = new StAXOMBuilder(new ByteArrayInputStream(xmlStr
					.getBytes(encoding))).getDocumentElement();
			return xmlValue;
		} catch (Exception e) {
			return null;
		}
	}

}
