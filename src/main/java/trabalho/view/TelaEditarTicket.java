package trabalho.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import trabalho.controller.ValidaTicket;
import trabalho.model.Ticket;
import trabalho.model.Usuario;
import trabalho.persistence.UsuarioPersistence;
import trabalho.persistence.TicketPersistence;

public class TelaEditarTicket extends JFrame {
    private JTextField campoTitulo;
    private JComboBox<String> seletorUsuario;
    private JTextArea campoDescricao;
    private JButton botaoSalvar;
    private JButton botaoCancelar;

    public TelaEditarTicket(Ticket ticket) {
        setTitle("Editar Ticket");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2));

        add(new JLabel("Título:"));
        campoTitulo = new JTextField(ticket.getTitulo());
        add(campoTitulo);

        add(new JLabel("Vincular Usuario:"));
        UsuarioPersistence usuarioPersistence = new UsuarioPersistence();
        List<Usuario> usuarios = usuarioPersistence.findAllExceptLoggedIn();
        seletorUsuario = new JComboBox<>();
        for (Usuario usuario : usuarios) {
            seletorUsuario.addItem(usuario.getNomeUsuario());
        }
        seletorUsuario.setSelectedItem(ticket.getUsuario().getNomeUsuario());
        add(seletorUsuario);

        add(new JLabel("Descrição:"));
        campoDescricao = new JTextArea(ticket.getDescricao());
        add(campoDescricao);

        botaoSalvar = new JButton("Salvar");
        botaoSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ticket.setTitulo(campoTitulo.getText());
                ticket.setDescricao(campoDescricao.getText());
                ticket.setUsuario(usuarios.get(seletorUsuario.getSelectedIndex()));

                ValidaTicket.validaTitulo(ticket.getTitulo());
                ValidaTicket.validaDescricao(ticket.getDescricao());
                ValidaTicket.validaUsuario(ticket.getUsuario());

                new TicketPersistence().update(ticket);

                new TelaTicket().setVisible(true);
                dispose();
            }
        });
        add(botaoSalvar);

        botaoCancelar = new JButton("Cancelar");
        botaoCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TelaTicket().setVisible(true);
                dispose();
            }
        });
        add(botaoCancelar);

        setVisible(true);
    }
}