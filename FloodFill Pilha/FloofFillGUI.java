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

        // Carregar imagem usando a classe ImageLoader
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
        setVisible(true);

        // Inicia o Flood Fill automaticamente
        iniciarFloodFill(Color.BLUE.getRGB());

        // Salva a imagem 
        ImageSaver.saveImage(image, "output.png");
    }

    private void iniciarFloodFill(int novaCor) {
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                if (ehPreto(image.getRGB(x, y))) {
                    // Realiza o Flood Fill para todos os pixels válidos
                    FloodFillPilha.floodFill(image, x, y, novaCor);
                    imageLabel.repaint();  // Pode ser chamado após o preenchimento completo
                    // Não use 'return' aqui, assim a execução continua
                }
            }
        }
    }

    private boolean ehPreto(int cor) {
        int r = (cor >> 16) & 0xFF;
        int g = (cor >> 8) & 0xFF;
        int b = cor & 0xFF;
        return r < 100 && g < 100 && b < 100;  // Pode ser ajustado para detectar mais tons escuros
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FloodFillGUI("Trabalho-Estrutura-de-Dados-main/FloodFill Pilha/images/bolaVolei2.png"));
    }
}
