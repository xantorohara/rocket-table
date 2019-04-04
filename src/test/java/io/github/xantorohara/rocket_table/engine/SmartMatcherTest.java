package io.github.xantorohara.rocket_table.engine;

import io.github.xantorohara.rocket_table.engine.SmartMatcher;
import io.github.xantorohara.rocket_table.engine.SmartPattern;
import org.junit.Test;

import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class SmartMatcherTest {

    @Test
    public void testMakeGlobPatternString() {
        assertThat(SmartMatcher.makeGlobPatternString("A")).isEqualTo("A");
        assertThat(SmartMatcher.makeGlobPatternString("*A*")).isEqualTo(".*A.*");
        assertThat(SmartMatcher.makeGlobPatternString("?A?")).isEqualTo(".A.");
    }

    @Test
    public void testSimplePattern() {
        Map<String, Set<SmartPattern>> result = SmartMatcher.splitSearchString("ABC123");
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(SmartMatcher.GLOBAL_KEY)).extracting("string").containsExactly("ABC123");
    }

    @Test
    public void testSingleFieldPattern() {
        String[] patterns = new String[]{
                "ABC=123", "ABC=123 ", " ABC=123", "ABC= 123", "ABC =123", " ABC = 123 ", ",ABC=123", "ABC=123,"
        };
        for (String sample : patterns) {
            Map<String, Set<SmartPattern>> result = SmartMatcher.splitSearchString(sample);
            assertThat(result.size()).isEqualTo(1);
            assertThat(result.get("ABC")).extracting("string").containsExactly("123");
        }
    }

    @Test
    public void testDualFieldPattern() {
        Map<String, Set<SmartPattern>> result = SmartMatcher.splitSearchString("ABC=123,ABC=456");
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get("ABC")).extracting("string").containsExactlyInAnyOrder("123", "456");
    }

    @Test
    public void testMultipleFieldsPatterns() {
        Map<String, Set<SmartPattern>> result = SmartMatcher.splitSearchString("ABC=123, XYZ=xxx,TEST=test");
        assertThat(result.size()).isEqualTo(3);
        assertThat(result.get("ABC")).extracting("string").containsExactly("123");
        assertThat(result.get("XYZ")).extracting("string").containsExactly("xxx");
        assertThat(result.get("TEST")).extracting("string").containsExactly("test");
    }

    @Test
    public void testColumnShouldBeUppercase() {
        Map<String, Set<SmartPattern>> result = SmartMatcher.splitSearchString("ABC=123, Abc=456, abc=789, test=test");
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get("ABC")).extracting("string").containsExactlyInAnyOrder("123", "456", "789");
        assertThat(result.get("TEST")).extracting("string").containsExactly("test");
    }

    @Test
    public void testEmpty() {
        Map<String, Set<SmartPattern>> result;
        result = SmartMatcher.splitSearchString("");
        assertThat(result).containsOnlyKeys(SmartMatcher.NONE_MATCH);

        result = SmartMatcher.splitSearchString(" ");
        assertThat(result).containsOnlyKeys(SmartMatcher.NONE_MATCH);
    }

    @Test
    public void testAllMatched() {
        Map<String, Set<SmartPattern>> result;
        result = SmartMatcher.splitSearchString("*");
        assertThat(result).containsOnlyKeys(SmartMatcher.ALL_MATCH);
    }

    @Test
    public void testDualGlobPattern() {
        Map<String, Set<SmartPattern>> result = SmartMatcher.splitSearchString("ABC=12*,ABC=45?");
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get("ABC")).hasSize(2);
    }

}
