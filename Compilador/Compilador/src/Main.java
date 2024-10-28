public class Main {
    public static void main(String[] args) {
        String input = "if 5 == 8 then" +
                "/-Esto es\n" +
                "un comentario \n" +
                "de varias lineas" +
                "-/" +
                "//Esto es un comentario de una linea\n" +
                "5-3 != <> 4 < 3 > 2 <= 1 >= while break" +
                "else" +
                "5+34,35" +
                "5+3+3*5";
        AnalizadorLexico lexer = new AnalizadorLexico(input);
        /*Pruebas Analizador Lexico
        Token token = lexer.getNextToken();
        while(token.type != Token.Type.EOF) {
            System.out.println("tipo: "+token.type+", valor: "+ token.value);
            token = lexer.getNextToken();
        }*/


        AnalizadorSintactico parser = new AnalizadorSintactico(lexer);
        Node ast = parser.parse();

        System.out.println(ast);
//
//        AnalizadorSemantico semanticAnalyzer = new AnalizadorSemantico();
//        semanticAnalyzer.analizar(ast);
    }
}


