public enum Valor {
    A(1), DOIS(2), TRES(3), QUATRO(4), CINCO(5), SEIS(6), SETE(7), OITO(8), NOVE(9), DEZ(10), J(10), Q(10), K(10);

    private int pontuacao;

    Valor(int valor) {
        this.pontuacao = valor;
    }

    public int getPontuacao() {
        return pontuacao;
    }
}
