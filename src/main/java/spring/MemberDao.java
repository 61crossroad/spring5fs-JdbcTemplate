package spring;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class MemberDao {
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public MemberDao(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public Member selectByEmail(String email) {
        String query = "SELECT * FROM member WHERE email = :email";

        MapSqlParameterSource params = new MapSqlParameterSource("email", email);

        List<Member> results = namedParameterJdbcTemplate.query(
                query,
                params,
                (rs, rowNum) -> {
                    Member member = new Member(
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("name"),
                            rs.getTimestamp("regdate").toLocalDateTime()
                    );
                    member.setId(rs.getLong("id"));

                    return member;
                }
        );

        return results.isEmpty() ? null : results.get(0);
    }

    public void insert(Member member) {
    }

    public void update(Member member) {
    }

    public List<Member> selectAll() {
        String query = "SELECT * FROM member";

        List<Member> results = namedParameterJdbcTemplate.query(
                query,
                (rs, rowNum) -> {
                    Member member = new Member(
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("name"),
                            rs.getTimestamp("regdate").toLocalDateTime()
                    );
                    member.setId(rs.getLong("id"));

                    return member;
                }
        ) ;

        return results;
    }
}
