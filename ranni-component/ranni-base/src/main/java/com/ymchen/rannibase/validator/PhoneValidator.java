package com.ymchen.rannibase.validator;

import com.ymchen.rannibase.annotations.IsPhone;
import com.ymchen.rannibase.constant.RegexpConstant;
import com.ymchen.rannibase.util.RanniUtil;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 校验是否为合法的手机号码
 *
 * @author MrBird
 */
public class PhoneValidator implements ConstraintValidator<IsPhone, String> {

    @Override
    public void initialize(IsPhone isPhone) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        try {
            if (StringUtils.isBlank(value)) {
                return true;
            } else {
                return RanniUtil.match(RegexpConstant.PHONE_REGEXP, value);
            }
        } catch (Exception e) {
            return false;
        }
    }
}
