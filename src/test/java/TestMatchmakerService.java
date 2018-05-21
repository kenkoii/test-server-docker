import com.matchmaking.models.PlayerData;
import com.matchmaking.services.MatchService;
import com.matchmaking.services.MatchmakerService;
import com.matchmaking.services.SocketManager;
import com.matchmaking.services.SocketMessageManager;
import org.eclipse.jetty.websocket.api.*;
import org.junit.*;

import static org.mockito.Mockito.*;

public class TestMatchmakerService {

    MatchService matchmaking = MatchmakerService.getInstance();

    @Before
    public void Setup() {
        SocketManager socketManager = mock(SocketMessageManager.class);
        MatchmakerService.setSocketManager(socketManager);
    }

    @Test
    public void TestAddToMatchmaking() {
        //Arrange

        PlayerData player1 = new PlayerData("Philippines", "1", 1000, "Pantoy", 2000, 5, 5);
        PlayerData player2 = new PlayerData("Philippines", "1", 1000, "Kentoy", 2000, 5, 5);
        PlayerData player3 = new PlayerData("Philippines", "1", 1000, "Josh", 2000, 5, 5);

        Session session1 = mock(Session.class);
        Session session2 = mock(Session.class);
        Session session3 = mock(Session.class);


        //Assert if defaults are true
        Assert.assertTrue(matchmaking.GetMatchedPlayers().size() == 0);
        Assert.assertTrue(matchmaking.GetMatchmakingPool().size() == 0);

        //Add player
        matchmaking.AddToMatchmakingPool(session1, player1);

        //Assert if Matchmaking pool count increased by 1
        Assert.assertTrue(matchmaking.GetMatchmakingPool().size() == 1);
        Assert.assertTrue(matchmaking.GetMatchedPlayers().size() == 0);


        //Add another player
        matchmaking.AddToMatchmakingPool(session2, player2);

        //Assert if both players are removed from Matchmaking pool
        Assert.assertTrue(matchmaking.GetMatchmakingPool().size() == 0);
        //Assert if both players are moved to matchedPlayers
        Assert.assertTrue(matchmaking.GetMatchedPlayers().size() == 1);

        //Add another player
        matchmaking.AddToMatchmakingPool(mock(Session.class), player2);

        //Check if player just added is in matchmaking pool
        Assert.assertTrue(matchmaking.GetMatchmakingPool().size() == 1);

        //Add another player
        matchmaking.AddToMatchmakingPool(session3, player3);

        //Check again if moved to the right pool
        Assert.assertTrue(matchmaking.GetMatchmakingPool().size() == 0);
        Assert.assertTrue(matchmaking.GetMatchedPlayers().size() == 2);
    }

}