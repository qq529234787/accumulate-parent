package com.wme.base.vo;

import com.wme.base.utils.StrUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wangmingne on 2015/8/21.
 */
public class TicketResult {

    private static final Logger logger = LoggerFactory.getLogger(TicketResult.class);

    private int code = 200;//默认设置为正常的值
    private String message;
    private Object data;

    private TicketResult() {
    }

    private TicketResult(Object data) {
        this.data = data;
    }

    private TicketResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private TicketResult(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static TicketResult generate(Object data) {
        TicketResult result = new TicketResult(data);
        if (logger.isInfoEnabled()) {
            logger.info("ticketResult is: {}", StrUtils.json(result));
        }
        return result;
    }

    public static TicketResult generate(int code, String message) {
        TicketResult result = new TicketResult(code, message);
        if (logger.isInfoEnabled()) {
            logger.info("ticketResult is: {}", StrUtils.json(result));
        }
        return result;
    }

    public static TicketResult generate(int code, String message, Object data) {
        TicketResult result = new TicketResult(code, message, data);
        if (logger.isInfoEnabled()) {
            logger.info("ticketResult is: {}", StrUtils.json(result));
        }
        return result;
    }
}
