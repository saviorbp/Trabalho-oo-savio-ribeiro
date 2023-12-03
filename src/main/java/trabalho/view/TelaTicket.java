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
    botaoCriarTicket.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        new TelaCriarTicket().setVisible(true);
        dispose();
      }
    });
    add(botaoCriarTicket, BorderLayout.NORTH);

    List<Ticket> tickets = new TicketPersistence().findAll();

    DefaultListModel<Ticket> model = new DefaultListModel<>();
    for (Ticket ticket : tickets) {
      model.addElement(ticket);
    }
    JList<Ticket> listaTickets;
    listaTickets = new JList<Ticket>(model);

    listaTickets = new JList<>(model);
    listaTickets.setCellRenderer(new DefaultListCellRenderer() {
      @Override
      public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
          boolean cellHasFocus) {
        if (value instanceof Ticket) {
          value = ((Ticket) value).getTitulo();
        }
        return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
      }
    });

    add(new JScrollPane(listaTickets), BorderLayout.CENTER);

    JPanel painelBotoes = new JPanel();

    botaoExcluir = new JButton("Excluir");
    final JList<Ticket> finalListaTickets = listaTickets;
    botaoExcluir.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Ticket ticketSelecionado = finalListaTickets.getSelectedValue();
        if (ticketSelecionado != null) {
          int resposta = JOptionPane.showConfirmDialog(null, "Deseja excluir o ticket selecionado?", "Confirmação",
              JOptionPane.YES_NO_OPTION);
          if (resposta == JOptionPane.YES_OPTION) {
            try {
              model.removeElement(ticketSelecionado);

              List<Ticket> tickets = Collections.list(model.elements());
              new TicketPersistence().save(tickets);
            } catch (Exception ex) {
              throw new RuntimeException("Erro ao excluir o ticket", ex);
            }
          }
        } else {
          JOptionPane.showMessageDialog(null, "Nenhum ticket selecionado", "Erro", JOptionPane.ERROR_MESSAGE);
        }
      }
    });
    painelBotoes.add(botaoExcluir);

    botaoEditar = new JButton("Editar");
    botaoEditar.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Ticket ticketSelecionado = finalListaTickets.getSelectedValue();
        if (ticketSelecionado != null) {
          int resposta = JOptionPane.showConfirmDialog(null, "Deseja editar o ticket selecionado?", "Confirmação",
              JOptionPane.YES_NO_OPTION);
          if (resposta == JOptionPane.YES_OPTION) {
            // Abre a tela de edição de tickets
            new TelaEditarTicket(ticketSelecionado).setVisible(true);
          }
        } else {
          JOptionPane.showMessageDialog(null, "Nenhum ticket selecionado", "Erro", JOptionPane.ERROR_MESSAGE);
        }
      }
    });
    painelBotoes.add(botaoEditar);
    add(painelBotoes, BorderLayout.SOUTH);

    setVisible(true);
  }
}