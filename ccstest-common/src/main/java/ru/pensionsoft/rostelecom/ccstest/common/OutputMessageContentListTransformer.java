package ru.pensionsoft.rostelecom.ccstest.common;

import org.apache.cxf.message.MessageContentsList;

/**
 * User: alexey
 * Date: 9/16/12
 */
public class OutputMessageContentListTransformer {
    public MessageContentsList transform(Object obj) {
        // todo: nullability checks
        return new MessageContentsList(obj);
    }
}
