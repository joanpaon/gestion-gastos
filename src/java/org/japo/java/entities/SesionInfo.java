package org.japo.java.entities;

import java.util.Date;

/**
 *
 * @author JAPO Labs - japolabs@gmail.com
 */
public class SesionInfo {
    private String sesionId;
    private String remoteHost;
    private Date startedAt;

    public SesionInfo(String sesionId, String remoteHost, Date startedAt) {
        this.sesionId = sesionId;
        this.remoteHost = remoteHost;
        this.startedAt = startedAt;
    }

    public String getSesionId() {
        return sesionId;
    }

    public void setSesionId(String sesionId) {
        this.sesionId = sesionId;
    }

    public String getRemoteHost() {
        return remoteHost;
    }

    public void setRemoteHost(String remoteHost) {
        this.remoteHost = remoteHost;
    }

    public Date getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Date startedAt) {
        this.startedAt = startedAt;
    }
    
    
}
