package com.snowm.global.lock;

public class MySqlDao extends BaseRMDBDao {

    private static final String DELETE_LOCK_SQL = "DELETE FROM lock_entity WHERE id = :id AND timedue < NOW()";
    private static final String TOUCH_LOCK_SQL = "UPDATE lock_entity SET timedue = DATE_ADD(NOW(), INTERVAL :timeout SECOND)"
            + " WHERE id = :id AND owner = :owner ";
    private static final String GET_LOCK_SQL = "SELECT id, owner, created_date, timedue, NOW() now"
            + " FROM lock_entity WHERE id = :id AND owner = :owner";
    private static final String ADD_LOCK_SQL = "INSERT INTO lock_entity (id, owner, created_date, timedue)"
            + " VALUES (:id, :owner, NOW(), DATE_ADD(NOW(), INTERVAL :timeout SECOND) )";
    private static final String DELETE_OWNED_LOCK_SQL = "DELETE FROM lock_entity WHERE id = :id AND owner = :owner";
    private static final String GET_SQL = "SELECT id, owner, created_date, timedue, NOW() now"
            + " FROM lock_entity WHERE id = :id";

    protected MySqlDao() {
        super();
    }

    @Override
    protected String generateDeleteLockSQL() {
        return DELETE_LOCK_SQL;
    }

    @Override
    protected String generateAddLockSQL() {
        return ADD_LOCK_SQL;
    }

    @Override
    protected String generateGetLockSQL() {
        return GET_LOCK_SQL;
    }

    @Override
    protected String generateTouchLockSQL() {
        return TOUCH_LOCK_SQL;
    }

    @Override
    protected String generateDeleteMyLockSQL() {
        return DELETE_OWNED_LOCK_SQL;
    }

    @Override
    protected String generateGetSQL() {
        return GET_SQL;
    }
}
