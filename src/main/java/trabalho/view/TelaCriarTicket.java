package trabalho.view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import trabalho.controller.ValidaTicket;
import trabalho.model.Ticket;
import trabalho.model.Usuario;
import trabalho.persistence.UsuarioPersistence;
import trabalho.persistence.TicketPersistence;

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

        add(new JLabel("Vincular Usuario:"));
        UsuarioPersistence usuarioPersistence = new UsuarioPersistence();
        List<Usuario> usuarios = usuarioPersistence.findAllExceptLoggedIn();
        System.out.println(usuarios);
        seletorUsuario = new JComboBox<>();
        for (Usuario usuario : usuarios) {
            seletorUsuario.addItem(usuario.getNomeUsuario());
        }
        add(seletorUsuario);

        add(new JLabel("Descrição:"));
        campoDescricao = new JTextArea();
        add(campoDescricao);

        botaoSalvar = new JButton("Salvar");
        botaoSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ticket ticket = new Ticket();
                ticket.setId(new TicketPersistence().getNextId()); 
                ticket.setTitulo(campoTitulo.getText());
                ticket.setDescricao(campoDescricao.getText());
                ticket.setUsuario(usuarios.get(seletorUsuario.getSelectedIndex()));

                ValidaTicket.validaTitulo(ticket.getTitulo());
                ValidaTicket.validaDescricao(ticket.getDescricao());
                ValidaTicket.validaUsuario(ticket.getUsuario());

                List<Ticket> tickets = new TicketPersistence().findAll();
                tickets.add(ticket);
                new TicketPersistence().save(tickets);

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