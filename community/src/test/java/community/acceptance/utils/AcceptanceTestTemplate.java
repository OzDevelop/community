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
}
