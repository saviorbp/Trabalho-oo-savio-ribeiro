package trabalho.persistence;

import java.util.List;

public interface Persistence<Generic> {

    String DIRECTORY = "data";
    public void save(List<Generic> itens);
    public List<Generic> findAll();

}

