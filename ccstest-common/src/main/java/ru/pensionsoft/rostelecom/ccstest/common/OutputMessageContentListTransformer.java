package ru.pensionsoft.rostelecom.ccstest.common;

import org.apache.camel.Handler;
import org.apache.cxf.message.MessageContentsList;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Adapter for output CXF messages declared as {@code <soap:body use="literal"/>}.
 * Wraps POJO message in {@code MessageContentsList}
 *
 * @author alexey
 * Date: 9/16/12
 * @see InputMessageContentListTransformer
 */
public class OutputMessageContentListTransformer {
    /**
     * @param payload output message payload for CXF
     * @return payload wrapped into {@code MessageContentsList}
     */
    @Handler
    public MessageContentsList transform(Object payload) {
        checkNotNull(payload, "Provided payload is null");
        return new MessageContentsList(payload);
    }
}
