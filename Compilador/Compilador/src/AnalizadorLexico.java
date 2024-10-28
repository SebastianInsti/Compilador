class Token {
    enum Type {
        LONG, DOUBLE, IF, THEN, ELSE, WHILE, BREAK, READ, WRITE, NUMERO, SUMA, RESTA, MULTIPLICACION, DIVISION,
        MENOR, MAYOR, MENORIGUAL, MAYORIGUAL, IGUAL, DIFERENTE, EOF
    }

    Type type;
    String value;

    Token(Type type, String value) {
        this.type = type;
        this.value = value;
    }
}

public class AnalizadorLexico {
    private String input;
    private int pos;
    private char charActual;

    public AnalizadorLexico(String input) {
        this.input = input;
        this.pos = 0;
        this.charActual = input.charAt(pos);
    }

    private void advance() {
        pos++;
        charActual = (pos < input.length()) ? input.charAt(pos) : '\0';
    }

    private void skipWhitespace() {
        while (charActual != '\0' && Character.isWhitespace(charActual)) {
            advance();
        }
    }

    private String number() {
        StringBuilder result = new StringBuilder();
        boolean flag = false;
        while (charActual != '\0' && Character.isDigit(charActual) || !flag && charActual == ',') {
            if(charActual == ','){
                flag = true;
            }
            result.append(charActual);
            advance();
        }
        if (flag) {
            result.append('d');
        }
        return result.toString();
    }
    private void comentarioMultilinea() {
//        advance();  // Saltar el `-`
        while (!(charActual == '-' && pos + 1 < input.length() && input.charAt(pos + 1) == '/')) {
            advance();
            if (charActual == '\0') {
                error();  // Si nunca se cierra el comentario
            }
        }
        advance();  // Saltar el `-`
        advance();  // Saltar el `/`
    }

    public Token getNextToken() {
        while (charActual != '\0') {
            if (Character.isWhitespace(charActual)) {
                skipWhitespace();
                continue;
            }

            // Números
            if (Character.isDigit(charActual)) {
                String t = number();
                if (t.charAt(t.length()-1) == 'd') {
                    t = t.substring(0, t.length() - 1);
                    return new Token(Token.Type.DOUBLE, t);
                } else {
                    return new Token(Token.Type.LONG, t);
                }
                //return new Token(Token.Type.NUMERO, number());}
            }

            // Comentarios
            //Comentario de una linea
            if (charActual == '/' && pos + 1 < input.length() && input.charAt(pos + 1) == '/') {advance(); advance();
                while (charActual != '\0' && charActual != '\n') {
                    advance();
                }
                continue;}
            //Comentario de varias lineas
            if (charActual == '/' && pos + 1 < input.length() && input.charAt(pos + 1) == '-') {advance();advance();
                comentarioMultilinea();
                continue;  // Saltar el comentario y seguir buscando el siguiente token
            }


            // Operadores aritméticos
            if (charActual == '+') {advance();return new Token(Token.Type.SUMA, "+");}
            if (charActual == '-') {advance();return new Token(Token.Type.RESTA, "-");}
            if (charActual == '*') {advance();return new Token(Token.Type.MULTIPLICACION, "*");}
            if (charActual == '/') {advance();return new Token(Token.Type.DIVISION, "/");}

            // Operadores lógicos

            if (charActual == '<' && pos + 1 < input.length() && input.charAt(pos + 1) == '>') {advance(); advance();return new Token(Token.Type.DIFERENTE, "<>");}
            if (charActual == '<') {advance();if (charActual == '=') {advance();return new Token(Token.Type.MENORIGUAL, "<=");}return new Token(Token.Type.MENOR, "<");}
            if (charActual == '>') {advance();if (charActual == '=') {advance();return new Token(Token.Type.MAYORIGUAL, ">=");}return new Token(Token.Type.MAYOR, ">");}
            if (charActual == '=' && pos + 1 < input.length() && input.charAt(pos + 1) == '=') {advance(); advance();return new Token(Token.Type.IGUAL, "==");}
            if (charActual == '!' && pos + 1 < input.length() && input.charAt(pos + 1) == '=') {advance(); advance();return new Token(Token.Type.DIFERENTE, "!=");}

            //if-then-else
            if (charActual == 'i' && pos + 1 < input.length() && input.charAt(pos + 1) == 'f') {advance(); advance();return new Token(Token.Type.IF, "if");}
            if (charActual == 't' && pos + 3 < input.length() &&
                    input.charAt(pos+1)=='h' &&
                    input.charAt(pos+2)=='e' &&
                    input.charAt(pos+3)=='n') {advance(); advance();advance();advance();return new Token(Token.Type.THEN, "then");}
            if (charActual == 'e' && pos + 3 < input.length() &&
                    input.charAt(pos+1)=='l' &&
                    input.charAt(pos+2)=='s' &&
                    input.charAt(pos+3)=='e') {advance(); advance();advance();advance();return new Token(Token.Type.ELSE, "else");}

            //while - break
            if (charActual == 'w' && pos + 4 < input.length() &&
                    input.charAt(pos+1)=='h' &&
                    input.charAt(pos+2)=='i' &&
                    input.charAt(pos+3)=='l' &&
                    input.charAt(pos+4)=='e') {advance(); advance();advance();advance();advance();return new Token(Token.Type.WHILE, "while");}
            if (charActual == 'b' && pos + 4 < input.length() &&
                    input.charAt(pos+1)=='r' &&
                    input.charAt(pos+2)=='e' &&
                    input.charAt(pos+3)=='a' &&
                    input.charAt(pos+4)=='k') {advance(); advance();advance();advance();advance();return new Token(Token.Type.BREAK, "break");}

            //entrada - salida !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!implementar!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!


            // Si llega aquí y no reconoció nada, arroja un error
            error();
        }

        return new Token(Token.Type.EOF, null);  // Fin de la entrada
    }

    private void error() {
        throw new RuntimeException("Error en la entrada en posición: " + pos);
    }
}

