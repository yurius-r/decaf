package decaf;

import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Util {
    private static final Map<Integer, String> tokenTypeToString = createMap();

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
//
//    public static void main(String[] args) {
//        for (int i = 32; i <= 126; i++) {
//            if (i != 92) {
//                System.out.println(i + " =" + (char)i);
//            } else {
//                System.out.println(i + " =" + (char)i + "\t" + (int) '\\' + " =" + '\\');
//            }
//        }
//
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
