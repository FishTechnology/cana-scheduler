package services.commons;

import services.restclients.commons.ErrorMessageModel;

import java.util.List;

public class CanaSchedulerUtility {
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
}
