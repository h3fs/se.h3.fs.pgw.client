package se.h3.fs.pgw.api;

import java.io.Serializable;
import java.util.Date;

public class LogEntry implements Serializable {
    private LogEntry() {
    }

    public LogEntry(PaymentRequest.PaymentState state) {
        this.time = new Date();
        this.state = state;
    }

    private Date time;
    private PaymentRequest.PaymentState state;

    public Date getTime() {
        return time;
    }
    public PaymentRequest.PaymentState getState() {
        return state;
    }
}
