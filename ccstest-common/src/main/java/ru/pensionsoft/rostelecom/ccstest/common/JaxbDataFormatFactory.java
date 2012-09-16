package ru.pensionsoft.rostelecom.ccstest.common;


import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.commons.lang.UnhandledException;
import org.springframework.beans.factory.FactoryBean;

import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.namespace.QName;
import java.lang.reflect.Method;

/**
 * User: alexey
 * Date: 9/16/12
 */
public class JaxbDataFormatFactory implements FactoryBean<JaxbDataFormat> {
    private final Class<?> elementClass;
    private JaxbDataFormat memo = null;

    public JaxbDataFormatFactory(Class<?> elementClass) {
        this.elementClass = elementClass;
    }

    @Override
    public JaxbDataFormat getObject() throws Exception {
        // externally synchronized by spring
        if(null == memo) memo = jaxb(elementClass);
        return memo;
    }

    @Override
    public Class<?> getObjectType() {
        return JaxbDataFormat.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public static JaxbDataFormat jaxb(Class<?> elementClass) {
        // todo: checks, error info
        try {
            JaxbDataFormat res = new JaxbDataFormat(elementClass.getPackage().getName());
            res.setPartClass(elementClass.getName());
            res.setPartNamespace(obtainQname(elementClass));
            return res;
        } catch(ClassNotFoundException e) {
            throw new UnhandledException(e);
        } catch(NoSuchMethodException e) {
            throw new UnhandledException(e);
        }
    }

    private static QName obtainQname(Class<?> cl) throws ClassNotFoundException, NoSuchMethodException {
        String elTypeName = cl.getSimpleName();
        String elName = elTypeName.substring(0, elTypeName.length() - 4);
        String methodName = "create" + elName;
        String ofClassName = cl.getPackage().getName() + ".ObjectFactory";
        Class ofClass = Class.forName(ofClassName);
        Method method = ofClass.getMethod(methodName, cl);
        XmlElementDecl ann = method.getAnnotation(XmlElementDecl.class);
        if(null == ann) throw new RuntimeException("Cannot determine namespace for class: " + cl);
        return new QName(ann.namespace(), elName);
    }
}
