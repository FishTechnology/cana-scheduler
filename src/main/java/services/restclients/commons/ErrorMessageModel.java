package services.restclients.commons;

import lombok.Data;

@Data
public class ErrorMessageModel {
    private String errorCode;
    private String message;
}
