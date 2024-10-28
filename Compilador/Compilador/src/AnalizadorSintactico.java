//
//class Node {
//    enum Type { LONG, DOUBLE, IF, THEN, ELSE, WHILE, BREAK, READ, WRITE, NUMERO, SUMA, RESTA, MULTIPLICACION, DIVISION,
//        MENOR, MAYOR, MENORIGUAL, MAYORIGUAL, IGUAL, DIFERENTE, EOF }
//    Type type;
//    String value;
//    Node left;
//    Node right;
//
//    Node(Type type, String value, Node left, Node right) {
//        this.type = type;
//        this.value = value;
//        this.left = left;
//        this.right = right;
//    }
//
//    public String toString() {
//        return ("type: " + type + " value: " + value + " left: " + left + " right: " + right);
//    }
//}
//
//class AnalizadorSintactico {
//    private AnalizadorLexico lexer;
//    private Token currentToken;
//
//    public AnalizadorSintactico(AnalizadorLexico lexer) {
//        this.lexer = lexer;
//        this.currentToken = lexer.getNextToken();
//    }
//
//    private void error() {
//        throw new RuntimeException("Error de análisis");
//    }
//
//    private void eat(Token.Type tokenType) {
//        if (currentToken.type == tokenType) {
//            currentToken = lexer.getNextToken();
//        } else {
//            error();
//        }
//    }
//
//    public Node parse() {
//        return expresion();
//    }
//
//    private Node expresion() {
//        Node node = termino();
//        while (currentToken.type == Token.Type.SUMA || currentToken.type == Token.Type.RESTA) {
//            Token op = currentToken;
//            eat(op.type);
//            node = new Node(op.type == Token.Type.SUMA ? Node.Type.SUMA : Node.Type.RESTA, op.value, node, termino());
//        }
//        return node;
//    }
//
//    private Node termino() {
//        Node node = factor();
//        while (currentToken.type == Token.Type.MULTIPLICACION || currentToken.type == Token.Type.DIVISION) {
//            Token op = currentToken;
//            eat(op.type);
//            node = new Node(op.type == Token.Type.MULTIPLICACION ? Node.Type.MULTIPLICACION : Node.Type.DIVISION, op.value, node, factor());
//        }
//        return node;
//    }
//
//    private Node factor() {
//        Token token = currentToken;
//        if (token.type == Token.Type.DOUBLE) {
//            eat(Token.Type.DOUBLE);
//            return new Node(Node.Type.DOUBLE, token.value, null, null);
//        } else if (token.type == Token.Type.LONG) {
//            eat(Token.Type.LONG);
//            return new Node(Node.Type.LONG, token.value, null, null);
//        }
//        error();
//        return null;
//    }
//}


class Node {
    enum Type {
        LONG, DOUBLE, IF, THEN, ELSE, WHILE, BREAK, READ, WRITE, NUMERO, SUMA, RESTA, MULTIPLICACION, DIVISION,
        MENOR, MAYOR, MENORIGUAL, MAYORIGUAL, IGUAL, DIFERENTE, EOF, STATEMENT
    }

    Type type;
    String value;
    Node left;
    Node right;

    Node(Type type, String value, Node left, Node right) {
        this.type = type;
        this.value = value;
        this.left = left;
        this.right = right;
    }

    public String toString() {
        return ("type: " + type + " value: " + value + " left: " + left + " right: " + right);
    }
}

class AnalizadorSintactico {
    private AnalizadorLexico lexer;
    private Token currentToken;

    public AnalizadorSintactico(AnalizadorLexico lexer) {
        this.lexer = lexer;
        this.currentToken = lexer.getNextToken();
    }

    private void error() {
        throw new RuntimeException("Error de análisis");
    }

    private void eat(Token.Type tokenType) {
        if (currentToken.type == tokenType) {
            currentToken = lexer.getNextToken();
        } else {
            error();
        }
    }

    public Node parse() {
        return estructura(); // Cambiado para manejar declaraciones
    }

    // En el método estructura:
    private Node estructura() {
        if (currentToken.type == Token.Type.IF) {
            return EstructuraIf();
        } else if (currentToken.type == Token.Type.WHILE) {
            return EstructuraWhile();
        } else {
            return expresion();
        }
    }

    private Node EstructuraIf() {
        eat(Token.Type.IF);
        Node condicion = expresion();
        System.out.println("Condicion del if: " + condicion);
        eat(Token.Type.THEN);
        Node codigoThen = estructura();
        Node codigoElse = null;
        if (currentToken.type == Token.Type.ELSE) {
            eat(Token.Type.ELSE);
            codigoElse = estructura();
        }
        return new Node(Node.Type.IF, "if", condicion, new Node(Node.Type.STATEMENT, "then", codigoThen, codigoElse));
    }

    private Node EstructuraWhile() {
        eat(Token.Type.WHILE);
        Node condition = expresion();
        Node body = estructura();
        return new Node(Node.Type.WHILE, "while", condition, body);
    }

    private Node expresion() {

        Node node = comparacion();

        while (currentToken.type == Token.Type.SUMA || currentToken.type == Token.Type.RESTA) {
            Token op = currentToken;
            eat(op.type);
            node = new Node(op.type == Token.Type.SUMA ? Node.Type.SUMA : Node.Type.RESTA, op.value, node, termino());
        }
        return node;
    }

    private Node comparacion() {
        Node left = termino(); // Asumimos que expresion() ya maneja operaciones aritméticas
        Token token = currentToken;

        // Manejar operadores de comparación
        if (token.type == Token.Type.MENOR || token.type == Token.Type.MAYOR ||
                token.type == Token.Type.MENORIGUAL || token.type == Token.Type.MAYORIGUAL ||
                token.type == Token.Type.IGUAL || token.type == Token.Type.DIFERENTE) {
            eat(token.type); // Consume el operador de comparación
            Node right = expresion(); // La parte derecha de la comparación
            switch (token.type){
                case MENOR:
                    return new Node(Node.Type.MENOR, token.value, left, right);
                case MAYOR:
                    return new Node(Node.Type.MAYOR, token.value, left, right);
                case MENORIGUAL:
                    return new Node(Node.Type.MENORIGUAL , token.value, left, right);
                case MAYORIGUAL:
                    return new Node(Node.Type.MAYORIGUAL, token.value, left, right);
                case IGUAL:
                    return new Node(Node.Type.IGUAL, token.value, left, right);
                case DIFERENTE:
                    return new Node(Node.Type.DIFERENTE, token.value, left, right);
            }
            System.out.println("que raro que salio"); // lkuego borraaaaaarrrrrrrrrrrrrrrrrrrrr!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        }

        return left; // Si no hay comparación, devolvemos la parte izquierda
    }



    private Node termino() {
        Node node = factor();  // Factor incluye operaciones unitarias o números

        while (currentToken.type == Token.Type.MULTIPLICACION || currentToken.type == Token.Type.DIVISION) {
            Token op = currentToken;
            eat(op.type);
            node = new Node(op.type == Token.Type.MULTIPLICACION ? Node.Type.MULTIPLICACION : Node.Type.DIVISION, op.value, node, factor());
        }
        return node;
    }


    private Node factor() {
        Token token = currentToken;
        if (token.type == Token.Type.DOUBLE) {
            eat(Token.Type.DOUBLE);
            return new Node(Node.Type.DOUBLE, token.value, null, null);
        } else if (token.type == Token.Type.LONG) {
            eat(Token.Type.LONG);
            return new Node(Node.Type.LONG, token.value, null, null);
        }
        error();
        return null;
    }
}
