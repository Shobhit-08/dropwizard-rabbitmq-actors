package io.appform.dropwizard.actors.failurehandler.config;

import io.appform.dropwizard.actors.failurehandler.handlers.FailureHandlerType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SidelineConfig extends FailureHandlerConfig {

    public SidelineConfig() {
        super(FailureHandlerType.SIDELINE);
    }

    @Override
    public <T> T accept(FailureHandlerConfigVisitor<T> visitor) {
        return visitor.visit(this);
    }
}