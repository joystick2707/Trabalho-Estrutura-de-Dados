import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class FloodFillGUI extends JFrame {
    private BufferedImage image;
    private JLabel imageLabel;

    public FloodFillGUI(String imagePath) {
        setTitle("Flood Fill - Automático");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Carrega a imagem com a ImageLoader
        image = ImageLoader.loadImage(imagePath);
        if (image == null) {
            System.out.println("Erro ao carregar a imagem.");
            System.exit(1);
        }

        // Cria JLabel com a imagem
        imageLabel = new JLabel(new ImageIcon(image));
        add(imageLabel, BorderLayout.CENTER);

        setSize(image.getWidth(), image.getHeight());
        setLocationRelativeTo(null);
        setSize(500,500);
        setVisible(true);

        // Inicia o Flood Fill
        iniciarFloodFill(Color.BLUE.getRGB());

        // Salva a imagem
        ImageSaver.saveImage(image, "output.png");
    }

    private void iniciarFloodFill(int novaCor) {
        // Cria um novo thread para o Flood Fill, só para não bloquear a interface
        new Thread(() -> {
            final int delay = 1;

            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    if (ehPreto(image.getRGB(x, y))) {
                        // Preenche o pixel atual
                        FloodFillPilha.floodFill(image, x, y, novaCor);

                        // Atualiza a interface gráfica para mostrar a animacao de pintar
                        SwingUtilities.invokeLater(() -> imageLabel.repaint());

                        // Adiciona um  delay para fazer o preenchimento aparecer gradualmente
                        try {
                            Thread.sleep(delay);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();  // Inicia o thread para evitar bloquear a interface
    }

    private boolean ehPreto(int cor) {
        int r = (cor >> 16) & 0xFF;
        int g = (cor >> 8) & 0xFF;
        int b = cor & 0xFF;
        return r < 100 && g < 100 && b < 100;  // Pode ser ajustado para detectar mais tons escuros
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FloodFillGUI("/Users/bryan/Downloads/Trabalho-Estrutura-de-Dados-main/FloodFill Pilha/images/bolaVolei3.jpg"));
    }
}
