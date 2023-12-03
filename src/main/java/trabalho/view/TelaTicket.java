package trabalho.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;

import trabalho.controller.GerenciadorSessao;
import trabalho.model.Ticket;
import trabalho.model.Usuario;
import trabalho.persistence.TicketPersistence;
import trabalho.persistence.UsuarioPersistence;
import trabalho.view.TelaEditarUsuario;

public class TelaTicket extends JFrame {
  private final int WIDTH = 600;
  private final int HEIGHT = 350;
  private JList<Ticket> listaTickets = new JList<Ticket>();

  public TelaTicket() {
    setTitle("Home");
    setSize(WIDTH, HEIGHT);
    setLocationRelativeTo(null);
    setLayout(new BorderLayout());
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    JPanel ticketListPanel = desenhaLista();
    JPanel ticketFunctionsPanel = desenhaFuncaoTicket();
    JPanel userFunctionsPanel = desenhaFuncaoUsuario();

    JPanel leftPanel = new JPanel(new GridLayout(2, 1));
    leftPanel.add(ticketFunctionsPanel);
    leftPanel.add(userFunctionsPanel);

    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.add(ticketListPanel, BorderLayout.WEST);
    mainPanel.add(leftPanel, BorderLayout.EAST);

    getContentPane().add(mainPanel);

    pack();
    setVisible(true);
  }

  private JPanel desenhaLista() {
    JPanel painel = new JPanel();
    painel.setBorder(BorderFactory.createTitledBorder("Tickets"));
    painel.setPreferredSize(new Dimension(WIDTH / 2, HEIGHT));
    painel.setLayout(new BorderLayout());

    List<Ticket> tickets = new TicketPersistence().findAll();
    DefaultListModel<Ticket> model = new DefaultListModel<>();
    for (Ticket ticket : tickets) {
      model.addElement(ticket);
    }

    listaTickets = new JList<>(model);
    listaTickets.setCellRenderer(new DefaultListCellRenderer() {
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
    painel.add(new JScrollPane(listaTickets), BorderLayout.CENTER);

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
              deletaTicket(ticketSelecionado);
              atualizaListaTicket();
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
    return listaTickets.getSelectedValue();
  }

  public void deletaTicket(Ticket ticket) {
    DefaultListModel<Ticket> model = (DefaultListModel<Ticket>) listaTickets.getModel();
    model.removeElement(ticket);
  }

  public void atualizaListaTicket() {
    DefaultListModel<Ticket> model = (DefaultListModel<Ticket>) listaTickets.getModel();
    List<Ticket> tickets = Collections.list(model.elements());
    new TicketPersistence().save(tickets);
  }

  public void editaTicket(Ticket ticket) {
    new TelaEditarTicket(ticket).setVisible(true);
    dispose();
  }

  private JPanel desenhaFuncaoUsuario() {
    JPanel painel = new JPanel();
    painel.setBorder(BorderFactory.createTitledBorder("Funções do Usuário"));
    painel.setPreferredSize(new Dimension(WIDTH / 2, HEIGHT / 2));
    painel.setLayout(new BorderLayout());

    JPanel painelBotoes = new JPanel();
    JButton btnEditar;
    JButton btnDeletar;

    btnDeletar = new JButton("Deletar Usuário");
    btnDeletar.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        int resposta = JOptionPane.showConfirmDialog(null, "Deseja excluir o usuário?", "Confirmação",
            JOptionPane.YES_NO_OPTION);
        if (resposta == JOptionPane.YES_OPTION) {
          try {
            deletarUsuarioLogado();
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
        TelaEditarUsuario telaEditarUsuario = new TelaEditarUsuario();
        telaEditarUsuario.mostrar();
        dispose();
      }
    });

    JPanel botoes = new JPanel();
    botoes.add(btnEditar);
    botoes.add(btnDeletar);
    painelBotoes.add(botoes, BorderLayout.SOUTH);

    painel.add(painelBotoes, BorderLayout.SOUTH);

    return painel;
  }

  public void deletarUsuarioLogado() {
    Usuario usuarioLogado = GerenciadorSessao.getUsuarioLogado();
    UsuarioPersistence usuarioPersistence = new UsuarioPersistence();
    usuarioPersistence.removeUsuario(usuarioLogado);
    new TelaLogin().setVisible(true);
    dispose();
  }
}