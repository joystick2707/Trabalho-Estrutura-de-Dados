import java.awt.image.BufferedImage;
import java.util.Stack;

public class FloodFillStack {

    public static void floodFill(BufferedImage image, int x, int y, int novaCor) {
        int corOriginal = image.getRGB(x, y);

        if (corOriginal == novaCor) return; // Evita preenchimento desnecessário

        int largura = image.getWidth();
        int altura = image.getHeight();

        Stack<Pixel> pilha = new Stack<>();
        pilha.push(new Pixel(x, y));

        while (!pilha.isEmpty()) {
            Pixel p = pilha.pop();
            int px = p.x, py = p.y;

            if (px < 0 || py < 0 || px >= largura || py >= altura) continue; // Fora da imagem
            if (image.getRGB(px, py) != corOriginal) continue; // Já pintado ou diferente

            image.setRGB(px, py, novaCor); // Pinta o pixel

            // Adiciona os vizinhos na pilha
            pilha.push(new Pixel(px + 1, py));
            pilha.push(new Pixel(px - 1, py));
            pilha.push(new Pixel(px, py + 1));
            pilha.push(new Pixel(px, py - 1));
        }
    }
}
