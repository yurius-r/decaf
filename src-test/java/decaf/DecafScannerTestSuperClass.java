package decaf;

import antlr.CharStreamException;
import antlr.Token;
import antlr.TokenStreamException;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import static junit.framework.Assert.assertEquals;

public class DecafScannerTestSuperClass {
    private static final File SCANNER_INPUT_DIR = new File("./src-test/resources/scanner").getAbsoluteFile();
    private static final File SCANNER_OUTPUT_DIR = new File(SCANNER_INPUT_DIR, "output").getAbsoluteFile();

    private static File asInputFile(String inputFileName) {
        return new File(SCANNER_INPUT_DIR, inputFileName);
    }

    private static File asOutputFile(String inputFileName) {
        return new File(SCANNER_OUTPUT_DIR, inputFileName + ".out");
    }

    private CheckResults checkLexer(String inputFileName) throws IOException, CharStreamException {
        InputStream inputStream = null;
        BufferedReader expectedOutput = null;
        try {
            inputStream = new FileInputStream(asInputFile(inputFileName));
            expectedOutput = new BufferedReader(new FileReader(asOutputFile(inputFileName)));
            final DecafScanner lexer = new DecafScanner(new DataInputStream(inputStream));
            return checkLexer(expectedOutput, lexer, inputFileName);
        } finally {
            Util.close(inputStream);
            Util.close(expectedOutput);
        }
    }

    private CheckResults checkLexer(BufferedReader expectedOutput, DecafScanner lexer, String fileName) throws IOException, CharStreamException {
        final CheckResults results = new CheckResults(fileName);
        while (true) {
            try {
                for (
                        Token token = lexer.nextToken()
                        ; token.getType() != DecafParserTokenTypes.EOF
                        ; token = lexer.nextToken()) {
                    Util.debug(Util.asString(token));
                    assertEq(expectedOutput.readLine(), Util.asString(token), results);
                }
                break;
            } catch(TokenStreamException e) {
                Util.debug(e);
                assertEq(expectedOutput.readLine(), asString(e, fileName), results);
                lexer.consume ();
            } catch (Exception e) {
                Util.error(e);
            }
        }
        String expectedString;
        while ((expectedString = expectedOutput.readLine()) != null) {
            assertEq(expectedString, null, results);
        }
        return results;
    }

    private void assertEq(String expected, String actual, CheckResults results) {
        if (!eq(expected, actual)) {
            results.badStrings.add(String.format("Expected: *%s*, Actual: *%s*", expected, actual));
        } else {
            results.goodStrings.add(String.format("Expected and Actual Strings: *%s*", actual));
        }
    }

    private static boolean eq(String s1, String s2) {
        if (s1 == null)
            return s2 == null;
        return s1.equals(s2);
    }

    protected void testLexer(String fileName) {
        try {
            final CheckResults problems = checkLexer(fileName);
            assertEquals(problems.toString(), 0, problems.badStrings.size());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CharStreamException e) {
            throw new RuntimeException(e);
        }
    }

    private String asString(Exception e, String fileName) {
        return fileName + " " + e;
    }


    public static void main(String[] args) {
        final String template =
                "\n" +
                        "    @Test\n" +
                        "    public void test%1$s() {\n" +
                        "        testLexer(\"%1$s\");\n" +
                        "    }\n";
        for (File file : SCANNER_INPUT_DIR.listFiles()) {
            if (file.isFile()) {
                System.out.println(String.format(template, file.getName()));
            }
        }
    }

    private static class CheckResults {
        private final String fileName;
        private final List<String> goodStrings = new LinkedList<String>();
        private final List<String> badStrings = new LinkedList<String>();

        public CheckResults(String fileName) {
            this.fileName = fileName;
        }

        private static void append(List<String> problems, StringBuilder builder) {
            for (String problem : problems) {
                builder.append(problem).append("\n");
            }
        }

        @Override
        public String toString() {
            final StringBuilder builder = new StringBuilder();
            builder.append(String.format("\n***** File %s has errors *****\n", fileName));
            append(badStrings, builder);
            if (goodStrings.size() > 0) {
                builder.append("***** And good results *****\n");
                append(goodStrings, builder);
            }
            return builder.toString();
        }
    }
}
