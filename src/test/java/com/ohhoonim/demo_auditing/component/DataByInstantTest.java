package com.ohhoonim.demo_auditing.component;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@WebMvcTest(Testcontroller.class)
public class DataByInstantTest {

    Logger log = LoggerFactory.getLogger(this.getClass());
    
    @Test
    public void instantTest() {
        Instant now = Instant.now();
        LocalDateTime localNow = LocalDateTime.now();

        log.info("{}", now);
        log.info("{}", localNow.atZone(ZoneId.of("Asia/Seoul")).toInstant()); 
        /* 
         * Instant와 LocalDateTime의 차이
         * LocalDateTime은 TimeZone이 적용된 상태이다. 
        */
    }

    @Autowired
    MockMvcTester mockMvc;

    @Test
    public void controllerInstantTest() {
        mockMvc.get().uri("/test/iso")
                .param("now", "2025-08-18T04:56:16.517031Z") // 'Z'가 있다
                .accept(MediaType.APPLICATION_JSON)
                .assertThat().apply(print())
                .hasStatusOk();
    }
    
    @Test
    public void controllerLocalTimeTest() {
        mockMvc.get().uri("/test/local")
                .param("now", "2025-08-18T04:56:16.517031") // 'Z'가 없다. 
                .accept(MediaType.APPLICATION_JSON)
                .assertThat().apply(print())
                .hasStatusOk();

        /*
         * javascript 에서 toISOString() 을 이용하여 iso형식으로 만들면 맨 끝에 'Z'가 
         * 붙는다.  (UTC, 협정세계시) UTC로 보내면 java에서 Instant로 받을 수 있다. 
         * LocalDateTime으로는 'Z'를 뺀 형식만 인식한다.
         * 
         */
    }


    /*
     * 일반적으로 날짜 검색시 문자열 'yyyy-MM-dd'로 서버 전송하고 필요시 변환해서 사용
     * SI플젝에서 시분초까지 검색하는 케이스는 거의 없음
     */
    @Test
    public void charToLocalDate() {
        var isoFormat = "2025-08-18T04:56:16.517031Z";
        var localeFormat = "2025-08-18T04:56:16.517031";

        var instantObject = Instant.parse(isoFormat);
        assertThat(instantObject.toString()).isEqualTo(isoFormat);

        assertThatThrownBy(() -> Instant.parse(localeFormat))
               .hasMessageContaining("could not be parsed");

        assertThat(localeFormat).isEqualTo(LocalDateTime.parse(localeFormat).toString());
    }
}


@RestController
class Testcontroller {

    @GetMapping("/test/iso")
    Audit test(@RequestParam("now") Instant now) {
        return new Audit(now);
    }
    
    @GetMapping("/test/local")
    AuditLocale test(@RequestParam("now") LocalDateTime now) {
        return new AuditLocale(now);
    }
}

record Audit(Instant now) {}
record AuditLocale(LocalDateTime now) {
    public AuditLocale(String paramNow) {
        this(LocalDateTime.parse(paramNow));
    } 
}
