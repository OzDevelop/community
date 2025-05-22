package community.acceptance.utils;


import groovy.util.logging.Slf4j;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Table;
import java.util.List;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Profile("test")
@Component
@Slf4j
public class DatabaseCleanUp implements InitializingBean {

    @PersistenceContext
    private EntityManager em;
    private List<String> tableNames;
    private List<String> notGeneratedIdTableNames;

    @Override
    public void afterPropertiesSet() throws Exception {
        tableNames = em.getMetamodel().getEntities()
                .stream()
                .filter(entity -> entity.getJavaType().getAnnotation(Entity.class) != null)
                .map(entity -> entity.getJavaType().getAnnotation(Table.class).name())
                .toList();

        notGeneratedIdTableNames = List.of("community_user_auth","community_user_relation", "community_like");
    }

    //위 정보를 바탕으로 테이블 데이터를 지우는 메소드 구현
    @Transactional
    public void execute() {
        em.flush();  // 혹시 모를 수행안된 쿼리문을 플러시 처리
        // 테이블 데이터 삭제 전 REFERENTIAL_INTEGRITY을 FALSE로 설정해 설정값들이 있을 경우 삭제 제한이 걸리지 않게 변경하는 옵션
        em.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();
        for (String tableName : tableNames) {
            // TRUNCATE TABLE을 통해 테이블 데이터 삭제
            em.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate();
            if (!notGeneratedIdTableNames.contains(tableName)) {
                // Auto increment를 사용하는 테이블들은 Id값을 1로 초기화
               em.createNativeQuery("ALTER TABLE " + tableName + " ALTER COLUMN ID RESTART WITH 1").executeUpdate();
            }
        }
        em.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
    }
}
