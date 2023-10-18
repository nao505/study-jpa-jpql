import jpql.Member;
import jpql.MemberDTO;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            for (int i = 0; i < 100; i++) {
                Member member = new Member();
                member.setUserName("member" + i);
                member.setAge(i);
                em.persist(member);
            }

            em.flush();
            em.clear();
            // desc == 역순
            List<Member> resultList = em.createQuery("select m from Member m order by m.age desc", Member.class)
                    .setFirstResult(1)
                    .setMaxResults(10)
                    .getResultList();
            System.out.println("resultList.size() = " + resultList.size());
            for (Member member1 : resultList) {
                System.out.println("member1 = " + member1);
            }

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