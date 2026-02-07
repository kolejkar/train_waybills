package karol.train_waybill.database.adnotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import karol.train_waybill.database.TransportStatus;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = StatusCheck.class)
@Target({ METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Repeatable(ListStatus.class)
public @interface Status {

    String message() default "Invalid transport status";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    TransportStatus status() default TransportStatus.Report; 
}