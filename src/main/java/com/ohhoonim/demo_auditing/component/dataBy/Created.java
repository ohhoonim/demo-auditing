package com.ohhoonim.demo_auditing.component.dataBy;

import java.time.Instant;

import com.ohhoonim.demo_auditing.component.id.Id;

public final class Created implements DataBy {

    private Id id;
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

    public Id getId() {
        return this.id;
    }

    public void setId(Id id) {
        this.id = id;
    }
}
