package org.japo.java.entities;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URLConnection;
import java.util.Base64;
import java.util.Date;
import org.japo.java.libraries.UtilesValidacion;

/**
 *
 * @author JAPO Labs - japolabs@gmail.com
 */
public final class Usuario implements Serializable {

    // Expresiones regulares
    public static final String ER_USER = "[a-zA-ZáéíóúüÁÉÍÓÚÜñÑ\\-\\. ]{3,20}";
    public static final String ER_PASS = "[\\w]{3,20}";

    // Campos
    private int id;
    private String user;
    private String pass;
    private String email;
    private String icono;
    private int perfil;
    private String info;
    private int status;
    private String data;
    private Date createdAt;
    private Date updatedAt;

    public Usuario(int id, String user, String pass, String email,
            String icono, int perfil, String info,
            int status, String data, Date createdAt, Date updatedAt)
            throws Exception {
        // ID
        if (validarId(id)) {
            this.id = id;
        } else {
            throw new Exception();
        }

        // USER
        if (UtilesValidacion.validarDato(user, ER_USER)) {
            this.user = user;
        } else {
            throw new Exception();
        }

        // PASS
        if (UtilesValidacion.validarDato(pass, ER_PASS)) {
            this.pass = pass;
        } else {
            throw new Exception();
        }

        // EMAIL
        if (UtilesValidacion.validarEMail(email)) {
            this.email = email;
        } else {
            throw new Exception();
        }

        this.icono = icono;
        this.perfil = perfil;
        this.info = info;
        this.status = status;
        this.data = data;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public int getPerfil() {
        return perfil;
    }

    public void setPerfil(int perfil) {
        this.perfil = perfil;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public static final boolean validarId(int id) {
        return id >= 0;
    }

    public static final boolean validarIcono(String icono) {
        boolean checkOK;
        try {
            // Elimina Prefijo
            String strImg = icono.substring(icono.indexOf(',') + 1);

            // Base64 > Binario
            byte[] binImg = Base64.getDecoder().decode(strImg);

            // Binario > Cauce Lectura
            InputStream is = new ByteArrayInputStream(binImg);

            // Cauce Lectura > Tipo MIME
            String mimeType = URLConnection.guessContentTypeFromStream(is);

            // Tipo MIME > Semáforo
            checkOK = false
                    || mimeType.equals("image/jpeg")
                    || mimeType.equals("image/png")
                    || mimeType.equals("image/gif");
        } catch (IOException | NullPointerException e) {
            checkOK = false;
        }

        // Retorno: true | false
        return checkOK;
    }

    public static final boolean validarUsername(String dato) {
        return UtilesValidacion.validarDato(dato, ER_USER);
    }

    public static final boolean validarPassword(String dato) {
        return UtilesValidacion.validarDato(dato, ER_PASS);
    }
}
