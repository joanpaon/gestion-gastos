package org.japo.java.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author JAPO Labs - japolabs@gmail.com
 */
public final class ParametrosListado implements Serializable {

    public static final long DEF_FILAS_PAGINA = 10;

    private Usuario user;
    private String userField;
    private String dbName = "gestion_gastos";
    private String table;
    private String filterField = "";
    private List<String> filterFields = new ArrayList<>();
    private String filterValue = "";
    private boolean filterStrict = false;
    private String orderField = "";
    private String orderAdvance = "";
    private String action = "ini";
    private int page = 0;
    private Long rowCount;
    private Long rowsPage = DEF_FILAS_PAGINA;
    private Long rowIndex = 0L;

    public ParametrosListado() {
    }

    public ParametrosListado(
            String dbName, String table,
            Usuario user, String userField,
            String filterField, String filterValue, boolean filterStrict,
            String orderField, String orderAdvance,
            Long rowCount, Long rowsPage, Long rowIndex) {
        this.dbName = dbName;
        this.table = table;
        this.user = user;
        this.userField = userField;
        this.filterField = filterField;
        this.filterValue = filterValue;
        this.filterStrict = filterStrict;
        this.orderField = orderField;
        this.orderAdvance = orderAdvance;
        this.rowCount = rowCount;
        this.rowsPage = rowsPage;
        this.rowIndex = rowIndex;
    }

    public ParametrosListado(String dbName, String table) {
        this.dbName = dbName;
        this.table = table;
    }

    public ParametrosListado(String dbName, String table, Usuario user) {
        this.dbName = dbName;
        this.table = table;
        this.user = user;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public String getUserField() {
        return userField;
    }

    public void setUserField(String userField) {
        this.userField = userField;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getFilterField() {
        return filterField;
    }

    public void setFilterField(String filterField) {
        this.filterField = filterField;
    }

    public List<String> getFilterFields() {
        return filterFields;
    }

    public void setFilterFields(List<String> filterFields) {
        this.filterFields = filterFields;
    }

    public String getFilterValue() {
        return filterValue;
    }

    public void setFilterValue(String filterValue) {
        this.filterValue = filterValue;
    }

    public boolean isFilterStrict() {
        return filterStrict;
    }

    public void setFilterStrict(boolean filterStrict) {
        this.filterStrict = filterStrict;
    }

    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    public String getOrderAdvance() {
        return orderAdvance;
    }

    public void setOrderAdvance(String orderAdvance) {
        this.orderAdvance = orderAdvance;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public Long getRowCount() {
        return rowCount;
    }

    public void setRowCount(Long rowCount) {
        this.rowCount = rowCount;
    }

    public Long getRowsPage() {
        return rowsPage;
    }

    public void setRowsPage(Long rowsPage) {
        this.rowsPage = rowsPage;
    }

    public Long getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(Long rowIndex) {
        this.rowIndex = rowIndex;
    }
}
