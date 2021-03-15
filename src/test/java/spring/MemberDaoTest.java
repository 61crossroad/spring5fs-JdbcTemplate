package spring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
}
