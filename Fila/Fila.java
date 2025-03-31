class Fila {
    private Node inicio = null;
    private Node fim = null;

    public Fila() {
    }

    public void enfileirar(Pixel valor) {
        Node novoNo = new Node(valor);
        if (this.fim == null) {
            this.inicio = novoNo;
        } else {
            this.fim.proximo = novoNo;
        }

        this.fim = novoNo;
    }

    public Pixel desenfileirar() {
        if (this.inicio == null) {
            return null;
        } else {
            Pixel valorRemovido = this.inicio.valor;
            this.inicio = this.inicio.proximo;
            if (this.inicio == null) {
                this.fim = null;
            }

            return valorRemovido;
        }
    }

    public boolean estaVazia() {
        return this.inicio == null;
    }
}
