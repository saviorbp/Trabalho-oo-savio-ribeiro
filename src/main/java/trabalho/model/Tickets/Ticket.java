//Sávio Ribeiro de Barros Pereira                                      
//201976013                                                        
package trabalho.model.Tickets;

public class Ticket {
    private Number id;
    private String titulo;
    private String descricao;
    private Integer idUsuarioCriou;
    private Integer idUsuarioVinculado;
    private Categoria categoria;
    private Subcategoria subcategoria;

    public Ticket() {
    }

    public Number getId() {
        return id;
    }

    public void setId(Number id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getIdUsuarioCriou() {
        return idUsuarioCriou;
    }

    public void setIdUsuarioCriou(Integer idUsuarioCriou) {
        this.idUsuarioCriou = idUsuarioCriou;
    }

    public Integer getIdUsuarioVinculado() {
        return idUsuarioVinculado;
    }

    public void setIdUsuarioVinculado(Integer idUsuarioVinculado) {
        this.idUsuarioVinculado = idUsuarioVinculado;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = new Categoria();
        this.categoria.setId(categoria.getId());
        this.categoria.setNome(categoria.getNome());
    }

    public Subcategoria getSubcategoria() {
        return subcategoria;
    }

    public void setSubcategoria(Subcategoria subcategoria) {
        this.subcategoria = new Subcategoria();
        this.subcategoria.setId(subcategoria.getId());
        this.subcategoria.setNome(subcategoria.getNome());
    }
}