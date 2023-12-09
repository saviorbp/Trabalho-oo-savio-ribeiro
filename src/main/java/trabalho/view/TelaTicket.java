package trabalho.view;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import trabalho.controller.GerenciadorSessao;
import trabalho.model.Tickets.Ticket;
import trabalho.model.Usuarios.Usuario;
import trabalho.persistence.TicketPersistence;
import trabalho.persistence.UsuarioPersistence;

public class TelaTicket extends JFrame {
  private final int WIDTH = 600;
  private final int HEIGHT = 350;
  private JList<Ticket> listaTicketsCriados;
  private JList<Ticket> listaTicketsVinculados;

  public TelaTicket() {
    setTitle("Home");
    setSize(WIDTH, HEIGHT);
    setLocationRelativeTo(null);
    setLayout(new BorderLayout());
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    Usuario usuarioLogado = GerenciadorSessao.getUsuarioLogado();

    JPanel ticketListPanel = desenhaListaTicketsCriados(usuarioLogado.getId());
    JPanel ticketVinculadoPanel = desenhaListaTicketsVinculados(usuarioLogado.getId());
    JPanel ticketFunctionsPanel = desenhaFuncaoTicket();
    JPanel userFunctionsPanel = desenhaFuncaoUsuario();

    JPanel leftPanel = new JPanel(new GridLayout(2, 1));
    leftPanel.add(ticketFunctionsPanel);
    leftPanel.add(userFunctionsPanel);

    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.add(ticketListPanel, BorderLayout.WEST);
    mainPanel.add(leftPanel, BorderLayout.CENTER);
    mainPanel.add(ticketVinculadoPanel, BorderLayout.EAST);

    getContentPane().add(mainPanel);

    pack();
    setVisible(true);
  }

  private JPanel desenhaListaTicketsCriados(int idUsuarioLogado) {
    JPanel painel = new JPanel();
    painel.setBorder(BorderFactory.createTitledBorder("Tickets Criados por " + GerenciadorSessao.getUsuarioLogado().getNome()));
    painel.setPreferredSize(new Dimension(WIDTH / 2, HEIGHT));
    painel.setLayout(new BorderLayout());

    List<Ticket> tickets = new TicketPersistence().findAll();
    List<Ticket> ticketsFiltrados = tickets.stream()
        .filter(ticket -> ticket.getIdUsuarioCriou() == idUsuarioLogado)
        .collect(Collectors.toList());

    DefaultListModel<Ticket> model = new DefaultListModel<>();
    for (Ticket ticket : ticketsFiltrados) {
      model.addElement(ticket);
    }

    listaTicketsCriados = new JList<>(model);
    listaTicketsCriados.setCellRenderer(new DefaultListCellRenderer() {
      @Override
      public Component getListCellRendererComponent(JList<?> list, Object value,
          int index, boolean isSelected,
          boolean cellHasFocus) {
        if (value instanceof Ticket) {
          value = ((Ticket) value).getTitulo();
        }
        return super.getListCellRendererComponent(list, value, index, isSelected,
            cellHasFocus);
      }
    });
    painel.add(new JScrollPane(listaTicketsCriados), BorderLayout.CENTER);

    listaTicketsCriados.addListSelectionListener(new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting() && listaTicketsCriados.getSelectedValue() != null) {
          listaTicketsVinculados.clearSelection();
        }
      }
    });
    return painel;
  }

  private JPanel desenhaListaTicketsVinculados(int idUsuarioLogado) {
    JPanel painel = new JPanel();
    painel.setBorder(BorderFactory.createTitledBorder("Tickets Vinculados a " + GerenciadorSessao.getUsuarioLogado().getNome()));
    painel.setPreferredSize(new Dimension(WIDTH / 2, HEIGHT));
    painel.setLayout(new BorderLayout());

    List<Ticket> tickets = new TicketPersistence().findAll();
    List<Ticket> ticketsFiltrados = tickets.stream()
        .filter(ticket -> ticket.getIdUsuarioVinculado() == idUsuarioLogado)
        .collect(Collectors.toList());
    DefaultListModel<Ticket> model = new DefaultListModel<>();
    for (Ticket ticket : ticketsFiltrados) {
      model.addElement(ticket);
    }

    listaTicketsVinculados = new JList<>(model);
    listaTicketsVinculados.setCellRenderer(new DefaultListCellRenderer() {
      @Override
      public Component getListCellRendererComponent(JList<?> list, Object value,
          int index, boolean isSelected,
          boolean cellHasFocus) {
        if (value instanceof Ticket) {
          value = ((Ticket) value).getTitulo();
        }
        return super.getListCellRendererComponent(list, value, index, isSelected,
            cellHasFocus);
      }
    });
    painel.add(new JScrollPane(listaTicketsVinculados), BorderLayout.CENTER);
    listaTicketsVinculados.addListSelectionListener(new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting() && listaTicketsVinculados.getSelectedValue() != null) {
          listaTicketsCriados.clearSelection();
        }
      }
    });
    return painel;
  }

  private JPanel desenhaFuncaoTicket() {
    JPanel painel = new JPanel();
    painel.setBorder(BorderFactory.createTitledBorder("Funções do Ticket"));
    painel.setPreferredSize(new Dimension(WIDTH / 2, HEIGHT / 2));
    painel.setLayout(new BorderLayout());

    JPanel painelBotoes = new JPanel();
    painelBotoes.setLayout(new GridLayout(2, 1));
    JButton btnEditar;
    JButton btnDeletar;
    JButton btnCriarTicket;

    btnDeletar = new JButton("Deletar Ticket");
    btnDeletar.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Ticket ticketSelecionado = getTicketSelecionado();
        if (ticketSelecionado != null) {
          int resposta = JOptionPane.showConfirmDialog(null, "Deseja excluir o ticket selecionado?", "Confirmação",
              JOptionPane.YES_NO_OPTION);
          if (resposta == JOptionPane.YES_OPTION) {
            try {
              deletaTicket(ticketSelecionado, listaTicketsCriados);
              deletaTicket(ticketSelecionado, listaTicketsVinculados);
              atualizaListaTicket(listaTicketsCriados);
              atualizaListaTicket(listaTicketsVinculados);
            } catch (Exception ex) {
              throw new RuntimeException("Erro ao excluir o ticket", ex);
            }
          }
        } else {
          JOptionPane.showMessageDialog(null, "Nenhum ticket selecionado", "Erro", JOptionPane.ERROR_MESSAGE);
        }
      }
    });

    btnEditar = new JButton("Editar Ticket");
    btnEditar.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Ticket ticketSelecionado = getTicketSelecionado();
        if (ticketSelecionado != null) {
          int resposta = JOptionPane.showConfirmDialog(null, "Deseja editar o ticket selecionado?", "Confirmação",
              JOptionPane.YES_NO_OPTION);
          if (resposta == JOptionPane.YES_OPTION) {
            editaTicket(ticketSelecionado);
          }
        } else {
          JOptionPane.showMessageDialog(null, "Nenhum ticket selecionado", "Erro",
              JOptionPane.ERROR_MESSAGE);
        }
      }
    });

    btnCriarTicket = new JButton("Criar Ticket");
    btnCriarTicket.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        new TelaCriarTicket().setVisible(true);
        dispose();
      }
    });

    JPanel botoes = new JPanel();
    botoes.add(btnEditar);
    botoes.add(btnDeletar);
    painelBotoes.add(botoes, BorderLayout.SOUTH);

    painel.add(btnCriarTicket, BorderLayout.NORTH);
    painel.add(painelBotoes, BorderLayout.SOUTH);

    return painel;
  }

  public Ticket getTicketSelecionado() {
    Ticket ticketCriadoSelecionado = listaTicketsCriados.getSelectedValue();

    if (ticketCriadoSelecionado != null) {
      return ticketCriadoSelecionado;
    }

    Ticket ticketVinculadoSelecionado = listaTicketsVinculados.getSelectedValue();

    if (ticketVinculadoSelecionado != null) {
      return ticketVinculadoSelecionado;
    }

    return null;
  }

  public void deletaTicket(Ticket ticket, JList<Ticket> lista) {
    DefaultListModel<Ticket> model = (DefaultListModel<Ticket>) lista.getModel();
    model.removeElement(ticket);
  }

  public void atualizaListaTicket(JList<Ticket> lista) {
    DefaultListModel<Ticket> model = (DefaultListModel<Ticket>) lista.getModel();
    List<Ticket> tickets = Collections.list(model.elements());
    new TicketPersistence().save(tickets);
  }

  public void editaTicket(Ticket ticket) {
    new TelaEditarTicket(ticket).setVisible(true);
    dispose();
  }

  private JPanel desenhaFuncaoUsuario() {
    JPanel painel = new JPanel();
    painel.setBorder(BorderFactory.createTitledBorder("Funções do Usuário   "));
    painel.setPreferredSize(new Dimension(WIDTH / 2, HEIGHT / 2));
    painel.setLayout(new BorderLayout());

    JPanel painelBotoes = new JPanel();
    painelBotoes.setLayout(new GridLayout(2, 1));
    JButton btnEditar;
    JButton btnDeletar;
    JButton btnLogout;

    btnDeletar = new JButton("Deletar Usuário");
    btnDeletar.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        int resposta = JOptionPane.showConfirmDialog(null, "Deseja excluir o usuário?", "Confirmação",
            JOptionPane.YES_NO_OPTION);
        if (resposta == JOptionPane.YES_OPTION) {
          try {
            UsuarioPersistence usuarioPersistence = new UsuarioPersistence();
            usuarioPersistence.deletarUsuarioLogado();
            new TelaLogin().setVisible(true);
            dispose();
          } catch (Exception ex) {
            throw new RuntimeException("Erro ao excluir o usuário", ex);
          }
        }
      }
    });

    btnEditar = new JButton("Editar Usuário");
    btnEditar.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Usuario usuarioLogado = GerenciadorSessao.getUsuarioLogado();
        System.out.println(usuarioLogado.getPerfil());

        TelaEditarUsuario telaEditarUsuario = new TelaEditarUsuario(usuarioLogado);
        telaEditarUsuario.exibir();
        dispose();
      }
    });

    btnLogout = new JButton("Logout");
    btnLogout.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        GerenciadorSessao.encerrarSessao();
        new TelaLogin().setVisible(true);
        dispose();
      }
    });

    JPanel botoes = new JPanel();
    botoes.add(btnEditar);
    botoes.add(btnDeletar);
    painelBotoes.add(botoes, BorderLayout.SOUTH);

    painel.add(btnLogout, BorderLayout.NORTH);
    painel.add(painelBotoes, BorderLayout.SOUTH);

    return painel;
  }

}