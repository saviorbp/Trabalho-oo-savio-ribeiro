package trabalho.view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import trabalho.controller.ValidaTicket;
import trabalho.model.Tickets.Categoria;
import trabalho.model.Tickets.InitCategoria;
import trabalho.model.Tickets.Subcategoria;
import trabalho.model.Tickets.Ticket;
import trabalho.model.Usuarios.PerfilUsuario;
import trabalho.model.Usuarios.Usuario;
import trabalho.persistence.UsuarioPersistence;
import trabalho.persistence.TicketPersistence;
import trabalho.controller.GerenciadorSessao;

public class TelaCriarTicket extends JFrame {
    private Map<PerfilUsuario, List<Categoria>> categoriasPorPerfil;
    private JComboBox<Categoria> seletorCategoria;
    private JTextField campoTitulo;
    private JComboBox<String> seletorUsuario;
    private JTextArea campoDescricao;
    private JButton botaoSalvar;
    private JButton botaoCancelar;

    public TelaCriarTicket() {
        setTitle("Criar Ticket");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 2));

        add(new JLabel("Título:"));
        campoTitulo = new JTextField();
        add(campoTitulo);

        add(new JLabel("Vincular Usuario:"));
        UsuarioPersistence usuarioPersistence = new UsuarioPersistence();
        List<Usuario> usuarios = usuarioPersistence.findAllExceptLoggedIn();
        System.out.println(usuarios);
        seletorUsuario = new JComboBox<>();
        for (Usuario usuario : usuarios) {
            seletorUsuario.addItem(usuario.getNome());
        }
        add(seletorUsuario);

        add(new JLabel("Categoria:"));
        seletorCategoria = new JComboBox<>();
        categoriasPorPerfil = new HashMap<>();
        InitCategoria initCategoria = new InitCategoria();
        List<Categoria> todasCategorias = initCategoria.getCategorias();
        Categoria categoriaZero = todasCategorias.get(0);

        categoriasPorPerfil.put(PerfilUsuario.ADMINISTRADOR,
                new ArrayList<>(Arrays.asList(categoriaZero, todasCategorias.get(1), todasCategorias.get(2),
                        todasCategorias.get(3))));
        categoriasPorPerfil.put(PerfilUsuario.FUNCIONARIO,
                new ArrayList<>(Arrays.asList(categoriaZero, todasCategorias.get(4), todasCategorias.get(5),
                        todasCategorias.get(6))));
        categoriasPorPerfil.put(PerfilUsuario.SUPERVISOR,
                new ArrayList<>(Arrays.asList(categoriaZero, todasCategorias.get(7), todasCategorias.get(8),
                        todasCategorias.get(9))));
        categoriasPorPerfil.put(PerfilUsuario.FINANCEIRO,
                new ArrayList<>(Arrays.asList(categoriaZero, todasCategorias.get(10), todasCategorias.get(11),
                        todasCategorias.get(12))));

        PerfilUsuario perfilUsuarioLogado = GerenciadorSessao.getPerfilUsuarioLogado();
        preencherCategorias(perfilUsuarioLogado);
        add(seletorCategoria);

        add(new JLabel("Subcategoria:"));
        JComboBox<String> seletorSubcategoria = new JComboBox<>();
        add(seletorSubcategoria);

        seletorCategoria.addItemListener(e -> {
            seletorSubcategoria.removeAllItems();

            Categoria categoriaSelecionada = (Categoria) seletorCategoria.getSelectedItem();

            for (String subcategoria : categoriaSelecionada.getSubcategorias()) {
                seletorSubcategoria.addItem(subcategoria);
            }
        });

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
                ticket.setIdUsuarioCriou(GerenciadorSessao.getIdUsuarioLogado());
                ticket.setIdUsuarioVinculado(usuarios.get(seletorUsuario.getSelectedIndex()).getId());
                ticket.setCategoria((Categoria) seletorCategoria.getSelectedItem());
                // String nomeSubcategoriaSelecionada = (String) seletorSubcategoria.getSelectedItem();
                // List<Subcategoria> todasSubcategorias = new InitCategoria().getSubcategorias();
                // Subcategoria subcategoriaSelecionada = null;
                // for (Subcategoria subcategoria : todasSubcategorias) {
                //     if (subcategoria.getNome().equals(nomeSubcategoriaSelecionada)) {
                //         subcategoriaSelecionada = subcategoria;
                //         break;
                //     }
                // }
                // if (subcategoriaSelecionada != null) {
                //     ticket.setSubcategoria(subcategoriaSelecionada);
                // } else {
                //     throw new RuntimeException("Subcategoria não encontrada");
                // }
                ValidaTicket.validaTitulo(ticket.getTitulo());
                ValidaTicket.validaDescricao(ticket.getDescricao());
                ValidaTicket.validaUsuarioVinculado(ticket.getIdUsuarioVinculado());

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

    public void preencherCategorias(PerfilUsuario perfilUsuario) {
        List<Categoria> categorias = categoriasPorPerfil.get(perfilUsuario);
        seletorCategoria.removeAllItems();
        for (Categoria categoria : categorias) {
            seletorCategoria.addItem(categoria);
        }
    }
}