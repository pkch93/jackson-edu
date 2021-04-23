package edu.pkch.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class ObjectMapperTest {

    @Test
    @DisplayName("ObjectMapper 기본 생성시 JsonView에 따라 직렬화를 한다.")
    void objectMapperWithJsonView() throws JsonProcessingException {
        // given
        ObjectMapper objectMapper = new ObjectMapper();
        LocalDateTime birthDay = LocalDateTime.of(1993, 9, 17, 12, 0, 0);
        JsonViewPerson person = new JsonViewPerson("pkch", 29, 3, birthDay);

        // when
        String actual = objectMapper.writeValueAsString(person);

        // then
        assertThat(actual.contains("name")).isTrue();
        assertThat(actual.contains("pkch")).isTrue();

        assertThat(actual.contains("age")).isTrue();
        assertThat(actual.contains("29")).isTrue();

        assertThat(actual.contains("birthDay")).isFalse();
    }

    @Test
    @DisplayName("ObjectMapper#writerWithView로 @JsonView와 지정한 클래스에 따라 직렬화를 할 수 있다.")
    void objectMapperWithWriterWithView() throws JsonProcessingException {
        // given
        ObjectMapper objectMapper = new ObjectMapper();
        LocalDateTime birthDay = LocalDateTime.of(1993, 9, 17, 12, 0, 0);
        JsonViewPerson person = new JsonViewPerson("pkch", 29, 3, birthDay);

        // when
        String actual = objectMapper.writerWithView(View.Name.class).writeValueAsString(person);

        // then
        assertThat(actual.contains("name")).isTrue();
        assertThat(actual.contains("pkch")).isTrue();

        assertThat(actual.contains("age")).isFalse();
        assertThat(actual.contains("29")).isFalse();

        assertThat(actual.contains("birthDay")).isFalse();
    }

    @Test
    void name() throws JsonProcessingException {
        // given
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS"));

        // when
        LocalDateTime birthDay = LocalDateTime.of(1993, 9, 17, 12, 0, 0);
        JsonViewPerson person = new JsonViewPerson("pkch", 29, 3, birthDay);

        // when
        String actual = objectMapper.writeValueAsString(person);
        JsonViewPerson jsonViewPerson = objectMapper.convertValue(person, JsonViewPerson.class);

        // then
        assertThat(actual.contains("name")).isTrue();
        assertThat(actual.contains("pkch")).isTrue();

        assertThat(actual.contains("age")).isTrue();
        assertThat(actual.contains("29")).isTrue();

        assertThat(actual.contains("grade")).isFalse();

        assertThat(actual.contains("birthDay")).isFalse();
    }
}
