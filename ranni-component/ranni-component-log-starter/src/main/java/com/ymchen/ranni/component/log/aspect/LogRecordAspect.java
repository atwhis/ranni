package com.ymchen.ranni.component.log.aspect;


import com.ymchen.ranni.component.log.annotation.LogRecord;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;

@Aspect
@Slf4j
public class LogRecordAspect {

    //定义解析格式的前后缀
    public static final TemplateParserContext TEMPLATE_PARSER_CONTEXT = new TemplateParserContext("{", "}");
    public static final ExpressionParser EXPRESSION_PARSER = new SpelExpressionParser();


    @Pointcut("@annotation(com.ymchen.ranni.component.log.annotation.LogRecord)")
    public void logRecord() {
    }


    @Before(value = "logRecord()")
    public void recordLog(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        String[] parameterNames = signature.getParameterNames();
        Method method = signature.getMethod();
        LogRecord logRecordAnnotation = method.getAnnotation(LogRecord.class);
        try {
            EvaluationContext evaluationContext = new StandardEvaluationContext();
            for (int i = 0; i < args.length; i++) {
                evaluationContext.setVariable(parameterNames[i], args[i]);
            }
            String logContent = EXPRESSION_PARSER.parseExpression(logRecordAnnotation.content(), TEMPLATE_PARSER_CONTEXT)
                    .getValue(evaluationContext, String.class);
            log.info("logRecord:{}", logContent);
        } catch (Exception ex) {
            log.error("logRecord error", ex);
        }
    }
}
