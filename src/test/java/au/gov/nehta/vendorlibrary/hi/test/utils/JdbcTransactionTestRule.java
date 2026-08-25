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

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.sql.Connection;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Runs each test method with a JDBC {@link Connection} in a transaction and rolls back after the
 * method completes (success or failure). Use for tests that execute DML so the database is left
 * unchanged. The {@link Supplier} must return a new connection per test invocation (or one that
 * may be closed when the statement finishes). Not applicable to remote SOAP/registry calls.
 */
public final class JdbcTransactionTestRule implements TestRule {

    private final Supplier<Connection> connectionSupplier;
    private final ThreadLocal<Connection> current = new ThreadLocal<>();

    public JdbcTransactionTestRule(Supplier<Connection> connectionSupplier) {
        this.connectionSupplier = Objects.requireNonNull(connectionSupplier, "connectionSupplier");
    }

    /**
     * Connection for the active test; only valid on the Surefire thread while the test body runs.
     */
    public Connection connection() {
        Connection c = current.get();
        if (c == null) {
            throw new IllegalStateException("No JDBC transaction is active for this thread");
        }
        return c;
    }

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                try (Connection connection = connectionSupplier.get()) {
                    boolean originalAutoCommit = connection.getAutoCommit();
                    connection.setAutoCommit(false);
                    current.set(connection);
                    try {
                        base.evaluate();
                    } finally {
                        current.remove();
                        try {
                            connection.rollback();
                        } finally {
                            connection.setAutoCommit(originalAutoCommit);
                        }
                    }
                }
            }
        };
    }
}
