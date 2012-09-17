package ru.pensionsoft.rostelecom.ccstest.common;

import org.apache.camel.Handler;
import org.apache.cxf.message.MessageContentsList;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Adapter for input CXF messages declared as {@code <soap:body use="literal"/>}.
 * Unwraps POJO message from {@code MessageContentsList}
 *
 * @author alexey
 * Date: 9/16/12
 * @see OutputMessageContentListTransformer
 */

public class InputMessageContentListTransformer {
    /**
     * @param input {@code MessageContentsList} from CXF
     * @return payload unwrapped from input
     * @throws NullPointerException on null input or null payload
     * @throws IllegalArgumentException on input with multiple elements
     */
    @Handler
    public Object transform(MessageContentsList input) {
        checkNotNull(input, "Provided MessageContentsList is null");
        checkArgument(1 == input.size(), "MessageContentsLists with multiple elements not supported: '%s'", input);
        Object res = input.get(0);
        checkNotNull(res, "Received message payload is null");
        return res;
    }
}
