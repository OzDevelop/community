package community.acceptance.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class AcceptanceTestTemplate {

    @Autowired
    private DatabaseCleanUp databaseCleanUp;

    @Autowired
    private DataLoader dataLoader;

    public void setUp() {
        databaseCleanUp.execute();
        dataLoader.loadData();
    }

    protected void cleanUp() {
        databaseCleanUp.execute();
    }

    protected void createUser(String email) {
        dataLoader.createUser(email);
    }

    protected String getEmailToken(String email) {
        return dataLoader.getEmailToken(email);
    }

    protected boolean isEmailVerified(String email) {
        return dataLoader.isEmailVerified(email);
    }

    protected Long getUserId(String email) {
        return dataLoader.getUserId(email);
    }
}
