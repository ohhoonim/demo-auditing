# Auditing component


- 아래 내용은 초안임

## 전략1 : Auditing 필드 

- 데이터를 생성한 사용자와 수정한 사용자의 정보를 해당 시점에 저장
- 생성자, 생성일, 수정자, 수정일

```plantuml
@startuml
skinparam monochrome reverse
skinparam classAttributeIconSize 0
left to right direction

title Auditing 전략  

class Id <<공통식별자 필드>> {
    id: UUID
    ---
    + Id()
    - Id(ulidType: String)
    ---
    toString(): String
    {static} valueOf(ulid: String): Id
    getId(): String
}

interface DataBy <<sealed>>

class Created {
    id: Id
    creator: String
    created: Instant 
}
class Modified {
    id: Id
    modifier: String
    modified: Instant 
}

DataBy <|.. Created
DataBy <|.. Modified


@enduml
```

## 전략2 : revision 이력관리 

- revision은 aggregate root 를 대상으로 한다

### Table 

```plantuml
@startuml
skinparam monochrome reverse

object Revision <<table>> {
    id VARCHAR(26) PK
    event_type VARCHAR(10) NOT NULL
    entity_type VARCHAR(10) NOT NULL
    entity_id VARCHAR(26) NOT NULL
    event_data JSONB NOT NULL
    creator TIMESTAMP NOT NULL
    created VARCHAR(26) NOT NULL
}

@enduml
```

### JSON 구조

#### 1. create 이벤트: 모든 필드를 담아 이벤트를 발생 시킨다

```plantuml
@startuml
json create_event{
  "entity_id": "uuid-1234-abcd",
  "name": "새로운 제품",
  "price": 50000,
  "status": "DRAFT"
}
@enduml
```

#### 2. update 이벤트: 변경이 된 필드에 대하여 수정 전후 데이터를 이벤트로 발생시킨다 

```plantuml
@startuml
json update_event {
  "changed_fields": {
    "name": {
      "old_value": "새로운 제품",
      "new_value": "업데이트된 제품"
    },
    "price": {
      "old_value": 50000,
      "new_value": 60000
    }
  }
}
@enduml
```

## 다양한 JSON 구조를 위한 이벤트 구현 전략

```plantuml
@startuml
skinparam monochrome reverse

interface DomainEvent {
  getId(): Id
  getEntityType(): String
  getEntityId(): Id
  getEventType(): String 
  getCreated(): DataBy
  getModified(): DataBy
}

class SomeDomainCreateEvent extends DomainEvent {
  @JsonIgnoreProperties(ignoreUnknown = true)
  <<공통필드>>
  id: Id
  entityType: String
  entityId: Id
  eventType: String
  created: DataBy 
  <<개별필드>>
  someField1: String
  someField2: String
  ---
  <<methods>>
}

class SomeDomainUpdateEvent extends DomainEvent {
  @JsonIgnoreProperties(ignoreUnknown = true)
  <<공통필드>>
  id: Id
  entityType: String
  entityId: Id
  eventType: String
  created: DataBy 
  ---
  oldValues: Map<String, Object>
  newValues: Map<String, Object>
  ---
  <<methods>>
}

@enduml
```

## JSONB 핸들러 사용법

```xml
<insert id="insertEvent">
    INSERT INTO event_log (
        id,
        event_data
    ) VALUES (
        #{id},
        #{eventData, jdbcType=OTHER, javaType=com.yourpackage.EventData, typeHandler="com.yourpackage.TypedJsonbTypeHandler"}
    )
</insert>

<resultMap id="eventResultMap" type="com.yourpackage.EventDto">
    <id property="id" column="id"/>
    <result property="eventData" column="event_data" javaType="com.yourpackage.EventData" typeHandler="com.yourpackage.TypedJsonbTypeHandler"/>
</resultMap>

<select id="getEventById" resultMap="eventResultMap">
    SELECT
        id,
        event_data
    FROM
        event_log
    WHERE
        id = #{id}
</select>
```


