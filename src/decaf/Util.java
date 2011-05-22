package decaf;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Util {
    private static final Map<Integer, String> tokenTypeToString = createMap();

    private static Map<Integer, String> createMap() {
        final Map<Integer, String> map = new HashMap<Integer, String>();
        final DecafParserTokenTypes decafParserTokenTypes = new DecafParserTokenTypes() {};
        for (Field field : DecafParserTokenTypes.class.getDeclaredFields()) {
            try {
                map.put(field.getInt(decafParserTokenTypes), field.getName());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    public static String tokenTypeAsString (int type) {
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
}
