import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

public class TelaResultado extends JFrame {

    // Constantes do Sistema
    private static final double FATOR_CARRO = 0.21;
    private static final double FATOR_MOTO = 0.11;
    private static final double FATOR_ONIBUS = 0.05;
    private static final double FATOR_BIKE = 0.00;

    private static final double ABSORCAO_ARVORE_ANO = 7.14;
    private static final int DIAS_UTEIS_MES = 22;
    private static final int DIAS_ANO = 365;

    public TelaResultado(String veiculo, double kmDia) {
        setTitle("Resultados de Impacto Ambiental");
        setSize(420, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Fator de emissão dinâmico
        double fatorEmissao = obterFatorEmissao(veiculo);

        // A. Cálculos de Emissão Acumulada
        double emissaoDiaria = kmDia * fatorEmissao;
        double emissaoMensal = emissaoDiaria * DIAS_UTEIS_MES;
        double emissaoAnual = emissaoDiaria * DIAS_ANO;

        // B. Neutralização
        double arvoresNecessarias = emissaoAnual / ABSORCAO_ARVORE_ANO;

        // Visualização da interface
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        // Título
        JLabel lblTitulo = new JLabel("RELATÓRIO DE IMPACTO CO2");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        painel.add(lblTitulo);
        painel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Resumo
        painel.add(criarLabelFormatado("<b>Veículo:</b> " + veiculo));
        painel.add(criarLabelFormatado("<b>Distância diária:</b> " + String.format("%.2f km", kmDia)));
        painel.add(Box.createRigidArea(new Dimension(0, 10)));
        painel.add(new JSeparator());
        painel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Emissões
        painel.add(criarLabelFormatado("<b>Emissão Diária:</b> " + String.format("%.2f kg CO₂", emissaoDiaria)));
        painel.add(criarLabelFormatado("<b>Emissão Mensal (22 dias):</b> " + String.format("%.2f kg CO₂", emissaoMensal)));
        painel.add(criarLabelFormatado("<b>Emissão Anual (365 dias):</b> " + String.format("%.2f kg CO₂", emissaoAnual)));
        painel.add(Box.createRigidArea(new Dimension(0, 10)));
        painel.add(criarLabelFormatado("<b>Árvores p/ Neutralizar (Ano):</b> " + String.format("%.1f árvores", arvoresNecessarias)));

        // Módulo Comparador ODS 13 (Apenas para Carro ou Moto e distância > 0)
        if ((veiculo.equalsIgnoreCase("Carro") || veiculo.equalsIgnoreCase("Moto")) && kmDia > 0) {
            painel.add(Box.createRigidArea(new Dimension(0, 15)));
            painel.add(new JSeparator());
            painel.add(Box.createRigidArea(new Dimension(0, 10)));

            JLabel lblOds = new JLabel("Dica de Impacto (ODS 13 - Ação Climática)");
            lblOds.setFont(new Font("Arial", Font.BOLD, 14));
            lblOds.setAlignmentX(Component.CENTER_ALIGNMENT);
            painel.add(lblOds);
            painel.add(Box.createRigidArea(new Dimension(0, 10)));

            // Simulação Rotina Mista: 3 dias veículo próprio + 2 dias ônibus
            double emissaoSemanalReduzida = (kmDia * fatorEmissao * 3) + (kmDia * FATOR_ONIBUS * 2);
            double emissaoAnualReduzida = (emissaoSemanalReduzida / 5.0) * DIAS_ANO;
            double economiaCO2 = emissaoAnual - emissaoAnualReduzida;

            painel.add(criarLabelFormatado("<i>Simulação (3 dias " + veiculo + " + 2 dias Ônibus/sem):</i>"));
            painel.add(criarLabelFormatado("<b>Emissão Anual Reduzida:</b> " + String.format("%.2f kg CO₂", emissaoAnualReduzida)));
            painel.add(criarLabelFormatado("<b>Redução Estimada:</b> <font color='green'>" + String.format("%.2f kg CO₂/ano", economiaCO2) + "</font>"));
        }

        painel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Botão Voltar
        JButton btnVoltar = new JButton("Novo Cálculo");
        btnVoltar.setFont(new Font("Arial", Font.PLAIN, 14));
        btnVoltar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnVoltar.addActionListener(e -> {
            new Calculadora().setVisible(true);
            dispose();
        });

        painel.add(btnVoltar);
        add(painel);
    }

    private double obterFatorEmissao(String veiculo) {
        switch (veiculo.toLowerCase()) {
            case "carro": return FATOR_CARRO;
            case "moto":  return FATOR_MOTO;
            case "ônibus":
            case "onibus": return FATOR_ONIBUS;
            default:       return FATOR_BIKE;
        }
    }

    private JLabel criarLabelFormatado(String htmlTexto) {
        JLabel label = new JLabel("<html>" + htmlTexto + "</html>");
        label.setFont(new Font("Arial", Font.PLAIN, 13));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }
}