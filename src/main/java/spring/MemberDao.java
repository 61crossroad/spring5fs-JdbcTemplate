package spring;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
        String query = "INSERT INTO member (email, password, name, regdate)"
                + " values (:email, :password, :name, :regdate)";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("email", member.getEmail());
        params.addValue("password", member.getPassword());
        params.addValue("name", member.getName());
        params.addValue("regdate", Timestamp.valueOf(member.getRegisterDateTime()));

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(
                query,
                params,
                keyHolder
        );

        Number keyValue = keyHolder.getKey();
        member.setId(keyValue.longValue());
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
        );
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
