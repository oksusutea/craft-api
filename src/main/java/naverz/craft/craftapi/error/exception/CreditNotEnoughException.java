package naverz.craft.craftapi.error.exception;

import naverz.craft.craftapi.error.ErrorCode;

public class CreditNotEnoughException extends BusinessException {

    public CreditNotEnoughException() {
        super(ErrorCode.CREIDT_NOT_ENOUGH);
    }
}