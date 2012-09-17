package ru.pensionsoft.rostelecom.ccstest.common;


import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.commons.lang.UnhandledException;
import org.springframework.beans.factory.FactoryBean;

import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.namespace.QName;
import java.lang.reflect.Method;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Factory for creating {@code JaxbDataFormat} to workaround this <a href="http://weblogs.java.net/blog/2006/03/03/why-does-jaxb-put-xmlrootelement-sometimes-not-always">JAXB shortcoming</a>
 * May be used as static factory and as Spring's {@code FactoryBean}
 *
 * @author alexey
 * Date: 9/16/12
 */
public class JaxbDataFormatFactory implements FactoryBean<JaxbDataFormat> {
    private final JaxbDataFormat jdf;

    /**
     * @param elementClass JAXB class without {@link javax.xml.bind.annotation.XmlRootElement} annotation
     *                     that should be used as XML root
     * @throws NullPointerException on null input
     * @throws UnhandledException on invalid JAXB class
     */
    public JaxbDataFormatFactory(Class<?> elementClass) {
        checkNotNull(elementClass, "Provided element class is null");
        this.jdf = jaxb(elementClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JaxbDataFormat getObject() throws Exception {
        return this.jdf;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<?> getObjectType() {
        return JaxbDataFormat.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSingleton() {
        return true;
    }

    /**
     * Creates {@code JaxbDataFormat} searching for JAXB namespace using provided class' package and
     * {@link XmlElementDecl} annotation on {@code ObjectFactory} class in that package
     *
     * @param elementClass JAXB class without {@link javax.xml.bind.annotation.XmlRootElement} annotation
     *                     that should be used as XML root
     * @return {@code JaxbDataFormat} for provided class
     * @throws NullPointerException on null input
     * @throws UnhandledException on invalid JAXB class
     */
    public static JaxbDataFormat jaxb(Class<?> elementClass) {
        try {
            checkNotNull(elementClass, "Provided elementClass is null");
            JaxbDataFormat res = new JaxbDataFormat(elementClass.getPackage().getName());
            res.setPartClass(elementClass.getName());
            res.setPartNamespace(obtainQname(elementClass));
            return res;
        } catch(ClassNotFoundException e) {
            throw new UnhandledException("Cannot obtain JAXB namespace for class: " + elementClass, e);
        } catch(NoSuchMethodException e) {
            throw new UnhandledException("Cannot obtain JAXB namespace for class: " + elementClass, e);
        }
    }

    private static QName obtainQname(Class<?> cl) throws ClassNotFoundException, NoSuchMethodException {
        String elTypeName = cl.getSimpleName();
        checkArgument(elTypeName.endsWith("Type"), "Unsupported class provided: '%s', name must be ended with 'Type'", cl);
        String elName = elTypeName.substring(0, elTypeName.length() - 4);
        String ofClassName = cl.getPackage().getName() + ".ObjectFactory";
        Class ofClass = Class.forName(ofClassName);
        String methodName = "create" + elName;
        Method method = ofClass.getMethod(methodName, cl);
        XmlElementDecl ann = method.getAnnotation(XmlElementDecl.class);
        checkNotNull(ann, "Cannot determine namespace for class: '%s'", cl);
        return new QName(ann.namespace(), elName);
    }
}
