import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
        setTitle("Flood Fill - Animação");
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

        // Evento de clique para iniciar o Flood Fill animado
        imageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                iniciarFloodFillAnimado(e.getX(), e.getY(), Color.RED.getRGB());
            }
        });

        setSize(image.getWidth(), image.getHeight());
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void iniciarFloodFillAnimado(int x, int y, int novaCor) {
        int corOriginal = image.getRGB(x, y);

        // Verifique se a cor é preta (0x000000) antes de iniciar o Flood Fill
        if (corOriginal != 0x000000) return;

        pixelsParaPintar = new LinkedList<>();
        pixelsParaPintar.add(new Point(x, y));
        image.setRGB(x, y, novaCor); // Define o primeiro pixel antes de iniciar

        timer = new Timer(50, e -> preencherProximoPixel(corOriginal, novaCor)); // Atualiza a cada 50ms
        // Atualiza rapidamente
        timer.start();
    }


    private void preencherProximoPixel(int corOriginal, int novaCor) {
        if (pixelsParaPintar.isEmpty()) {
            timer.stop();
            return;
        }

        Point p = pixelsParaPintar.poll();
        int px = p.x, py = p.y;

        if (px < 0 || py < 0 || px >= image.getWidth() || py >= image.getHeight()) return;
        if (image.getRGB(px, py) != corOriginal) return;

        image.setRGB(px, py, novaCor);

        // Adiciona os próximos pixels apenas se ainda forem da cor original
        if (px + 1 < image.getWidth() && image.getRGB(px + 1, py) == corOriginal) {
            pixelsParaPintar.add(new Point(px + 1, py));
        }
        if (px - 1 >= 0 && image.getRGB(px - 1, py) == corOriginal) {
            pixelsParaPintar.add(new Point(px - 1, py));
        }
        if (py + 1 < image.getHeight() && image.getRGB(px, py + 1) == corOriginal) {
            pixelsParaPintar.add(new Point(px, py + 1));
        }
        if (py - 1 >= 0 && image.getRGB(px, py - 1) == corOriginal) {
            pixelsParaPintar.add(new Point(px, py - 1));
        }

        // Atualiza a imagem na interface
        imageLabel.setIcon(new ImageIcon(image));  // Força o JLabel a atualizar com a nova imagem
        imageLabel.repaint();
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FloodFillGUI("src/images/testeTrabalho.jpg"));
    }
}
