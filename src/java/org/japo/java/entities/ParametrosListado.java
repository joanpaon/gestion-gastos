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
    private String dbName = "gestion_gastos";
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

    public ParametrosListado(String dbName, Usuario user) {
        this.dbName = dbName;
        this.user = user;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
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
