public enum Ansi {

    CLEAR("\u001B[H\u001B[2J"),
    RESET("\u001B[0m"),
    GREEN("\u001B[32m"),
    RED("\u001B[31m");

    private String codigo;

    Ansi(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }
}
    