package spring;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class MemberDao {
    final private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

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
        String query = "UPDATE member SET name = :name, password = :password"
                + " WHERE email = :email";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", member.getName());
        params.addValue("password", member.getPassword());
        params.addValue("email", member.getEmail());

        namedParameterJdbcTemplate.update(query, params);
    }

    public List<Member> selectAll() {
        String query = "SELECT * FROM member";

        return namedParameterJdbcTemplate.query(
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
    }

    public Integer count() {
        String query = "SELECT COUNT(*) FROM member";

        return namedParameterJdbcTemplate.queryForObject(
                query,
                new MapSqlParameterSource(),
                Integer.class
        );
    }
}
