package naverz.craft.craftapi.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // Common
    INPUT_INVALID(400, "C001", "입력 값이 유효하지 않습니다."),

    // Order
    CREIDT_NOT_ENOUGH(400, "O001", "재화가 부족합니다.");
    
    private int status;
    private final String code;
    private final String message;


}