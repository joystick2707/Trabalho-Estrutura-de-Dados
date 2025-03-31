import java.awt.image.BufferedImage;

public class FloodFillFila {
    public FloodFillFila() {
    }

    public static void floodFill(BufferedImage image, int x, int y, int novaCor) {
        int corOriginal = image.getRGB(x, y);
        if (corOriginal != novaCor) {
            int largura = image.getWidth();
            int altura = image.getHeight();
            Fila fila = new Fila();
            fila.enfileirar(new Pixel(x, y));

            while(!fila.estaVazia()) {
                Pixel p = fila.desenfileirar();
                if (p != null) {
                    int px = p.x;
                    int py = p.y;
                    if (px >= 0 && py >= 0 && px < largura && py < altura && image.getRGB(px, py) == corOriginal) {
                        image.setRGB(px, py, novaCor);
                        fila.enfileirar(new Pixel(px + 1, py));
                        fila.enfileirar(new Pixel(px - 1, py));
                        fila.enfileirar(new Pixel(px, py + 1));
                        fila.enfileirar(new Pixel(px, py - 1));
                        System.out.println("Processando pixel: (" + px + ", " + py + ")");
                    }
                }
            }

        }
    }
}
