package com.example.authsessionexam.global.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<ValidEnum, Object> {

    private Class<? extends Enum<?>> enumClass;
    private boolean ignoreCase;

    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        // 어노테이션이 초기화될 때 검증할 Enum 클래스를 가져오기.
        this.enumClass = constraintAnnotation.enumClass();
        this.ignoreCase = constraintAnnotation.ignoreCase();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        // value가 null이면 @NotNull 같은 다른 어노테이션에서 처리하므로 여기서는 true를 반환하거나 null 검사를 추가
        if (value == null) {
            return true;
        }

        // Enum 상수를 반복하며 입력된 문자열과 일치하는지 확인합니다.
        for (Enum<?> enumConstant : enumClass.getEnumConstants()) {
            String enumName = ignoreCase ? enumConstant.name().toLowerCase() : enumConstant.name();
            String valueToCompare = ignoreCase ? value.toString().toLowerCase() : value.toString();

            if (enumName.equals(valueToCompare)) {
                return true; // 일치하는 상수를 찾으면 유효함
            }
        }

        return false; // 일치하는 값이 없으면 false 반환
    }

}
