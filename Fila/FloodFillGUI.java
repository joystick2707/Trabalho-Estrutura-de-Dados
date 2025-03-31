import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class FloodFillGUI extends JFrame {
    private BufferedImage image;
    private JLabel imageLabel;

    public FloodFillGUI(String imagePath) {
        this.setTitle("Flood Fill - Automático");
        this.setDefaultCloseOperation(3);
        this.setLayout(new BorderLayout());
        this.image = ImageLoader.loadImage(imagePath);
        if (this.image == null) {
            System.out.println("Erro ao carregar a imagem.");
            System.exit(1);
        }

        this.imageLabel = new JLabel(new ImageIcon(this.image));
        this.add(this.imageLabel, "Center");
        this.setSize(this.image.getWidth(), this.image.getHeight());
        this.setLocationRelativeTo((Component)null);
        this.setVisible(true);
        this.iniciarFloodFill(Color.BLUE.getRGB());
        ImageSaver.saveImage(this.image, "output.png");
    }

    private void iniciarFloodFill(int novaCor) {
        (new Thread(() -> {
            int delay = 1;

            for(int y = 0; y < this.image.getHeight(); ++y) {
                for(int x = 0; x < this.image.getWidth(); ++x) {
                    if (this.ehPreto(this.image.getRGB(x, y))) {
                        FloodFillFila.floodFill(this.image, x, y, novaCor);
                        SwingUtilities.invokeLater(() -> {
                            this.imageLabel.repaint();
                        });

                        try {
                            Thread.sleep(1L);
                        } catch (InterruptedException var6) {
                            InterruptedException e = var6;
                            e.printStackTrace();
                        }
                    }
                }
            }

        })).start();
    }

    private boolean ehPreto(int cor) {
        int r = cor >> 16 & 255;
        int g = cor >> 8 & 255;
        int b = cor & 255;
        return r < 100 && g < 100 && b < 100;
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new FloodFillGUI("/Users/bryan/Downloads/Trabalho-Estrutura-de-Dados-main 2/Fila/images/bolavolei3.jpg");
        });
    }
}
