import jpql.Member;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Member member = new Member();
            member.setUserName("member1");
            member.setAge(10);
            em.persist(member);

            TypedQuery<Member> query = em.createQuery("select m from Member m where m.userName = :userName", Member.class);
            query.setParameter("userName", "member1");
            Member singleResult = query.getSingleResult();
            System.out.println("singleResult = " + singleResult.getUserName().gitignore);
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