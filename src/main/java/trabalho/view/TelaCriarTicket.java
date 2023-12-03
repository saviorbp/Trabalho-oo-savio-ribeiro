package trabalho.view;

import javax.swing.*;
import java.awt.*;

public class TelaCriarTicket extends JFrame {
    private JTextField campoTitulo;
    private JComboBox<String> seletorUsuario;
    private JTextArea campoDescricao;
    private JButton botaoSalvar;
    private JButton botaoCancelar;

    public TelaCriarTicket() {
        setTitle("Criar Ticket");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2));

        add(new JLabel("Título:"));
        campoTitulo = new JTextField();
        add(campoTitulo);

        add(new JLabel("Usuário:"));
        seletorUsuario = new JComboBox<>();
        // Adicione os nomes dos usuários ao seletorUsuario aqui
        add(seletorUsuario);

        add(new JLabel("Descrição:"));
        campoDescricao = new JTextArea();
        add(campoDescricao);

        botaoSalvar = new JButton("Salvar");
        add(botaoSalvar);

        botaoCancelar = new JButton("Cancelar");
        add(botaoCancelar);

        setVisible(true);
    }
}