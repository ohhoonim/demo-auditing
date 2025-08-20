package com.ohhoonim.demo_auditing.component.id;

import java.io.Serializable;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonValue;
import com.github.f4b6a3.ulid.Ulid;

public class Id implements Serializable {

    private UUID id;

    public Id() {
        this.id = UUID.randomUUID();
    }

    private Id(String ulidType) {
        if (ulidType == null || ulidType.length() != 26) {
            throw new RuntimeException("id가 존재하지 않거나 ulid 형식이 아닙니다");
        }
        this.id = Ulid.from(ulidType).toUuid();
    }

    @Override
    public String toString() {
        return Ulid.from(id).toString();
    }

    public static Id valueOf(String ulid) {
        return new Id(ulid);
    }

    @JsonValue
    public String getId() {
        return this.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Id other = (Id) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    
}
