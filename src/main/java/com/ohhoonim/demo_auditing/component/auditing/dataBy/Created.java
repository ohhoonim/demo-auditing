package com.ohhoonim.demo_auditing.component.auditing.dataBy;

import java.time.Instant;

public final class Created implements DataBy {

    private String creator;
    private Instant created;

    public Created() {
        this.created = Instant.now();
    }

    public Created(String creator) {
        this.creator = creator;
        this.created = Instant.now();
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }
}
