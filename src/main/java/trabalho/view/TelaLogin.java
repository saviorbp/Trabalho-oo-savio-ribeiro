package trabalho.view;

import javax.swing.*;

import trabalho.controller.GerenciadorSessao;
import trabalho.persistence.UsuarioPersistence;
import trabalho.controller.ValidarUsuario;
import trabalho.exception.ValidacaoException;
import trabalho.model.Usuarios.Usuario;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaLogin extends JFrame {
    private final int WIDTH = 300;
    private final int HEIGHT = 200;
    private JTextField campoEmail;
    private JPasswordField campoSenha;
    private JButton botaoLogin;
    private JButton botaoCriarUsuario;

    public TelaLogin() {
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 2));

        JLabel labelEmail = new JLabel("Email:");
        campoEmail = new JTextField();

        JLabel labelSenha = new JLabel("Senha:");
        campoSenha = new JPasswordField();

        botaoLogin = new JButton("Login");
        botaoLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = campoEmail.getText();
                String senha = new String(campoSenha.getPassword());

                try {
                    ValidarUsuario.validarLogin(email, senha);
                    UsuarioPersistence usuarioPersistence = new UsuarioPersistence();
                    Usuario usuario = usuarioPersistence.findByEmail(email);

                    if (email != null && usuario.getSenha().equals(senha)) {
                        GerenciadorSessao.setUsuarioLogado(usuario);

                        new TelaTicket().setVisible(true);

                        dispose();
                    } else {
                        throw new ValidacaoException("Nome de usuário ou senha inválidos");
                    }
                } catch (ValidacaoException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro de validação",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        botaoCriarUsuario = new JButton("Criar Usuário");
        botaoCriarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TelaCriarUsuario TelaCriarUsuario = new TelaCriarUsuario();
                TelaCriarUsuario.exibir();
                setVisible(false);
            }
        });
        panel.add(labelEmail);
        panel.add(campoEmail);
        panel.add(labelSenha);
        panel.add(campoSenha);
        panel.add(botaoLogin);
        panel.add(botaoCriarUsuario);

        getContentPane().add(panel, BorderLayout.CENTER);
    }

    public void inicia() {
        setVisible(true);
    }

}