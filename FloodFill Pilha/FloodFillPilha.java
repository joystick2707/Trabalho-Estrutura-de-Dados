import java.awt.image.BufferedImage;

public class FloodFillPilha {
    public static void floodFill(BufferedImage image, int x, int y, int novaCor) {
        int corOriginal = image.getRGB(x, y);
        if (corOriginal == novaCor) return; // Evita preenchimento desnecessário

        int largura = image.getWidth();
        int altura = image.getHeight();

        Pilha pilha = new Pilha();
        pilha.insere(new Pixel(x, y));

        while (!pilha.estaVazia()) {
            Pixel p = pilha.remover();
            if (p == null) continue;
            int px = p.x, py = p.y;

            if (px < 0 || py < 0 || px >= largura || py >= altura) continue;
            if (image.getRGB(px, py) != corOriginal) continue;

            image.setRGB(px, py, novaCor);

            pilha.insere(new Pixel(px + 1, py));  // Direita
            pilha.insere(new Pixel(px - 1, py));  // Esquerda
            pilha.insere(new Pixel(px, py + 1));  // Abaixo
            pilha.insere(new Pixel(px, py - 1));  // Acima

            // Debug para verificar o comportamento
            System.out.println("Processando pixel: (" + px + ", " + py + ")");

        }
    }
}
