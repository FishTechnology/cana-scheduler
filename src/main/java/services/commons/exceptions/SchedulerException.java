package services.commons.exceptions;

public class SchedulerException extends Exception {
    public ExceptionStatus status;
    public String errorMessage;

    public SchedulerException(String errorMessage) {
        super(errorMessage);
        this.status = ExceptionStatus.Error;
        this.errorMessage = errorMessage;
    }

    public SchedulerException(String errorMessage,
                              ExceptionStatus status) {
        super(errorMessage);
        this.status = status;
        this.errorMessage = errorMessage;
    }
}
