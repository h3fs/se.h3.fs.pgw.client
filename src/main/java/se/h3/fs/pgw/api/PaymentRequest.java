package se.h3.fs.pgw.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class PaymentRequest implements Serializable {

    public enum PaymentState {
        REQUESTED(false), FAILED(true), PAID(true);

        private final boolean end;

        PaymentState(boolean end) {
            this.end = end;
        }

        public boolean isEnd() {
            return end;
        }
    }

    @Id
    @GeneratedValue
    private Long id;

    private double amount;
    private String currency;
    private String ref;
    private String qr;
    private String msg;
    private List<LogEntry> log = new LinkedList<>();

    private PaymentRequest() {
    }

    public PaymentRequest(double amount, String currency, String ref) {
        this.amount = amount;
        this.currency = currency;
        this.ref = ref;

        setState(PaymentState.REQUESTED);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        if (this.id != null)
            throw new RuntimeException("We cant set a Id twice");
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getRef() {
        return ref;
    }

    public String getQr() {
        return qr;
    }

    public void setQr(String qr) {
        this.qr = qr;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public PaymentState getState() {
        return log.get(log.size() - 1).getState();
    }

    public boolean isFinnished() {
        return getState().isEnd();
    }

    public synchronized void waitForEndState(long timeOut) {
        try {
            if (!getState().isEnd()) {
                this.wait(timeOut);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @JsonIgnore
    public synchronized void setState(PaymentState state) {

        // Check that we are not trying to set a new terminal state
        if (log.size() > 0 && getState().isEnd())
            return;

        log.add(new LogEntry(state));

        if (state.isEnd())
            this.notifyAll();
    }

    public Date getTime() {
        return log.get(log.size() - 1).getTime();
    }

    public List<LogEntry> getLog() {
        return log;
    }
}
