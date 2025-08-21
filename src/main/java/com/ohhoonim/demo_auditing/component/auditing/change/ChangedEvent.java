package com.ohhoonim.demo_auditing.component.auditing.change;

import com.ohhoonim.demo_auditing.component.auditing.dataBy.Created;
import com.ohhoonim.demo_auditing.component.auditing.dataBy.Id;

public sealed interface ChangedEvent <T> permits CreatedEvent, ModifiedEvent, LookupEvent {
    
    public Id getId();
    public String getEntityType(); // Id.entityType(Class) static 메소드를 사용하면 된다 
    public String getEntityId();
    public String getEventType() ; 
    public Created getCreator();
    public String getJsonData();
}
