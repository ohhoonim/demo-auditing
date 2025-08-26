package com.ohhoonim.demo_auditing.component;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class EntityTypeInferTest {
    
    @Test
    public void classForNameTest() throws ClassNotFoundException {
        Class clazz = Class.forName("com.ohhoonim.demo_auditing.para.Note");
        
        assertThat(clazz.getSimpleName()).isEqualTo("Note");
    }

    @Test
    public void lookupEntityTypeTest() {
        
    }
}
