package com.marlin.tralp.Conexao.Repository;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by cfernandes on 27/05/2016.
 */
public interface RepositoryFactory {

    public MovementRepository getMovementRepository() throws IOException, SQLException;

}
