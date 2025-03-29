import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import javax.imageio.ImageIO;

public class FloodFillGUI extends JFrame {
    private BufferedImage image;
    private JLabel imageLabel;
    private Queue<Point> pixelsParaPintar;
    private Timer timer;

    public FloodFillGUI(String imagePath) {
        setTitle("Flood Fill - Automático");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Carregar imagem
        image = loadImage(imagePath);
        if (image == null) {
            System.out.println("Erro ao carregar a imagem.");
            System.exit(1);
        }

        // Criar JLabel com a imagem
        imageLabel = new JLabel(new ImageIcon(image));
        add(imageLabel, BorderLayout.CENTER);

        setSize(image.getWidth(), image.getHeight());
        setLocationRelativeTo(null);
        setVisible(true);

        // Inicia o Flood Fill automaticamente
        iniciarFloodFillAutomatico(Color.RED.getRGB());
    }

    private BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void iniciarFloodFillAutomatico(int novaCor) {
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                if (ehPreto(image.getRGB(x, y))) {
                    iniciarFloodFillAnimado(x, y, novaCor);
                    return;
                }
            }
        }
    }

    private void iniciarFloodFillAnimado(int x, int y, int novaCor) {
        int corOriginal = image.getRGB(x, y);
        if (!ehPreto(corOriginal)) return;

        pixelsParaPintar = new LinkedList<>();
        pixelsParaPintar.add(new Point(x, y));

        timer = new Timer(1, e -> preencherProximoPixel(novaCor));
        timer.start();
    }

    private boolean ehPreto(int cor) {
        int r = (cor >> 16) & 0xFF;
        int g = (cor >> 8) & 0xFF;
        int b = cor & 0xFF;
        return r < 30 && g < 30 && b < 30; // Considera tons próximos de preto
    }

    private void preencherProximoPixel(int novaCor) {
        if (pixelsParaPintar.isEmpty()) {
            timer.stop();
            return;
        }

        Point p = pixelsParaPintar.poll();
        int px = p.x, py = p.y;

        if (px < 0 || py < 0 || px >= image.getWidth() || py >= image.getHeight()) return;
        if (!ehPreto(image.getRGB(px, py))) return;

        image.setRGB(px, py, novaCor);

        pixelsParaPintar.add(new Point(px + 1, py));
        pixelsParaPintar.add(new Point(px - 1, py));
        pixelsParaPintar.add(new Point(px, py + 1));
        pixelsParaPintar.add(new Point(px, py - 1));

        imageLabel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FloodFillGUI("src/images/bolaVolei.jpg"));
    }
}
