import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {
        setTitle("ECOTRACER");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        setLayout(new BorderLayout());

        // 1. TÍTULO (NORTH)
        JLabel texto = new JLabel("BEM VINDO AO ECOTRACER", JLabel.CENTER);
        texto.setFont(new Font("Arial", Font.BOLD, 22));

        JPanel painelTopo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelTopo.setBorder(BorderFactory.createEmptyBorder(40, 10, 0, 10));
        painelTopo.add(texto);
        add(painelTopo, BorderLayout.NORTH);

        // 2. ÍCONE NO CENTRO (CENTER)
        // Substitua "logo.png" pelo caminho da sua imagem
        ImageIcon iconeOriginal = new ImageIcon("logo.png"); 
        
        // Redimensiona a imagem para caber bem na janela (ex: 150x150)
        Image imagemRedimensionada = iconeOriginal.getImage().getScaledInstance(230, 300, Image.SCALE_SMOOTH);
        ImageIcon iconeFormatado = new ImageIcon(imagemRedimensionada);

        JLabel lblIcone = new JLabel(iconeFormatado, JLabel.CENTER);
        
        // Adiciona a imagem no centro
        add(lblIcone, BorderLayout.CENTER);

        // 3. BOTÃO NO RODAPÉ (SOUTH)
        JButton btnEntrar = new JButton("Entrar");
        btnEntrar.setFont(new Font("Arial", Font.PLAIN, 16)); 
        btnEntrar.setPreferredSize(new Dimension(200, 35));

        btnEntrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Calculadora novaTela = new Calculadora();
                novaTela.setVisible(true);
                dispose();
            }
        });

        JPanel painelRodaPe = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelRodaPe.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0));
        painelRodaPe.add(btnEntrar);

        add(painelRodaPe, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new TelaPrincipal().setVisible(true);
        });
    }
}