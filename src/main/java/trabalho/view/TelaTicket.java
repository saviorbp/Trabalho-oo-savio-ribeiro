package trabalho.view;

import javax.swing.*;
import java.awt.*;

public class TelaTicket extends JFrame {
    private JButton botaoCriarTicket;
    private JList<String> listaTickets;
    private JButton botaoExcluir;
    private JButton botaoEditar;

    public TelaTicket() {
        setTitle("Tickets");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        botaoCriarTicket = new JButton("Criar Ticket");
        add(botaoCriarTicket, BorderLayout.NORTH);

        listaTickets = new JList<>();
        // Adicione os tickets à listaTickets aqui
        add(new JScrollPane(listaTickets), BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel();
        botaoExcluir = new JButton("Excluir");
        painelBotoes.add(botaoExcluir);
        botaoEditar = new JButton("Editar");
        painelBotoes.add(botaoEditar);
        add(painelBotoes, BorderLayout.SOUTH);

        setVisible(true);
    }
}