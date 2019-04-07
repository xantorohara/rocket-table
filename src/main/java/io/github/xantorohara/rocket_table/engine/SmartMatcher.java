package io.github.xantorohara.rocket_table.engine;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SmartMatcher {

    public static final String GLOBAL_KEY = "_-GLOBAL-_";
    public static final String ALL_MATCH = "_-=ALL=-_";
    public static final String NONE_MATCH = "_-=NONE=-_";

    private static final Pattern COLUMN_EQUAL_SIGN_PATTERN = Pattern.compile("([^\\s,]+)\\s*=\\s*([^\\s,]+)");

    public static boolean stringMatches(String value, SmartPattern[] patterns) {
        if (value != null) {
            for (SmartPattern pattern : patterns) {
                if (pattern.getPattern() == null) {
                    boolean matched = pattern.isCaseSensitive() ? value.equals(pattern.getString()) : value.equalsIgnoreCase(pattern.getString());

                    if (pattern.isInverse()) {
                        if (!matched) {
                            return true;
                        }
                    } else {
                        if (matched) {
                            return true;
                        }
                    }
                } else {
                    boolean matched = pattern.getPattern().matcher(value).matches();

                    if (pattern.isInverse()) {
                        if (!matched) {
                            return true;
                        }
                    } else {
                        if (matched) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static Map<String, Set<SmartPattern>> splitSearchString(String searchString) {
        Map<String, Set<SmartPattern>> out = new HashMap<>();

        searchString = searchString.trim();
        if (searchString.isEmpty()) {
            out.put(NONE_MATCH, null);
            return out;
        } else if ("*".equals(searchString)) {
            out.put(ALL_MATCH, null);
            return out;
        }

        Matcher matcher = COLUMN_EQUAL_SIGN_PATTERN.matcher(searchString);

        while (matcher.find()) {
            String key = matcher.group(1).toUpperCase();
            SmartPattern smartPattern = stringToSmartPattern(matcher.group(2));
            if (smartPattern != null) {
                out.computeIfAbsent(key, k -> new HashSet<>()).add(smartPattern);
            }
        }

        if (out.isEmpty()) {
            String key = GLOBAL_KEY;
            SmartPattern smartPattern = stringToSmartPattern(searchString);
            if (smartPattern == null) {
                out.put(NONE_MATCH, null);
            } else {
                out.put(key, Collections.singleton(smartPattern));
            }
        }
        return out;
    }

    private static SmartPattern stringToSmartPattern(String string) {
        boolean caseSensitive = true;
        boolean inverseMatch = false;

        while (true) {
            if (string.indexOf('~') == 0) {
                caseSensitive = false;
            } else if (string.indexOf('!') == 0) {
                inverseMatch = true;
            } else {
                break;
            }
            string = string.substring(1).trim();
            if (string.isEmpty()) {
                return null;
            }
        }

        SmartPattern smartPattern = new SmartPattern();
        smartPattern.setCaseSensitive(caseSensitive);
        smartPattern.setInverse(inverseMatch);

        if (string.indexOf('*') >= 0 || string.indexOf('?') >= 0) {
            string = makeGlobPatternString(string);
            smartPattern.setPattern(Pattern.compile(string, caseSensitive ? 0 : Pattern.CASE_INSENSITIVE));
        }
        smartPattern.setString(string);
        return smartPattern;
    }

    protected static String makeGlobPatternString(String str) {
        str = str.trim();

        StringBuilder sb = new StringBuilder();

        for (char currentChar : str.toCharArray()) {
            switch (currentChar) {
                case '*':
                    sb.append(".*");
                    break;
                case '?':
                    sb.append('.');
                    break;
                case '.':
                case '(':
                case ')':
                case '{':
                case '}':
                case '+':
                case '|':
                case '^':
                case '$':
                case '@':
                case '%':
                case '\\':
                    sb.append('\\');
                    sb.append(currentChar);
                    break;
                default:
                    sb.append(currentChar);
            }
        }
        return sb.toString();
    }
}
