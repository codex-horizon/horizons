package com.later.horizon.core.anything;

import org.springframework.core.env.Environment;

public interface AnythingAware {

    void doAnything(Environment environment);

    void doAnythingOfScript(Environment environment);

}
