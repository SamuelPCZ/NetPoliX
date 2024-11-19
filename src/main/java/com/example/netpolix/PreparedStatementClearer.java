package com.example.netpolix;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class PreparedStatementClearer {

    private final DataSource dataSource;

    public PreparedStatementClearer(DataSource dataSource) {
        this.dataSource = dataSource;
        clearPreparedStatementCache();
    }

    @Scheduled(fixedRate = 3600000)
    public void clearPreparedStatementCache() {
        try (Connection connection = dataSource.getConnection()) {
            connection.prepareStatement("DISCARD ALL").execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
