package trabalho.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

import trabalho.controller.ValidarUsuario;
import trabalho.exception.ValidacaoException;
import trabalho.model.PerfilUsuario;
import trabalho.model.Usuario;
import trabalho.persistence.UsuarioPersistence;

public class TelaCriarUsuario {
    private JFrame frame;
    private JTextField campoUsuario;
    private JPasswordField campoSenha;
    private JComboBox<PerfilUsuario> campoPerfil;

    public TelaCriarUsuario() {
        frame = new JFrame("Cadastro de Usuário");
        frame.setSize(300, 200);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4, 2));

        JLabel labelUsuario = new JLabel("Usuário:");
        campoUsuario = new JTextField();

        JLabel labelSenha = new JLabel("Senha:");
        campoSenha = new JPasswordField();

        JLabel labelPerfil = new JLabel("Perfil:");
        campoPerfil = new JComboBox<>(PerfilUsuario.values());

        JButton botaoCadastrar = new JButton("Cadastrar");
        botaoCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UsuarioPersistence usuarioPersistence = new UsuarioPersistence();
                try {
                    String nomeUsuario = campoUsuario.getText();
                    String senha = new String(campoSenha.getPassword());
                    PerfilUsuario perfil = (PerfilUsuario) campoPerfil.getSelectedItem();

                    List<Usuario> usuarios = usuarioPersistence.findAll();

                    ValidarUsuario.validarNomeUsuario(nomeUsuario);
                    ValidarUsuario.validarSenha(senha);
                    ValidarUsuario.validarUsuarioExistente(nomeUsuario, usuarios);

                    Usuario usuario = new Usuario();
                    usuario.setNomeUsuario(nomeUsuario);
                    usuario.setSenha(senha);
                    usuario.setPerfil(perfil);

                    usuarios.add(usuario);
                    usuarioPersistence.save(usuarios);

                    campoUsuario.setText("");
                    campoSenha.setText("");
                } catch (ValidacaoException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro de validação",
                            JOptionPane.ERROR_MESSAGE);
                }
                campoPerfil.setSelectedIndex(0);

                JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");

                frame.dispose();

                TelaLogin telaLogin = new TelaLogin();
                telaLogin.inicia();
                frame.setVisible(false);

            }
        });

        panel.add(labelUsuario);
        panel.add(campoUsuario);
        panel.add(labelSenha);
        panel.add(campoSenha);
        panel.add(labelPerfil);
        panel.add(campoPerfil);
        panel.add(botaoCadastrar);

        frame.getContentPane().add(panel, BorderLayout.CENTER);
    }

    public void exibir() {
        frame.setVisible(true);
    }

    public String getNomeUsuario() {
        return campoUsuario.getText();
    }

    public String getSenha() {
        return new String(campoSenha.getPassword());
    }

    public PerfilUsuario getPerfil() {
        return (PerfilUsuario) campoPerfil.getSelectedItem();
    }

}