package com.later.horizon.common.validated;

import javax.validation.groups.Default;

public class GroupValidator {

    public interface Authentication extends Default {
    }

    public interface Create extends Default {
    }

    public interface Delete extends Default {
    }

    public interface Modify extends Default {
    }

    public interface Query extends Default {
    }

    public interface Confirm extends Default {

    }

}

