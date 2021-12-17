package org.japo.java.entities;

import java.io.Serializable;

/**
 *
 * @author JAPO Labs - japolabs@gmail.com
 */
public final class ParametrosListado implements Serializable {

    private Usuario user;
    private String table;
    private String filterFld;
    private String filterExp;
    private String sortFld;
    private String sortDir;
    private Long rowCount;
    private Long rowsPage;
    private Long rowIndex;

    public ParametrosListado() {
    }

    public ParametrosListado(
            Usuario user,
            String table,
            String filterFld, String filterExp,
            String sortFld, String sortDir,
            Long rowCount, Long rowsPage, Long rowIndex) {
        this.user = user;
        this.table = table;
        this.filterFld = filterFld;
        this.filterExp = filterExp;
        this.sortFld = sortFld;
        this.sortDir = sortDir;
        this.rowCount = rowCount;
        this.rowsPage = rowsPage;
        this.rowIndex = rowIndex;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getFilterFld() {
        return filterFld;
    }

    public void setFilterFld(String filterFld) {
        this.filterFld = filterFld;
    }

    public String getFilterExp() {
        return filterExp;
    }

    public void setFilterExp(String filterExp) {
        this.filterExp = filterExp;
    }

    public String getSortFld() {
        return sortFld;
    }

    public void setSortFld(String sortFld) {
        this.sortFld = sortFld;
    }

    public String getSortDir() {
        return sortDir;
    }

    public void setSortDir(String sortDir) {
        this.sortDir = sortDir;
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
