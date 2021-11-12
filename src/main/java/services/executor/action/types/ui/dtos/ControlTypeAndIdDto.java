package services.executor.action.types.ui.dtos;

import lombok.Data;

@Data
public class ControlTypeAndIdDto {
    private ControlIdTypeDto controlIdType;
    private String id;
}
