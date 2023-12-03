package trabalho.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;

import trabalho.model.Ticket;
import trabalho.persistence.TicketPersistence;

public class TelaTicket extends JFrame {
  private final int WIDTH = 500;
  private final int HEIGHT = 200;
  private JButton botaoCriarTicket;
  private JList<Ticket> listaTickets = new JList<Ticket>();

  public TelaTicket() {
    setTitle("Tickets");
    setSize(WIDTH, HEIGHT);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    botaoCriarTicket = new JButton("Criar Ticket");
    botaoCriarTicket.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        new TelaCriarTicket().setVisible(true);
        dispose();
      }
    });
    add(botaoCriarTicket, BorderLayout.NORTH);

    desenhaLista();
    desenhaFormulario();

    pack();
    setVisible(true);
  }

  private void desenhaLista() {
    JPanel painel = new JPanel();
    painel.setBorder(BorderFactory.createTitledBorder("Tickets"));
    painel.setPreferredSize(new Dimension(WIDTH / 3, HEIGHT));
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

    getContentPane().add(painel, BorderLayout.WEST);
  }

  public Ticket getTicketSelecionado() {
    return listaTickets.getSelectedValue();
  }

  private void desenhaFormulario() {
    JPanel painel = new JPanel();
    painel.setBorder(BorderFactory.createTitledBorder("Funções"));
    JButton btnEditar;
    JButton btnDeletar;

    btnDeletar = new JButton("Deletar");
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
    painel.add(btnDeletar);

    btnEditar = new JButton("Editar");
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

    JPanel botoes = new JPanel();
    botoes.add(btnEditar);
    botoes.add(btnDeletar);

    painel.add(botoes, BorderLayout.SOUTH);

    getContentPane().add(painel, BorderLayout.CENTER);
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
}