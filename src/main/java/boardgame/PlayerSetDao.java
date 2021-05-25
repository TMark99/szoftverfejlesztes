package boardgame;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

@RegisterBeanMapper(Players.class)
public interface PlayerSetDao {

    @SqlUpdate("""
        CREATE TABLE playerset (
            id IDENTITY PRIMARY KEY,
            playerone VARCHAR NOT NULL,
            playertwo VARCHAR NOT NULL
        )
        """
    )
    void createTable();

    @SqlUpdate("INSERT INTO playerset (playerone, playertwo) VALUES (:playerone, :playertwo)")
    @GetGeneratedKeys
    long insertPlayers(@Bind("playerone") String playerone, @Bind("playertwo") String playertwo);

    @SqlUpdate("INSERT INTO playerset (playerone, playertwo) VALUES (:playerone, :playertwo)")
    @GetGeneratedKeys
    long insertPlayers(@BindBean Players playerSet);

    @SqlQuery("SELECT * FROM playerset")
    List<Players> listPlayerSets();
}
