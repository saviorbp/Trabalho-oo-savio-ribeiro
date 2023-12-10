//Sávio Ribeiro de Barros Pereira                                     
//201976013                                                             
package trabalho.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import trabalho.controller.GerenciadorSessao;
import trabalho.exception.ValidaTicket;
import trabalho.model.Tickets.Ticket;
import trabalho.model.Usuarios.Usuario;
import trabalho.persistence.TicketPersistence;
import trabalho.persistence.UsuarioPersistence;

public class TelaEditarTicket extends JFrame {
    private JTextField campoTitulo;
    private JComboBox<String> seletorUsuario;
    private JTextArea campoDescricao;
    private JButton botaoSalvar;
    private JButton botaoCancelar;
    private TicketPersistence ticketPersistence;

    public TelaEditarTicket(Ticket ticket) {
        ticketPersistence = new TicketPersistence();
        setTitle("Editar Ticket");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(7, 2));

        add(new JLabel("Título:"));
        campoTitulo = new JTextField(ticket.getTitulo());
        add(campoTitulo);

        add(new JLabel("Vincular Usuario:"));
        UsuarioPersistence usuarioPersistence = new UsuarioPersistence();
        List<Usuario> usuarios = usuarioPersistence.findAllExceptLoggedIn();
        seletorUsuario = new JComboBox<>();
        for (Usuario usuario : usuarios) {
            seletorUsuario.addItem(usuario.getNome());
        }
        Usuario usuarioVinculado = usuarioPersistence.findById(ticket.getIdUsuarioVinculado());

        if (usuarioVinculado != null) {
            seletorUsuario.setSelectedItem(usuarioVinculado.getNome());
        }
        add(seletorUsuario);

        add(new JLabel("Categoria:"));
        JLabel labelCategoria = new JLabel(ticket.getCategoria().getNome());

        add(labelCategoria);

        add(new JLabel("Subcategoria:"));
        JLabel labelSubcategoria = new JLabel(ticket.getSubcategoria().getNome());

        add(labelSubcategoria);

        add(new JLabel("Descrição:"));
        campoDescricao = new JTextArea(ticket.getDescricao());
        add(campoDescricao);

        botaoSalvar = new JButton("Salvar");
        botaoSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ticket.setTitulo(campoTitulo.getText());
                    ticket.setDescricao(campoDescricao.getText());
                    ticket.setIdUsuarioCriou(GerenciadorSessao.getIdUsuarioLogado());
                    ticket.setIdUsuarioVinculado(usuarios.get(seletorUsuario.getSelectedIndex()).getId());

                    ValidaTicket.validaTitulo(ticket.getTitulo());
                    ValidaTicket.validaDescricao(ticket.getDescricao());
                    ValidaTicket.validaUsuarioVinculado(ticket.getIdUsuarioVinculado());

                    ticketPersistence.update(ticket.getId(), ticket);

                    new TelaTicket().setVisible(true);
                    dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao editar o ticket: " + ex.getMessage(), "Erro",
                            JOptionPane.ERROR_MESSAGE);
                }
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