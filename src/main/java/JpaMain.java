import jpql.Member;
import jpql.MemberDTO;
import jpql.Team;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUserName("member1");
            member.setAge(10);
            member.setTeam(team);


            em.persist(member);

            em.flush();
            em.clear();
            // desc == 역순
            String query = "select m from Member m inner join m.team t";
            List<Member> resultList = em.createQuery(query, Member.class)
                    .getResultList();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}

/*

            TypedQuery<String> query2 = em.createQuery("select m.userName from Member m", String.class); // 반환 타입이 명확할 때
            Query query3 = em.createQuery("select m.userName, m.age from Member m"); // 반환 타입이 명확하지 않을 때
 */