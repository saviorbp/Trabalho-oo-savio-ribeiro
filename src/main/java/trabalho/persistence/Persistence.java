package trabalho.persistence;

import java.util.List;

public interface Persistence<Usuario> {

    String DIRECTORY = "data";
    public void save(List<Usuario> itens);
    public List<Usuario> findAll();

}
