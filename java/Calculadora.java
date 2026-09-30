import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculadora extends JFrame {

    private JComboBox<String> comboVeiculos;
    private JTextField txtKmDia;

    public Calculadora() {
        setTitle("Calculadora - ECOTRACER");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // --- SEÇÃO DO COMBOBOX ---
        JLabel lblVeiculo = new JLabel("Selecione o veículo:");
        lblVeiculo.setFont(new Font("Arial", Font.PLAIN, 14));
        lblVeiculo.setAlignmentX(Component.CENTER_ALIGNMENT);

        String[] opcoes = {"Carro", "Moto", "Ônibus", "Bike"};
        comboVeiculos = new JComboBox<>(opcoes);
        comboVeiculos.setFont(new Font("Arial", Font.PLAIN, 16));
        comboVeiculos.setMaximumSize(new Dimension(250, 40));
        comboVeiculos.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- SEÇÃO DA PERGUNTA ---
        JLabel lblKmDia = new JLabel("Quantos km você percorre por dia?");
        lblKmDia.setFont(new Font("Arial", Font.PLAIN, 14));
        lblKmDia.setAlignmentX(Component.CENTER_ALIGNMENT);

        txtKmDia = new JTextField();
        txtKmDia.setFont(new Font("Arial", Font.PLAIN, 16));
        txtKmDia.setMaximumSize(new Dimension(250, 35));
        txtKmDia.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- BOTÃO DE CÁLCULO ---
        JButton btnCalcular = new JButton("Calcular Impacto");
        btnCalcular.setFont(new Font("Arial", Font.BOLD, 16));
        btnCalcular.setPreferredSize(new Dimension(220, 45));
        btnCalcular.setMaximumSize(new Dimension(250, 45));
        btnCalcular.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnCalcular.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processarCalculo();
            }
        });

        // --- MONTAGEM DA TELA ---
        painelPrincipal.add(lblVeiculo);
        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 5)));
        painelPrincipal.add(comboVeiculos);

        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 20)));

        painelPrincipal.add(lblKmDia);
        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 5)));
        painelPrincipal.add(txtKmDia);

        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 30)));
        painelPrincipal.add(btnCalcular);

        add(painelPrincipal);
    }

    private void processarCalculo() {
        String textoKm = txtKmDia.getText().trim();

        // 1. Validação de campo vazio
        if (textoKm.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, insira a quantidade de quilômetros diários.",
                    "Entrada Invalida",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Permite digitação com ponto ou vírgula
            double kmDia = Double.parseDouble(textoKm.replace(',', '.'));

            // 2. Validação de número negativo
            if (kmDia < 0) {
                JOptionPane.showMessageDialog(this,
                        "O valor em km não pode ser negativo.",
                        "Valor Invalido",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            String veiculo = (String) comboVeiculos.getSelectedItem();

            // Abre a tela com os resultados
            TelaResultado telaResultado = new TelaResultado(veiculo, kmDia);
            telaResultado.setVisible(true);
            dispose();

        } catch (NumberFormatException ex) {
            // 3. Validação de formato incorreto (digitação de texto/letras)
            JOptionPane.showMessageDialog(this,
                    "Por favor, digite um valor numérico válido (ex: 15.5).",
                    "Erro de Formatação",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}