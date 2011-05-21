package decaf;

import java.io.Closeable;
import java.io.IOException;

public class Util {
    enum TokenType {
        UNUSED              ,
        EOF                 ,
        NULL_TREE_LOOKAHEAD ,
        TK_class            ,
        LCURLY              ,
        RCURLY              ,
        ID                  ,
        WS_                 ,
        SL_COMMENT          ,
        CHAR                ,
        STRING              ,
        ESC
    }

    public static String tokenTypeAsString (int type) {
        if (type <= 0 || type > TokenType.values().length -1) {
            throw new IllegalArgumentException("Token type must be...");
        }
        return TokenType.values()[type].name();
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
