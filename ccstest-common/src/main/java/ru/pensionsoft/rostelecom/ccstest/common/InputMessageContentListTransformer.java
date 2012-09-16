package ru.pensionsoft.rostelecom.ccstest.common;

import org.apache.camel.Handler;
import org.apache.cxf.message.MessageContentsList;

/**
 * User: alexey
 * Date: 9/16/12
 */

public class InputMessageContentListTransformer {
    @Handler
    public Object transform(MessageContentsList input) {
        // todo: proper logging here on empty input
        return input.get(0);
    }
}
