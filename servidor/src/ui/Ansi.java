package src.ui;

public enum Ansi {

    CLEAR("\u001B[2J\u001B[H"),
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
    