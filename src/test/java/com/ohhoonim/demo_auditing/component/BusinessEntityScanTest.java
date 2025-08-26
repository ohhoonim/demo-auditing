package com.ohhoonim.demo_auditing.component;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import com.ohhoonim.demo_auditing.component.auditing.dataBy.Entity;
import com.ohhoonim.demo_auditing.para.Note;

@SpringBootTest
public class BusinessEntityScanTest {
    @Autowired
    ApplicationContext context;

    @Test
    void entityBeanScanTest() {
        var beans = List.of(context.getBeanDefinitionNames());
        assertThat(beans).contains("businessEntityMap");

        var map = (Map<String, Class<? extends Entity>>)context.getBean("businessEntityMap");
        assertThat(map.get("note")).isEqualTo(Note.class);
    }

    @Autowired
    @Qualifier("businessEntityMap")
    Map<String, Class<Entity>> businessEntityMap;

    @Test
    void diBeanTest() {
        var note = businessEntityMap.get("note");
        assertThat(note).isEqualTo(Note.class);
    }
}
