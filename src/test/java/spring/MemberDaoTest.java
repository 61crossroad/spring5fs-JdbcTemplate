package spring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class MemberDaoTest {
    @Autowired
    MemberDao memberDao;

    @Test
    public void testSelectAll() {
        List<Member> memberList = memberDao.selectAll();
        memberList.forEach(member -> System.out.println(member.getEmail() + " (" + member.getRegisterDateTime() + ")"));
    }

    @Test
    public void testSelectByEmail() {
        Member member = memberDao.selectByEmail("61crossroad@gmail.com");
        System.out.println(member);
    }

    @Test
    public void testUpdate() {
        Member member = new Member(
                "61crossroad@gmail.com",
                "1234",
                "ModifiedName",
                LocalDateTime.now()
        );
        memberDao.update(member);

        testSelectByEmail();
    }
}
