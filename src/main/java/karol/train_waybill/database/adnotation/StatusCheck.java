package karol.train_waybill.database.adnotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import karol.train_waybill.database.TransportStatus;

public class StatusCheck  implements ConstraintValidator<Status, TransportStatus> {

    private TransportStatus status;

    @Override
    public void initialize(Status constraint) {
        this.status = constraint.status();
    }

    @Override
    public boolean isValid(TransportStatus value, ConstraintValidatorContext context) {
                if(value.equals(this.status))
            return true;

        return false;
    }
}
