package cana.codelessautomation.scheduler.v2.commons;

import org.apache.commons.lang3.StringUtils;
import services.restclients.commons.ErrorMessageModel;

import java.util.ArrayList;
import java.util.List;

public class CanaSchedulerUtility {

    public static String getMessage(Throwable exception) {
        StringBuilder sb = new StringBuilder();
        sb.append("Message=" + exception.getMessage());
        sb.append("CauseBy=" + exception.getCause());
        sb.append("StackTrace=" + exception.getStackTrace());
        return sb.toString();
    }

    public static String getMessage(Exception exception) {
        StringBuilder sb = new StringBuilder();
        sb.append("Message=" + exception.getMessage());
        sb.append("CauseBy=" + exception.getCause());
        sb.append("StackTrace=" + exception.getStackTrace());
        return sb.toString();
    }

    public static String getMessage(List<ErrorMessageModel> errorMessageModels) {
        StringBuilder sb = new StringBuilder();
        for (ErrorMessageModel errorMessageModel : errorMessageModels) {
            sb.append("[");
            sb.append("message=" + errorMessageModel.getMessage());
            sb.append("ErrorCode=" + errorMessageModel.getErrorCode());
            sb.append("]");
        }
        return sb.toString();
    }

    public static List<ErrorMessageModel> getErrorMessageModels(String errorMessage) {
        return getErrorMessageModels(errorMessage, "");
    }

    public static List<ErrorMessageModel> getErrorMessageModels(String errorCode, String message) {
        List<ErrorMessageModel> errorMsgModels = new ArrayList<>();
        ErrorMessageModel errorMessageModel = new ErrorMessageModel();
        if (!StringUtils.isEmpty(message)) {
            errorMessageModel.setMessage(message);
        }
        errorMessageModel.setErrorCode(errorCode);
        errorMsgModels.add(errorMessageModel);
        return errorMsgModels;
    }
}
