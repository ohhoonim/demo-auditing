package com.ohhoonim.demo_auditing.component.container;

import com.ohhoonim.demo_auditing.component.auditing.dataBy.Created;
import com.ohhoonim.demo_auditing.component.auditing.dataBy.Modified;

// Valued Object
// value object 아님
public record Vo<T>(
        T record,
        Page page,
        Created creator,
        Modified modifier) implements Container {

    public Vo(T record) {
        this(record, null, null, null);
    }
}
