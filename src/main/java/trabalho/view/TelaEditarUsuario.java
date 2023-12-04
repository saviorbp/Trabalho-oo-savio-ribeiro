package trabalho.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import trabalho.controller.ValidarUsuario;
import trabalho.exception.ValidacaoException;
import trabalho.model.PerfilUsuario;
import trabalho.model.Usuario;
import trabalho.persistence.UsuarioPersistence;

public class TelaEditarUsuario {
    private JFrame frame;
    private JTextField campoUsuario;
    private JPasswordField campoSenha;
    private JComboBox<PerfilUsuario> campoPerfil;
    UsuarioPersistence usuarioPersistence = new UsuarioPersistence();
    List<Usuario> usuarios = usuarioPersistence.findAll();

    public TelaEditarUsuario(Usuario usuarioLogado) {
        frame = new JFrame("Edição de Usuário");
        frame.setSize(300, 200);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4, 2));

        JLabel labelUsuario = new JLabel("Usuário:");
        campoUsuario = new JTextField(usuarioLogado.getNomeUsuario());

        JLabel labelSenha = new JLabel("Senha:");
        campoSenha = new JPasswordField(usuarioLogado.getSenha());

        JLabel labelPerfil = new JLabel("Perfil:");
        campoPerfil = new JComboBox<>();
        for (PerfilUsuario perfil : PerfilUsuario.values()) {
            campoPerfil.addItem(perfil);
        }
        System.out.println(usuarioLogado.getPerfil());
        campoPerfil.setSelectedItem(usuarioLogado.getPerfil());

        JButton botaoSalvar = new JButton("Salvar");
        botaoSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UsuarioPersistence usuarioPersistence = new UsuarioPersistence();
                try {
                    String nomeUsuario = campoUsuario.getText();
                    String senha = new String(campoSenha.getPassword());
                    PerfilUsuario perfil = (PerfilUsuario) campoPerfil.getSelectedItem();

                    if (!nomeUsuario.equals(usuarioLogado.getNomeUsuario()) ||
                            !senha.equals(usuarioLogado.getSenha()) ||
                            !perfil.equals(usuarioLogado.getPerfil())) {

                        ValidarUsuario.validarNomeUsuario(nomeUsuario);
                        ValidarUsuario.validarSenha(senha);

                        Usuario usuarioAtualizado = new Usuario();
                        usuarioAtualizado.setId(usuarioLogado.getId());
                        usuarioAtualizado.setNomeUsuario(nomeUsuario);
                        usuarioAtualizado.setSenha(senha);
                        usuarioAtualizado.setPerfil(perfil);

                        usuarioPersistence.update(usuarioAtualizado.getId(), usuarioAtualizado);

                        JOptionPane.showMessageDialog(null, "Usuário atualizado com sucesso!");

                        TelaLogin telaLogin = new TelaLogin();
                        telaLogin.setVisible(true);
                    } else {
                        TelaTicket telaTicket = new TelaTicket();
                        telaTicket.setVisible(true);
                        frame.dispose();
                    }

                } catch (ValidacaoException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro de validação",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton botaoCancelar = new JButton("Cancelar");
        botaoCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                TelaTicket telaTicket = new TelaTicket();
                telaTicket.setVisible(true);
            }
        });

        panel.add(labelUsuario);
        panel.add(campoUsuario);
        panel.add(labelSenha);
        panel.add(campoSenha);
        panel.add(labelPerfil);
        panel.add(campoPerfil);
        panel.add(botaoSalvar);
        panel.add(botaoCancelar);

        frame.getContentPane().add(panel, BorderLayout.CENTER);
    }

    public void exibir() {
        frame.setVisible(true);
    }
}