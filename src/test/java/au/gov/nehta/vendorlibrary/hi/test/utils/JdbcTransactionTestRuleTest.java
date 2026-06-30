/*
 * Copyright 2011 NEHTA
 * Copyright 2021-2026 ADHA (Australian Digital Health Agency)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package au.gov.nehta.vendorlibrary.hi.test.utils;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Verifies {@link JdbcTransactionTestRule} rolls back DML so rows do not survive the test method.
 */
public class JdbcTransactionTestRuleTest {

    private static final String JDBC_URL = "jdbc:h2:mem:jdbc_tx_rule_verify;DB_CLOSE_DELAY=-1";

    @Rule
    public final JdbcTransactionTestRule jdbcTx =
            new JdbcTransactionTestRule(() -> {
                try {
                    return DriverManager.getConnection(JDBC_URL, "sa", "");
                } catch (SQLException e) {
                    throw new IllegalStateException(e);
                }
            });

    @BeforeClass
    public static void createCommittedSchema() throws Exception {
        try (Connection c = DriverManager.getConnection(JDBC_URL, "sa", "");
                Statement s = c.createStatement()) {
            s.execute("drop table if exists jdbc_tx_rule_t");
            s.execute("create table jdbc_tx_rule_t (id int primary key)");
        }
    }

    @AfterClass
    public static void dropSchema() throws Exception {
        try (Connection c = DriverManager.getConnection(JDBC_URL, "sa", "");
                Statement s = c.createStatement()) {
            s.execute("drop table if exists jdbc_tx_rule_t");
        }
    }

    @Test
    public void insertVisibleDuringTestThenRolledBack() throws Exception {
        Connection c = jdbcTx.connection();
        try (Statement st = c.createStatement()) {
            st.executeUpdate("insert into jdbc_tx_rule_t (id) values (4242)");
        }
        try (Statement st = c.createStatement();
                ResultSet rs = st.executeQuery("select count(*) from jdbc_tx_rule_t where id = 4242")) {
            rs.next();
            Assert.assertEquals(1, rs.getInt(1));
        }
    }

    @Test
    public void previousInsertNotVisibleAfterRollback() throws Exception {
        Connection c = jdbcTx.connection();
        try (Statement st = c.createStatement();
                ResultSet rs = st.executeQuery("select count(*) from jdbc_tx_rule_t where id = 4242")) {
            rs.next();
            Assert.assertEquals(0, rs.getInt(1));
        }
    }
}
