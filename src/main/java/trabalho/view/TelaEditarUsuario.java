//Sávio Ribeiro de Barros Pereira                                    
//201976013                                                           
package trabalho.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import trabalho.exception.ValidacaoException;
import trabalho.exception.ValidarUsuario;
import trabalho.model.Usuarios.PerfilUsuario;
import trabalho.model.Usuarios.Usuario;
import trabalho.persistence.UsuarioPersistence;

public class TelaEditarUsuario {
    private JFrame frame;
    private JTextField campoNome;
    private JTextField campoSobrenome;
    private JTextField campoEmail;
    private JTextField campoCpf;
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
        JPanel panel = new JPanel(new GridLayout(7, 2));

        JLabel labelNome = new JLabel("Nome:");
        campoNome = new JTextField(usuarioLogado.getNome());

        JLabel labelSobrenome = new JLabel("Sobrenome:");
        campoSobrenome = new JTextField(usuarioLogado.getSobrenome());

        JLabel labelEmail = new JLabel("Email:");
        campoEmail = new JTextField(usuarioLogado.getEmail());

        JLabel labelCpf = new JLabel("CPF:");
        campoCpf = new JTextField(usuarioLogado.getCpf());

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
                    Usuario novoUsuario = new Usuario();
                    boolean isEdited = false;

                    String nome = campoNome.getText();
                    if (!nome.equals(usuarioLogado.getNome())) {
                        ValidarUsuario.validarNome(nome);
                        isEdited = true;
                    }

                    String sobrenome = campoSobrenome.getText();
                    if (!sobrenome.equals(usuarioLogado.getSobrenome())) {
                        ValidarUsuario.validarSobrenome(sobrenome);
                        isEdited = true;
                    }

                    String email = campoEmail.getText();
                    if (!email.equals(usuarioLogado.getEmail())) {
                        ValidarUsuario.validarEmail(email);
                        isEdited = true;
                    }

                    String cpf = campoCpf.getText();
                    if (!cpf.equals(usuarioLogado.getCpf())) {
                        ValidarUsuario.validarCpf(cpf);
                        isEdited = true;
                    }

                    String senha = new String(campoSenha.getPassword());
                    if (!senha.equals(usuarioLogado.getSenha())) {
                        ValidarUsuario.validarSenha(senha);
                        isEdited = true;
                    }

                    PerfilUsuario perfil = (PerfilUsuario) campoPerfil.getSelectedItem();
                    if (!perfil.equals(usuarioLogado.getPerfil())) {
                        isEdited = true;
                    }

                    if (isEdited) {
                        novoUsuario.setNome(nome);
                        novoUsuario.setSobrenome(sobrenome);
                        novoUsuario.setEmail(email);
                        novoUsuario.setCpf(cpf);
                        novoUsuario.setSenha(senha);
                        novoUsuario.setPerfil(perfil);
                        usuarioPersistence.update(usuarioLogado.getId(), novoUsuario);
                        frame.dispose();
                        TelaTicket telaTicket = new TelaTicket();
                        telaTicket.setVisible(true);

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

        panel.add(labelNome);
        panel.add(campoNome);
        panel.add(labelSobrenome);
        panel.add(campoSobrenome);
        panel.add(labelEmail);
        panel.add(campoEmail);
        panel.add(labelCpf);
        panel.add(campoCpf);
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