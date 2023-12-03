package trabalho.view;

import javax.swing.*;

import trabalho.controller.GerenciadorSessao;
import trabalho.model.Usuario;
import trabalho.controller.ValidarUsuario;
import trabalho.exception.ValidacaoException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaLogin {
    private JFrame frame;
    private JTextField campoUsuario;
    private JPasswordField campoSenha;
    private JButton botaoLogin;
    private JButton botaoCriarUsuario;

    public TelaLogin() {
        frame = new JFrame("Tela de Login");
        frame.setSize(300, 200);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 2));

        JLabel labelUsuario = new JLabel("Usuário:");
        campoUsuario = new JTextField();

        JLabel labelSenha = new JLabel("Senha:");
        campoSenha = new JPasswordField();

        botaoLogin = new JButton("Login");
        botaoLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nomeUsuario = campoUsuario.getText();
                    String senha = new String(campoSenha.getPassword());

                    ValidarUsuario.validarNomeUsuario(nomeUsuario);
                    ValidarUsuario.validarSenha(senha);

                    try {
                        ValidarUsuario.validar(nomeUsuario, senha);

                        Usuario usuario = new Usuario(nomeUsuario, senha, null);
                        GerenciadorSessao.setUsuarioLogado(usuario);

                        new TelaTicket().setVisible(true);

                        frame.dispose();
                    } catch (ValidacaoException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro de validação",
                                JOptionPane.ERROR_MESSAGE);
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
                frame.setVisible(false);
            }
        });
        panel.add(labelUsuario);
        panel.add(campoUsuario);
        panel.add(labelSenha);
        panel.add(campoSenha);
        panel.add(botaoLogin);
        panel.add(botaoCriarUsuario);

        frame.getContentPane().add(panel, BorderLayout.CENTER);
    }

    public void inicia() {
        frame.setVisible(true);
    }

}