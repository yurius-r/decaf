package decaf;

import antlr.Token;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Util {
    private static final boolean IS_DEBUG = false;
    private static final boolean IS_ERROR = true;
    private static final String PLUS = "PLUS";
    private static final String MINUS = "MINUS";
    private static final Map<Integer, String> tokenTypeToString = createMap();
    private static final Set<String> tokensToShow = createSet();

    private static Set<String> createSet() {
        final Set<String> set = new HashSet<String>();
        set.add("CHARLITERAL");
        set.add("INTLITERAL");
        set.add("BOOLEANLITERAL");
        set.add("STRINGLITERAL");
        set.add("IDENTIFIER");
        return set;
    }
//    private static final Map<String, String> remap = createReMap(tokenTypeToString);

    private static Map<Integer, String> createMap() {
        final Map<Integer, String> map = new HashMap<Integer, String>();
        final DecafScannerTokenTypes decafParserTokenTypes = new DecafScannerTokenTypes() {};
        for (Field field : DecafScannerTokenTypes.class.getDeclaredFields()) {
            try {
                map.put(field.getInt(decafParserTokenTypes), field.getName());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

//    private static Map<String, String> createReMap(Map<Integer, String> originalMap) {
//        final Map<String, String> map = new HashMap<String, String>();
//        for (String string : originalMap.values()) {
//            map.put(string, string);
//        }
//        map.put(MINUS, "");
//        map.put(PLUS, "");
//        return map;
//    }

    public static String asString(Token token) {
        final String tokenTypeString = Util.tokenTypeAsString(token.getType());
        if (tokensToShow.contains(tokenTypeString)) {
            return token.getLine()
                    + " " + tokenTypeString
                    + " " + token.getText();
        } else {
            return token.getLine()
                    + " " + token.getText();
        }
    }

    public static String tokenTypeAsString (int type) {
//        return remap.get(tokenTypeToString.get(type));
        return tokenTypeToString.get(type);
    }

    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void error(Exception e) {
        if (!IS_ERROR) {
            return;
        }
//        final StringBuilder builder = new StringBuilder();
//        for (Object object : objects) {
//            builder.append(object).append(" ");
//        }
//        System.err.println(builder);
        e.printStackTrace(System.err);
    }

    public static void debug(Object... objects) {
        if (!IS_DEBUG) {
            return;
        }
        final StringBuilder builder = new StringBuilder();
        for (Object object : objects) {
            builder.append(object).append(" ");
        }
        System.out.println(builder);
    }
//
//    public static void main(String[] args) {
//        for (int i = (int) '\0' ; i <= (int) '\377' ; i++) {
//            if (i != 92) {
//                System.out.println(i + " =" + (char)i);
//            } else {
//                System.out.println(i + " =" + (char)i + "\t" + (int) '\\' + " =" + '\\');
//            }
//        }
//        for (int i = 32; i <= 126; i++) {
//            if (i != 92) {
//                System.out.println(i + " =" + (char)i);
//            } else {
//                System.out.println(i + " =" + (char)i + "\t" + (int) '\\' + " =" + '\\');
//            }
//        }

//    }
//    public static void main(String[] args) throws IOException {
//        final FileInputStream is = new FileInputStream("D:\\hobby\\mit\\6.035\\work\\src-test\\resources\\scanner\\char4");
//        int i = 0;
//        while ((i = is.read()) > 0) {
//            System.out.println(i + " =*" + (char)i + "*");
//        }
//        System.out.println("*********");
//        i = (int) '\n';
//        System.out.println(i + " =*" + (char)i + "*");
//    }
}
