class AnalizadorSemantico {

    public void analizar(Node node) {
        if (node == null) return;
/*
        switch (node.type) {
            case NUMBER:
                System.out.println(node.value);
                break;
            case ADD:
                analizar(node.left);
                analizar(node.right);
                node.value = String.valueOf(Integer.parseInt(node.left.value) + Integer.parseInt(node.right.value));
                node.type = Node.Type.NUMBER;
                System.out.println(node.value);
                if (node.left.type != Node.Type.NUMBER || node.right.type != Node.Type.NUMBER) {
                    System.out.println("Error sum" );
                    System.out.println(node.left.value);
                    System.out.println(node.right.value);
                    throw new RuntimeException("Error: Operands must be numbers.");
                }
                break;
            case SUBTRACT:
                analizar(node.left);
                analizar(node.right);
                node.value = String.valueOf(Integer.parseInt(node.left.value) - Integer.parseInt(node.right.value));
                node.type = Node.Type.NUMBER;
                System.out.println(node.value);
                if (node.left.type != Node.Type.NUMBER || node.right.type != Node.Type.NUMBER) {
                    System.out.println("Error res");
                    System.out.println(node.left.value);
                    System.out.println(node.right.value);
                    throw new RuntimeException("Error: Operands must be numbers.");
                }
                break;
            case MULTIPLY:
                analizar(node.left);
                analizar(node.right);
                node.value = String.valueOf(Integer.parseInt(node.left.value) * Integer.parseInt(node.right.value));
                node.type = Node.Type.NUMBER;
                System.out.println(node.value);
                if (node.left.type != Node.Type.NUMBER || node.right.type != Node.Type.NUMBER) {
                    System.out.println("Error multi" );
                    System.out.println(node.left.value);
                    System.out.println(node.right.value);
                    throw new RuntimeException("Error: Operands must be numbers.");
                }
                break;
            case DIVIDE:
                analizar(node.left);
                analizar(node.right);
                node.value = String.valueOf(Integer.parseInt(node.left.value) / Integer.parseInt(node.right.value));
                node.type = Node.Type.NUMBER;
                System.out.println(node.value);
                if (node.left.type != Node.Type.NUMBER || node.right.type != Node.Type.NUMBER) {
                    throw new RuntimeException("Error: Operands must be numbers.");
                }
                break;
                default:
                    analizar(node.left);
                    analizar(node.right);
                    // Aquí podrías añadir más verificaciones semánticas si es necesario.
                    break;
        }

 */
    }
}
